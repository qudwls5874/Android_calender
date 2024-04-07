package com.example.myapplication.event;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;


/**
 * @explain
 * 		[공통 다이얼로그 정의]
 * 		showNoticeDialog - 데이터 조회 프로그레스 다이얼로그 출력
 *      removeNoticeDialog - 데이터 조회 프로그레스 다이얼로그 숨김
 *		showDateDialog - 날짜 선택 다이얼로그(DatePicker)
 *      ShowSimpleAlertDialog - 확인용 다이얼로그(확인버튼만 있음)
 */
public class CommonDialog {

   private static String TAG = "scanner";

   /* Sound, Vibrator
   private static SoundAndVibratorManager soundAndVibratorManager;
   */

   private static Context context;


   /* 다이얼로그 숨김 카운트다운 */
   private static int COUNT_TIME_SHOWDIALOG = 1000;		// 이벤트 체크
   private static SHOWDIALOGTimer showDIALOGTimer;

   /* 데이터 조회용 공통 다이얼로그 */
   private static ProgressDialog progressDialog;

   static class SHOWDIALOGTimer extends CountDownTimer {

      public SHOWDIALOGTimer(long millisInFuture, long countDownInterval) {
         super(millisInFuture, countDownInterval);
      }

      @Override
      public void onTick(long millisUntilFinished) { }

      @Override
      public void onFinish() {
         showSimpleAlertDialog(context, "재 요청해주시기 바랍니다.");
         removeProgressBar();
      }
   }












   /**
    * @param _context
    * @param message
    * @explain 데이터 조회 프로그레스바 표출
    */
   public static void showProgressBar(Context _context, String message) {

      try {

         if((null!=progressDialog) && (progressDialog.isShowing()))
            progressDialog.dismiss();

         context = _context;
         progressDialog = new ProgressDialog(_context);
         progressDialog.setMessage(message);
         progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         progressDialog.setCancelable(true);
         progressDialog.setCanceledOnTouchOutside(false);

         progressDialog.show();

      } catch(android.view.WindowManager.BadTokenException e) { }


      if (showDIALOGTimer == null)
         showDIALOGTimer = new SHOWDIALOGTimer(COUNT_TIME_SHOWDIALOG*13, COUNT_TIME_SHOWDIALOG);
      else
         showDIALOGTimer.cancel();

      showDIALOGTimer.start();

   }

   /**
    * @explain 데이터 조회 프로그레스바 숨김
    */
   public static void removeProgressBar() {

      /* 소영과장님의 조언 */
      if( (null!=progressDialog) && (progressDialog.isShowing()) ) {
         showDIALOGTimer.cancel();
         showDIALOGTimer = null;

         progressDialog.dismiss();
         progressDialog=null;
//			isShowDialog = false;
      }

   }

   public static boolean isProgressBarShowing() {
      return (progressDialog!=null)? progressDialog.isShowing() : false;
   }

   public static ProgressDialog getProgressBarDialog() {
      return progressDialog;
   }

   /**
    * @param context
    * @param onDateSetListener
    * @param dateType
    * @param dateValue
    * @explain 날짜 선택 다이얼로그
    */
   public static void showDateDialog(Context context, OnDateSetListener onDateSetListener, String dateType,
                                     String dateValue) {

      int year	= 0;
      int month	= 0;
      int day		= 0;

      if (dateValue.equals("")) {
         final Calendar c = Calendar.getInstance();
         year  = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day   = c.get(Calendar.DAY_OF_MONTH);
      } else {
         year  = Integer.valueOf(dateValue.substring(0, 4));
         month = Integer.valueOf(dateValue.substring(4, 6));
         day   = Integer.valueOf(dateValue.substring(6, 8));
      }

      DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerDialogStyle, onDateSetListener, year, month - 1, day);
      datePickerDialog.show();
   }




   private static AlertDialog.Builder builder;
   private static AlertDialog alert;

   /**
    * @param context
    * @param msg
    * @explain
    * 	기본 얼럿 다이얼로그(확인버튼만 있음)
    */
   public static void showSimpleAlertDialog(Context context, String msg) {

      if ( (alert!=null) && (alert.isShowing()) ) {
         try {
            alert.dismiss();
         } catch(java.lang.IllegalArgumentException e) {
//				return;
         }

      }


      builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
      builder.setMessage(msg).setCancelable(false)
              .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                    alert.dismiss();
                 }
              });

      alert = builder.create();


      /* 진동 & 음향
      if(null==soundAndVibratorManager)
         soundAndVibratorManager = new SoundAndVibratorManager(context);
      soundAndVibratorManager.callVibrator();
      */

      try {
         alert.show();
      } catch(ActivityNotFoundException e) {
      } catch(android.view.WindowManager.BadTokenException e) { }

   }

   public static boolean isSimpleAlertDialogShowing() {
      return (alert != null) ? alert.isShowing() : false;
   }




   /////////////////// 수량 컨트롤 //////////////////////

   private static boolean checkChangeNumber=false;

   public static boolean isChangeNumber() {
      return checkChangeNumber;
   }

   public static TextWatcher getTextWatcher() {
      TextWatcher textWatcher = new TextWatcher() {

         private Editable target;

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (isChangeNumber())
               return;


            checkChangeNumber = true;

            String inputAmountValue = s.toString();
//                Log.i(TAG, "> INPUT AMOUNT VALUE ORIGINAL : " + s);
//                Log.i(TAG, "> INPUT AMOUNT VALUE : " + inputAmountValue);

            String result = integerValidateCheck(inputAmountValue);
//                Log.i(TAG, "> INPUT AMOUNT VALUE [ 5-LEVEL ] : " + result);

            if (target != null) {
               target.setFilters(new InputFilter[]{});
               target.replace(0, target.length(), result, 0, result.length());
            }

            checkChangeNumber = false;

         }

         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // TODO Auto-generated method stub
         }

         @Override
         public void afterTextChanged(Editable s) {

            if (isChangeNumber())
               return;

            target = s;
         }
      };

      return textWatcher;
   }


   private static String integerValidateCheck(String amountValue) {

      /* 최초 입력 값이 "." 일 경우 */
      if (amountValue.equals(".")) {
         amountValue = amountValue.replace(".", "") + ".";
         return amountValue;
      }


      if (amountValue.length() > 0) {
         int maxDecimal=4; // 2020.02.10  4 -> 6
         DecimalFormat df = new DecimalFormat("#.0###");
         NumberFormat nf = NumberFormat.getInstance();
         nf.setMaximumFractionDigits(maxDecimal);                // 소수점 이하 최대 4자리
         df.setMaximumFractionDigits(maxDecimal);				// 소수점 이하 최대 6자리
         // nf.setRoundingMode(RoundingMode.UNNECESSARY); // 2020.02.10 RoundingMode.UNNECESSARY

         /* 최대 입력 값이 넘어 갈 경우 입력 방지(안하고 계속 입력 시, 값이 증가 됨) */
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 00-LEVEL ] : " + amountValue);
         String[] tempSplitAmount = amountValue.split("\\.");
//            Log.i(TAG, "**소수분할 : " + "" + tempSplitAmount.length);
         if (tempSplitAmount.length > 1) {
//                Log.i(TAG, "**소수점 이하 개수 : " + "" + tempSplitAmount[tempSplitAmount.length - 1].length());
            if(tempSplitAmount[tempSplitAmount.length - 1].length()>maxDecimal) {
//                    Log.i(TAG,  "4개이상임:" + getRoundingModeDown(amountValue, maxDecimal));
               return getRoundingModeDown(amountValue, maxDecimal);
            }
         }




//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 1-LEVEL ] : " + amountValue);
         amountValue = getOnlyNumber(amountValue);
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 2-LEVEL ] : " + amountValue);

         boolean decimalExistence = false;
         if (amountValue.substring(amountValue.length() - 1).equals(".") || amountValue.substring(amountValue.length() - 1).equals("-")) {

            /* 소수점이 2개가 들어간 경우 */
            if (getCountSpecialWord(amountValue) > 1) {
               StringBuilder tempAmountValue = new StringBuilder(amountValue);
               tempAmountValue.setCharAt((tempAmountValue.length() - 1), ' ');
               amountValue = tempAmountValue.toString().trim();
//                    Log.i(TAG, "> INPUT AMOUNT VALUE [ 2'-LEVEL ] : " + amountValue);
            } else
               decimalExistence = true;
         } else {
            decimalExistence = false;
         }


//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 3-LEVEL ] : " + amountValue);
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 3-LEVEL / 1-DGREE ] : " + amountValue);
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 3-LEVEL / 2-DGREE ] : " + Double.parseDouble(amountValue));
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 3-LEVEL / 3-DGREE ] : " + (nf.format(Double.parseDouble(amountValue))));
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 3-LEVEL / 4-DGREE ] : " + String.valueOf(nf.format(Double.parseDouble(amountValue))));

//			if (!(amountValue.length() > 2 && amountValue.substring(amountValue.length() -2, amountValue.length()).equals(".0"))
//					|| !(amountValue.indexOf(".") != (-1) && amountValue.substring(amountValue.length()-1, amountValue.length()).equals("0")))


         if (tempSplitAmount.length == 1) {
            amountValue = String.valueOf(nf.format(Double.parseDouble(amountValue)));
         }
//            Log.i(TAG, "> INPUT AMOUNT VALUE [ 4-LEVEL ] : " + amountValue);

         if (decimalExistence) {
            amountValue = amountValue + ".";
         }

      }

      return amountValue;
   }

   public static String getOnlyNumber(String amountValue) {
      return amountValue.replaceAll("[-,]", "");
   }

   /* 문자열에서 특정 문자의 갯수 구하기 */
   private static int getCountSpecialWord(String word) {
      word = word.trim();
      String specialWord = ".";
      int count = 0;

      char specialValue = specialWord.charAt(0);
      int fromIndex = 0;
      while (true) {
         int index = word.indexOf(specialValue, fromIndex);
         if (index == -1)
            break;
         else {
            count++;
            fromIndex = index + 1;
         }
      }

//        Log.i(TAG, "> '.'의 개수 : " + count);

      return count;
   }


   /* 소수점 이하 지정 된 자리수 이상 일 경우 버림처리 */
   private static String getRoundingModeDown(String word, int maxDecimal) {
      word = word.trim();
      String specialWord = ".";
      char specialValue = specialWord.charAt(0);

      int fromIndex = word.length()-1;

      Log.i(TAG, "**specialValue : " + specialValue);
      Log.i(TAG, "**fromIndex : " + fromIndex);
      int index = word.indexOf(specialValue, 0);
      Log.i(TAG, "**Index : " + index);
      if (index == -1)
         return word;
      else {
         fromIndex = index + maxDecimal;
      }

//        Log.i(TAG, "****Index : " + fromIndex);
//        Log.i(TAG, "************************************************");
      return word.substring(0, fromIndex);
   }

}
