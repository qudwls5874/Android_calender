package com.example.myapplication.astnctask;

import android.content.Context;

public class LoadMembersTask {

    private Context mContext;
    private OnTaskCompletedListener onOnTaskCompletedListener;

    public LoadMembersTask(Context _conContext){
        mContext = _conContext;
    }

    /* 결과값 반환 */
    public void setOnTaskCompletedListener(OnTaskCompletedListener _onOnTaskCompletedListener){
        this.onOnTaskCompletedListener = _onOnTaskCompletedListener;
    }

    public void requestTask(){
        //mLoadingDialog.show();
        try {
            // 3초간 대기
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        onOnTaskCompletedListener.onTaskCompleted("굿굿");
        //mLoadingDialog.dismiss();
    }

    /*
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // 비동기 작업이 시작되기 전에 로딩 다이얼로그를 표시합니다.
        mLoadingDialog.show();
    }

    @Override
    protected List<Member> doInBackground(Void... voids) {
        // 백그라운드에서 회원 정보를 로드하는 작업을 수행합니다.
        // 백그라운드 스레드에서 데이터를 가져오는 작업 수행
        try {
            // 3초간 대기
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Member> members) {
        super.onPostExecute(members);
        // 비동기 작업이 완료되면 로딩 다이얼로그를 숨깁니다.
        mLoadingDialog.dismiss();
        // 완료된 회원 정보를 리스너를 통해 콜백합니다.
        // mListener.onTaskCompleted(members);
    }
    */
}
