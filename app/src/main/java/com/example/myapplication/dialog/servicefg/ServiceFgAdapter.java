package com.example.myapplication.dialog.servicefg;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.view.MenuJoin;
import com.example.myapplication.databinding.ViewCategoryHRowBinding;

import java.util.List;

public class ServiceFgAdapter extends RecyclerView.Adapter<ServiceFgAdapter.ViewModel> {

    private ViewCategoryHRowBinding binding;
    private List<MenuJoin> menuLists;


    public ServiceFgAdapter(List<MenuJoin> menuLists) {
        this.menuLists = menuLists;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewCategoryHRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return menuLists.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder{

        private ViewCategoryHRowBinding binding;

        public ViewModel(@NonNull ViewCategoryHRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }




}
