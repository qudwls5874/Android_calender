package com.example.myapplication.dialog.list;

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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.database.viewmodel.MenuListViewModel;
import com.example.myapplication.databinding.DialogServiceAddlistBinding;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;
import com.example.myapplication.event.WatcherSearchText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceListHDialog extends DialogFragment implements View.OnClickListener, WatcherSearchText.OnSearchChangeListener{

    private String tag ;

    private DialogServiceAddlistBinding binding;
    private MenuListViewModel viewModel;
    private LoadingDialog loading;

    private ServiceListHAdapter adapter;
    private ArrayList<MenuJoin> list;
    private ArrayList<MenuJoin> filterList;
    private ArrayList<MenuJoin> checkList;
    private ServiceListDAdapter.ItemClickLisner itemClickLisner;

    /* data */
    private int workFg;

    public ServiceListHDialog(int workFg, ServiceListDAdapter.ItemClickLisner itemClickLisner){
        this.workFg = workFg;
        this.itemClickLisner = itemClickLisner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = com.example.myapplication.databinding.DialogServiceAddlistBinding.inflate(inflater, container, false);

        initUI();
        initData();

        return binding.getRoot();
    }

    private void initUI() {

        // 로딩
        loading = new LoadingDialog(getContext());

        // 키보드
        HideKeyboardHelperDialog.setupUI(binding.getRoot(), super.getDialog());

        // 입력 리스너
        binding.settingServiceListSearchEditText.addTextChangedListener(new WatcherSearchText(this::onSearchTextChanged));

        // 스와이프 닫기
        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddTopLayout.setOnTouchListener(swipeDismissTouchListener);

        // 클릭 리스너
        binding.settingServiceListaddCloseBtn.setOnClickListener(this::onClick);     // 닫기 버튼
        binding.settingServiceListEraseBtn.setOnClickListener(this::onClick);        // 지우기 버튼

        // 필요없는 버튼 가리기
        binding.settingServiceListBtnLayout.setVisibility(View.GONE);
        binding.settingServiceListBtnView.setVisibility(View.VISIBLE);
        binding.settingServiceListaddChangeBtn.setVisibility(View.GONE);

        // 어댑터
        list = new ArrayList<>();
        filterList = new ArrayList<>();
        checkList = new ArrayList<>();
        adapter = new ServiceListHAdapter(filterList, itemClickLisner, this);
        binding.settingServiceListRecyclerView.setAdapter(adapter);
        binding.settingServiceListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 뷰모델
        viewModel = new ViewModelProvider(this).get(MenuListViewModel.class);

    }

    private void initData() {
        viewModel.getList().observe(this, this::setDataList);
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
    public void onResume() {
        super.onResume();
        loading.show();
        viewModel.getSelectList(workFg);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == binding.settingServiceListaddCloseBtn.getId()){
            dismiss();  // 닫기버튼
        } else if (v.getId() == binding.settingServiceListEraseBtn.getId()) {
            // 검색 지우기 버튼
            binding.settingServiceListSearchEditText.setText("");
        }

    }



    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        filterList.clear();
        binding.settingServiceListaddDeleteBtn.setTextColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.textBeNormalColor));

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


    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager, tag);
        this.tag = tag;
    }
}
