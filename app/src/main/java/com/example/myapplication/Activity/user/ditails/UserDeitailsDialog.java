package com.example.myapplication.Activity.user.ditails;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
import com.example.myapplication.database.view.CalendarJoin;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.database.viewmodel.ScheduleCalendarViewModel;
import com.example.myapplication.databinding.DialogUserDBinding;
import com.example.myapplication.event.SwipeDismissTouchListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserDeitailsDialog extends DialogFragment implements View.OnClickListener {

    private DialogUserDBinding binding;
    private UserJoin userJoin;

    private ScheduleCalendarViewModel viewModel;
    private UserDetailsAdapter adapter;
    private List<CalendarJoin> calendarJoins;

    public UserDeitailsDialog(UserJoin userJoin) {
        this.userJoin = userJoin;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogUserDBinding.inflate(inflater, container, false);
        initUI();
        initData();
        return binding.getRoot();
    }

    private void initUI() {

        SwipeDismissTouchListener swipeDismissTouchListener = new SwipeDismissTouchListener(getDialog().getWindow().getDecorView(), () -> dismiss());
        binding.userAddDTopLayout.setOnTouchListener(swipeDismissTouchListener);

        // 사용자 이미지
        String fileName = userJoin.user.getUserId() + ".jpg";
        File file = new File(binding.getRoot().getContext().getFilesDir(), fileName);
        if (file.exists()){
            // 변경 가능한(mutable) 비트맵 생성
            Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
            Bitmap circleBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(image.getWidth() / 2f, image.getHeight() / 2f, image.getWidth() / 2f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(image, 0, 0, paint);

            binding.userAddDProfile.setImageBitmap(circleBitmap);
        } else {
            binding.userAddDProfile.setImageResource(R.drawable.ic_person_image);
        }

        binding.userAddDNameTextView.setText(userJoin.user.getName());
        binding.userAddDTelTextView.setText(userJoin.userTelList.size() > 0 ? userJoin.userTelList.get(0).getTelNumber() : "");
        binding.userAddDAddressTextView.setText(userJoin.userAddressList.size() > 0 ? userJoin.userAddressList.get(0).getAddressName() : "");
        binding.userAddDEventTextView.setText(userJoin.userEventsList.size() > 0 ? userJoin.userEventsList.get(0).getEventName() : "");

        // 뷰모델 객체생성
        viewModel = new ViewModelProvider(this).get(ScheduleCalendarViewModel.class);

        calendarJoins = new ArrayList<>();
        adapter = new UserDetailsAdapter(calendarJoins);
        binding.userAddDRecyclerView.setAdapter(adapter);
        binding.userAddDRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.userAddDCloseBtn.setOnClickListener(this);

    }

    private void initData() {
        viewModel.getLiveList().observe(this, results -> {
            Log.i("데이터?", "들어오냥?" + results.size());
        });

        viewModel.getScheduleCalendar(userJoin.user.getUserId());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == binding.userAddDCloseBtn.getId()){
            dismiss();
        }
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
