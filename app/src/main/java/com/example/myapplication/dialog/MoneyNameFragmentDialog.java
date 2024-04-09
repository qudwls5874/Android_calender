package com.example.myapplication.dialog;


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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.user.money.UserMoney;
import com.example.myapplication.Activity.user.money.UserMoneyAdapter;
import com.example.myapplication.R;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.database.viewmodel.MoneyNameViewModel;
import com.example.myapplication.databinding.DialogMoneyNameBinding;
import com.example.myapplication.databinding.DialogUserAddBinding;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoneyNameFragmentDialog extends DialogFragment implements View.OnClickListener, MoneyNameAdapter.CanselMoneyLisner {

    private MoneyNameViewModel moneyNameViewModel;
    private DialogMoneyNameBinding binding;
    public ArrayList<MoneyName> moneyList = new ArrayList<>();
    private MoneyNameAdapter moneyNameAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogMoneyNameBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        // 키보드 숨기기
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        binding.dialogMoneyCloseBtn.setOnClickListener(this);

        /*
        * // 어댑터
        userAdapter = new UserAdapter(getActivity(), R.layout.view_user_item_row, filterList);
        binding.recyclerView.setAdapter(userAdapter);*/
        moneyNameAdapter = new MoneyNameAdapter(moneyList, this);
        binding.dialogMoneyNameRecyclerView.setAdapter(moneyNameAdapter);

        // 뷰모델
        moneyNameViewModel = new ViewModelProvider(this).get(MoneyNameViewModel.class);

        // LiveData를 관찰하여 업데이트가 발생할 때마다 RecyclerView에 데이터를 설정
        moneyNameViewModel.getAllMoneyList().observe(getViewLifecycleOwner(),moneyNames -> {
            updateMoneyNameList(moneyNames);
        });

    }

    private void updateMoneyNameList(List<MoneyName> moneyNames) {
        moneyList.clear();
        moneyList.addAll(moneyNames);
        moneyNameAdapter.notifyDataSetChanged();
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


    @Override
    public void setCanselMoney(int index) {

    }

    @Override
    public void showMoneyDialog() {

    }
}