package com.example.myapplication.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

public class WatcherMoneyText implements TextWatcher {

    private OnSearchChangeListener mListener ;
    private Integer index;
    EditText editText;
    private DecimalFormat decimalFormat;


    public WatcherMoneyText(OnSearchChangeListener listener, int index, EditText editText){
        this.mListener = listener;
        this.index = index;
        this.editText = editText;
        this.decimalFormat = new DecimalFormat("#,###");
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 입력 전
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 입력 중
        if (mListener != null){
            mListener.onSearchTextChanged(s.toString(), index);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 입력된 텍스트 변경 후 호출
        editText.removeTextChangedListener(this);

        String originalText = s.toString();

        if (!originalText.isEmpty()) {
            long number = Long.parseLong(originalText.replaceAll(",", ""));
            String formattedText = decimalFormat.format(number);
            editText.setText(formattedText);
            editText.setSelection(formattedText.length()); // 커서를 끝으로 이동
        }

        editText.addTextChangedListener(this);
    }

    public interface OnSearchChangeListener{
        void onSearchTextChanged(String newText, Integer index);
    }

}
