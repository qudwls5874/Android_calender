package com.example.myapplication.Activity.setting.tel;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogSettingTelDBinding;

import java.util.List;

public class SettingTelListDDialog extends DialogFragment {

    private DialogSettingTelDBinding binding;

    private SettingTelListDAdapter adapter;
    private TelData telData;

    public SettingTelListDDialog(TelData telData){
        this.telData = telData;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingTelDBinding.inflate(inflater, container, false);

        initUI();

        return binding.getRoot();
    }

    private void initUI() {

        adapter = new SettingTelListDAdapter(telData.getTel());
        binding.settingTelDRecyclerView.setAdapter(adapter);
        binding.settingTelDRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.settingTelDNameTextView.setText(telData.getName());

        if (telData.getProfile() != null){
            binding.settingTelDImageView.setImageBitmap(telData.getProfile());
        } else {
            binding.settingTelDImageView.setImageResource(R.drawable.ic_person);
        }


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // XML 레이아웃을 사용하여 다이얼로그의 크기를 지정합니다.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // 다이얼로그의 배경을 투명하게 설정합니다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


}
