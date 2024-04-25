package com.example.myapplication.Activity.setting.tel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.compose.animation.core.Spring;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewSettingTelItemDBinding;
import com.example.myapplication.event.WatcherPhoneNumberText;

import java.util.List;

public class SettingTelListDAdapter extends RecyclerView.Adapter<SettingTelListDAdapter.ViewHolder> {

    private ViewSettingTelItemDBinding binding;
    private List<String> list;

    public SettingTelListDAdapter(List<String> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewSettingTelItemDBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String[] data = list.get(position).split(":");

        holder.binding.itemTelDTypeTextView.setText(data[0]);
        holder.binding.itemTelDTextView.setText(new WatcherPhoneNumberText().formatPhoneNumber(data[1]));




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewSettingTelItemDBinding binding;

        public ViewHolder(@NonNull ViewSettingTelItemDBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }



}
