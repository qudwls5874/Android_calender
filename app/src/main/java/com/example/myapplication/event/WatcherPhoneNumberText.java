package com.example.myapplication.event;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class WatcherPhoneNumberText implements TextWatcher {

    private OnPhoneNumberChangeListener mListener;
    private Integer index;
    EditText editText;

    public WatcherPhoneNumberText() {
    }

    // 어댑터 인덱스 필요할 때
    public WatcherPhoneNumberText(OnPhoneNumberChangeListener listener, int index, EditText editText) {
        this.mListener = listener;
        this.index = index;
        this.editText = editText;
    }

    // 텍스트뷰 생성자 & 국제 전화번호 형식으로 포맷팅
    public String formatInternationalPhoneNumber(String text) {
        if (!text.isEmpty()) {
            // 국가 코드 추가
            if (!text.startsWith("+")) {
                text = "+" + text;
            }

            // 하이픈(-) 추가
            if (text.length() > 3 && !text.substring(1, 2).equals("-")) {
                text = text.substring(0, 3) + "-" + text.substring(3);
            }
            if (text.length() > 7 && !text.substring(4, 5).equals("-")) {
                text = text.substring(0, 7) + "-" + text.substring(7);
            }
            if (text.length() > 11 && !text.substring(8, 9).equals("-")) {
                text = text.substring(0, 11) + "-" + text.substring(11);
            }
        }
        return text;
    }

    // 텍스트뷰 생성자 & 하이픈(-) 추가. 입력된 텍스트에서 하이픈을 제거하고, 전화번호 형식으로 다시 포맷팅합니다.
    public String formatPhoneNumber(String text) {
        if (!text.isEmpty()) {
            text = text.replace("-", "");
            if (text.length() >= 4 && text.length() <= 7) {
                text = text.substring(0, 3) + "-" + text.substring(3);
            } else if (text.length() >= 8) {
                text = text.substring(0, 3) + "-" + text.substring(3, 7) + "-" + text.substring(7);
            }
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
        if (mListener != null) {
            mListener.onPhoneNumberTextChanged(formatPhoneNumber(s.toString()), index);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 입력된 텍스트 변경 후 호출
        editText.removeTextChangedListener(this);

        String formattedText = formatPhoneNumber(s.toString());
        editText.setText(formattedText);
        editText.setSelection(formattedText.length()); // 커서를 끝으로 이동

        editText.addTextChangedListener(this);
    }

    public interface OnPhoneNumberChangeListener {
        void onPhoneNumberTextChanged(String newPhoneNumber, Integer index);
    }
}
