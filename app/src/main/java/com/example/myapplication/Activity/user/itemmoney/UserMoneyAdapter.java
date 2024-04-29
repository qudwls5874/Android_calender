package com.example.myapplication.Activity.user.itemmoney;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ViewMoneyItemRowBinding;
import com.example.myapplication.event.WatcherMoneyText;

import java.util.ArrayList;

public class UserMoneyAdapter extends RecyclerView.Adapter<UserMoneyAdapter.UserMoneyHolder> {

    private ViewMoneyItemRowBinding binding;

    private ArrayList<UserMoney> moneyList;
    private CanselMoneyLisner moneyLisner;

    public UserMoneyAdapter(ArrayList<UserMoney> _moneyList, CanselMoneyLisner lisner){
        this.moneyList = _moneyList;
        this.moneyLisner = lisner;
    }

    @NonNull
    @Override
    public UserMoneyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewMoneyItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserMoneyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMoneyHolder holder, int position) {

        UserMoney rowItem = this.moneyList.get(position);

        holder.binding.viewMoneyNameTextView.setText(rowItem.getMoneyName());
        holder.binding.viewMoneyDateTextView.setText(rowItem.getMoneyDt().toString());
        holder.binding.viewMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(String.valueOf(rowItem.getMoney())));

    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }


    public class UserMoneyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewMoneyItemRowBinding binding;

        public UserMoneyHolder(@NonNull ViewMoneyItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewMoneyImageView.setOnClickListener(this);
            binding.viewMoneyNameTextView.setOnClickListener(this);
            binding.viewMoneyTextView.setOnClickListener(this);
            binding.viewMoneyDateTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == binding.viewMoneyNameTextView.getId()){
                // 결제명 텍스트
                moneyLisner.showMoneyDialog();
            } else if (v.getId() == binding.viewMoneyImageView.getId()){
                // 삭제 버튼
                moneyLisner.setCanselMoney(getBindingAdapterPosition());
            } else if (v.getId() == binding.viewMoneyTextView.getId()){
                // 금액 텍스트

            } else if (v.getId() == binding.viewMoneyDateTextView.getId()) {
                // 날짜 텍스트

            }
        }
    }

    public interface CanselMoneyLisner{
        void setCanselMoney(int index);
        void showMoneyDialog();
    }
}
