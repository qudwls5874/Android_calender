package com.example.myapplication.event;

import org.json.JSONObject;

public interface OnAutoCycleResultListener {

    void onProcessing(boolean status);
    void onTaskCompleted();

}
