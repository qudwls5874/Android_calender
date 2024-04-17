package com.example.myapplication.Activity.user.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.user.UserAddFragmentDialog;
import com.example.myapplication.R;
import com.example.myapplication.database.viewmodel.MoneyNameViewModel;
import com.example.myapplication.dialog.MoneyNameFragmentDialog;
import com.example.myapplication.event.WatcherMoneyText;
import com.example.myapplication.event.WatcherSearchText;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserMoneyAdapter extends RecyclerView.Adapter<UserMoneyAdapter.UserMoneyHolder> implements WatcherMoneyText.OnSearchChangeListener {

    private ArrayList<UserMoney> moneyList;
    private CanselMoneyLisner moneyLisner;

    public UserMoneyAdapter(ArrayList<UserMoney> _moneyList, CanselMoneyLisner lisner){
        this.moneyList = _moneyList;
        this.moneyLisner = lisner;
    }

    @NonNull
    @Override
    public UserMoneyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_money_item_row, parent, false);
        return new UserMoneyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMoneyHolder holder, int position) {

        UserMoney rowItem = this.moneyList.get(position);

        holder.imageView.setOnClickListener(v -> {
            if (moneyLisner != null){
                moneyLisner.setCanselMoney(position);
            }
        });
        holder.textView1.setOnClickListener(v -> {
            if (moneyLisner != null){
                moneyLisner.showMoneyDialog();
            }
//            MoneyNameFragmentDialog dialog = new MoneyNameFragmentDialog();
//            dialog.show(getSupportFragmentManager(), "MoneyNameFragmentDialog");

        });


        holder.textView1.setText(rowItem.getMoneyName());
        holder.editText2.setText(rowItem.getMoneyDt().toString());

        String originalText = String.valueOf(rowItem.getMoney());
        if (!originalText.isEmpty()) {
            long number = Long.parseLong(originalText.replaceAll(",", ""));
            String formattedText = new DecimalFormat("#,###").format(number);
            holder.editText3.setText(formattedText);
            holder.editText3.setSelection(formattedText.length()); // 커서를 끝으로 이동
        }

        // 새로운 TextWatcher 생성하여 등록
        holder.editText3.addTextChangedListener(new WatcherMoneyText(this, position, holder.editText3));


    }

    @Override
    public int getItemCount() {
        return moneyList.size();
    }


    public class UserMoneyHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView1;
        EditText editText2;
        EditText editText3;

        public UserMoneyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_money_imageView);
            textView1 = itemView.findViewById(R.id.view_money_textView1);
            editText2 = itemView.findViewById(R.id.view_money_editText2);
            editText3 = itemView.findViewById(R.id.view_money_editText3);
        }

    }

    @Override
    public void onSearchTextChanged(String newText, Integer index) {
        moneyList.get(index).setMoney((newText.equals("")) ? 0 : Integer.valueOf(newText.replaceAll(",", "")));
    }

    public interface CanselMoneyLisner{
        void setCanselMoney(int index);
        void showMoneyDialog();
    }
}
