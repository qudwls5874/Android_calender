package com.example.myapplication.dialog;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;
import com.example.myapplication.databinding.DialogServiceAddMeneyBinding;
import com.example.myapplication.event.WatcherMoneyText;

public class ServiceAddMoneyDialog extends Dialog implements View.OnClickListener {

    private DialogServiceAddMeneyBinding binding;
    private String money;
    private WatcherMoneyText moneyText = new WatcherMoneyText();
    private OnGetMoney getMoneyLisner;

    public ServiceAddMoneyDialog(@NonNull Context context, String money, OnGetMoney getMoneyLisner) {
        super(context);
        this.money = !money.equals("") ? money : "0";
        this.getMoneyLisner = getMoneyLisner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogServiceAddMeneyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();

    }

    private void initUI() {

        binding.dialogMoneyAllMoneyTextView.setText(money);
        pickerSetting();
        pickerChanged("");

        binding.dialogMoneyTextView1.setOnClickListener(this::onClick);
        binding.dialogMoneyTextView2.setOnClickListener(this::onClick);
        binding.dialogMoneyTextView3.setOnClickListener(this::onClick);
        binding.dialogMoneyTextView4.setOnClickListener(this::onClick);
        binding.dialogMoneyTextView5.setOnClickListener(this::onClick);
        binding.dialogMoneyCloseBtn.setOnClickListener(this::onClick);
        binding.dialogMoneyAddBtn.setOnClickListener(this::onClick);

        // 회전 막기
        binding.dialogMoneyPicker1.setWrapSelectorWheel(false);
        binding.dialogMoneyPicker1.setOnValueChangedListener(((picker, oldVal, newVal) -> {
            setAllMoney();
        }));
        binding.dialogMoneyPicker2.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if ( (oldVal == 0 && newVal == 9) && (!binding.dialogMoneyPicker1.getDisplayedValues()[binding.dialogMoneyPicker1.getValue()].equals("0"))){
                binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() - 1);
            } else if ( (oldVal == 9 && newVal == 0) ) {
                binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 1);
            }
            setAllMoney();
        });

        // 키패드
        binding.dialogMoneyKeypad1.setOnClickListener(v -> { pickerChanged("1"); });
        binding.dialogMoneyKeypad2.setOnClickListener(v -> { pickerChanged("2"); });
        binding.dialogMoneyKeypad3.setOnClickListener(v -> { pickerChanged("3"); });
        binding.dialogMoneyKeypad4.setOnClickListener(v -> { pickerChanged("4"); });
        binding.dialogMoneyKeypad5.setOnClickListener(v -> { pickerChanged("5"); });
        binding.dialogMoneyKeypad6.setOnClickListener(v -> { pickerChanged("6"); });
        binding.dialogMoneyKeypad7.setOnClickListener(v -> { pickerChanged("7"); });
        binding.dialogMoneyKeypad8.setOnClickListener(v -> { pickerChanged("8"); });
        binding.dialogMoneyKeypad9.setOnClickListener(v -> { pickerChanged("9"); });
        binding.dialogMoneyKeypad0.setOnClickListener(v -> { pickerChanged("0"); });
        binding.dialogMoneyKeypadBack.setOnClickListener(v -> {
            binding.dialogMoneyAllMoneyTextView.setText(
                    binding.dialogMoneyAllMoneyTextView.getText().toString().substring(0, binding.dialogMoneyAllMoneyTextView.getText().toString().length()-1)
            );
            pickerChanged("");
        });
        binding.dialogMoneyKeypadBack.setOnLongClickListener(v -> {
            binding.dialogMoneyPicker1.setValue(0);
            binding.dialogMoneyPicker2.setValue(0);
            binding.dialogMoneyPicker3.setDisplayedValues(new String[]{"000"});
            setAllMoney();
            return false;
        });
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == binding.dialogMoneyTextView1.getId()){
            if (Integer.parseInt(binding.dialogMoneyAllMoneyTextView.getText().toString().replaceAll(",", "")) + 1000 > 9999999)
                return;
            binding.dialogMoneyPicker2.setValue(binding.dialogMoneyPicker2.getValue() + 1);
            if (binding.dialogMoneyPicker2.getValue() < 1){
                binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 1);
            }
            setAllMoney();
        } else if (v.getId() == binding.dialogMoneyTextView2.getId()) {
            if (Integer.parseInt(binding.dialogMoneyAllMoneyTextView.getText().toString().replaceAll(",", "")) + 5000 > 9999999)
                return;
            binding.dialogMoneyPicker2.setValue(binding.dialogMoneyPicker2.getValue() + 5);
            if (binding.dialogMoneyPicker2.getValue() < 5){
                binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 1);
            }
            setAllMoney();
        } else if (v.getId() == binding.dialogMoneyTextView3.getId()) {
            if (Integer.parseInt(binding.dialogMoneyAllMoneyTextView.getText().toString().replaceAll(",", "")) + 10000 > 9999999)
                return;
            binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 1);
            setAllMoney();
        } else if (v.getId() == binding.dialogMoneyTextView4.getId()) {
            if (Integer.parseInt(binding.dialogMoneyAllMoneyTextView.getText().toString().replaceAll(",", "")) + 50000 > 9999999)
                return;
            binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 5);
            setAllMoney();
        } else if (v.getId() == binding.dialogMoneyTextView5.getId()) {
            if (Integer.parseInt(binding.dialogMoneyAllMoneyTextView.getText().toString().replaceAll(",", "")) + 100000 > 9999999)
                return;
            binding.dialogMoneyPicker1.setValue(binding.dialogMoneyPicker1.getValue() + 10);
            setAllMoney();
        } else if (v.getId() == binding.dialogMoneyCloseBtn.getId()) {
            dismiss();
        } else if (v.getId() == binding.dialogMoneyAddBtn.getId()) {
            getMoneyLisner.getMoney(binding.dialogMoneyAllMoneyTextView.getText().toString());
            dismiss();
        }


    }

    private void setAllMoney(){

        binding.dialogMoneyAllMoneyTextView.setText(
                moneyText.beforeMoneyTextChanged(
                        String.valueOf(
                                Integer.parseInt(binding.dialogMoneyPicker1.getDisplayedValues()[binding.dialogMoneyPicker1.getValue()]
                                        + binding.dialogMoneyPicker2.getDisplayedValues()[binding.dialogMoneyPicker2.getValue()]
                                        + binding.dialogMoneyPicker3.getDisplayedValues()[0]
                                )
                        )
                )
        );

    }

    // 초기값 세팅
    private void pickerSetting(){

        String[] dataIndex1 = new String[1000];
        for (int i = 0; i < dataIndex1.length; i++) {
            dataIndex1[i] = String.valueOf(i) ;
        }
        binding.dialogMoneyPicker1.setMinValue(0);
        binding.dialogMoneyPicker1.setMaxValue(dataIndex1.length-1);
        binding.dialogMoneyPicker1.setDisplayedValues(dataIndex1);

        String[] dataIndex2 = new String[10];
        for (int i = 0; i < dataIndex2.length; i++) {
            dataIndex2[i] = String.valueOf(i) ;
        }
        binding.dialogMoneyPicker2.setMinValue(0);
        binding.dialogMoneyPicker2.setMaxValue(dataIndex2.length-1);
        binding.dialogMoneyPicker2.setDisplayedValues(dataIndex2);

        binding.dialogMoneyPicker3.setMinValue(0);
        binding.dialogMoneyPicker3.setMaxValue(0);
        binding.dialogMoneyPicker3.setDisplayedValues(new String[]{"000"});

    }

    private void pickerChanged(String value){

        String allMoneyText = String.valueOf(binding.dialogMoneyAllMoneyTextView.getText()).replaceAll(",", "")
                + (!value.equals("") ? value:"");

        if (allMoneyText.length() > 7)
            return;

        binding.dialogMoneyPicker1.setValue(
                (allMoneyText.length() < 5) ? 0 : Integer.parseInt(allMoneyText.substring(0, allMoneyText.length() -4))
        );
        binding.dialogMoneyPicker2.setValue(
                (allMoneyText.length() < 4) ? 0 : Integer.parseInt(allMoneyText.substring(allMoneyText.length() - 4, allMoneyText.length() - 3))
        );
        binding.dialogMoneyPicker3.setDisplayedValues(
                new String[]{
                        ("000" + allMoneyText).substring(("000" + allMoneyText).length()-3)
                }
        );

        setAllMoney();
    }

    // 이동 애니메이션
    private void animateNumberPicker(NumberPicker picker, int value){
        ObjectAnimator animator = ObjectAnimator.ofInt(picker, "value", picker.getValue(), value);
        animator.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        // 다이얼로그의 배경을 투명하게 설정합니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 다이얼로그를 백그라운드 클릭 시 닫히지 않도록 설정합니다.
        setCanceledOnTouchOutside(false);
    }


    public interface OnGetMoney{
        void getMoney(String money);
    }



}
