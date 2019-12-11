package com.server.calendarapp.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTTokenGenerator implements Serializable {


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;
    @Value("${jwt.secret}")
    private String secret;


    /**
     * @param token
     * @return string username from token
     */
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);

    }

    /**
     * @param token
     * @return date of token expiration
     */
    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);

    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);

    }


    /**
     * parse token with
     *
     * @param token string
     * @return all information from token
     */
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

    }


    /**
     * @param token string
     * @return bool value describing if token has expired or not
     */
    private Boolean isTokenExpired(String token) {

        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());

    }


    /**
     * generate token for user
     *
     * @param userDetails
     * @return token
     */
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();

        return doGenerateToken(claims, userDetails.getUsername());

    }


    /**
     * while creating the token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * compaction of the JWT to a URL-safe string
     *
     * @param claims
     * @param subject
     * @return
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }


    /**
     * validate token
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
