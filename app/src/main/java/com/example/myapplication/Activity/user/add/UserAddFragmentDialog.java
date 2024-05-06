package com.example.myapplication.Activity.user.add;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Activity.user.add.cash.UserCashFgAdapter;
import com.example.myapplication.Activity.user.add.coupon.UserCouponFgAdapter;
import com.example.myapplication.Activity.user.add.schedule.UserServiceFgAdapter;
import com.example.myapplication.R;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.schedule.ScheduleCalendarH;
import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.database.view.CashJoin;
import com.example.myapplication.database.view.CouponJoin;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.database.view.schedule.CalendarHJoin;
import com.example.myapplication.database.viewmodel.UserViewModel;
import com.example.myapplication.databinding.DialogUserAddBinding;
import com.example.myapplication.dataclass.UserProfile;
import com.example.myapplication.event.HideKeyboardHelperDialog;
import com.example.myapplication.event.SwipeDismissTouchListener;
import com.example.myapplication.photoediting.ImageUtils;
import com.example.myapplication.settingpermissions.PermissionManager;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserAddFragmentDialog extends DialogFragment implements View.OnClickListener {

    private DialogUserAddBinding binding;

    private ArrayList<CalendarHJoin> calendarHJoins;
    private UserServiceFgAdapter userServiceFgAdapter;
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

    // 권한설정
    private PermissionManager permissionManager;
    private ActivityResultLauncher<String> galleryLauncher;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUserAddBinding.inflate(inflater, container, false);

        permissionManager = new PermissionManager(
                requireContext(),
                registerForActivityResult(
                        new ActivityResultContracts.RequestPermission(),
                        this::onPermissionResult)
        );

        // 갤러리 런처 초기화
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        userProfile.setProifle(
                                ImageUtils.decodeSampledBitmap(getContext(), result, 200, 200)
                        );
                        if (userProfile.getProifle() != null){
                            // 동그란 프로필 이미지 생성
                            Bitmap circleBitmap = Bitmap.createBitmap(userProfile.getProifle().getWidth(), userProfile.getProifle().getHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(circleBitmap);
                            Paint paint = new Paint();
                            // 안티앨리어싱을 사용하여 부드럽고 곡선적인 효과를 부여함
                            paint.setAntiAlias(true);
                            // 원 모양의 경계를 그리기 위해 Canvas에 원을 그림
                            canvas.drawCircle(userProfile.getProifle().getWidth() / 2f, userProfile.getProifle().getHeight() / 2f, userProfile.getProifle().getWidth() / 2f, paint);
                            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                            // 원 모양으로 자른 이미지를 Canvas에 그림
                            canvas.drawBitmap(userProfile.getProifle(), 0, 0, paint);

                            binding.userAddProfileImageView.setImageBitmap(circleBitmap);
                        } else {
                            binding.userAddProfileImageView.setImageResource(R.drawable.ic_person_image);
                        }
                    }
                });

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
        calendarHJoins = new ArrayList<>();
        userServiceFgAdapter = new UserServiceFgAdapter(calendarHJoins, getParentFragmentManager());
        binding.userAddServiceRecyclerView.setAdapter(userServiceFgAdapter);
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
            CalendarHJoin cal = new CalendarHJoin();
            cal.scheduleCalendarH = new ScheduleCalendarH(String.valueOf(LocalDate.now()));
            cal.calendarDJoins = new ArrayList<>();
            cal.menuCategory = new ArrayList<>();

            calendarHJoins.add(cal);

            userServiceFgAdapter.notifyDataSetChanged();
        } else if (v.getId() == binding.userAddCashAddBtn.getId()) {
            // 금액충전 클릭
//            CashJoin cal = new CashJoin();
//            cal.userCash = new UserCash(String.valueOf(LocalDate.now()), 0, 0);
//            cal.menuCategory = new MenuCategory();
//            cal.menuList = new MenuList();
//
//            cashList.add(cal);
            cashFgAdapter.notifyDataSetChanged();

        } else if (v.getId() == binding.userAddCouponAddBtn.getId()) {
            // 쿠폰 클릭
//            CouponJoin cal = new CouponJoin();
//            cal.userCoupon = new UserCoupon(String.valueOf(LocalDate.now()), "", 0, 0);
//            cal.menuCategory = new MenuCategory();
//            cal.menuList = new MenuList();
//
//            couponList.add(cal);
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
            userJoin.calendarHJoins = calendarHJoins;
            userJoin.userCashes = cashList;
            userJoin.userCoupons = couponList;

            UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            userViewModel.setUser(userJoin, userProfile);


        } else if (v.getId() == binding.userAddProfileBtn.getId()) {
            boolean imageCheck;
            // 사진
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                // 안드로이드 13 이하
                imageCheck = permissionManager.requestPermission(Manifest.permission.READ_MEDIA_IMAGES);
            } else {
                // 안드로이드 13 이상
                imageCheck = permissionManager.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (imageCheck){
                openGallery();
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 100);
            }

        } else if (v.getId() == binding.userAddCloseBtn.getId()) {
            dismiss();
        }
    }

    // 갤러리 열기
    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    // 비트맵을 파일로 저장
    private void saveBitmap(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), imageUri);
            // 내부 저장소에 저장할 파일의 Uri 생성
            String fileName = "my_image.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri outputFileUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // 비트맵을 OutputStream을 사용하여 파일로 저장
            OutputStream outputStream = requireContext().getContentResolver().openOutputStream(outputFileUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();

            Toast.makeText(requireContext(), "이미지가 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "이미지를 저장하는 데 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }


    private void onPermissionResult(boolean result){
        if (result){
            // 권한이 허용됐을 때 처리할 작업
            Toast.makeText(getContext(), "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 권한이 거부됐을 때 처리할 작업
            Toast.makeText(getContext(), "권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }


}