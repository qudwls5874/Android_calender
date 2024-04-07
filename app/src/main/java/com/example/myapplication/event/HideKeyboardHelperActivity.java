package com.example.myapplication.event;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class HideKeyboardHelperActivity {

    // 뷰를 터치했을 때 키보드를 숨기는 기능을 설정하는 메서드
    public static void setupUI(View view, final Activity activity) {
        // TextInputLayout이 아닌 경우에만 터치 이벤트를 설정
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                // 터치 이벤트 핸들러
                public boolean onTouch(View v, MotionEvent event) {
                    // 키보드 숨기기
                    hideSoftKeyboard(activity);
                    // 클릭된 Layout 에 포커스 맞추기
                    view.requestFocus();
                    // 다른 이벤트 핸들러들에게 이벤트를 전달하기 위해 false 반환
                    return false;
                }
            });
        }

        // 레이아웃 컨테이너인 경우 자식 뷰들에 대해 재귀적으로 호출
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }

    }

    // 키보드를 숨기는 메서드
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        // 포커스된 뷰의 토큰을 사용하여 키보드 숨김
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    focusedView.getWindowToken(), 0);
        }
    }
}