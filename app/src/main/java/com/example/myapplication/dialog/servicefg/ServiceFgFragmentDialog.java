package com.example.myapplication.dialog.servicefg;


import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.database.viewmodel.MenuListViewModel;
import com.example.myapplication.databinding.DialogCategoryMenuBinding;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceFgFragmentDialog extends DialogFragment implements View.OnClickListener {

    private DialogCategoryMenuBinding binding;
    private MenuListViewModel menuViewModel;
    public ArrayList<MenuJoin> menuJoins = new ArrayList<>();
    private ServiceFgAdapter categoryMenuAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCategoryMenuBinding.inflate(inflater, container, false);

        initUI();
        initData();

        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initUI() {

        // 키보드 숨기기
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        binding.dialogMoneyCloseBtn.setOnClickListener(this);

        // 어댑터
        categoryMenuAdapter = new ServiceFgAdapter(menuJoins);
        binding.dialogMoneyNameHRecyclerView.setAdapter(categoryMenuAdapter);
        binding.dialogMoneyNameHRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 뷰모델
        menuViewModel = new ViewModelProvider(this).get(MenuListViewModel.class);

    }

    private void initData() {

        menuViewModel.getList().observe(this, this::updateMenuList);

    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateMenuList(List<MenuJoin> menuJoins){
        this.menuJoins.clear();
        this.menuJoins.addAll(menuJoins);
        categoryMenuAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if (window != null) {
            // 백그라운드 투명
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            // 열기&닫기 시 애니메이션 설정
            params.windowAnimations = R.style.AnimationPopupStyle;
            // 화면에 가득 차도록
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.95);
            window.setAttributes(params);

            // UI 하단 정렬
            window.setGravity(Gravity.BOTTOM);

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.dialogMoneyCloseBtn.getId()){
            dismiss();
        }
    }


}