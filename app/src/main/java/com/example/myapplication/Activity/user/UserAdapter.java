package com.example.myapplication.Activity.user;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.table.User;
import com.example.myapplication.database.view.UserJoin;
import com.example.myapplication.databinding.ViewUserItemRowBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

   private ViewUserItemRowBinding binding;
   private ArrayList<UserJoin> userJoins;

   public UserAdapter(ArrayList<UserJoin> userJoins){
      this.userJoins = userJoins;
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
      holder.binding.rowNameTextView.setText(rowItem.user.getName());
      holder.binding.rowMoneyTextView.setText(rowItem.user.getMoney());

   }

   @Override
   public int getItemCount() {
      return userJoins.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

      ViewUserItemRowBinding binding;

      public ViewHolder(@NonNull ViewUserItemRowBinding binding) {
         super(binding.getRoot());
         this.binding = binding;
      }
   }



}