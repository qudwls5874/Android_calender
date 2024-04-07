package com.example.myapplication.Activity.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.database.table.User;
import com.example.myapplication.event.HideKeyboardHelperActivity;
import com.example.myapplication.databinding.FragmentUserBinding;
import com.example.myapplication.event.SearchTextWatcher;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment implements View.OnClickListener, SearchTextWatcher.OnSearchChangeListener {

    // 뷰 바인딩
    private FragmentUserBinding binding;

    // 뷰모델
    private UserViewModel userViewModel;

    // 어댑터
    private UserAdapter userAdapter;
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<User> filterList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);

        initData();
        // user_search_text
        return binding.getRoot();
    }

    private void initData() {

        // 키보드 숨기기
        HideKeyboardHelperActivity.setupUI(binding.getRoot(), getActivity());

        // 클릭 리스너 이벤트
        binding.userAddBtn.setOnClickListener(this);

        // 입력 리스너 이벤트
        binding.userSearchText.addTextChangedListener(new SearchTextWatcher(this));

        // 어댑터
        userAdapter = new UserAdapter(getActivity(), R.layout.view_user_item_row, filterList);
        binding.recyclerView.setAdapter(userAdapter);

        // 뷰모델 초기화
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // 사용자 목록 LiveData를 관찰하여 업데이트가 발생할 때마다 RecyclerView에 데이터를 설정
        userViewModel.getUserList().observe(getViewLifecycleOwner(),users -> {
            updateUserProfileList(users);
        });

    }

    private void updateUserProfileList(List<User> users) {
        userList.clear();
        userList.addAll(users);
        onSearchTextChanged(String.valueOf(binding.userSearchText.getText()));
    }

    @Override
    public void onClick(View v) {
        /* 저장버튼 */
        if (v.getId() == binding.userAddBtn.getId()) {
            UserAddFragmentDialog fragmentDialog = new UserAddFragmentDialog();
            fragmentDialog.show(getParentFragmentManager(), "user_add_dialog");
            /*
            fragmentDialog.show(getSupportFragmentManager());
            UserAddDialog dialog = new UserAddDialog(getContext(), userViewModel);
            // UserAddDialog dialog = new UserAddDialog(getContext(), R.layout.dialog_user_add);
            dialog.show();
            */
        }
    }

    @Override
    public void onSearchTextChanged(String newText) {

        filterList.clear();
        if (newText.isEmpty()) {
            filterList.addAll(userList); // 검색 쿼리가 비어 있으면 전체 목록을 보여줍니다.
        } else {
            for (User user : userList) {
                if (user.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filterList.add(user); // 사용자 이름이 검색 쿼리와 일치하는 경우에만 추가합니다.
                }
            }
        }
        userAdapter.notifyDataSetChanged(); // 변경된 목록을 RecyclerView에 반영합니다.
    }

}