package com.example.myapplication.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.database.table.SettingDate;
import com.example.myapplication.database.viewmodel.SettingDateViewModel;
import com.example.myapplication.databinding.DialogSettingSettingdateBinding;
import com.example.myapplication.event.ConvertTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class SettingDateDialog extends DialogFragment implements View.OnClickListener {

    private DialogSettingSettingdateBinding binding;
    private SettingDateViewModel viewModel;
    private List<SettingDate> settingDates = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingSettingdateBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        // 뷰모델
        viewModel = new ViewModelProvider(this).get(SettingDateViewModel.class);

        // 옵져버
        viewModel.getList().observe(getViewLifecycleOwner(), list ->{
            settingDates.clear();
            settingDates = list;
            updpateSettingList();
        });

        binding.settingOpenTextView.setOnClickListener(this);
        binding.settingCloseTextView.setOnClickListener(this);
        binding.settingIntervalTextView.setOnClickListener(this);
    }

    private void updpateSettingList() {
        binding.settingOpenTextView.setText(
                new ConvertTimeFormat(String.valueOf(settingDates.get(0).getSettingTime())).convertTimeFormat()
        );
        binding.settingCloseTextView.setText(
                new ConvertTimeFormat(String.valueOf(settingDates.get(1).getSettingTime())).convertTimeFormat()
        );
        binding.settingIntervalTextView.setText(""+settingDates.get(2).getSettingTime()+" 분");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // XML 레이아웃을 사용하여 다이얼로그의 크기를 지정합니다.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // 다이얼로그의 배경을 투명하게 설정합니다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == binding.settingOpenTextView.getId()){
            // 오픈시간
            TimePickerDialog dialog = new TimePickerDialog();
            Bundle bundle = new Bundle();
            bundle.putString("dateFg", "open");
            bundle.putString("dateStartValue", binding.settingOpenTextView.getText().toString());
            bundle.putString("dateEndValue", binding.settingCloseTextView.getText().toString());
            bundle.putInt("dateIntervalValue", Integer.valueOf(binding.settingIntervalTextView.getText().toString().replaceAll("분","").trim()));
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "timePicker");

            dialog.setOnTimeSetListener((timeData) -> {
                settingDates.get(0).setSettingTime(timeData);
                if ( Integer.valueOf(settingDates.get(0).getSettingTime()) >= Integer.valueOf(settingDates.get(1).getSettingTime()) ){
                    settingDates.get(1).setSettingTime( settingDates.get(0).getSettingTime() );
                }
                updpateSettingList();
                dialog.dismiss();
            });

        } else if (v.getId() == binding.settingCloseTextView.getId()) {
            // 클로즈 시간
            TimePickerDialog dialog = new TimePickerDialog();
            Bundle bundle = new Bundle();
            bundle.putString("dateFg", "close");
            bundle.putString("dateStartValue", binding.settingOpenTextView.getText().toString());
            bundle.putString("dateEndValue", binding.settingCloseTextView.getText().toString());
            bundle.putInt("dateIntervalValue", Integer.valueOf(binding.settingIntervalTextView.getText().toString().replaceAll("분","").trim()));
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "timePicker");

            dialog.setOnTimeSetListener((timeData) -> {
                // 선택한 시간을 처리하는 코드
                settingDates.get(1).setSettingTime(timeData);
                updpateSettingList();
                dialog.dismiss();
            });
        } else if (v.getId() == binding.settingIntervalTextView.getId()) {
            // 시간 간격
            TimePickerDialog dialog = new TimePickerDialog();
            Bundle bundle = new Bundle();
            bundle.putString("dateFg", "interval");
            bundle.putString("dateStartValue", binding.settingOpenTextView.getText().toString());
            bundle.putString("dateEndValue", binding.settingCloseTextView.getText().toString());
            bundle.putInt("dateIntervalValue", Integer.valueOf(binding.settingIntervalTextView.getText().toString().replaceAll("분","").trim()));
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "timePicker");

            dialog.setOnTimeSetListener((timeData) -> {
                // 선택한 시간을 처리하는 코드
                settingDates.get(1).setSettingTime(timeData);
                updpateSettingList();
                dialog.dismiss();
            });
        }
    }
}
