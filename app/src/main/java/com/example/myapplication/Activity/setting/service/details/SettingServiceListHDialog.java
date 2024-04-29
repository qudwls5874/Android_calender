package com.example.myapplication.Activity.setting.service.details;

import android.app.Dialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.database.viewmodel.MenuListViewModel;
import com.example.myapplication.databinding.DialogServiceAddlistBinding;
import com.example.myapplication.dialog.LoadingDialog2;
import com.example.myapplication.dialog.ServiceAddDialog;
import com.example.myapplication.dialog.ServiceUpdateDialog;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;
import com.example.myapplication.event.WatcherSearchText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingServiceListHDialog extends DialogFragment implements    View.OnClickListener,
                                                                            WatcherSearchText.OnSearchChangeListener,
                                                                            ServiceAddDialog.SetMenuListLisner,
                                                                            ServiceUpdateDialog.SetMenuUpdateLisner,
                                                                            SettingServiceListDAdapter.setOnclickColorChangedLisner{

    public static boolean layoutCheck = false;

    private DialogServiceAddlistBinding binding;
    private MenuListViewModel viewModel;
    private LoadingDialog2 loading;

    private SettingServiceListHAdapter adapter;
    private ArrayList<MenuJoin> list = new ArrayList<>();
    private ArrayList<MenuJoin> filterList = new ArrayList<>();
    private ArrayList<MenuList> delList = new ArrayList<>();

    private int category = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogServiceAddlistBinding.inflate(inflater, container, false);

        initUI();
        initData();

        return binding.getRoot();
    }

    private void initUI() {

        // 로딩
        loading = new LoadingDialog2(getContext());

        // 키보드
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        // 입력 리스너
        binding.settingServiceListSearchEditText.addTextChangedListener(new WatcherSearchText(this::onSearchTextChanged));

        // 스와이프 닫기
        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        // 클릭 리스너
        binding.settingServiceListaddCloseBtn.setOnClickListener(this::onClick);     // 닫기 버튼
        binding.settingServiceListaddCancelBtn.setOnClickListener(this::onClick);    // 취소 버튼
        binding.settingServiceListaddChangeBtn.setOnClickListener(this::onClick);    // 편집 버튼
        binding.settingServiceListaddDeleteBtn.setOnClickListener(this::onClick);    // 삭제 버튼
        binding.settingServiceListEraseBtn.setOnClickListener(this::onClick);        // 지우기 버튼
        binding.settingServiceListBtn1.setOnClickListener(this);
        binding.settingServiceListBtn2.setOnClickListener(this);
        binding.settingServiceListBtn3.setOnClickListener(this);

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
        list.addAll(result);
        onSearchTextChanged(String.valueOf(binding.settingServiceListSearchEditText.getText()), null);
        loading.dismiss();
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
            dismiss();  // 닫기버튼
        } else if (v.getId() == binding.settingServiceListaddCancelBtn.getId()) {
            setLayoutChanged(); // 취소 버튼
        } else if (v.getId() == binding.settingServiceListaddChangeBtn.getId()) {
            setLayoutChanged(); // 편집 버튼
        } else if (v.getId() == binding.settingServiceListaddDeleteBtn.getId()) {
            // 삭제 버튼
            if (binding.settingServiceListaddDeleteBtn.getCurrentTextColor() == ContextCompat.getColor(getContext(), R.color.textBeNormalColor))
                return;

            loading.show();
            viewModel.setDeleteList(delList, category);

            setLayoutChanged();

        } else if (v.getId() == binding.settingServiceListEraseBtn.getId()) {
            // 검색 지우기 버튼
            binding.settingServiceListSearchEditText.setText("");
        } else if (v.getId() == binding.settingServiceListBtn1.getId()){
            binding.settingServiceListBtn1.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button_gray));
            binding.settingServiceListBtn2.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            binding.settingServiceListBtn3.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            setCategoryChanged(0);
        } else if (v.getId() == binding.settingServiceListBtn2.getId()){
            binding.settingServiceListBtn1.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            binding.settingServiceListBtn2.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button_gray));
            binding.settingServiceListBtn3.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            setCategoryChanged(1);
        } else if (v.getId() == binding.settingServiceListBtn3.getId()){
            binding.settingServiceListBtn1.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            binding.settingServiceListBtn2.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button));
            binding.settingServiceListBtn3.setBackground(binding.getRoot().getContext().getDrawable(R.drawable.rounded_button_gray));
            setCategoryChanged(2);
        }

    }

    private void setCategoryChanged(int category){
        loading.show();

        // 기본상태로
        binding.settingServiceListaddCancelBtn.setVisibility(View.GONE);
        binding.settingServiceListaddDeleteBtn.setVisibility(View.GONE);
        binding.settingServiceListaddCloseBtn.setVisibility(View.VISIBLE);
        binding.settingServiceListaddChangeBtn.setVisibility(View.VISIBLE);
        layoutCheck = false;

        this.category = category;
        viewModel.getSelectList(this.category);
    }

    private void setLayoutChanged(){
        // layoutCheck = false > 기본상태, layoutCheck = true > 편집상태
        if (!layoutCheck){
            // 편집상태로
            binding.settingServiceListaddCancelBtn.setVisibility(View.VISIBLE); // 취소
            binding.settingServiceListaddDeleteBtn.setVisibility(View.VISIBLE); // 삭제
            binding.settingServiceListaddCloseBtn.setVisibility(View.GONE);     // 닫기
            binding.settingServiceListaddChangeBtn.setVisibility(View.GONE);    // 편집
            binding.settingServiceListaddDeleteBtn.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textBeNormalColor));
            delList.clear();
            layoutCheck = true;
        } else {
            // 기본상태로
            binding.settingServiceListaddCancelBtn.setVisibility(View.GONE);
            binding.settingServiceListaddDeleteBtn.setVisibility(View.GONE);
            binding.settingServiceListaddCloseBtn.setVisibility(View.VISIBLE);
            binding.settingServiceListaddChangeBtn.setVisibility(View.VISIBLE);
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
        binding.settingServiceListaddDeleteBtn.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textBeNormalColor));
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
        }
        // 변경된 목록을 RecyclerView에 반영합니다.
        adapter.notifyDataSetChanged();
    }

    // 저장값 리스너
    @Override
    public void setMenuListLisner(MenuList menuList) {
        viewModel.setList(menuList, category);
    }

    // 수정값 리스너
    @Override
    public void setUdateLisner(MenuList menuList, Dialog dialog) {
        viewModel.setUpdate(menuList, category);
        Toast.makeText(getContext(), "저장 되었습니다.", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    // 삭제값 등록 리스너
    @Override
    public void setOnclickColorChangedLisner(MenuList menuList) {
        if (delList.contains(menuList)){
            delList.remove(menuList);
        } else {
            delList.add(menuList);
        }

        binding.settingServiceListaddDeleteBtn.setTextColor(
                delList.size() > 0 ?
                        ContextCompat.getColor(binding.getRoot().getContext(), R.color.textPlusColor)
                        : ContextCompat.getColor(binding.getRoot().getContext(), R.color.textBeNormalColor)
        );

    }


}
