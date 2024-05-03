package com.example.myapplication.Activity.user.add;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.setting.tel.SettingTelMainDialog;
import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.table.ScheduleCalendar;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.UserCash;
import com.example.myapplication.database.table.UserCoupon;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.database.view.CalendarJoin;
import com.example.myapplication.database.view.CashJoin;
import com.example.myapplication.database.view.CouponJoin;
import com.example.myapplication.database.view.TelJoin;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.database.viewmodel.UserViewModel;
import com.example.myapplication.databinding.DialogUserAddBinding;
import com.example.myapplication.dataclass.UserProfile;
import com.example.myapplication.dialog.LoadingDialog;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserAddFragmentDialog extends DialogFragment implements View.OnClickListener {

    private DialogUserAddBinding binding;

    private ArrayList<CalendarJoin> scList;
    private UserServiceFgAdapter scAdapter;
    private ArrayList<CashJoin> cashList;
    private UserCashFgAdapter cashFgAdapter;
    private ArrayList<CouponJoin> couponList;
    private UserCouponFgAdapter couponFgAdapter;

    /* 회원정보 */
    private ArrayList<UserTel> userTels;
    private UserTelFgAdapter userTelFgAdapter;
    private ArrayList<UserAddress> userAddresses;
    private UserAddressFgAdapter userAddressFgAdapter;
    private ArrayList<UserEvent> userEvents;
    private UserEventFgAdapter userEventFgAdapter;

    // 사진
    private UserProfile userProfile;


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
        binding.userAddTelAddBtn.setOnClickListener(this);
        binding.userAddAddressAddBtn.setOnClickListener(this);
        binding.userAddEventAddBtn.setOnClickListener(this);
        binding.userAddCloseBtn.setOnClickListener(this);
        binding.userAddInsertBtn.setOnClickListener(this);
        binding.userAddProfileBtn.setOnClickListener(this);


        // 서비스
        scList = new ArrayList<>();
        scAdapter = new UserServiceFgAdapter(scList, getParentFragmentManager());
        binding.userAddServiceRecyclerView.setAdapter(scAdapter);
        binding.userAddServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 금액충전
        cashList = new ArrayList<>();
        cashFgAdapter = new UserCashFgAdapter(cashList, getParentFragmentManager());
        binding.userAddCashRecyclerView.setAdapter(cashFgAdapter);
        binding.userAddCashRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 쿠폰
        couponList = new ArrayList<>();
        couponFgAdapter = new UserCouponFgAdapter(couponList, getParentFragmentManager());
        binding.userAddCouponRecyclerView.setAdapter(couponFgAdapter);
        binding.userAddCouponRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 번호
        userTels = new ArrayList<>();
        userTelFgAdapter = new UserTelFgAdapter(userTels, getParentFragmentManager());
        binding.userAddTelRecyclerView.setAdapter(userTelFgAdapter);
        binding.userAddTelRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 주소
        userAddresses = new ArrayList<>();
        userAddressFgAdapter = new UserAddressFgAdapter(userAddresses, getParentFragmentManager());
        binding.userAddAddressRecyclerView.setAdapter(userAddressFgAdapter);
        binding.userAddAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 일정
        userEvents = new ArrayList<>();
        userEventFgAdapter = new UserEventFgAdapter(userEvents, getParentFragmentManager());
        binding.userAddEventRecyclerView.setAdapter(userEventFgAdapter);
        binding.userAddEventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 사진
        userProfile = new UserProfile();


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
            params.height = (int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.96);
            window.setAttributes(params);

            // UI 하단 정렬
            window.setGravity(Gravity.BOTTOM);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        if (v.getId() == binding.userAddServiceAddBtn.getId()){
            // 서비스 클릭
            CalendarJoin cal = new CalendarJoin();
            cal.scheduleCalendar = new ScheduleCalendar(String.valueOf(LocalDate.now()), "", 0,0);
            cal.menuCategory = new MenuCategory();
            cal.menuList = new MenuList();

            scList.add(cal);
            scAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddCashAddBtn.getId()) {
            // 금액충전 클릭
            CashJoin cal = new CashJoin();
            cal.userCash = new UserCash(String.valueOf(LocalDate.now()), 0, 0);
            cal.menuCategory = new MenuCategory();
            cal.menuList = new MenuList();

            cashList.add(cal);
            cashFgAdapter.notifyDataSetChanged();

        } else if (v.getId() == binding.userAddCouponAddBtn.getId()) {
            // 쿠폰 클릭
            CouponJoin cal = new CouponJoin();
            cal.userCoupon = new UserCoupon(String.valueOf(LocalDate.now()), "", 0, 0);
            cal.menuCategory = new MenuCategory();
            cal.menuList = new MenuList();

            couponList.add(cal);
            couponFgAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddTelAddBtn.getId()){
            // 번호 추가
            userTels.add(new UserTel("",""));
            userTelFgAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddAddressAddBtn.getId()){
            // 주소 추가
            userAddresses.add(new UserAddress("",""));
            userAddressFgAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddEventAddBtn.getId()) {
            // 일정 추가
            userEvents.add(new UserEvent("",""));
            userEventFgAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddInsertBtn.getId()) {
            // 저장
            UserJoin userJoin = new UserJoin();
            userJoin.user = new User(
                    "",
                    binding.userAddNameEdit.getText().toString(),
                    binding.userAddTelEdit.getText().toString(),
                    binding.userAddAddressEdit.getText().toString(),
                    binding.userAddEventEdit.getText().toString(),
                    "0",
                    0
                    );
            userJoin.userTelList = userTels;
            userJoin.userAddressList = userAddresses;
            userJoin.userEventsList = userEvents;
            userJoin.scheduleCalendars = scList;
            userJoin.userCashes = cashList;
            userJoin.userCoupons = couponList;

            UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
//            userViewModel.setUser(userJoin);

        } else if (v.getId() == binding.userAddProfileBtn.getId()) {
            // 사진
            requestContactsPermission();

        } else if (v.getId() == binding.userAddCloseBtn.getId()) {
            dismiss();
        }
    }




    // 사진첩 권한 설정
    private void requestContactsPermission() {
        // 권한이 없는 경우 권한 요청
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // 최초실행
                requestPStringActivityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            } else {
                // 이전에 승인거절
                showPermissionDeniedDialog();
            }
        } else {
            showLoadingAndFetchContacts();
        }
    }

    // 이전에 승인거절
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder perBuilder = new AlertDialog.Builder(getContext());
        perBuilder.setTitle("권한 설정")
                .setMessage("권한 거절로 인해 일부기능이 제한됩니다.")
                .setPositiveButton("권한 설정하러 가기", (dialog1, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("취소", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .show();
    }

    // 최초 권한설정에서 버튼 눌렀을시
    private ActivityResultLauncher<String> requestPStringActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    showLoadingAndFetchContacts();
                } else {
                    Log.i("result2", result.toString());
                }
            }
    );


    // 사진첩 가져오기
    private void showLoadingAndFetchContacts() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
    }



}
















