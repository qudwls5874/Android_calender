package com.example.myapplication.database.table.user;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.myapplication.database.table.User;

@Entity(tableName = "userevents",
        foreignKeys = @ForeignKey(  entity = User.class,
                                    parentColumns = "userId",
                                    childColumns = "userId",
                                    onDelete = ForeignKey.CASCADE),
        primaryKeys = {"eventId", "userId"})
public class UserEvent {

    private long eventId;
    private long userId;
    private String eventName;
    private String eventContent;

    public UserEvent(String eventName, String eventContent) {
        this.eventName = eventName;
        this.eventContent = eventContent;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
