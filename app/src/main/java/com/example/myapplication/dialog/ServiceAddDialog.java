package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.databinding.DialogServiceAddBinding;
import com.example.myapplication.event.WatcherMoneyText;

public class ServiceAddDialog extends Dialog implements View.OnClickListener, ServiceAddMoneyDialog.OnGetMoney {

    private DialogServiceAddBinding binding;
    private Context context;
    private MenuCategory menuCategory;
    private WatcherMoneyText watcherMoneyText;
    private SetMenuListLisner lisner;

    public ServiceAddDialog(@NonNull Context context, MenuCategory menuCategory, SetMenuListLisner lisner) {
        super(context);
        this.context = context;
        this.menuCategory = menuCategory;
        this.lisner = lisner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogServiceAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // EditText에 포커스 주기
        binding.settingServiceAddMenulistnameEditText.requestFocus();

        // 타이틀
        binding.settingServiceAddTitleTextView.setText(menuCategory.getMenuCategoryName());
        // 금액
        binding.settingServiceAddMenulistmoneyTextView.setText("0");

        binding.settingServiceAddCloseBtn.setOnClickListener(this::onClick);
        binding.settingServiceAddSaveBtn.setOnClickListener(this::onClick);
        binding.settingServiceAddMenulistmoneyTextView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.settingServiceAddCloseBtn.getId()){
            // 닫기 버튼
            dismiss();
        } else if (v.getId() == binding.settingServiceAddSaveBtn.getId()) {
            // 저장 버튼
            MenuList data = new MenuList(
                    menuCategory.getMenuCategoryId(),
                    binding.settingServiceAddMenulistnameEditText.getText().toString(),
                    Integer.parseInt(binding.settingServiceAddMenulistmoneyTextView.getText().toString().replaceAll(",", ""))
            );
            lisner.setMenuListLisner(data);
            Toast.makeText(getContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (v.getId() == binding.settingServiceAddMenulistmoneyTextView.getId()) {
            // 금액 버튼
            ServiceAddMoneyDialog moneyDialog = new ServiceAddMoneyDialog(getContext(), String.valueOf(binding.settingServiceAddMenulistmoneyTextView.getText()), this::getMoney);
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
    public void dismiss() {
        super.dismiss();
        if (watcherMoneyText != null){
            binding.settingServiceAddMenulistmoneyTextView.removeTextChangedListener(watcherMoneyText);
            watcherMoneyText = null;
        }
    }

    @Override
    public void getMoney(String money) {
        binding.settingServiceAddMenulistmoneyTextView.setText(money);
    }

    public interface SetMenuListLisner {
        void setMenuListLisner(MenuList menuList);
    }


}
