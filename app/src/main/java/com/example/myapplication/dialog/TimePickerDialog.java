package com.example.myapplication.dialog;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogIntervalTimePickerBinding;
import com.example.myapplication.event.ConvertTimeFormat;

import java.util.Arrays;

public class TimePickerDialog extends DialogFragment implements View.OnClickListener {

    DialogIntervalTimePickerBinding binding;
    private OnTimeSetListener listener;

    String dateFg = "";
    String startDate = "";
    String endDate = "";
    int interval = 30;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogIntervalTimePickerBinding.inflate(inflater,container,false);

        initData();
        
        return binding.getRoot();
    }

    private void initData() {

        // 전달받은 데이터
        Bundle bundle = getArguments();
        if (bundle != null){
            dateFg = bundle.getString("dateFg");
            startDate = bundle.getString("dateStartValue");
            endDate = bundle.getString("dateEndValue");
            interval = bundle.getInt("dateIntervalValue");
        }

        binding.dialogTimeAmpmPicker.setWrapSelectorWheel(false);
        binding.dialogTimeHourPicker.setWrapSelectorWheel(false);
        binding.dialogTimeMinutePicker.setWrapSelectorWheel(false);

        switch (dateFg){
            case "open":
                openSetting(startDate, interval);
                break;
            case "close":
                closeSetting(startDate, endDate, interval);
                break;
            case "interval":
                intervalSetting(interval);
                break;
        }

        binding.dialogTimeCanselBtn.setOnClickListener(this);
        binding.dialogTimeOkBtn.setOnClickListener(this);
    }



    // 오픈 UI 세팅
    private void openSetting(String startDate, int interval) {

        // dialog_time_ampm_picker
        String[] ampm = {"오전", "오후"};
        binding.dialogTimeAmpmPicker.setMinValue(0);
        binding.dialogTimeAmpmPicker.setMaxValue(1);
        binding.dialogTimeAmpmPicker.setDisplayedValues(ampm);
        binding.dialogTimeAmpmPicker.setValue(Integer.parseInt(startDate) < 1200 ? 0 : 1);

        // dialog_time_hour_picker (시) 0 ~ 24
        // 0  1  2  3  4  5  6  7  8  9  10 11
        // 12 13 14 15 16 17 18 19 20 21 22 23
        String[] hourValues = new String[25];
        for (int i = 0; i < hourValues.length; i++) {
            hourValues[i] = i < 13 ? String.valueOf(i)+" ("+i+")" : String.valueOf(i-12)+" ("+i+")";

            //hourValues[i] = !String.valueOf(i < 12 ? i : i-12).equals("0") ? String.valueOf(i < 12 ? i : i-12) : "12";
        }
        binding.dialogTimeHourPicker.setMinValue(0);
        binding.dialogTimeHourPicker.setMaxValue(hourValues.length-1);
        binding.dialogTimeHourPicker.setDisplayedValues(hourValues);
        binding.dialogTimeHourPicker.setValue(Integer.parseInt(startDate.substring(0,2)));

        // dialog_time_minute_picker (분)        60 / 10 = 6, 60 / 20 = 3
        String[] minuteValues = new String[60 / interval];
        minuteValues[0] = "00";
        int setValue = 0;
        for (int i = 1; i < minuteValues.length; i++) {
            minuteValues[i] = String.valueOf(i * interval);
            if (minuteValues[i].equals(startDate.substring(2))){
                setValue = i;
            }
        }
        binding.dialogTimeMinutePicker.setMinValue(0);
        binding.dialogTimeMinutePicker.setMaxValue(minuteValues.length-1);
        binding.dialogTimeMinutePicker.setDisplayedValues(minuteValues);
        binding.dialogTimeMinutePicker.setValue(setValue);

        // 이벤트
        binding.dialogTimeAmpmPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (oldVal == 0 && newVal == 1){
                animateNumberPicker(binding.dialogTimeHourPicker, 12);
            } else if (oldVal == 1 && newVal == 0) {
                animateNumberPicker(binding.dialogTimeHourPicker, 0);
            }
        });
        binding.dialogTimeHourPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

            if (oldVal == 0 || oldVal == 11 || oldVal == 12 || oldVal == 23 || oldVal == 24 || oldVal == 25 ||
                newVal == 0 || newVal == 11 || newVal == 12 || newVal == 23 || newVal == 24 || newVal == 25){

                if (oldVal == 11 && newVal == 12){
                    animateNumberPicker(binding.dialogTimeAmpmPicker, 1);
                } else if (oldVal == 12 && newVal == 11){
                    animateNumberPicker(binding.dialogTimeAmpmPicker, 0);
                } else if (oldVal == 23 && newVal == 0) {
                    animateNumberPicker(binding.dialogTimeAmpmPicker, 0);
                } else if (oldVal == 0 && newVal == 23) {
                    animateNumberPicker(binding.dialogTimeAmpmPicker, 1);
                } else if (oldVal == 24 && newVal == 25){

                } else if (oldVal == 25) {

                }

            }
        });
    }

    // 클로즈 UI 세팅
    private void closeSetting(String startDate, String endDate, int interval) {


        String[] ampm = Integer.parseInt(startDate) < 1200 ? new String[]{"오전", "오후"} : new String[]{"오후"} ;
        binding.dialogTimeAmpmPicker.setMinValue(0);
        binding.dialogTimeAmpmPicker.setMaxValue(ampm.length-1);
        binding.dialogTimeAmpmPicker.setDisplayedValues(ampm);
        binding.dialogTimeAmpmPicker.setValue(
                Integer.parseInt(startDate) > 1200 ? 0 : Integer.parseInt(endDate) < 1200 ? 0 : 1
        );

        // dialog_time_hour_picker (시) 0 ~ 24
        int hourShowVal = 0;
        String[] hourValues = new String[25 - Integer.parseInt(startDate.substring(0, 2))];
        for (int i = 0; i < hourValues.length; i++) {
            int result = i + Integer.parseInt(startDate.substring(0,2));
            hourValues[i] = result < 13 ? String.valueOf(result)+" ("+result+")" : String.valueOf(result-12)+" ("+result+")";

            if (result == Integer.parseInt(endDate.substring(0,2))){
                hourShowVal = i;
            }
        }
        binding.dialogTimeHourPicker.setMinValue(0);
        binding.dialogTimeHourPicker.setMaxValue(hourValues.length-1);
        binding.dialogTimeHourPicker.setDisplayedValues(hourValues);
        binding.dialogTimeHourPicker.setValue(hourShowVal);

        // dialog_time_minute_picker (분)        60 / 10 = 6, 60 / 20 = 3
        String[] minuteValues = new String[60 / interval];
        minuteValues[0] = "00";
        int minuteVal = 0;
        for (int i = 1; i < minuteValues.length; i++) {
            minuteValues[i] = String.valueOf(i * interval);
            if (minuteValues[i].equals(startDate.substring(2))){
                minuteVal = i;
            }
        }
        binding.dialogTimeMinutePicker.setMinValue(0);
        binding.dialogTimeMinutePicker.setMaxValue(minuteValues.length-1);
        binding.dialogTimeMinutePicker.setDisplayedValues(minuteValues);
        binding.dialogTimeMinutePicker.setValue(minuteVal);

        // 이벤트
        binding.dialogTimeAmpmPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

            if (oldVal == 0 && newVal == 1){
                // 24 - 11 = 14
                animateNumberPicker(binding.dialogTimeHourPicker, 12-Integer.parseInt(startDate.substring(0,2)));
            } else if (oldVal == 1 && newVal == 0) {
                animateNumberPicker(binding.dialogTimeHourPicker, 0);
            }
        });
        int startIndex = 24-Integer.parseInt(startDate.substring(0,2));
        binding.dialogTimeHourPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {

            if (binding.dialogTimeHourPicker.getDisplayedValues()[newVal]
                    .split(" ")[1]
                    .replace("(","")
                    .replace(")","").equals("12")){

                animateNumberPicker(binding.dialogTimeAmpmPicker, 1);

            } else if (binding.dialogTimeHourPicker.getDisplayedValues()[newVal]
                    .split(" ")[1]
                    .replace("(","")
                    .replace(")","").equals("11")) {
                animateNumberPicker(binding.dialogTimeAmpmPicker, 0);
            }

        });

    }

    // 간격 UI 세팅
    private void intervalSetting(int interval) {

        binding.dialogTimeAmpmPicker.setVisibility(View.GONE);
        binding.dialogTimeHourPicker.setVisibility(View.GONE);
        binding.dialogTimeIntervalTextView.setVisibility(View.GONE);

        String[] intervalVals = {"10", "20", "30", "60"};
        int result = 0;
        for (int i = 0; i < 4; i++){
            if (intervalVals[i].equals(String.valueOf(interval))) {
                result = i;
                break;
            }
        }
        binding.dialogTimeMinutePicker.setMinValue(0);
        binding.dialogTimeMinutePicker.setMaxValue(3);
        binding.dialogTimeMinutePicker.setDisplayedValues(intervalVals);
        binding.dialogTimeMinutePicker.setValue(result);


    }

    // 이동 애니메이션
    private void animateNumberPicker(NumberPicker picker, int value){
        ObjectAnimator animator = ObjectAnimator.ofInt(picker, "value", picker.getValue(), value);
        animator.setDuration(200);
        animator.start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == binding.dialogTimeCanselBtn.getId()){
            dismiss();
        } else if (v.getId() == binding.dialogTimeOkBtn.getId()) {

            if (!dateFg.equals("interval")){
                listener.onTimeSet(
                        binding.dialogTimeHourPicker.getDisplayedValues()[binding.dialogTimeHourPicker.getValue()]
                                .split("\\(")[1].replace(")", "")
                                + binding.dialogTimeMinutePicker.getDisplayedValues()[binding.dialogTimeMinutePicker.getValue()]
                );
            } else {
                listener.onTimeSet(binding.dialogTimeMinutePicker.getDisplayedValues()[binding.dialogTimeMinutePicker.getValue()]);
            }
        }
    }

    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.listener = listener;
    }

    public interface OnTimeSetListener {
        void onTimeSet(String timeData);
    }

}
