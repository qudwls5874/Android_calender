package com.example.myapplication.Activity.setting.service;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.viewmodel.MenuCategoryViewModel;
import com.example.myapplication.databinding.DialogSettingServiceBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingServiceMainDialog extends DialogFragment implements View.OnClickListener, SettingServiceMainChangeAdapter.OnItemCheckedChangeLisner {

    private DialogSettingServiceBinding binding;
    private SettingServiceMainChangeAdapter settingServiceChangeAdapter;
    private MenuCategoryViewModel viewModel;
    private List<MenuCategory> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogSettingServiceBinding.inflate(inflater, container, false);

        initData();

        return binding.getRoot();
    }

    private void initData() {

        binding.settingServiceMainCloseBtn.setOnClickListener(this);
        binding.settingServiceMainAddBtn.setOnClickListener(this);
        binding.settingServiceMainChangeTextView.setOnClickListener(this);
        binding.settingServiceMainAddTextView.setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(MenuCategoryViewModel.class);

        viewModel.getList().observe(getViewLifecycleOwner(), resultList ->{
            setDataList(resultList);
        });

    }

    private void setDataList(List<MenuCategory> result){
        list = result;
        binding.settingServiceChangeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        settingServiceChangeAdapter = new SettingServiceMainChangeAdapter(list, this);
        binding.settingServiceChangeRecyclerView.setAdapter(settingServiceChangeAdapter);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // XML 레이아웃을 사용하여 다이얼로그의 크기를 지정합니다.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // 다이얼로그의 배경을 투명하게 설정합니다.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == binding.settingServiceMainCloseBtn.getId()){
            // 닫기
            if (binding.settingServiceMainAddBtn.getVisibility() == View.VISIBLE){
                binding.settingServiceMainCloseBtn.setText("닫기");
                binding.settingServiceMainAddBtn.setVisibility(View.GONE);
                binding.settingServiceMainLinearLayout.setVisibility(View.VISIBLE);
                binding.settingServiceChangeLinearLayout.setVisibility(View.GONE);
            } else {
                dismiss();
            }
        } else if (v.getId() == binding.settingServiceMainAddBtn.getId()) {
            // 저장
            viewModel.setList(list);
            Toast.makeText(getContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == binding.settingServiceMainAddTextView.getId()){
            // 항목 추가
            SettingServiceListHDialog listDialog = new SettingServiceListHDialog();
            listDialog.show(getParentFragmentManager(), "setting_list_dialog");
        } else if (v.getId() == binding.settingServiceMainChangeTextView.getId()) {
            // 항목 선택(바꾸기)
            binding.settingServiceMainCloseBtn.setText("＜");
            binding.settingServiceMainAddBtn.setVisibility(View.VISIBLE);
            binding.settingServiceMainLinearLayout.setVisibility(View.GONE);
            binding.settingServiceChangeLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void OnitemCheckedChangeLisner(int index, boolean checked) {
        list.get(index).setMenuCategoryYn(checked?"Y":"N");
    }
}
