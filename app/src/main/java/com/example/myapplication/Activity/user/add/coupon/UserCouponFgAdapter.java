package com.example.myapplication.Activity.user.add.coupon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.CouponJoin;
import com.example.myapplication.databinding.ViewItemCouponRowBinding;
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

public class UserCouponFgAdapter extends RecyclerView.Adapter<UserCouponFgAdapter.ViewHolder> {

    private ViewItemCouponRowBinding binding;
    private List<CouponJoin> list;
    private FragmentManager manager;

    public UserCouponFgAdapter(List<CouponJoin> list, FragmentManager manager) {
        this.list = list;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemCouponRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CouponJoin data = list.get(position);

        holder.binding.viewCouponNameTextView.setText(data.menuList.getMenuName());
        holder.binding.viewCouponDateTextView.setText(data.userCoupon.getCouponInsertDt());
        holder.binding.viewCouponMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+data.userCoupon.getCouponPayment()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ServiceListDAdapter.ItemClickLisner, CustomDatePickerDialog.ReturnValue, ServiceAddMoneyDialog.OnGetMoney {

        private ViewItemCouponRowBinding binding;

        public ViewHolder(@NonNull ViewItemCouponRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewCouponDeleteImageView.setOnClickListener(this);
            binding.viewCouponNameTextView.setOnClickListener(this);
            binding.viewCouponDateTextView.setOnClickListener(this);
            binding.viewCouponMoneyTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.viewCouponDeleteImageView.getId()){
                // 삭제
                list.remove(getBindingAdapterPosition());
                notifyDataSetChanged();
            } else if (v.getId() == binding.viewCouponNameTextView.getId()) {
                // 쿠폰명
                ServiceListHDialog serviceHDialog = new ServiceListHDialog(2, this);
                serviceHDialog.show(manager, "couponListHDialog");
            } else if (v.getId() == binding.viewCouponDateTextView.getId()) {
                // 날짜
                try {
                    Date defaultDdate = new SimpleDateFormat("yyyy-MM-dd").parse(list.get(getBindingAdapterPosition()).userCoupon.getCouponInsertDt());
                    Calendar defaultCal = Calendar.getInstance();
                    defaultCal.setTime(defaultDdate);

                    CustomDatePickerDialog dateDialog = new CustomDatePickerDialog(defaultCal, this);
                    dateDialog.show(manager, "couponDateDialog");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else if (v.getId() == binding.viewCouponMoneyTextView.getId()) {
                // 금액
                ServiceAddMoneyDialog moneyDialog = new ServiceAddMoneyDialog(binding.getRoot().getContext(), ""+list.get(getBindingAdapterPosition()).userCoupon.getCouponPayment(), this);
                moneyDialog.show();

            }
        }

        @Override
        public void setOnItemClickLisner(int position) {
//            list.get(getBindingAdapterPosition()).userCoupon.setCouponPayment(menuList.getMenuMoney());
//            list.get(getBindingAdapterPosition()).menuList = menuList;
//            notifyDataSetChanged();
        }

        @Override
        public void getReturnValue(Date date) {
            list.get(getBindingAdapterPosition()).userCoupon.setCouponInsertDt(new SimpleDateFormat("yyyy-MM-dd").format(date));
            notifyDataSetChanged();
        }

        @Override
        public void getMoney(String money) {
            list.get(getBindingAdapterPosition()).userCoupon.setCouponPayment(Integer.parseInt(money.replaceAll(",","")));
            notifyDataSetChanged();
        }
    }
}
