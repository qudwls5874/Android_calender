package com.example.myapplication.Activity.setting.service;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.myapplication.databinding.DialogServiceAddlistBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingServiceListDialog extends DialogFragment {


    private DialogServiceAddlistBinding binding;
    private MenuListViewModel viewModel;

    private SettingServiceListAdapter adapter;
    private ArrayList<MenuJoin> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogServiceAddlistBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {


        viewModel = new ViewModelProvider(this).get(MenuListViewModel.class);

        viewModel.getList().observe(getViewLifecycleOwner(), menuJoins -> {
            setDataList(menuJoins);
        });



    }

    private void setDataList(List<MenuJoin> result){
        if (list == null)
            list = new ArrayList<>();

        list.addAll(result);
        Log.i("갯수", ""+list.size());
        binding.settingServiceListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SettingServiceListAdapter(list);
        binding.settingServiceListRecyclerView.setAdapter(adapter);
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


}
