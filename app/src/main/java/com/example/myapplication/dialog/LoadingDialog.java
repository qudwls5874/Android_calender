package com.example.myapplication.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {

   private ProgressDialog mpProgressDialog;

   public LoadingDialog(Context _conContext){
      mpProgressDialog = new ProgressDialog(_conContext);
      mpProgressDialog.setMessage("로딩 중...");
      mpProgressDialog.setCancelable(false);
   }

   public void show(){
      if (mpProgressDialog != null && !mpProgressDialog.isShowing()){
         mpProgressDialog.show();
      }
   }

   public void dismiss(){
      if (mpProgressDialog != null && mpProgressDialog.isShowing()) {
         mpProgressDialog.dismiss();
      }
   }



}
