package com.example.myapplication.Activity;

import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout; // 탭 레이아웃
    private ViewPager viewPager; // 뷰페이저
    // Tab titles
    private String[] tabTitles = {"차트", "캘린더", "홈", "고객", "설정"}; // 탭 제목 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 레이아웃 설정

        initUI(); // UI 초기화 메서드 호출
    }

    private void initUI() {

        // ViewPager와 TabLayout 초기화
        viewPager = findViewById(R.id.main_frame_layout);
        tabLayout = findViewById(R.id.main_bottom_tab);

        // ViewPager에 어댑터 설정
        viewPager.setAdapter(new MainTabBarAdapter(getSupportFragmentManager(), tabTitles));

        // TabLayout에 ViewPager 연결
        tabLayout.setupWithViewPager(viewPager);

        // 뷰페이저 초기 위치 설정
        viewPager.setCurrentItem(2); // 홈 탭으로 초기 위치 설정

    }

}