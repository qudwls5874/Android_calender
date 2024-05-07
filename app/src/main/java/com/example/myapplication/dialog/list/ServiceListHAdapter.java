package com.example.myapplication.dialog.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.databinding.ViewServiceItemListHRowBinding;

import java.util.ArrayList;

public class ServiceListHAdapter extends RecyclerView.Adapter<ServiceListHAdapter.ViewHolder> {

    private ArrayList<MenuJoin> list;
    private ArrayList<MenuList> checkList;
    private ViewServiceItemListHRowBinding binding;
    private ServiceListDAdapter.ItemClickLisner lisner;
    private ServiceListHDialog dialog;

    public ServiceListHAdapter(ArrayList<MenuJoin> list, ArrayList<MenuList> checkList, ServiceListDAdapter.ItemClickLisner lisner, ServiceListHDialog dialog){
        this.list = list;
        this.checkList = checkList;
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
        holder.setMenuListAdapter(
                (ArrayList<MenuList>) data.menuLists,
                checkList,
                data.menuCategory
        );

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemCheckList(ArrayList<MenuList> _checkList){
        this.checkList = _checkList;
        notifyDataSetChanged(); // 데이터가 변경되었음을 RecyclerView에 알립니다.
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
        public void setMenuListAdapter(ArrayList<MenuList> menuLists, ArrayList<MenuList> _checkList, MenuCategory menuCategory){
            ServiceListDAdapter apSettingServiceListDAdapter = new ServiceListDAdapter(menuLists, _checkList, menuCategory, lisner, dialog);
            binding.itemServiceRecyclerView.setAdapter(apSettingServiceListDAdapter);
            binding.itemServiceRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemServiceRecyclerView.getContext()));
        }
    }

}
