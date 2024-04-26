package com.example.myapplication.Activity.setting.tel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ViewSettingTelItemBinding;

import java.util.List;

public class SettingTelMainAdapter extends RecyclerView.Adapter<SettingTelMainAdapter.ViewHolder> {

    private ViewSettingTelItemBinding binding;
    private List<TelData> telDataList;
    private OnTelItemClickListener onTelItemClickListener;

    public SettingTelMainAdapter(List<TelData> telDataList, OnTelItemClickListener onTelItemClickListener){
        this.telDataList = telDataList;
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
        TelData data = telDataList.get(position);

        holder.binding.itemTelNameTextView.setText(data.getName());

        // 프로필 사진
        if (data.getProfile() != null){
            // 동그란 프로필 이미지 생성
            Bitmap circleBitmap = Bitmap.createBitmap(data.getProfile().getWidth(), data.getProfile().getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawCircle(data.getProfile().getWidth() / 2f, data.getProfile().getHeight() / 2f, data.getProfile().getWidth() / 2f, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(data.getProfile(), 0, 0, paint);

            holder.binding.itemTelProfileImageView.setImageBitmap(circleBitmap);
        } else {
            holder.binding.itemTelProfileImageView.setImageResource(R.drawable.ic_person_image);
        }

        // 선택 디자인
        if (data.isChoiceTel()){
            holder.binding.itemTelCheckLayout.setVisibility(View.VISIBLE);
        } else {
            holder.binding.itemTelCheckLayout.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return telDataList.size();
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
                int posision = getBindingAdapterPosition();

                TelData telData = telDataList.get(posision);
                telData.setChoiceTel(!telData.isChoiceTel());

                onTelItemClickListener.onTelItemClick(telData.getId());

                notifyItemChanged(posision);

            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (v.getId() == binding.itemTelCardView.getId()){
                onTelItemClickListener.onTelItemLongClick(telDataList.get(getBindingAdapterPosition()));
            }
            return false;
        }

    }

    public interface OnTelItemClickListener{
        void onTelItemClick(int telId);
        void onTelItemLongClick(TelData telData);
    }


}
