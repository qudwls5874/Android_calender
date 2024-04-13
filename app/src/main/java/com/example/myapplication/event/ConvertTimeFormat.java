package com.example.myapplication.event;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertTimeFormat {

    String inputTime;

    public ConvertTimeFormat(String inputTime){
        this.inputTime = inputTime;
    }

    public String convertTimeFormat() {

        Log.i("inputTime", inputTime);

        if (inputTime.equals("0000")){
            return "오전 00:00";
        } else if (inputTime.equals("2400")) {
            return "오후 00:00";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("HHmm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("a hh:mm");

        try {
            Date date = inputFormat.parse(inputTime);
            String outputTime = outputFormat.format(date);

            if (outputTime.startsWith("AM")) {
                return outputTime.replace("AM ", "오전 ");
            } else {
                return outputTime.replace("PM ", "오후 ");
            }
        } catch (ParseException e) {
            return "잘못된 시간 형식입니다";
        }
    }

    public String convertTimeFormat2() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("a hh:mm");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HHmm");

        try {
            Date date = inputFormat.parse(inputTime);

            return outputFormat.format(date);
        } catch (ParseException e) {
            return "잘못된 시간 형식입니다";
        }
    }



}
