package com.example.myapplication.event;

import android.content.Context;
import android.view.View;

import org.json.JSONObject;

public class RequestAsyncTask {

    private Context context;
    private OnAutoCycleResultListener onAutoCycleResultListener;
    private String receiveTaskID = "";

    public void setOnAutoCycleResultListener(OnAutoCycleResultListener listener) {
        this.onAutoCycleResultListener = listener;
    }

    public RequestAsyncTask(Context context) {
        this.context = context;
    }

    public void requestTask(final View displayView, final String message, final String functionCd, final JSONObject parameters) {
        onAutoCycleResultListener.onProcessing(CommonDialog.isProgressBarShowing());

        startInfinityTask(displayView, message, functionCd, parameters);
    }

    private void startInfinityTask(View displayView, String message, String functionCd, JSONObject parameters) {
        // 비동기 작업 처리
        // 작업이 완료되면 onTaskCompleted 메서드 호출
    }

    private void stopInfinityTask() {
        // 작업 중단 처리
    }

    private void setReceiveTaskID(String taskId) {
        this.receiveTaskID = taskId;
    }

    public String getReceiveTaskID() {
        return receiveTaskID;
    }

    private void onTaskCompleted() {
        // 작업 완료 후 처리할 작업
        onAutoCycleResultListener.onTaskCompleted();
    }


}
