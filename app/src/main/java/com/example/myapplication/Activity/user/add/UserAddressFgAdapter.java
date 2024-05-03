package com.example.myapplication.Activity.user.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.databinding.ViewItemAddressRowBinding;

import java.util.List;

public class UserAddressFgAdapter extends RecyclerView.Adapter<UserAddressFgAdapter.ViewHolder> {

    private ViewItemAddressRowBinding binding;
    private List<UserAddress> userAddresses;
    private FragmentManager manager;

    public UserAddressFgAdapter(List<UserAddress> userAddresses, FragmentManager manager) {
        this.userAddresses = userAddresses;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemAddressRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userAddresses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewItemAddressRowBinding binding;

        public ViewHolder(@NonNull ViewItemAddressRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }


}
