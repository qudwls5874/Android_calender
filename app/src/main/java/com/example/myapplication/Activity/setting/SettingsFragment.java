package com.example.myapplication.Activity.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.setting.date.SettingDateDialog;
import com.example.myapplication.Activity.setting.service.SettingServiceMainDialog;
import com.example.myapplication.Activity.setting.tel.SettingTelMainDialog;
import com.example.myapplication.databinding.FragmentSettingBinding;
import com.example.myapplication.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;

public class SettingsFragment extends Fragment implements SettingsAdapter.OnItemClickListener {

    private FragmentSettingBinding binding;
    private SettingsAdapter settingsAdapter;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList("시간 설정", "서비스분야 설정", "연락처 가져오기"));

    private LoadingDialog loadingDialog;

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
                showSettingDateDialog();
                break;
            case 1:
                // 서비스분야 설정
                showSettingServiceDialog();
                break;
            case 2:
                // 연락처 설정
                requestContactsPermission();
                break;
        }

    }

    // 시간 설정
    private void showSettingDateDialog() {
        SettingDateDialog dialog = new SettingDateDialog();
        dialog.show(getParentFragmentManager(), "setting_date_dialog");
    }

    // 서비스분야 설정
    private void showSettingServiceDialog() {
        SettingServiceMainDialog serviceDialog = new SettingServiceMainDialog();
        serviceDialog.show(getParentFragmentManager(), "setting_service_dialog");
    }

    // 연락처 가져오기
    private void showLoadingAndFetchContacts() {
        loadingDialog = new LoadingDialog(getContext());
        loadingDialog.show();
        SettingTelMainDialog telDialog = new SettingTelMainDialog(loadingDialog);
        telDialog.show(getParentFragmentManager(), "setting_tel_dialog");

    }

    // 연락처 권한 설정
    private void requestContactsPermission() {
        // 권한이 없는 경우 권한 요청
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                // 최초실행
                requestPStringActivityResultLauncher.launch(Manifest.permission.READ_CONTACTS);
            } else {
                // 이전에 승인거절
                showPermissionDeniedDialog();
            }
        } else {
            showLoadingAndFetchContacts();
        }
    }

    // 이전에 승인거절
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder perBuilder = new AlertDialog.Builder(getContext());
        perBuilder.setTitle("권한 설정")
                .setMessage("권한 거절로 인해 일부기능이 제한됩니다.")
                .setPositiveButton("권한 설정하러 가기", (dialog1, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("취소", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .show();
    }

    // 최초 권한설정에서 버튼 눌렀을시
    private ActivityResultLauncher<String> requestPStringActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    showLoadingAndFetchContacts();
                } else {
                    Log.i("result2", result.toString());
                }
            }
    );

}