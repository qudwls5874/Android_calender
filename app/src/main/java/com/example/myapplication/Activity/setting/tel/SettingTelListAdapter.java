package com.example.myapplication.Activity.setting.tel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewServiceTelItemBinding;

import java.util.List;

public class SettingTelListAdapter extends RecyclerView.Adapter<SettingTelListAdapter.ViewHolder> {

    private ViewServiceTelItemBinding binding;
    private List<TelData> telDataList;

    public SettingTelListAdapter(List<TelData> telDataList){
        this.telDataList = telDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewServiceTelItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TelData data = telDataList.get(position);

        holder.binding.itemTelNameTextView.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return telDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewServiceTelItemBinding binding;

        public ViewHolder(@NonNull ViewServiceTelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            initUI();
        }

        private void initUI() {

        }
    }

}
