package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userevents")
public class UserEvent {

    @PrimaryKey(autoGenerate = true)
    private int eventId;
    private int userId;
    private String eventType;
    private String eventName;

    public UserEvent(String eventType, String eventName) {
        this.eventType = eventType;
        this.eventName = eventName;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
