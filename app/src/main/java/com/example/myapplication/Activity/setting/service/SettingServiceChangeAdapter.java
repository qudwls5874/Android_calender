package com.example.myapplication.Activity.setting.service;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.databinding.ViewServiceItemRowBinding;

import java.util.List;

class SettingServiceChangeAdapter extends RecyclerView.Adapter<SettingServiceChangeAdapter.ViewHolder> {

    private ViewServiceItemRowBinding binding;
    private List<MenuCategory> list;

    public SettingServiceChangeAdapter(List<MenuCategory> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String data = list.get(position).getMenuCategoryName();

        holder.binding.settingTextView1.setText(data);
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewServiceItemRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
