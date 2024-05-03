package com.example.myapplication.settingpermissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PermissionManager {

    private final Context context;
    private final ActivityResultLauncher<String> requestPermissionLauncher;

    public PermissionManager(Context context, ActivityResultLauncher<String> requestPermissionLauncher) {
        this.context = context;
        this.requestPermissionLauncher = requestPermissionLauncher;
    }

    public Boolean requestPermission(String permission) {

        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
                // 최초실행
                requestPermissionLauncher.launch(permission);
            } else {
                // 이전에 승인거절
                showPermissionDeniedDialog();
            }
        } else {
            // 권한이 이미 허용되어 있음
            return true;
        }

        return false;
    }

    private void showPermissionDeniedDialog() {
        AlertDialog.Builder perBuilder = new AlertDialog.Builder(context);
        perBuilder.setTitle("권한 설정")
                .setMessage("권한 거절로 인해 일부 기능이 제한됩니다.")
                .setPositiveButton("권한 설정하러 가기", (dialog1, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    intent.setData(uri);
                    context.startActivity(intent);
                })
                .setNegativeButton("취소", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .show();
    }


}
