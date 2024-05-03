package com.example.myapplication.Activity.setting.tel.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewTelItemListDRowBinding;
import com.example.myapplication.dataclass.DefaultListData;
import com.example.myapplication.dataclass.DefaultListDataD;
import com.example.myapplication.event.WatcherPhoneNumberText;

import java.util.List;

public class SettingTelListDAdapter extends RecyclerView.Adapter<SettingTelListDAdapter.ViewHolder> {

    private ViewTelItemListDRowBinding binding;

    private String haedName;
    private List<DefaultListDataD> list;

    public SettingTelListDAdapter(DefaultListData list) {
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

        DefaultListDataD data = list.get(position);

        if (haedName.equals("전화번호"))
            data.setDataName(new WatcherPhoneNumberText().formatPhoneNumber(list.get(position).getDataName()));

        holder.binding.itemTelDTextView.setText(data.getDataName());
        holder.binding.itemTelDTextView2.setText(data.getDataContent());

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
