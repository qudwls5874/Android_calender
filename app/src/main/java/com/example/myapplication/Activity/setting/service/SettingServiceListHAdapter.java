package com.example.myapplication.Activity.setting.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.databinding.ViewServiceItemListRowBinding;

import java.util.ArrayList;
import java.util.List;

public class SettingServiceListHAdapter extends RecyclerView.Adapter<SettingServiceListHAdapter.ViewHolder> {

    private ArrayList<MenuJoin> list;
    private ViewServiceItemListRowBinding binding;

    public SettingServiceListHAdapter(ArrayList<MenuJoin> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemListRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuJoin data = list.get(position);

        holder.binding.itemServiceTextView.setText(data.menuCategory.getMenuCategoryName());
        holder.setMenuListAdapter((ArrayList<MenuList>) data.menuLists);

        if (SettingServiceListHDialog.layoutCheck)
            holder.binding.itemServiceAddBtn.setVisibility(View.VISIBLE);
        else
            holder.binding.itemServiceAddBtn.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewServiceItemListRowBinding binding;


        public ViewHolder(@NonNull ViewServiceItemListRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void setMenuListAdapter(ArrayList<MenuList> menuLists){
            SettingServiceListDAdapter apSettingServiceListDAdapter = new SettingServiceListDAdapter(menuLists);
            binding.itemServiceRecyclerView.setAdapter(apSettingServiceListDAdapter);
            binding.itemServiceRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemServiceRecyclerView.getContext()));
        }

    }


}
