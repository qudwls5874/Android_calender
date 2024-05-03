package com.example.myapplication.dialog.customcalender;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
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

        // 선택된 년 월
        int defaultDay = CustomDatePickerUtil.defaultDate.get(Calendar.DAY_OF_MONTH);
        int defaultMonth = CustomDatePickerUtil.defaultDate.get(Calendar.MONTH)+1;
        int defaultYear = CustomDatePickerUtil.defaultDate.get(Calendar.YEAR);

        // 보여지는달 년 월
        int currentDay = CustomDatePickerUtil.viewDate.get(Calendar.DAY_OF_MONTH);
        int currentMonth = CustomDatePickerUtil.viewDate.get(Calendar.MONTH)+1;
        int currentYear = CustomDatePickerUtil.viewDate.get(Calendar.YEAR);

        // 넘어온 전체 데이터
        int displayDay = dateCalendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalendar.get(Calendar.MONTH)+1;
        int displayYear = dateCalendar.get(Calendar.YEAR);

        // 날짜 변수에 담기
        int dayNo = dateCalendar.get(Calendar.DAY_OF_MONTH);
        holder.binding.dayText.setText(String.valueOf(dayNo));

        // 텍스트 색상 지정
        if( (position + 1) % 7 == 0){ //토요일 파랑
            holder.binding.dayText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textPlusColor));
        }else if( position == 0 || position % 7 == 0){ //일요일 빨강
            holder.binding.dayText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textMinusColor));
        } else {
            holder.binding.dayText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textNormalColor));   // 나머지 검정
        }

        // 현재 달에 존재 하는 날짜가 아니면 회색 텍스트 로 변경
        if (displayMonth != currentMonth){
            holder.binding.dayText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textBeNormalColor));
        }

        // 초기 선택 일자랑 보이는 일자랑 같을시
        if (displayYear == defaultYear && displayMonth == defaultMonth && displayDay == defaultDay){
            holder.binding.dayDefaultImage.setVisibility(View.VISIBLE);
            holder.binding.dayText.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white));
        }

        // 선택한 데이터
        if (CustomDatePickerUtil.choiceDate != null){
            if (displayYear == CustomDatePickerUtil.choiceDate.get(Calendar.YEAR)
                && displayMonth == CustomDatePickerUtil.choiceDate.get(Calendar.MONTH) + 1
                && displayDay == CustomDatePickerUtil.choiceDate.get(Calendar.DAY_OF_MONTH)){
                holder.binding.dayCheckImage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final DialogCustomDatePickerCellBinding binding;

        public ViewHolder(@NonNull DialogCustomDatePickerCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.dayLayout.setOnClickListener(this);
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View v) {
            if (v.getId() == binding.dayLayout.getId()){
                //달력 초기화
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar.setTime(dayList.get(getBindingAdapterPosition()));

                if (CustomDatePickerUtil.choiceDate != null && CustomDatePickerUtil.choiceDate.equals(dateCalendar)){
                    CustomDatePickerUtil.choiceDate = null;
                } else {
                    CustomDatePickerUtil.choiceDate = dateCalendar;
                }
                notifyDataSetChanged();
            }
        }
    }


}
