package com.example.myapplication.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Activity.calender.CalendarFragment;
import com.example.myapplication.Activity.chart.ChartFragment;
import com.example.myapplication.Activity.home.HomeFragment;
import com.example.myapplication.Activity.setting.SettingsFragment;
import com.example.myapplication.Activity.user.UserFragment;

// ViewPager 어댑터 클래스
public class MainTabBarAdapter extends FragmentPagerAdapter {

    String[] tabTitles;

    MainTabBarAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        // 각 탭에 해당하는 Fragment 반환
        switch (position) {
            case 0:
                return new ChartFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new UserFragment();
            case 4:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // 탭 개수 반환
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // 탭의 제목 반환
        return tabTitles[position];
    }
}