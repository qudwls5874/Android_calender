package com.example.myapplication.Activity.setting.tel.details;

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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.setting.tel.TelData;
import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogSettingTelDBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingTelListHDialog extends DialogFragment {

    private DialogSettingTelDBinding binding;

    private SettingTelListHAdapter adapter;
    private TelData telData;
    private List<TelDataDList> detailsList;

    public SettingTelListHDialog(TelData telData){
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

        detailsList = new ArrayList<>();
        detailsList.add(new TelDataDList("전화번호", telData.getTel()));
        detailsList.add(new TelDataDList("주소", telData.getAddress()));
        detailsList.add(new TelDataDList("일정", telData.getEventDate()));

        adapter = new SettingTelListHAdapter(detailsList);
        binding.settingTelDRecyclerView.setAdapter(adapter);
        binding.settingTelDRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.settingTelDNameTextView.setText(telData.getName());

        if (telData.getProfile() != null){
            binding.settingTelDImageView.setImageBitmap(telData.getProfile());
        } else {
            binding.settingTelDImageView.setImageResource(R.drawable.ic_person);
        }

        binding.settingTelCloseDBtn.setOnClickListener(v -> {
            dismiss();
        });

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
