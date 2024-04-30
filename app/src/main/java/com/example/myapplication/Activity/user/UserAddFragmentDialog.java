package com.example.myapplication.Activity.user;


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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.ServiceCalender;
import com.example.myapplication.database.table.UserCash;
import com.example.myapplication.database.table.UserCoupon;
import com.example.myapplication.database.view.CalenderJoin;
import com.example.myapplication.databinding.DialogUserAddBinding;
import com.example.myapplication.dialog.servicefg.ServiceFgAdapter;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserAddFragmentDialog extends DialogFragment implements View.OnClickListener {

    private DialogUserAddBinding binding;

    private ArrayList<CalenderJoin> scList = new ArrayList<>();
    private ServiceFgAdapter scAdapter;
    private ArrayList<UserCash> cashList = new ArrayList<>();
    private ArrayList<UserCoupon> couponList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUserAddBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        // 키보드 숨기기
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        // 클릭 이벤트
        binding.userAddServiceAddBtn.setOnClickListener(this);
        binding.userAddCashAddBtn.setOnClickListener(this);
        binding.userAddCouponAddBtn.setOnClickListener(this);
        binding.userAddCloseBtn.setOnClickListener(this);

        // 서비스
        scAdapter = new ServiceFgAdapter(scList, getParentFragmentManager());
        binding.userAddServiceRecyclerView.setAdapter(scAdapter);
        binding.userAddServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



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
        if (v.getId() == binding.userAddServiceAddBtn.getId()){

            // 서비스 클릭
            CalenderJoin cal = new CalenderJoin();
            cal.serviceCalender = new ServiceCalender(String.valueOf(LocalDate.now()), 0, 0);
            cal.menuCategory = new MenuCategory();
            cal.menuList = new MenuList();

            scList.add(scList.size(), cal);
            scAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddCashAddBtn.getId()) {
            // 금액충전 클릭
            cashList.add(cashList.size(), new UserCash(String.valueOf(LocalDate.now()),0,0));

        } else if (v.getId() == binding.userAddCouponAddBtn.getId()) {
            // 쿠폰 클릭

        } else if (v.getId() == binding.userAddCloseBtn.getId()) {
            dismiss();
        }
    }


}