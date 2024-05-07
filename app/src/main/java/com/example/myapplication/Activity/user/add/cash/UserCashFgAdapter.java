package com.example.myapplication.Activity.user.add.cash;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.CashJoin;
import com.example.myapplication.databinding.ViewItemCashRowBinding;
import com.example.myapplication.dialog.ServiceAddMoneyDialog;
import com.example.myapplication.dialog.customcalender.CustomDatePickerDialog;
import com.example.myapplication.dialog.list.ServiceListDAdapter;
import com.example.myapplication.dialog.list.ServiceListHDialog;
import com.example.myapplication.event.WatcherMoneyText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserCashFgAdapter extends RecyclerView.Adapter<UserCashFgAdapter.ViewHolder>{

    private ViewItemCashRowBinding binding;
    private List<CashJoin> list;
    private FragmentManager manager;

    public UserCashFgAdapter(List<CashJoin> list, FragmentManager manager) {
        this.list = list;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemCashRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CashJoin data = list.get(position);

        holder.binding.viewCashDtTextView.setText(data.userCash.getCashDt());
        holder.binding.viewCashMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+data.userCash.getCashPayment()));
        holder.binding.viewCashNameTextView.setText(data.menuList.getMenuName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,
                                                                        CustomDatePickerDialog.ReturnValue,
                                                                        ServiceAddMoneyDialog.OnGetMoney,
                                                                        ServiceListDAdapter.ItemClickLisner {

        private ViewItemCashRowBinding binding;

        public ViewHolder(@NonNull ViewItemCashRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewCashNameTextView.setOnClickListener(this);
            binding.viewCashDeleteImageView.setOnClickListener(this);
            binding.viewCashDtTextView.setOnClickListener(this);
            binding.viewCashMoneyTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.viewCashDeleteImageView.getId()){
                // 삭제
                list.remove(getBindingAdapterPosition());
                notifyDataSetChanged();
            } else if (v.getId() == binding.viewCashNameTextView.getId()){
                // 충전명 클릭
                ServiceListHDialog serviceListHDialog = new ServiceListHDialog(1, this);
                serviceListHDialog.show(manager, "cashListHDialog");

            } else if (v.getId() == binding.viewCashDtTextView.getId()) {
                // 날짜
                try {
                    Date defaultDdate = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(getBindingAdapterPosition()).userCash.getCashDt());
                    Calendar defaultCal = Calendar.getInstance();
                    defaultCal.setTime(defaultDdate);

                    CustomDatePickerDialog dateDialog = new CustomDatePickerDialog(defaultCal, this);
                    dateDialog.show(manager, "cashDatePickerDialog");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (v.getId() == binding.viewCashMoneyTextView.getId()) {
                // 금액
                ServiceAddMoneyDialog moneyDialog = new ServiceAddMoneyDialog(binding.getRoot().getContext(), String.valueOf(list.get(getBindingAdapterPosition()).userCash.getCashPayment()), this);
                moneyDialog.show();
            }
        }

        @Override
        public void getReturnValue(Date date) {
            list.get(getBindingAdapterPosition()).userCash.setCashDt(new SimpleDateFormat("yyyy-MM-dd").format(date));
            notifyDataSetChanged();
        }

        @Override
        public void getMoney(String money) {
            list.get(getBindingAdapterPosition()).userCash.setCashPayment(Integer.parseInt(money.replaceAll(",", "")));
            notifyDataSetChanged();
        }

        @Override
        public void setOnItemClickLisner(int position) {
//            list.get(getBindingAdapterPosition()).userCash.setCashPayment(menuList.getMenuMoney());
//            list.get(getBindingAdapterPosition()).menuList = menuList;
//            notifyDataSetChanged();
        }
    }

}
