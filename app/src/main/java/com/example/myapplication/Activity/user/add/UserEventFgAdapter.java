package com.example.myapplication.Activity.user.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.user.UserEvent;
import com.example.myapplication.databinding.ViewItemEventRowBinding;

import java.util.List;

public class UserEventFgAdapter extends RecyclerView.Adapter<UserEventFgAdapter.ViewHolder> {

    private ViewItemEventRowBinding binding;
    private List<UserEvent> userEvents;
    private FragmentManager manager;

    public UserEventFgAdapter(List<UserEvent> userEvents, FragmentManager manager) {
        this.userEvents = userEvents;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemEventRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return userEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ViewItemEventRowBinding binding;

        public ViewHolder(@NonNull ViewItemEventRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
