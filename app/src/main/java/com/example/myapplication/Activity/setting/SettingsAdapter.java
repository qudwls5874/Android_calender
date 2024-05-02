package com.example.myapplication.Activity.setting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ViewSettingItemRowBinding;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsHolder> {

    private ViewSettingItemRowBinding binding;
    private ArrayList<String> list;
    private OnItemClickListener listener;

    public SettingsAdapter(ArrayList<String> _list, OnItemClickListener listener){
        this.list = _list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewSettingItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SettingsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsHolder holder, int position) {

        String data = this.list.get(position);

        holder.binding.settingTitleTextView.setText(data);

        switch (data){
            case "서비스분야 설정":
                holder.binding.rowProfileImageView.setImageDrawable(holder.binding.getRoot().getContext().getDrawable(R.drawable.ic_add_white));
                break;
            case "연락처 가져오기":
                holder.binding.rowProfileImageView.setImageDrawable(holder.binding.getRoot().getContext().getDrawable(R.drawable.ic_call_add_white));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SettingsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewSettingItemRowBinding binding;

        public SettingsHolder(@NonNull ViewSettingItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.OnItemClickListener(getBindingAdapterPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(int position);
    }
}
