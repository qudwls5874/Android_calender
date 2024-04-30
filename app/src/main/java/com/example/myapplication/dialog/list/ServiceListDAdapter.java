package com.example.myapplication.dialog.list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MenuCategory;
import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.databinding.ViewServiceItemListDRowBinding;
import com.example.myapplication.dialog.ServiceUpdateDialog;
import com.example.myapplication.event.WatcherMoneyText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceListDAdapter extends RecyclerView.Adapter<ServiceListDAdapter.ViewHolder> {

    private ViewServiceItemListDRowBinding binding;
    private ArrayList<MenuList> list;
    private MenuCategory menuCategory;
    private ItemClickLisner itemClickLisner;
    private ServiceListHDialog dialog;

    public ServiceListDAdapter(ArrayList<MenuList> list,
                               MenuCategory menuCategory,
                               ItemClickLisner itemClickLisner,
                               ServiceListHDialog dialog){
        this.list = list;
        this.menuCategory = menuCategory;
        this.itemClickLisner = itemClickLisner;
        this.dialog = dialog;
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
        WatcherMoneyText watcherMoneyText = new WatcherMoneyText();
        holder.binding.itemServiceDTextView2.setText(watcherMoneyText.beforeMoneyTextChanged(String.valueOf(data.getMenuMoney())));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewServiceItemListDRowBinding binding;

        public ViewHolder(@NonNull ViewServiceItemListDRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.itemServiceDAllLinearLayout.setVisibility(View.VISIBLE);
            // 클릭 이벤트 itemservicedlinearLayout
            binding.itemServiceDAllLinearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.itemServiceDAllLinearLayout.getId()) {
                itemClickLisner.setOnItemClickLisner(list.get(getBindingAdapterPosition()));
                dialog.dismiss();
            }
        }


    }

    public interface ItemClickLisner{
        void setOnItemClickLisner(MenuList menuList);
    }


}
