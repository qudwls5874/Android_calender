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

import com.example.myapplication.Activity.user.money.UserMoney;
import com.example.myapplication.Activity.user.money.UserMoneyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogUserAddBinding;
import com.example.myapplication.dialog.moneyfg.MoneyNameFragmentDialog;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.time.LocalDate;
import java.util.ArrayList;

public class UserAddFragmentDialog extends DialogFragment implements View.OnClickListener, UserMoneyAdapter.CanselMoneyLisner {

    private DialogUserAddBinding binding;
    public ArrayList<UserMoney> moneyList = new ArrayList<>();
    private UserMoneyAdapter moneyAdapter;

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

        binding.userAddMoneyAddBtn.setOnClickListener(this);
        binding.userAddCloseBtn.setOnClickListener(this);

        //
        moneyAdapter = new UserMoneyAdapter(moneyList, this);
        binding.userAddMoneyRecyclerView.setAdapter(moneyAdapter);
        binding.userAddMoneyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        if (v.getId() == binding.userAddMoneyAddBtn.getId()){
            moneyList.add(moneyList.size(), new UserMoney(0,"정액제", LocalDate.now()));
            moneyAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddCloseBtn.getId()) {
            dismiss();
        }
    }

    @Override
    public void setCanselMoney(int index) {
        moneyList.remove(index);
        moneyAdapter = new UserMoneyAdapter(moneyList, this);
        binding.userAddMoneyRecyclerView.setAdapter(moneyAdapter);
        moneyAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoneyDialog() {
        // 결제 타입 (종류)
        MoneyNameFragmentDialog dialog = new MoneyNameFragmentDialog();
        dialog.show(getParentFragmentManager(), "MoneyNameFragmentDialog");
    }


}