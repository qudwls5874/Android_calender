package com.example.myapplication.Activity.setting.tel.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.user.UserAddress;
import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.databinding.ViewTelItemListHRowBinding;
import com.example.myapplication.dataclass.DefaultListData;
import com.example.myapplication.dataclass.DefaultListDataD;

import java.util.ArrayList;
import java.util.List;

public class SettingTelListHAdapter extends RecyclerView.Adapter<SettingTelListHAdapter.ViewHolder> {

    private ViewTelItemListHRowBinding binding;
    private List<String> userDList;
    private UserJoin detailsList;


    public SettingTelListHAdapter(List<String> userDList, UserJoin detailsList){
        this.userDList = userDList;
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

        String data = userDList.get(position);
        holder.binding.itemTelTitleTextView.setText(data);

        switch (userDList.get(position)){
            case "전화번호":
                List<DefaultListDataD> telListBox = new ArrayList<>();
                for (UserTel telList : detailsList.userTelList){
                    telListBox.add(new DefaultListDataD(telList.getTelName(), telList.getTelNumber()));
                }
                holder.setMenuListAdapter(new DefaultListData(data, telListBox));
                break;
            case "주소":
                List<DefaultListDataD> addressListBox = new ArrayList<>();
                for (UserAddress telList : detailsList.userAddressList){
                    addressListBox.add(new DefaultListDataD(telList.getAddressName(), telList.getAddressContent()));
                }
                holder.setMenuListAdapter(new DefaultListData(data, addressListBox));
                break;
            case "일정":
                List<DefaultListDataD> eventListBox = new ArrayList<>();
                for (UserEvent eventList : detailsList.userEventsList){
                    eventListBox.add(new DefaultListDataD(eventList.getEventName(), eventList.getEventContent()));
                }
                holder.setMenuListAdapter(new DefaultListData(data, eventListBox));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userDList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewTelItemListHRowBinding binding;



        public ViewHolder(@NonNull ViewTelItemListHRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // 상세 어댑터 설정
        public void setMenuListAdapter(DefaultListData dataList){
            SettingTelListDAdapter adapter = new SettingTelListDAdapter(dataList);
            binding.itemTelRecyclerView.setAdapter(adapter);
            binding.itemTelRecyclerView.setLayoutManager(new LinearLayoutManager(binding.itemTelRecyclerView.getContext()));
        }


    }



}
