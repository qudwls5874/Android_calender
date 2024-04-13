package com.example.myapplication.Activity.setting;

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

import com.example.myapplication.database.table.SettingDate;
import com.example.myapplication.database.viewmodel.SettingDateViewModel;
import com.example.myapplication.databinding.DialogSettingServiceBinding;
import com.example.myapplication.databinding.DialogSettingSettingdateBinding;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.dialog.TimePickerDialog;
import com.example.myapplication.event.ConvertTimeFormat;

import java.util.ArrayList;
import java.util.List;

public class SettingServiceDialog extends DialogFragment implements View.OnClickListener {

    DialogSettingServiceBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingServiceBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {


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


    }
}
