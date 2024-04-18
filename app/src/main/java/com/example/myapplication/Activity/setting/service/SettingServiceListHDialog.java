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
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.database.viewmodel.MenuListViewModel;
import com.example.myapplication.databinding.DialogServiceAddlistBinding;
import com.example.myapplication.dialog.ServiceAddDialog;
import com.example.myapplication.dialog.ServiceUpdateDialog;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;
import com.example.myapplication.event.WatcherSearchText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SettingServiceListHDialog extends DialogFragment implements    View.OnClickListener,
                                                                            WatcherSearchText.OnSearchChangeListener,
                                                                            ServiceAddDialog.SetMenuListLisner,
                                                                            ServiceUpdateDialog.SetMenuUpdateLisner,
                                                                            SettingServiceListDAdapter.setOnclickColorChangedLisner {

    public static boolean layoutCheck = false;

    private DialogServiceAddlistBinding binding;
    private MenuListViewModel viewModel;

    private SettingServiceListHAdapter adapter;
    private ArrayList<MenuJoin> list = new ArrayList<>();
    private ArrayList<MenuJoin> filterList = new ArrayList<>();
    private ArrayList<MenuList> delList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogServiceAddlistBinding.inflate(inflater, container, false);

        initUI();
        initData();

        return binding.getRoot();
    }

    private void initUI() {

        // 키보드
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        // 입력 리스너
        binding.settingServiceListSearchEditText.addTextChangedListener(new WatcherSearchText(this::onSearchTextChanged));

        // 스와이프 닫기
        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        // 클릭 리스너
        binding.settingServiceListaddCloseBtn.setOnClickListener(this);
        binding.settingServiceListaddSaveBtn.setOnClickListener(this);
        binding.settingServiceListCanselBtn.setOnClickListener(this);

        // 어댑터
        adapter = new SettingServiceListHAdapter(filterList, this, this, this);
        binding.settingServiceListRecyclerView.setAdapter(adapter);
        binding.settingServiceListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void initData() {
        viewModel = new ViewModelProvider(this).get(MenuListViewModel.class);
        viewModel.getList().observe(getViewLifecycleOwner(), this::setDataList);
    }

    private void setDataList(List<MenuJoin> result){
        list.clear();
        delList.clear();
        list.addAll(result);
        onSearchTextChanged(String.valueOf(binding.settingServiceListSearchEditText.getText()), null);
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

        if (v.getId() == binding.settingServiceListaddCloseBtn.getId()){
            // 닫기, 취소 버튼
            if (layoutCheck){
                setLayoutChanged();
            } else {
                dismiss();
            }

        } else if (v.getId() == binding.settingServiceListaddSaveBtn.getId()) {
            // 편집, 저장 버튼
            if (layoutCheck){
                viewModel.setDeleteList(delList);
            }
            setLayoutChanged();
        } else if (v.getId() == binding.settingServiceListCanselBtn.getId()) {
            // 검색 지우기 버튼
            binding.settingServiceListSearchEditText.setText("");
        }
    }

    private void setLayoutChanged(){
        // layoutCheck = false > 기본상태, layoutCheck = true > 편집상태
        if (!layoutCheck){
            binding.settingServiceListaddCloseBtn.setText("취소");
            binding.settingServiceListaddSaveBtn.setText("저장");
            layoutCheck = true;
        } else {
            binding.settingServiceListaddCloseBtn.setText("닫기");
            binding.settingServiceListaddSaveBtn.setText("편집");
            layoutCheck = false;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (layoutCheck)
            setLayoutChanged();
    }

    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        filterList.clear();
        delList.clear();
        if (newText.isEmpty()) {
            // 검색 쿼리가 비어 있으면 전체 목록을 보여줍니다.
            filterList.addAll(list);
        } else {
            // 검색어가 있을 때는 각 항목을 검색하여 필터링합니다.
            for (MenuJoin menuJoin : list) {
                // 메뉴 리스트를 순회하면서 검색어를 포함하는 항목을 찾습니다.
                List<MenuList> filteredMenuLists = menuJoin.menuLists.stream()
                        .filter(data -> data.getMenuName().toLowerCase().contains(newText.toLowerCase()))
                        .collect(Collectors.toList());

                // 필터링된 결과가 비어있지 않다면 새로운 필터링된 메뉴 조인을 생성하여 필터링된 목록에 추가합니다.
                if (!filteredMenuLists.isEmpty()) {
                    MenuJoin filteredMenuJoin = new MenuJoin();
                    filteredMenuJoin.menuCategory = menuJoin.menuCategory;
                    filteredMenuJoin.menuLists = filteredMenuLists;
                    filterList.add(filteredMenuJoin);
                }
            }

            /*
            for (int i = 0; i < list.size(); i++){
                MenuCategory category = new MenuCategory(list.get(i).menuCategory);
                List<MenuList> menuLists = new ArrayList<>();
                for (MenuList data : list.get(i).menuLists){
                    if (data.getMenuName().toLowerCase().contains(newText.toLowerCase())) {
                        menuLists.add(data);
                    }
                }
                MenuJoin menuJoin = new MenuJoin();
                menuJoin.menuCategory = category;
                menuJoin.menuLists = menuLists;
                filterList.add(menuJoin);
            }
            */
        }
        // 변경된 목록을 RecyclerView에 반영합니다.
        adapter.notifyDataSetChanged();
    }

    // 저장값 리스너
    @Override
    public void setMenuListLisner(MenuList menuList) {
        viewModel.setList(menuList);
    }

    // 수정값 리스너
    @Override
    public void setUdateLisner(MenuList menuList) {

    }

    // 삭제값 등록 리스너
    @Override
    public void setOnclickColorChangedLisner(MenuList menuList) {
        if (delList.contains(menuList)){
            delList.remove(menuList);
        } else {
            delList.add(menuList);
        }
    }



}
