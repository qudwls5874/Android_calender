package com.example.myapplication.Activity.user.add.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.setting.service.servicelist.SettingServiceListDAdapter;
import com.example.myapplication.database.table.menu.MenuCategory;
import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.view.schedule.CalendarDJoin;
import com.example.myapplication.database.view.schedule.CalendarHJoin;
import com.example.myapplication.databinding.ViewItemServiceRowBinding;
import com.example.myapplication.databinding.ViewUserDetailsHItemBinding;
import com.example.myapplication.dialog.customcalender.CustomDatePickerDialog;
import com.example.myapplication.dialog.ServiceAddMoneyDialog;
import com.example.myapplication.dialog.list.ServiceListDAdapter;
import com.example.myapplication.dialog.list.ServiceListHDialog;
import com.example.myapplication.event.WatcherMoneyText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserServiceFgAdapter extends RecyclerView.Adapter<UserServiceFgAdapter.ViewHolder> {

    private ViewUserDetailsHItemBinding binding;
    private List<CalendarHJoin> calendarHJoins;
    private FragmentManager fragmentManager;


    public UserServiceFgAdapter(List<CalendarHJoin> _calendarHJoins, FragmentManager _fragmentManager) {
        this.calendarHJoins = _calendarHJoins;
        this.fragmentManager = _fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewUserDetailsHItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CalendarHJoin data = calendarHJoins.get(position);

        // 헤더 날짜
        holder.binding.userDetailsHDateTextView.setText(data.scheduleCalendarH.getCalDt());
        // 선택 카테고리
        ArrayList<MenuCategory> picCategory = new ArrayList<>();
        holder.binding.userDetailsHTitleTextView.setText("서비스 선택");
        if (data.menuCategory.size() > 0){
//            holder.binding.userDetailsHTitleTextView.setText(data.menuCategory.getMenuCategoryName() + "(" + data.calendarDJoins.size() + ")");
        }


//        holder.setMenuListAdapter(data.calendarDJoins, fragmentManager);


//        holder.binding.viewAddServiceListDtTextView.setText(data.scheduleCalendarH.getCalDt());
//        holder.binding.viewAddServiceListMoneyTextView.setText(new WatcherMoneyText().beforeMoneyTextChanged(""+data.scheduleCalendar.getCalPayment()));
//        holder.binding.viewAddServiceListNameTextView.setText(data.menuList.getMenuName());


    }

    @Override
    public int getItemCount() {
        return calendarHJoins.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements   View.OnClickListener,
                                                                        ServiceListDAdapter.ItemClickLisner,
                                                                        CustomDatePickerDialog.ReturnValue {

        private ViewUserDetailsHItemBinding binding;

        public ViewHolder(@NonNull ViewUserDetailsHItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            initUI();
        }

        public void initUI(){
            // 클릭 이벤트
            binding.viewAddServiceListImageView.setOnClickListener(this);       // 삭제 버튼
            binding.userDetailsHTitleTextView.setOnClickListener(this);         // 타이틀명 > 네일(2)
            binding.userDetailsHDateTextView.setOnClickListener(this);          // 날짜
        }

        // 상세 리사이클러뷰 어댑터 설정
        public void setMenuListAdapter(List<CalendarDJoin> calendarDJoins, FragmentManager manager){
            UserServiceFgDAdapter adapter = new UserServiceFgDAdapter(calendarDJoins, manager);
            binding.userDetailsHRecyclerView.setAdapter(adapter);
            binding.userDetailsHRecyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.viewAddServiceListImageView.getId()){
                // 리스트 삭제
                calendarHJoins.remove(getBindingAdapterPosition());
                notifyDataSetChanged();
            } else if (v.getId() == binding.userDetailsHTitleTextView.getId()) {
                // 서비스명 클릭
                ServiceListHDialog serviceHDialog = new ServiceListHDialog(0, this);
                serviceHDialog.show(fragmentManager, "service");    // 여기 수정중
            } else if (v.getId() == binding.userDetailsHDateTextView.getId()) {
                // 달력 다이얼로그
                try {
                    // 문자열을 Date 로 변환
                    Date defaultDdate = new SimpleDateFormat("yyyy-MM-dd").parse(calendarHJoins.get(getBindingAdapterPosition()).scheduleCalendarH.getCalDt());
                    Calendar defaultCal = Calendar.getInstance();
                    defaultCal.setTime(defaultDdate);

                    CustomDatePickerDialog dateDialog = new CustomDatePickerDialog(defaultCal, this);
                    dateDialog.show(fragmentManager, "customDatePickerDialog");
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        @Override
        public void setOnItemClickLisner(MenuList menuList) {

        }

        @Override
        public void getReturnValue(Date date) {

        }
    }


}
