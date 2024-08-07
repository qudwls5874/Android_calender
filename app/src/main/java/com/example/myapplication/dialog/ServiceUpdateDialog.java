package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.databinding.DialogServiceUpdateBinding;
import com.example.myapplication.event.WatcherMoneyText;

public class ServiceUpdateDialog extends Dialog implements View.OnClickListener, ServiceAddMoneyDialog.OnGetMoney {

    private DialogServiceUpdateBinding binding;
    private Context context;
    private MenuList menuList;
    private MenuCategory menuCategory;
    private SetMenuUpdateLisner updateLisner;

    public ServiceUpdateDialog(@NonNull Context context, MenuList menuList, MenuCategory menuCategory, SetMenuUpdateLisner updateLisner) {
        super(context);
        this.context = context;
        this.menuList = menuList;
        this.menuCategory = menuCategory;
        this.updateLisner = updateLisner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogServiceUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 타이틀
        binding.settingServiceUpdateTitleTextView.setText(menuCategory.getMenuCategoryName());
        // 서비스명 // EditText에 포커스 주기
        binding.settingServiceUpdateMenulistnameEditText.setText(menuList.getMenuName());
        binding.settingServiceUpdateMenulistnameEditText.requestFocus();
        binding.settingServiceUpdateMenulistnameEditText.setSelection(menuList.getMenuName().length()); // 커서를 끝으로 이동

        // 금액
        binding.settingServiceUpdateMenulistmoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+menuList.getMenuMoney()));

        binding.settingServiceUpdateCloseBtn.setOnClickListener(this::onClick);
        binding.settingServiceUpdateSaveBtn.setOnClickListener(this::onClick);
        binding.settingServiceUpdateMenulistmoneyTextView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.settingServiceUpdateCloseBtn.getId()){
            // 닫기 버튼
            dismiss();
        } else if (v.getId() == binding.settingServiceUpdateSaveBtn.getId()) {
            // 저장 버튼
            MenuList data = new MenuList(
                    menuList.getMenuListId(),
                    menuCategory.getMenuCategoryId(),
                    binding.settingServiceUpdateMenulistnameEditText.getText().toString(),
                    Integer.parseInt(binding.settingServiceUpdateMenulistmoneyTextView.getText().toString().replaceAll(",", ""))
            );
            updateLisner.setUdateLisner(data, this);
        } else if (v.getId() == binding.settingServiceUpdateMenulistmoneyTextView.getId()) {
            // 금액 버튼
            ServiceAddMoneyDialog moneyDialog = new ServiceAddMoneyDialog(getContext(), String.valueOf(binding.settingServiceUpdateMenulistmoneyTextView.getText()), this::getMoney);
            moneyDialog.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 다이얼로그의 배경을 투명하게 설정합니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void getMoney(String money) {
        binding.settingServiceUpdateMenulistmoneyTextView.setText(money);
    }

    public interface SetMenuUpdateLisner {
        void setUdateLisner(MenuList menuList, Dialog dialog);
    }


}
