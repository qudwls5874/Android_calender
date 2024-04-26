package com.example.myapplication.Activity.setting.tel.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewTelItemListHRowBinding;

import java.util.List;

public class SettingTelListHAdapter extends RecyclerView.Adapter<SettingTelListHAdapter.ViewHolder> {

    private ViewTelItemListHRowBinding binding;
    private List<TelDataDList> detailsList;


    public SettingTelListHAdapter(List<TelDataDList> detailsList){
        this.detailsList = detailsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewTelItemListHRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String data = detailsList.get(position).getName();

        holder.binding.itemTelTitleTextView.setText(data);

        holder.setMenuListAdapter(detailsList.get(position));

    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewTelItemListHRowBinding binding;



        public ViewHolder(@NonNull ViewTelItemListHRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // 상세 어댑터 설정
        public void setMenuListAdapter(TelDataDList dataList){
            SettingTelListDAdapter adapter = new SettingTelListDAdapter(dataList);
            binding.itemTelRecyclerView.setAdapter(adapter);
            binding.itemTelRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemTelRecyclerView.getContext()));
        }


    }



}
