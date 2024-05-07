package com.example.myapplication.database.table.setting;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settingdates")
public class SettingDate {

    @PrimaryKey(autoGenerate = true)
    private long settingId;      // 0: 오픈, 1: 마감, 2: 시간간격, 3: 휴무
    private String settingTime; // 0~2:시간.                    3: 1월,2화,3수,4목,5금,6토,7일

    public SettingDate(String settingTime){
        this.settingTime = settingTime;
    }

    public long getSettingId() {
        return settingId;
    }

    public void setSettingId(long settingId) {
        this.settingId = settingId;
    }

    public String getSettingTime() {
        return settingTime;
    }

    public void setSettingTime(String settingTime) {
        this.settingTime = settingTime;
    }
}