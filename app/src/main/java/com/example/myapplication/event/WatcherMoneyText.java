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

    public WatcherMoneyText(){}

    // 어댑터 인덱스 필요할때
    public WatcherMoneyText(OnSearchChangeListener listener, int index, EditText editText){
        this.mListener = listener;
        this.index = index;
        this.editText = editText;
        this.decimalFormat = new DecimalFormat("#,###");
    }

    // 어댑터 인덱스 필요할때
    public WatcherMoneyText(EditText editText){
        this.editText = editText;
        this.decimalFormat = new DecimalFormat("#,###");
    }

    // 텍스트뷰 생성자 & 콤마찍기 . 입력된 텍스트에서 콤마를 제거하고, 숫자를 콤마가 포함된 형식으로 다시 포맷팅합니다
    public String beforeMoneyTextChanged(String text){
        if (!text.isEmpty()) {
            long number = Long.parseLong(text.replaceAll(",", ""));
            text = new DecimalFormat("#,###").format(number);
        }
        return text;
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
