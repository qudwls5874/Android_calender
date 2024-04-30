package com.example.myapplication.dialog.customcalender;

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

    private CustomDatePickerAdapter dateAdapter;
    private ArrayList<Date> dayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCustomDatePickerBinding.inflate(inflater, container, false);
        initUI();
        return binding.getRoot();
    }

    private void initUI() {

        CustomDatePickerUtil.selectedDate = Calendar.getInstance();

        dayList = new ArrayList<>();
        dateAdapter = new CustomDatePickerAdapter(dayList);
        binding.recyclerView.setAdapter(dateAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));

        setMonthView();

        binding.preBtn.setOnClickListener(this);
        binding.nextBtn.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == binding.preBtn.getId()){
            // -1한 월을 넣어준다. (2월 -> 1월)
            CustomDatePickerUtil.selectedDate.add(Calendar.MONTH, -1); // -1
            setMonthView();
        } else if (v.getId() == binding.nextBtn.getId()){
            // +1한 월을 넣어준다.(2월 -> 3월)
            CustomDatePickerUtil.selectedDate.add(Calendar.MONTH, 1); // +1
            setMonthView();
        }
    }

    // 날짜 타입 설정
    private String monthYearFromDate(Calendar calendar){
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        String monthYear = month + "월 " + year;

        return monthYear;
    }

    // 화면 설정
    private void setMonthView() {
        dayList.clear();
        //년월 텍스트뷰 셋팅
        binding.monthYearText.setText(monthYearFromDate(CustomDatePickerUtil.selectedDate));
        dayList.addAll(daysInMonthArray());
        dateAdapter.notifyDataSetChanged();
    }


    private ArrayList<Date> daysInMonthArray(){

        ArrayList<Date> resultList = new ArrayList<>();

        //날짜 복사해서 변수 생성
        Calendar monthCalendar = (Calendar) CustomDatePickerUtil.selectedDate.clone();

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

        return resultList;
    }



}
