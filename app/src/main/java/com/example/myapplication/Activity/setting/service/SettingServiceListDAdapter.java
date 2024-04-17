package com.example.myapplication.Activity.setting.service;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.databinding.ViewServiceItemListDRowBinding;
import com.example.myapplication.event.WatcherMoneyText;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SettingServiceListDAdapter extends RecyclerView.Adapter<SettingServiceListDAdapter.ViewHolder> {

    private ViewServiceItemListDRowBinding binding;
    private ArrayList<MenuList> list;

    public SettingServiceListDAdapter(ArrayList<MenuList> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemListDRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuList data = list.get(position);

        holder.binding.itemServiceDTextView1.setText(data.getMenuName());
        holder.binding.itemServiceDTextView2.setText(new WatcherMoneyText().moneyTextChanged(String.valueOf(data.getMenuMoney())));

        if (SettingServiceListHDialog.layoutCheck)
            holder.binding.itemServiceDDelBtn.setVisibility(View.VISIBLE);
        else
            holder.binding.itemServiceDDelBtn.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ViewServiceItemListDRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemListDRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


}
