package com.example.myapplication.Activity.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.myapplication.R;

public class HomeFragment extends Fragment {

    private View rootView;

    private ListView home_graph_scroll_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fragment 초기화 작업 수행
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initUI();

        return rootView;
    }

    private void initUI() {

        home_graph_scroll_view = (ListView) rootView.findViewById(R.id.home_graph_scroll_view);
    }
}