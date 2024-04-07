package com.example.myapplication.Activity.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.database.table.User;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> implements Filterable {

   private Context context;
   private int resurce;
   private ArrayList<User> itemList;
   private LayoutInflater inflater;


   public UserAdapter(@NonNull Context _context, int _resource, @NonNull ArrayList<User> _itemList) {
      super(_context, _resource, _itemList);

      context = _context;
      resurce = _resource;
      itemList = _itemList;

      inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   }


   @Nullable
   @Override
   public User getItem(int _position) {
      return this.getItem(_position);
   }

   @NonNull
   @Override
   public View getView(int _position, @Nullable View _convertView, @NonNull ViewGroup _parent) {
      return getCustomView(_position, _convertView, _parent);
   }

   @Override
   public View getDropDownView(int _position, @Nullable View _convertView, @NonNull ViewGroup _parent) {
      return getCustomView(_position, _convertView, _parent);
   }

   public View getCustomView(int _position, View _convertView, ViewGroup _parent){

      User rowItem = this.itemList.get(_position);

      final ViewHolder holder;
      View view = _convertView;
      if (view == null){
         view = inflater.inflate(resurce, null);
         holder = new ViewHolder();
         assert view != null;
         holder.row_profile_imageView = (ImageView) view.findViewById(R.id.row_profile_imageView);
         holder.row_name_textView1 = (TextView) view.findViewById(R.id.row_name_textView);
         holder.row_tel_textView2 = (TextView) view.findViewById(R.id.row_tel_textView);
         holder.row_money_textView3 = (TextView) view.findViewById(R.id.row_money_textView);
         view.setTag(holder);
      } else {
         holder = (ViewHolder) view.getTag();
      }

      /* 화면에 데이터 표출 */
      holder.row_name_textView1.setText(rowItem.getName());
      holder.row_tel_textView2.setText(rowItem.getTel());
      holder.row_money_textView3.setText(rowItem.getMoney());

      return view;
   }




   static class ViewHolder {
      private ImageView row_profile_imageView;  // 프로필
      private TextView row_name_textView1;		// 이름
      private TextView row_tel_textView2;		// 번호
      private TextView row_money_textView3;		// 금액
   }







}