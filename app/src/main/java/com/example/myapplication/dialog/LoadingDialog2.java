package com.example.myapplication.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.myapplication.R;

public class LoadingDialog2 {

   private Dialog dialog;

   public LoadingDialog2(Context context){
      dialog = new Dialog(context);
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setContentView(R.layout.loading_layout);
      dialog.setCancelable(false);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
      progressBar.setIndeterminate(true);
   }

   public void show() {
      if (dialog != null && !dialog.isShowing()) {
         dialog.show();
      }
   }

   public void dismiss() {
      if (dialog != null && dialog.isShowing()) {
         dialog.dismiss();
      }
   }



}
