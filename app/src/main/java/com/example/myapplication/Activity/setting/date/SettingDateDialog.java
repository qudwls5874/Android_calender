package com.example.myapplication.Activity.setting.date;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.database.table.setting.SettingDate;
import com.example.myapplication.database.viewmodel.SettingDateViewModel;
import com.example.myapplication.databinding.DialogSettingSettingdateBinding;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.dialog.TimePickerDialog;
import com.example.myapplication.event.ConvertTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class SettingDateDialog extends DialogFragment implements View.OnClickListener {

    private DialogSettingSettingdateBinding binding;
    private SettingDateViewModel viewModel;
    private List<SettingDate> settingDates = new ArrayList<>();

    private LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingSettingdateBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        // 로딩
        loadingDialog = new LoadingDialog(getContext());

        // 뷰모델
        viewModel = new ViewModelProvider(this).get(SettingDateViewModel.class);

        // 옵져버
        viewModel.getList().observe(getViewLifecycleOwner(), list ->{
            settingDates.clear();
            settingDates = list;

            binding.settingMondayCheckBox.setChecked(list.get(3).getSettingTime().charAt(0) == 'Y');
            binding.settingTuesdayCheckBox.setChecked(list.get(3).getSettingTime().charAt(1) == 'Y');
            binding.settingWednesdayCheckBox.setChecked(list.get(3).getSettingTime().charAt(2) == 'Y');
            binding.settingThursdayCheckBox.setChecked(list.get(3).getSettingTime().charAt(3) == 'Y');
            binding.settingFridayCheckBox.setChecked(list.get(3).getSettingTime().charAt(4) == 'Y');
            binding.settingSaturdayCheckBox.setChecked(list.get(3).getSettingTime().charAt(5) == 'Y');
            binding.settingSundayCheckBox.setChecked(list.get(3).getSettingTime().charAt(6) == 'Y');

            updpateSettingList();
        });

        binding.settingOpenTextView.setOnClickListener(this);
        binding.settingCloseTextView.setOnClickListener(this);
        binding.settingIntervalTextView.setOnClickListener(this);
        binding.settingSettingdateCloseBtn.setOnClickListener(this);
        binding.settingSettingdateAddBtn.setOnClickListener(this);
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

        loadingDialog.show();

        if (v.getId() == binding.settingOpenTextView.getId()){
            // 오픈시간
            TimePickerDialog dialog = new TimePickerDialog();
            Bundle bundle = new Bundle();
            bundle.putString("dateFg", "open");
            bundle.putString("dateStartValue", settingDates.get(0).getSettingTime());
            bundle.putString("dateEndValue", settingDates.get(1).getSettingTime());
            bundle.putInt("dateIntervalValue", Integer.parseInt(settingDates.get(2).getSettingTime()));
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "timePicker");

            dialog.setOnTimeSetListener((timeData) -> {
                settingDates.get(0).setSettingTime(timeData);
                if (Integer.parseInt(settingDates.get(0).getSettingTime()) > Integer.parseInt(settingDates.get(1).getSettingTime())){
                    settingDates.get(1).setSettingTime(timeData);
                }
                updpateSettingList();
                dialog.dismiss();
            });

        } else if (v.getId() == binding.settingCloseTextView.getId()) {
            // 클로즈 시간
            TimePickerDialog dialog = new TimePickerDialog();
            Bundle bundle = new Bundle();
            bundle.putString("dateFg", "close");
            bundle.putString("dateStartValue", settingDates.get(0).getSettingTime());
            bundle.putString("dateEndValue", settingDates.get(1).getSettingTime());
            bundle.putInt("dateIntervalValue", Integer.parseInt(settingDates.get(2).getSettingTime()));
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
            bundle.putString("dateStartValue", settingDates.get(0).getSettingTime());
            bundle.putString("dateEndValue", settingDates.get(1).getSettingTime());
            bundle.putInt("dateIntervalValue", Integer.parseInt(settingDates.get(2).getSettingTime()));
            dialog.setArguments(bundle);
            dialog.show(getParentFragmentManager(), "timePicker");

            dialog.setOnTimeSetListener((timeData) -> {
                // 선택한 시간을 처리하는 코드
                if (!settingDates.get(2).getSettingTime().equals(timeData)){
                    settingDates.get(0).setSettingTime("0000");
                    settingDates.get(1).setSettingTime("0000");
                    settingDates.get(2).setSettingTime(timeData);
                    updpateSettingList();
                }
                dialog.dismiss();
            });
        } else if (v.getId() == binding.settingSettingdateCloseBtn.getId()) {
            // 닫기 버튼
            dismiss();
        } else if (v.getId() == binding.settingSettingdateAddBtn.getId()) {
            // 저장 버튼
            String aWeek = "";
            aWeek += binding.settingMondayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingTuesdayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingWednesdayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingThursdayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingFridayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingSaturdayCheckBox.isChecked() ? "Y" : "N";
            aWeek += binding.settingSundayCheckBox.isChecked() ? "Y" : "N";
            settingDates.get(3).setSettingTime(aWeek);
            viewModel.update(settingDates);
            dismiss();
        }

        loadingDialog.dismiss();
    }
}
