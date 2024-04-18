package com.example.myapplication.Activity.setting.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.databinding.ViewServiceItemListHRowBinding;
import com.example.myapplication.dialog.ServiceAddDialog;
import com.example.myapplication.dialog.ServiceUpdateDialog;

import java.util.ArrayList;

public class SettingServiceListHAdapter extends RecyclerView.Adapter<SettingServiceListHAdapter.ViewHolder> {

    private ArrayList<MenuJoin> list;
    private ViewServiceItemListHRowBinding binding;
    private ServiceAddDialog.SetMenuListLisner addLisner;
    private ServiceUpdateDialog.SetMenuUpdateLisner updateLisner;
    private SettingServiceListDAdapter.setOnclickColorChangedLisner colorLisner;

    public SettingServiceListHAdapter(ArrayList<MenuJoin> list, ServiceAddDialog.SetMenuListLisner addLisner,
                                                                ServiceUpdateDialog.SetMenuUpdateLisner updateLisner,
                                                                SettingServiceListDAdapter.setOnclickColorChangedLisner colorLisner){
        this.list = list;
        this.addLisner = addLisner;
        this.updateLisner = updateLisner;
        this.colorLisner = colorLisner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemListHRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding, addLisner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuJoin data = list.get(position);

        holder.binding.itemServiceTextView.setText(data.menuCategory.getMenuCategoryName());
        holder.setMenuListAdapter((ArrayList<MenuList>) data.menuLists, data.menuCategory);

        if (SettingServiceListHDialog.layoutCheck)
            holder.binding.itemServiceAddBtn.setVisibility(View.VISIBLE);
        else
            holder.binding.itemServiceAddBtn.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewServiceItemListHRowBinding binding;
        private ServiceAddDialog.SetMenuListLisner addLisner;

        public ViewHolder(@NonNull ViewServiceItemListHRowBinding binding, ServiceAddDialog.SetMenuListLisner addLisner) {
            super(binding.getRoot());

            this.binding = binding;
            this.addLisner = addLisner;

            binding.itemServiceAddBtn.setOnClickListener(this);
        }

        // 상세 어댑터 설정
        public void setMenuListAdapter(ArrayList<MenuList> menuLists, MenuCategory menuCategory){
            SettingServiceListDAdapter apSettingServiceListDAdapter = new SettingServiceListDAdapter(menuLists, updateLisner, colorLisner, menuCategory);
            binding.itemServiceRecyclerView.setAdapter(apSettingServiceListDAdapter);
            binding.itemServiceRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemServiceRecyclerView.getContext()));
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.itemServiceAddBtn.getId()){
                // 추가 다이얼로그 버튼 (저장)
                ServiceAddDialog addDialog = new ServiceAddDialog(binding.getRoot().getContext(), list.get(getBindingAdapterPosition()).menuCategory, addLisner);
                addDialog.show();
            }
        }
    }

}
