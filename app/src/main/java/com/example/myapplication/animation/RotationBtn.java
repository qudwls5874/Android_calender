package com.example.myapplication.animation;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.example.myapplication.R;


public class RotationBtn {

    private ImageButton imageButton;
    private Animation animation;

    public RotationBtn(ImageButton imageButton){
        this.imageButton = imageButton;
        this.animation = AnimationUtils.loadAnimation(imageButton.getContext(), R.anim.rotate_clockwise);
        this.animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startAnimation(){
        imageButton.startAnimation(animation);
    }


}
