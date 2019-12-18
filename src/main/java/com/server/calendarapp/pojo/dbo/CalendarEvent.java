package com.server.calendarapp.pojo.dbo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "UserCalendar")
public class CalendarEvent {


    private String eventID;
    private String eventName;
    private String userID;
    private String startTime;
    private String endTime;
    private String description;


    @Id
    @DynamoDBHashKey
    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }


    @DynamoDBAttribute(attributeName = "eventName")
    public String getEventName() {
        return eventName;
    }

    public CalendarEvent setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    @DynamoDBAttribute(attributeName = "userID")
    public String getUserID() {
        return userID;
    }

    public CalendarEvent setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    @DynamoDBAttribute(attributeName = "startTime")
    public String getStartTime() {
        return startTime;
    }


    public CalendarEvent setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    @DynamoDBAttribute(attributeName = "endTime")
    public String getEndTime() {
        return endTime;
    }

    public CalendarEvent setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public CalendarEvent setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CalendarEvent{");
        sb.append("eventID='").append(eventID).append('\'');
        sb.append(", eventName='").append(eventName).append('\'');
        sb.append(", userID='").append(userID).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarEvent that = (CalendarEvent) o;
        return eventID.equals(that.eventID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventID);
    }
}
