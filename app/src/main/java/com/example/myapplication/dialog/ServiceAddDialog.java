package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.databinding.DialogServiceAddBinding;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.WatcherMoneyText;

public class ServiceAddDialog extends Dialog implements View.OnClickListener {

    private DialogServiceAddBinding binding;
    private Context context;
    private MenuCategory menuCategory;
    private WatcherMoneyText watcherMoneyText;

    public ServiceAddDialog(@NonNull Context context, MenuCategory menuCategory) {
        super(context);
        this.context = context;
        this.menuCategory = menuCategory;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogServiceAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 키보드
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), this);
        // 타이틀
        binding.settingServiceAddTitleTextView.setText(menuCategory.getMenuCategoryName());
        // 금액
        watcherMoneyText = new WatcherMoneyText(binding.settingServiceAddMenulistmoneyEditText);
        binding.settingServiceAddMenulistmoneyEditText.addTextChangedListener(watcherMoneyText);

        binding.settingServiceAddCloseBtn.setOnClickListener(this);
        binding.settingServiceAddSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.settingServiceAddCloseBtn.getId()){
            // 닫기 버튼
            dismiss();
        } else if (v.getId() == binding.settingServiceAddSaveBtn.getId()) {
            // 저장 버튼

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // XML 레이아웃을 사용하여 다이얼로그의 크기를 지정합니다.
        Dialog dialog = this;
        // 다이얼로그의 배경을 투명하게 설정합니다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (watcherMoneyText != null){
            binding.settingServiceAddMenulistmoneyEditText.removeTextChangedListener(watcherMoneyText);
        }
    }


}
