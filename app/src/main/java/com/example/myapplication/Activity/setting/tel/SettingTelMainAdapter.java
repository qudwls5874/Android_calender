package com.example.myapplication.Activity.setting.tel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ViewSettingTelItemBinding;

import java.util.List;

public class SettingTelMainAdapter extends RecyclerView.Adapter<SettingTelMainAdapter.ViewHolder> {

    private ViewSettingTelItemBinding binding;
    private List<TelData> telDataList;
    private OnTelItemClickListener onTelItemClickListener;

    public SettingTelMainAdapter(List<TelData> telDataList, OnTelItemClickListener onTelItemClickListener){
        this.telDataList = telDataList;
        this.onTelItemClickListener = onTelItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewSettingTelItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TelData data = telDataList.get(position);

        holder.binding.itemTelNameTextView.setText(data.getName());

        if (data.getProfile() != null){
            holder.binding.itemTelProfileImageView.setImageBitmap(data.getProfile());
        } else {
            holder.binding.itemTelProfileImageView.setImageResource(R.drawable.ic_person_image);
        }

        if (data.isChoiceTel()){
            holder.binding.itemTelCheckLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.itemTelCheckLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return telDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ViewSettingTelItemBinding binding;

        public ViewHolder(@NonNull ViewSettingTelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.itemTelCardView.setOnClickListener(this);
            binding.itemTelCardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == binding.itemTelCardView.getId()){
                int posision = getBindingAdapterPosition();

                TelData telData = telDataList.get(posision);
                telData.setChoiceTel(!telData.isChoiceTel());

                onTelItemClickListener.onTelItemClick(telData.getId());

                notifyItemChanged(posision);

            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == binding.itemTelCardView.getId()){
                onTelItemClickListener.onTelItemLongClick(telDataList.get(getBindingAdapterPosition()));
            }
            return false;
        }

    }

    public interface OnTelItemClickListener{
        void onTelItemClick(int telId);
        void onTelItemLongClick(TelData telData);
    }


}
