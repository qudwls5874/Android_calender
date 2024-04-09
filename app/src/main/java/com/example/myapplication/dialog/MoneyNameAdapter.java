package com.example.myapplication.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.user.money.UserMoney;
import com.example.myapplication.R;
import com.example.myapplication.database.table.MoneyName;
import com.example.myapplication.event.WatcherMoneyText;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MoneyNameAdapter extends RecyclerView.Adapter<MoneyNameAdapter.MoneyNameHolder> implements WatcherMoneyText.OnSearchChangeListener {

    private ArrayList<MoneyName> moneyList;
    private CanselMoneyLisner moneyLisner;

    public MoneyNameAdapter(ArrayList<MoneyName> _moneyList, CanselMoneyLisner lisner){
        this.moneyList = _moneyList;
        this.moneyLisner = lisner;
    }

    @NonNull
    @Override
    public MoneyNameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_money_name_row, parent, false);
        return new MoneyNameHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoneyNameHolder holder, int position) {

        MoneyName rowItem = this.moneyList.get(position);

        holder.view_money_name_textView.setText(rowItem.getMoneyName());

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

        TextView view_money_name_textView;

        public MoneyNameHolder(@NonNull View itemView) {
            super(itemView);
            view_money_name_textView = itemView.findViewById(R.id.view_money_name_textView);
        }

    }

    public interface CanselMoneyLisner{
        void setCanselMoney(int index);
        void showMoneyDialog();
    }
}
