package com.example.myapplication.Activity.user.add.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.view.schedule.CalendarDJoin;
import com.example.myapplication.databinding.ViewUserDetailsDItemBinding;
import com.example.myapplication.databinding.ViewUserDetailsHItemBinding;
import com.example.myapplication.dialog.ServiceAddMoneyDialog;
import com.example.myapplication.dialog.customcalender.CustomDatePickerDialog;
import com.example.myapplication.dialog.list.ServiceListDAdapter;
import com.example.myapplication.dialog.list.ServiceListHDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserServiceFgDAdapter extends RecyclerView.Adapter<UserServiceFgDAdapter.ViewModel> {

    private ViewUserDetailsDItemBinding binding;
    private List<CalendarDJoin> calendarDJoins;
    private FragmentManager fragmentManager;


    public UserServiceFgDAdapter(List<CalendarDJoin> _calendarDJoinss, FragmentManager _fragmentManager) {
        this.calendarDJoins = _calendarDJoinss;
        this.fragmentManager = _fragmentManager;
    }

    @NonNull
    @Override
    public ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewUserDetailsDItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewModel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModel holder, int position) {

        CalendarDJoin data = calendarDJoins.get(position);


//        holder.binding.viewAddServiceListDtTextView.setText(data.scheduleCalendarH.getCalDt());
//        holder.binding.viewAddServiceListMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+data.scheduleCalendar.getCalPayment()));
//        holder.binding.viewAddServiceListNameTextView.setText(data.menuList.getMenuName());


    }

    @Override
    public int getItemCount() {
        return calendarDJoins.size();
    }




    public class ViewModel extends RecyclerView.ViewHolder implements   View.OnClickListener{

        private ViewUserDetailsDItemBinding binding;

        public ViewModel(@NonNull ViewUserDetailsDItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        @Override
        public void onClick(View v) {
        }

    }


}
