package com.example.myapplication.dialog.customcalender;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.DialogCustomDatePickerCellBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomDatePickerAdapter extends RecyclerView.Adapter<CustomDatePickerAdapter.ViewHolder>  {

    private DialogCustomDatePickerCellBinding binding;
    private ArrayList<Date> dayList;

    public CustomDatePickerAdapter(ArrayList<Date> dayList) {
        this.dayList = dayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DialogCustomDatePickerCellBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //날짜 변수에 담기
        Date monthDate = dayList.get(position);

        //달력 초기화
        Calendar dateCalendar = Calendar.getInstance();

        dateCalendar.setTime(monthDate);

        //현재 년 월
        int currentDay = CustomDatePickerUtil.selectedDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CustomDatePickerUtil.selectedDate.get(Calendar.MONTH)+1;
        int currentYear = CustomDatePickerUtil.selectedDate.get(Calendar.YEAR);

        //넘어온 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        //날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        holder.binding.dayText.setText(String.valueOf(dayNo));

        //텍스트 색상 지정
        if( (position + 1) % 7 == 0){ //토요일 파랑
            holder.binding.dayText.setTextColor(Color.BLUE);
        }else if( position == 0 || position % 7 == 0){ //일요일 빨강
            holder.binding.dayText.setTextColor(Color.RED);
        } else {
            holder.binding.dayText.setTextColor(Color.BLACK);   // 나머지 검정
        }

        //비교해서 년, 월 같으면 진한색 아니면 연한색으로 변경
        if(displayMonth == currentMonth && displayYear == currentYear){
            holder.binding.parentView.setBackgroundColor(Color.parseColor("#D5D5D5"));
            //날짜까지 맞으면 색상 표시
            if (currentDay == displayDay){
                holder.itemView.setBackgroundColor(Color.parseColor("#CEFBC9"));
            }
        }else{
            holder.binding.parentView.setBackgroundColor(Color.parseColor("#F6F6F6"));
        }


        //날짜 클릭 이벤트
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //클릭 시 기능 구현
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private DialogCustomDatePickerCellBinding binding;

        public ViewHolder(@NonNull DialogCustomDatePickerCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
