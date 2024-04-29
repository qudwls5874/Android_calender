package com.example.myapplication.Activity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.database.viewmodel.UserViewModel;
import com.example.myapplication.event.HideKeyboardHelper;
import com.example.myapplication.databinding.FragmentUserBinding;
import com.example.myapplication.event.WatcherSearchText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UserFragment extends Fragment implements View.OnClickListener, WatcherSearchText.OnSearchChangeListener {

    // 뷰 바인딩
    private FragmentUserBinding binding;

    // 뷰모델
    private UserViewModel userViewModel;

    // 어댑터
    private UserFragmentAdapter userAdapter;
    private ArrayList<UserJoin> userList = new ArrayList<>();
    private ArrayList<UserJoin> filterList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);

        initUI();
        initData();

        return binding.getRoot();
    }

    private void initUI() {
        // 키보드 숨기기
        HideKeyboardHelper.setupUI(binding.getRoot(), getActivity());

        // 클릭 리스너 이벤트
        binding.userAddBtn.setOnClickListener(this);

        // 입력 리스너 이벤트
        binding.userSearchText.addTextChangedListener(new WatcherSearchText(this));
        binding.userSearchEraseBtn.setOnClickListener(this);

        // 어댑터
        userAdapter = new UserFragmentAdapter(filterList);
        binding.recyclerView.setAdapter(userAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 뷰모델 초기화
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    private void initData() {
        userViewModel.getObUserJoinList().observe(getViewLifecycleOwner(), this::updateUserProfileList);
    }

    private void updateUserProfileList(List<UserJoin> users) {
        userList.clear();
        userList.addAll(users);
        onSearchTextChanged(String.valueOf(binding.userSearchText.getText()), null);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == binding.userAddBtn.getId()) {
            /* 저장버튼 */
            UserAddFragmentDialog fragmentDialog = new UserAddFragmentDialog();
            fragmentDialog.show(getParentFragmentManager(), "user_add_dialog");
        } else if (v.getId() == binding.userSearchEraseBtn.getId()){
            /* 검색어 지우기 */
            binding.userSearchText.setText("");
        }
    }

    @Override
    public void onSearchTextChanged(String newText, Integer index) {

        filterList.clear();
        if (newText.isEmpty()) {
            filterList.addAll(userList); // 검색 쿼리가 비어 있으면 전체 목록을 보여줍니다.
        } else {
            filterList.addAll(
                    userList.stream()
                            .filter(strData -> strData.user.getName().toLowerCase().contains(newText.toLowerCase()))
                            .collect(Collectors.toList())
            );
        }
        userAdapter.notifyDataSetChanged(); // 변경된 목록을 RecyclerView에 반영합니다.
    }


    @Override
    public void onStart() {
        super.onStart();
        updateUserProfileList(userViewModel.getUserJoinList());
    }

}