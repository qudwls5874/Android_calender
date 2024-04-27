package com.example.myapplication.Activity.setting.tel;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.databinding.ViewSettingTelItemBinding;
import com.example.myapplication.dataclass.UserProfile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SettingTelMainAdapter extends RecyclerView.Adapter<SettingTelMainAdapter.ViewHolder> {

    private ViewSettingTelItemBinding binding;

    private List<UserJoin> userDataList;    // 검색된 리스트
    private List<UserProfile> profileList;  // 전체 사진
    private List<UserJoin> insertList;      // 저장 리스트

    private OnTelItemClickListener onTelItemClickListener;

    public SettingTelMainAdapter(List<UserJoin> userDataList, List<UserJoin> insertList, List<UserProfile> profileList, OnTelItemClickListener onTelItemClickListener){
        this.userDataList = userDataList;
        this.insertList = insertList;
        this.profileList = profileList;
        this.onTelItemClickListener = onTelItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewSettingTelItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User data = userDataList.get(position).user;

        holder.binding.itemTelNameTextView.setText(data.getName());

        // 프로필 사진
        UserProfile profile = profileList.stream()
                .filter(result -> result.getProfileId().equals(data.getUserUrl()))
                .findFirst()
                .orElse(new UserProfile("", null));


        if (profile.getProifle() != null){
            // 동그란 프로필 이미지 생성
            Bitmap circleBitmap = Bitmap.createBitmap(profile.getProifle().getWidth(), profile.getProifle().getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(profile.getProifle().getWidth() / 2f, profile.getProifle().getHeight() / 2f, profile.getProifle().getWidth() / 2f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(profile.getProifle(), 0, 0, paint);

            holder.binding.itemTelProfileImageView.setImageBitmap(circleBitmap);
        } else {
            holder.binding.itemTelProfileImageView.setImageResource(R.drawable.ic_person_image);
        }

        // 선택 디자인
        if (insertList.stream().filter(result -> result.user.getUserUrl().equals(userDataList.get(position).user.getUserUrl())).findFirst().orElse(null) == null){
            holder.binding.itemTelCheckLayout.setVisibility(View.GONE);
        } else {
            holder.binding.itemTelCheckLayout.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItemCheckList(List<UserJoin> insertList){
        this.insertList = insertList;
        notifyDataSetChanged(); // 데이터가 변경되었음을 RecyclerView에 알립니다.
    }

    @Override
    public int getItemCount() {
        return userDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private ViewSettingTelItemBinding binding;

        public ViewHolder(@NonNull ViewSettingTelItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.itemTelCardView.setOnClickListener(this);
            binding.itemTelCardView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (v.getId() == binding.itemTelCardView.getId()){
                onTelItemClickListener.onTelItemClick(getBindingAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == binding.itemTelCardView.getId()){
                onTelItemClickListener.onTelItemLongClick(userDataList.get(getBindingAdapterPosition()));
            }
            return false;
        }

    }

    public interface OnTelItemClickListener{
        void onTelItemClick(int position);
        void onTelItemLongClick(UserJoin userData);
    }


}
