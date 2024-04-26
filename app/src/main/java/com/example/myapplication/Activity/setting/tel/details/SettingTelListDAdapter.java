package com.example.myapplication.Activity.setting.tel.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewTelItemListDRowBinding;
import com.example.myapplication.event.WatcherPhoneNumberText;

import java.util.List;

public class SettingTelListDAdapter extends RecyclerView.Adapter<SettingTelListDAdapter.ViewHolder> {

    private ViewTelItemListDRowBinding binding;

    private String haedName;
    private List<String> list;

    public SettingTelListDAdapter(TelDataDList list) {
        this.haedName = list.getName();
        this.list = list.getList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewTelItemListDRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String[] data = list.get(position).split(":");

        if (haedName.equals("전화번호"))
            data[1] = new WatcherPhoneNumberText().formatPhoneNumber(data[1]);

        holder.binding.itemTelDTextView.setText(data[0]);
        holder.binding.itemTelDTextView2.setText(data[1]);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewTelItemListDRowBinding binding;

        public ViewHolder(@NonNull ViewTelItemListDRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
