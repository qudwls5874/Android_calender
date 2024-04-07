package com.example.myapplication.Activity.user.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class UserMoneyAdapter extends RecyclerView.Adapter<UserMoneyAdapter.UserMoneyHolder> {
    private ArrayList<UserMoney> moneyList;

    public UserMoneyAdapter(ArrayList<UserMoney> _moneyList){
        this.moneyList = _moneyList;
    }

    @NonNull
    @Override
    public UserMoneyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_money_item_row, parent, false);
        return new UserMoneyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMoneyHolder holder, int position) {
        /*
        holder.editText1.setText("데이터");
        */
    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }

    public class UserMoneyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        EditText editText1;
        EditText editText2;
        EditText editText3;

        public UserMoneyHolder(@NonNull View itemView) {
            super(itemView);
            editText1 = imageView.findViewById(R.id.view_money_editText1);
            editText2 = imageView.findViewById(R.id.view_money_editText2);
            editText3 = imageView.findViewById(R.id.view_money_editText3);

        }
    }
}
