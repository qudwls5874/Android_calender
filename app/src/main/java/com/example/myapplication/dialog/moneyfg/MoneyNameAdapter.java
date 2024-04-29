package com.example.myapplication.dialog.moneyfg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.databinding.ViewMoneyNameRowBinding;
import com.example.myapplication.event.WatcherMoneyText;

import java.util.ArrayList;

public class MoneyNameAdapter extends RecyclerView.Adapter<MoneyNameAdapter.MoneyNameHolder> implements WatcherMoneyText.OnSearchChangeListener {

    private ViewMoneyNameRowBinding binding;

    private ArrayList<MoneyName> moneyList;
    private CanselMoneyLisner moneyLisner;

    public MoneyNameAdapter(ArrayList<MoneyName> _moneyList, CanselMoneyLisner lisner){
        this.moneyList = _moneyList;
        this.moneyLisner = lisner;
    }

    @NonNull
    @Override
    public MoneyNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewMoneyNameRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoneyNameHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyNameHolder holder, int position) {

        MoneyName rowItem = this.moneyList.get(position);

        holder.binding.viewMoneyNameTextView.setText(rowItem.getMoneyName());

    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }

    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        //moneyList.get(index).setMoney((newText.equals("")) ? 0 : Integer.valueOf(newText.replaceAll(",", "")));
    }

    public class MoneyNameHolder extends RecyclerView.ViewHolder {

        private ViewMoneyNameRowBinding binding;

        public MoneyNameHolder(@NonNull ViewMoneyNameRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface CanselMoneyLisner{
        void setCanselMoney(int index);
        void showMoneyDialog();
    }
}
