package com.example.myapplication.event;

import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

public class ImageButtonClickListener implements View.OnClickListener {

    private ImageButton imageButton;
    private int img_url;

    public ImageButtonClickListener(ImageButton imageButton, int img_url) {
        this.imageButton = imageButton;
        this.img_url = img_url;
    }

    @Override
    public void onClick(View v) {
        // 이미지 변경을 통한 클릭 효과 제공
        imageButton.setImageResource(img_url);

        // 클릭된 상태를 일정 시간 후에 원래 상태로 변경
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                // imageButton.setImageResource(R.drawable.button_normal);
            }
        }, 1000); // 1초 후에 원래 상태로 변경
    }
}

