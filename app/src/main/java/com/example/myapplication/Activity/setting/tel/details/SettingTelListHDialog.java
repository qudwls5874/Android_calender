package com.example.myapplication.Activity.setting.tel.details;

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
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.databinding.DialogSettingTelDBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingTelListHDialog extends DialogFragment {

    private DialogSettingTelDBinding binding;

    private List<String> userDList = new ArrayList<>(Arrays.asList("전화번호","주소","일정"));
    private SettingTelListHAdapter adapter;
    private UserJoin userJoin;
    private Bitmap profile;

    public SettingTelListHDialog(UserJoin userJoin, Bitmap profile){
        this.userJoin = userJoin;
        this.profile = profile;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingTelDBinding.inflate(inflater, container, false);

        initUI();

        return binding.getRoot();
    }

    private void initUI() {

        adapter = new SettingTelListHAdapter(userDList, userJoin);
        binding.settingTelDRecyclerView.setAdapter(adapter);
        binding.settingTelDRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.settingTelDNameTextView.setText(userJoin.user.getName());


        if (profile != null){
            binding.settingTelDImageView.setImageBitmap(profile);
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
