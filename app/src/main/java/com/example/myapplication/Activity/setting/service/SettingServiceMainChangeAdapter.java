package com.example.myapplication.Activity.setting.service;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.databinding.ViewServiceItemRowBinding;

import java.util.List;

class SettingServiceMainChangeAdapter extends RecyclerView.Adapter<SettingServiceMainChangeAdapter.ViewHolder> {

    private ViewServiceItemRowBinding binding;
    private List<MenuCategory> list;
    private OnItemCheckedChangeLisner lisner;

    public SettingServiceMainChangeAdapter(List<MenuCategory> list, OnItemCheckedChangeLisner lisner){
        this.list = list;
        this.lisner = lisner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuCategory data = list.get(position);

        holder.binding.settingCheckBox1.setText(data.getMenuCategoryName());
        if (data.getMenuCategoryYn().equals("Y")){
            holder.binding.settingCheckBox1.setChecked(true);
        }
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        ViewServiceItemRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemRowBinding binding) {
            super(binding.getRoot());

            binding.settingCheckBox1.setOnCheckedChangeListener(this);

            this.binding = binding;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            lisner.OnitemCheckedChangeLisner(getAdapterPosition(), isChecked);
        }
    }

    interface OnItemCheckedChangeLisner {
        void OnitemCheckedChangeLisner(int index, boolean checked);
    }
}