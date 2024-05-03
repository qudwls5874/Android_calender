package com.example.myapplication.dialog.customcalender;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.databinding.DialogCustomDatePickerBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class CustomDatePickerDialog extends DialogFragment implements View.OnClickListener {

    private DialogCustomDatePickerBinding binding;
    private ReturnValue returnValueLisner;

    private CustomDatePickerAdapter dateAdapter;
    private ArrayList<Date> dayList;
    private final Calendar defaultCal;

    public CustomDatePickerDialog(Calendar defaultCal, ReturnValue returnValueLisner){
        this.defaultCal = defaultCal;
        this.returnValueLisner = returnValueLisner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCustomDatePickerBinding.inflate(inflater, container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {

        CustomDatePickerUtil.defaultDate = defaultCal;             // 선택된 값
        CustomDatePickerUtil.viewDate = Calendar.getInstance();     // 보여질 값

        dayList = new ArrayList<>();
        dateAdapter = new CustomDatePickerAdapter(dayList);
        binding.dialogDateRecyclerView.setAdapter(dateAdapter);
        binding.dialogDateRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        // 화면 설정
        setMonthView();

        binding.dialogDatePreBtn.setOnClickListener(this);
        binding.dialogDateNextBtn.setOnClickListener(this);
        binding.dialogDateCloseBtn.setOnClickListener(this);
        binding.dialogDateAddBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == binding.dialogDatePreBtn.getId()){
            // -1한 월을 넣어준다. (2월 -> 1월)
            CustomDatePickerUtil.viewDate.add(Calendar.MONTH, -1); // -1
            setMonthView();
        } else if (v.getId() == binding.dialogDateNextBtn.getId()){
            // +1한 월을 넣어준다.(2월 -> 3월)
            CustomDatePickerUtil.viewDate.add(Calendar.MONTH, 1); // +1
            setMonthView();
        } else if (v.getId() == binding.dialogDateCloseBtn.getId()) {
            // 닫기
            dismiss();
        } else if (v.getId() == binding.dialogDateAddBtn.getId()){
            // 저장
            if (CustomDatePickerUtil.choiceDate != null){
                returnValueLisner.getReturnValue(CustomDatePickerUtil.choiceDate.getTime());
            }
            dismiss();
        }
    }

    // 날짜 타입 설정
    private String monthYearFromDate(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        return year + "년 " + month + "월 ";
    }

    // 화면 설정
    private void setMonthView() {
        dayList.clear();
        daysInMonthArray();
        binding.dialogDateMonthYearText.setText(monthYearFromDate(CustomDatePickerUtil.viewDate));
        dateAdapter.notifyDataSetChanged();
    }


    private void daysInMonthArray(){
        //날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar) CustomDatePickerUtil.viewDate.clone();
        //1일로 셋팅
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
        // 요일 가져와서 -1 일요일:1, 월요일:2
        int firstDayOfMonth = monthCalendar.get(Calendar.DAY_OF_WEEK) -1;
        // 날짜 셋팅 (-5일전)
        monthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        // 42전까지 반복
        while (dayList.size() < 42){
            // 리스트에 날짜 등록
            dayList.add(monthCalendar.getTime());
            // 1일씩 늘린 날짜로 변경 1일 -> 2일 -> 3일
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 다이얼로그의 배경을 투명하게 설정합니다.
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        getDialog().setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        CustomDatePickerUtil.defaultDate = null;
        CustomDatePickerUtil.viewDate = null;
        CustomDatePickerUtil.choiceDate = null;
    }


    public interface ReturnValue{
        void getReturnValue(Date date);
    }

}
