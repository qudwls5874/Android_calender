package com.example.myapplication.Activity.user.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.database.table.menu.MenuList;
import com.example.myapplication.database.table.user.UserTel;
import com.example.myapplication.databinding.ViewItemTelRowBinding;
import com.example.myapplication.dialog.list.ServiceListDAdapter;
import com.example.myapplication.dialog.list.ServiceListHDialog;

import java.util.List;

public class UserTelFgAdapter extends RecyclerView.Adapter<UserTelFgAdapter.ViewHolder> {

    private ViewItemTelRowBinding binding;
    private List<UserTel> telJoins;
    private FragmentManager manager;

    public UserTelFgAdapter(List<UserTel> telJoins, FragmentManager manager) {
        this.telJoins = telJoins;
        this.manager = manager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewItemTelRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return telJoins.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ServiceListDAdapter.ItemClickLisner {

        private ViewItemTelRowBinding binding;

        public ViewHolder(@NonNull ViewItemTelRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.viewTelDeleteImageView.setOnClickListener(this);
            binding.viewTelNameTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.viewTelDeleteImageView.getId()){
                telJoins.remove(getBindingAdapterPosition());
                notifyDataSetChanged();
            } else if (v.getId() == binding.viewTelNameTextView.getId()){
                ServiceListHDialog serviceListHDialog = new ServiceListHDialog(3, this);
                serviceListHDialog.show(manager, "serviceListHDialog");
            }
        }

        @Override
        public void setOnItemClickLisner(int position) {
//            telJoins.get(getBindingAdapterPosition()).setTelName(menuList.getMenuName());
//            notifyDataSetChanged();
        }
    }

}
