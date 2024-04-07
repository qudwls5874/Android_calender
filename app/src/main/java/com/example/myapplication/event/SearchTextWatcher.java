package com.example.myapplication.event;

import android.text.Editable;
import android.text.TextWatcher;

public class SearchTextWatcher implements TextWatcher {

    private OnSearchChangeListener mListener ;

    public SearchTextWatcher(OnSearchChangeListener listener){
        this.mListener = listener;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 입력 전
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 입력 중
        if (mListener != null){
            mListener.onSearchTextChanged(s.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 입력 후
    }

    public interface OnSearchChangeListener{
        void onSearchTextChanged(String newText);
    }

}
