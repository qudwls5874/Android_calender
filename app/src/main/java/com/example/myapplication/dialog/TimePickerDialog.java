package com.example.myapplication.dialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.databinding.DialogIntervalTimePickerBinding;
import com.example.myapplication.event.ConvertTimeFormat;

public class TimePickerDialog extends DialogFragment implements View.OnClickListener {

    DialogIntervalTimePickerBinding binding;
    private OnTimeSetListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogIntervalTimePickerBinding.inflate(inflater,container,false);

        initData();
        
        return binding.getRoot();
    }

    private void initData() {

        String dateFg = "";
        String[] startDate = new String[3];
        String[] endDate = new String[3];
        int interval = 30;

        // 전달받은 데이터
        Bundle bundle = getArguments();
        if (bundle != null){
            dateFg = bundle.getString("dateFg");
            startDate[0] = bundle.getString("dateStartValue").split(" ")[0].toString();
            startDate[1] = bundle.getString("dateStartValue").split(" ")[1].toString().split(":")[0].toString();
            startDate[2] = bundle.getString("dateStartValue").split(" ")[1].toString().split(":")[1].toString();
            endDate[0] = bundle.getString("dateEndValue").split(" ")[0].toString();
            endDate[1] = bundle.getString("dateEndValue").split(" ")[1].toString().split(":")[0].toString();
            endDate[2] = bundle.getString("dateEndValue").split(" ")[1].toString().split(":")[1].toString();
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
                break;
        }

        binding.dialogTimeCanselBtn.setOnClickListener(this);
        binding.dialogTimeOkBtn.setOnClickListener(this);
    }


    // 오픈 UI 세팅
    private void openSetting(String[] startDate, int interval) {

        // dialog_time_ampm_picker
        String[] ampm = {"오전", "오후"};
        binding.dialogTimeAmpmPicker.setMinValue(0);
        binding.dialogTimeAmpmPicker.setMaxValue(1);
        binding.dialogTimeAmpmPicker.setDisplayedValues(ampm);
        binding.dialogTimeAmpmPicker.setValue(startDate[0].equals("오전") ? 0 : 1);

        // dialog_time_hour_picker (시)
        String[] hourValues = new String[12];
        for (int i = 0; i < hourValues.length; i++) {
            hourValues[i] = String.valueOf(i + 1);
        }
        binding.dialogTimeHourPicker.setMinValue(0);
        binding.dialogTimeHourPicker.setMaxValue(11);
        binding.dialogTimeHourPicker.setDisplayedValues(hourValues);
        binding.dialogTimeHourPicker.setValue(Integer.valueOf(startDate[1]) - 1);


        // dialog_time_minute_picker (분)        60 / 10 = 6, 60 / 20 = 3
        String[] minuteValues = new String[60 / interval];
        minuteValues[0] = "00";
        for (int i = 1; i < minuteValues.length; i++) {
            minuteValues[i] = String.valueOf(i * interval);
            if (minuteValues[i] == startDate[2]){
                startDate[2] = String.valueOf(i);
            }
        }
        binding.dialogTimeMinutePicker.setMinValue(0);
        binding.dialogTimeMinutePicker.setMaxValue(minuteValues.length-1);
        binding.dialogTimeMinutePicker.setDisplayedValues(minuteValues);
        binding.dialogTimeMinutePicker.setValue(Integer.valueOf(startDate[2]));
    }

    // 클로즈 UI 세팅
    private void closeSetting(String[] startDate, String[] endDate, int interval) {

        //String[] ampm = startDate[0].equals("오전")? new String[]{"오전", "오후"} : new String[]{"오후"} ;
        if (startDate[0].equals("오전")){
            String[] ampm = {"오전", "오후"};
            binding.dialogTimeAmpmPicker.setMinValue(0);
            binding.dialogTimeAmpmPicker.setMaxValue(1);
            binding.dialogTimeAmpmPicker.setDisplayedValues(ampm);
            binding.dialogTimeAmpmPicker.setValue(startDate[0].equals("오전") ? 0 : 1);

            // dialog_time_hour_picker (시)
            String[] hourValues = new String[12];
            for (int i = 0; i < hourValues.length; i++) {
                hourValues[i] = String.valueOf(i + 1);
            }
            binding.dialogTimeHourPicker.setMinValue(0);
            binding.dialogTimeHourPicker.setMaxValue(11);
            binding.dialogTimeHourPicker.setDisplayedValues(hourValues);
            binding.dialogTimeHourPicker.setValue(Integer.valueOf(startDate[1]) - 1);


            // dialog_time_minute_picker (분)        60 / 10 = 6, 60 / 20 = 3
            String[] minuteValues = new String[60 / interval];
            minuteValues[0] = "00";
            for (int i = 1; i < minuteValues.length; i++) {
                minuteValues[i] = String.valueOf(i * interval);
                if (minuteValues[i] == startDate[2]){
                    startDate[2] = String.valueOf(i);
                }
            }
            binding.dialogTimeMinutePicker.setMinValue(0);
            binding.dialogTimeMinutePicker.setMaxValue(minuteValues.length-1);
            binding.dialogTimeMinutePicker.setDisplayedValues(minuteValues);
            binding.dialogTimeMinutePicker.setValue(Integer.valueOf(startDate[2]));
        }


    }




    @Override
    public void onClick(View v) {
        if (v.getId() == binding.dialogTimeCanselBtn.getId()){
            dismiss();
        } else if (v.getId() == binding.dialogTimeOkBtn.getId()) {
            listener.onTimeSet(
                    new ConvertTimeFormat(
                            binding.dialogTimeAmpmPicker.getDisplayedValues()[binding.dialogTimeAmpmPicker.getValue()] + " " +
                                    binding.dialogTimeHourPicker.getDisplayedValues()[binding.dialogTimeHourPicker.getValue()] + ":" +
                                    binding.dialogTimeMinutePicker.getDisplayedValues()[binding.dialogTimeMinutePicker.getValue()]
                    ).convertTimeFormat2()
            );
        }
    }

    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.listener = listener;
    }

    public interface OnTimeSetListener {
        void onTimeSet(String timeData);
    }

}
