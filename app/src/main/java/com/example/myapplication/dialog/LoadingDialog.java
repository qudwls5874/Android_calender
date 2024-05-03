package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.myapplication.R;

public class LoadingDialog {

   private Dialog dialog;

   public LoadingDialog(Context context){

      // 다이얼로그 인스턴스 생성
      dialog = new Dialog(context);

      // 타이틀 바 숨김
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

      // 레이아웃 설정
      dialog.setContentView(R.layout.loading_layout);

      // 다이얼로그 외부 터치시 닫히지 않게 설정
      dialog.setCancelable(false);

      // 배경을 투명하게 설정
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      // 다이얼로그 배경의 어두운 효과 제거
      dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

      // 프로그레스 바 인스턴스 가져오기
      ProgressBar progressBar = dialog.findViewById(R.id.progressBar);

      // 프로그레스 바를 무한 회전 모드로 설정
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
