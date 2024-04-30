package com.example.myapplication.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Calendar;

public class CalenderDialog {

    private Context context;

    public CalenderDialog(Context context) {
        this.context = context;
    }

    public void showDialog() {
        // 현재 날짜로 초기화된 Calendar 객체 생성
        final Calendar calendar = Calendar.getInstance();

        // 레이아웃 파일을 인플레이션하여 뷰 객체 생성
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View dialogView = inflater.inflate(R.layout.custom_calendar_dialog_layout, null);
        View dialogView = inflater.inflate(R.layout.dialog_date_picker, null);

        // 커스텀 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // 다이얼로그 안의 뷰들 초기화
        final DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
        Button okButton = dialogView.findViewById(R.id.dialog_button_ok);

        // OK 버튼 클릭 시 동작 설정
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 날짜를 가져옴
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                // 선택한 날짜로 Calendar 객체 설정
                calendar.set(year, month, dayOfMonth);

                // 선택한 날짜를 사용하여 원하는 동작 수행
                // 여기서는 선택한 날짜를 토스트 메시지로 표시하는 예제
                Toast.makeText(context, "Selected Date: " + year + "/" + (month + 1) + "/" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });

        // 커스텀 다이얼로그 표시
        AlertDialog dialog = builder.create();
        dialog.show();

        // DatePicker의 스타일을 변경하려면 다음과 같이 설정할 수 있습니다.
        datePicker.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setBackgroundColor(Color.RED);
        datePicker.findViewById(Resources.getSystem().getIdentifier("month", "id", "android")).setBackgroundColor(Color.RED);
        datePicker.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setBackgroundColor(Color.RED);
    }
}
