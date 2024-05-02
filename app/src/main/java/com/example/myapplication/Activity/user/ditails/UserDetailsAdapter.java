package com.example.myapplication.Activity.user.ditails;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.view.CalendarJoin;
import com.example.myapplication.databinding.ViewUserDetailsHItemBinding;

import java.util.List;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {

    private ViewUserDetailsHItemBinding binding;
    private List<CalendarJoin> list;

    public UserDetailsAdapter(List<CalendarJoin> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewUserDetailsHItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewUserDetailsHItemBinding binding;

        public ViewHolder(@NonNull ViewUserDetailsHItemBinding binding) {
            super(binding.getRoot());


        }
    }


}
