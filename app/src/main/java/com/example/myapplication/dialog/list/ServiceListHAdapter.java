package com.example.myapplication.dialog.list;

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

public class ServiceListHAdapter extends RecyclerView.Adapter<ServiceListHAdapter.ViewHolder> {

    private ArrayList<MenuJoin> list;
    private ViewServiceItemListHRowBinding binding;
    private ServiceListDAdapter.ItemClickLisner lisner;
    private ServiceListHDialog dialog;

    public ServiceListHAdapter(ArrayList<MenuJoin> list, ServiceListDAdapter.ItemClickLisner lisner, ServiceListHDialog dialog){
        this.list = list;
        this.lisner = lisner;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceItemListHRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MenuJoin data = list.get(position);

        holder.binding.itemServiceTextView.setText(data.menuCategory.getMenuCategoryName());
        holder.setMenuListAdapter((ArrayList<MenuList>) data.menuLists, data.menuCategory);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewServiceItemListHRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemListHRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        // 상세 어댑터 설정
        public void setMenuListAdapter(ArrayList<MenuList> menuLists, MenuCategory menuCategory){
            ServiceListDAdapter apSettingServiceListDAdapter = new ServiceListDAdapter(menuLists, menuCategory, lisner, dialog);
            binding.itemServiceRecyclerView.setAdapter(apSettingServiceListDAdapter);
            binding.itemServiceRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemServiceRecyclerView.getContext()));
        }
    }

}
