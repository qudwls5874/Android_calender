package com.example.myapplication.dialog.servicefg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.MenuList;
import com.example.myapplication.database.view.CalendarJoin;
import com.example.myapplication.databinding.ViewItemServiceRowBinding;
import com.example.myapplication.dialog.customcalender.CustomDatePickerDialog;
import com.example.myapplication.dialog.ServiceAddMoneyDialog;
import com.example.myapplication.dialog.list.ServiceListDAdapter;
import com.example.myapplication.dialog.list.ServiceListHDialog;
import com.example.myapplication.event.WatcherMoneyText;

import java.util.List;

public class ServiceFgAdapter extends RecyclerView.Adapter<ServiceFgAdapter.ViewModel> {

    private ViewItemServiceRowBinding binding;
    private List<CalendarJoin> scList;
    private FragmentManager fragmentManager;


    public ServiceFgAdapter(List<CalendarJoin> scList, FragmentManager fragmentManager) {
        this.scList = scList;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemServiceRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {

        CalendarJoin data = scList.get(position);


        holder.binding.viewAddServiceListDtTextView.setText(data.scheduleCalendar.getCalDt());
        holder.binding.viewAddServiceListMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+data.scheduleCalendar.getCalPayment()));
        holder.binding.viewAddServiceListNameTextView.setText(data.menuList.getMenuName());


    }

    @Override
    public int getItemCount() {
        return scList.size();
    }



    public class ViewModel extends RecyclerView.ViewHolder implements View.OnClickListener, ServiceListDAdapter.ItemClickLisner, ServiceAddMoneyDialog.OnGetMoney {

        private ViewItemServiceRowBinding binding;

        public ViewModel(@NonNull ViewItemServiceRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewAddServiceListImageView.setOnClickListener(this);
            binding.viewAddServiceListNameTextView.setOnClickListener(this);
            binding.viewAddServiceListMoneyTextView.setOnClickListener(this);
            binding.viewAddServiceListDtTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.viewAddServiceListImageView.getId()){
                // 리스트 삭제
                scList.remove(getBindingAdapterPosition());
                notifyDataSetChanged();
            } else if (v.getId() == binding.viewAddServiceListNameTextView.getId()) {
                // 서비스명 클릭
                ServiceListHDialog serviceHDialog = new ServiceListHDialog(0, this);
                serviceHDialog.show(fragmentManager, "serviceListHDialog");
            } else if (v.getId() == binding.viewAddServiceListMoneyTextView.getId()){
                // 금액 입력 다이얼로그
                ServiceAddMoneyDialog moneyDialog = new ServiceAddMoneyDialog(
                        binding.getRoot().getContext(),
                        binding.viewAddServiceListMoneyTextView.getText().toString(),
                        this
                        );
                moneyDialog.show();
            } else if (v.getId() == binding.viewAddServiceListDtTextView.getId()) {
                // 달력 다이얼로그
                CustomDatePickerDialog dateDialog = new CustomDatePickerDialog();
                dateDialog.show(fragmentManager, "customDatePickerDialog");
//                CustomDatePickerDialog dateDialog = new CustomDatePickerDialog(binding.getRoot().getContext());
//                dateDialog.showDialog();


//                CalenderDialog calDialog = new CalenderDialog(binding.getRoot().getContext());
//                calDialog.showDialog();
            }
        }

        @Override
        public void setOnItemClickLisner(MenuList menuList) {
            scList.get(getBindingAdapterPosition()).scheduleCalendar.setCalPayment(menuList.getMenuMoney());
            scList.get(getBindingAdapterPosition()).menuList = menuList;
            notifyDataSetChanged();
        }

        @Override
        public void getMoney(String money) {
            scList.get(getBindingAdapterPosition()).scheduleCalendar.setCalPayment(Integer.valueOf(money.replaceAll(",", "")));
            notifyDataSetChanged();
        }
    }


}
