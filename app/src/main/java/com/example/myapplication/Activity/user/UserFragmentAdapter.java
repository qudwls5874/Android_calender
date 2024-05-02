package com.example.myapplication.Activity.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.databinding.ViewUserItemRowBinding;

import java.io.File;
import java.util.ArrayList;

public class UserFragmentAdapter extends RecyclerView.Adapter<UserFragmentAdapter.ViewHolder> {

   private ViewUserItemRowBinding binding;
   private ArrayList<UserJoin> userJoins;
   private OnItemClickListener listener;

   public UserFragmentAdapter(ArrayList<UserJoin> userJoins, OnItemClickListener listener){
      this.userJoins = userJoins;
      this.listener = listener;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      binding = ViewUserItemRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      return new ViewHolder(binding);
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      UserJoin rowItem = userJoins.get(position);

      // 사용자 이미지
      String fileName = rowItem.user.getUserId() + ".jpg";
      File file = new File(holder.binding.getRoot().getContext().getFilesDir(), fileName);
      if (file.exists()){
         // 변경 가능한(mutable) 비트맵 생성
         Bitmap image = BitmapFactory.decodeFile(file.getAbsolutePath());
         Bitmap circleBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(circleBitmap);
         Paint paint = new Paint();
         paint.setAntiAlias(true);
         canvas.drawCircle(image.getWidth() / 2f, image.getHeight() / 2f, image.getWidth() / 2f, paint);
         paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
         canvas.drawBitmap(image, 0, 0, paint);

         holder.binding.rowProfileImageView.setImageBitmap(circleBitmap);
      } else {
         holder.binding.rowProfileImageView.setImageResource(R.drawable.ic_person_image);
      }

      // 화면에 데이터 표출
      holder.binding.rowProfileNameTextView.setText(rowItem.user.getName());
      holder.binding.rowProfileMoneyTextView.setText(rowItem.user.getMoney());

   }

   @Override
   public int getItemCount() {
      return userJoins.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

      ViewUserItemRowBinding binding;

      public ViewHolder(@NonNull ViewUserItemRowBinding binding) {
         super(binding.getRoot());
         this.binding = binding;

         binding.rowProfileCardView.setOnClickListener(this);
      }

      @Override
      public void onClick(View v) {
         if (v.getId() == binding.rowProfileCardView.getId()){
            listener.OnItemClickListener(getLayoutPosition());
         }
      }

   }

   public interface OnItemClickListener {
      void OnItemClickListener(int position);
   }



}