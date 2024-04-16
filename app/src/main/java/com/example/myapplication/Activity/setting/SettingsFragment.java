package com.example.myapplication.Activity.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.setting.date.SettingDateDialog;
import com.example.myapplication.Activity.setting.service.SettingServiceMainDialog;
import com.example.myapplication.databinding.FragmentSettingBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFragment extends Fragment implements SettingsAdapter.OnItemClickListener {

    private FragmentSettingBinding binding;
    private SettingsAdapter settingsAdapter;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList("시간 설정", "서비스분야 설정", "연락처 가져오기"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fragment 초기화 작업 수행
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        binding.settingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        settingsAdapter = new SettingsAdapter(list, this);
        binding.settingRecyclerView.setAdapter(settingsAdapter);

    }

    @Override
    public void OnItemClickListener(int position) {

        switch (position){
            case 0:
                // 시간 설정
                SettingDateDialog dialog = new SettingDateDialog();
                dialog.show(getParentFragmentManager(), "setting_date_dialog");
                break;
            case 1:
                // 서비스분야 설정
                SettingServiceMainDialog serviceDialog = new SettingServiceMainDialog();
                serviceDialog.show(getParentFragmentManager(), "setting_service_dialog");
                break;
            case 2:
                // 연락처 가져오기
                break;
        }

    }
}