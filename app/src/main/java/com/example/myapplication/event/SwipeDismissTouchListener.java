package com.example.myapplication.event;

import android.view.MotionEvent;
import android.view.View;

public class SwipeDismissTouchListener implements View.OnTouchListener {
    private static final int SWIPE_DISTANCE_THRESHOLD = 100;
    private static final int MIN_START_Y = 120;

    private float startY;
    private boolean isMoving;
    private DismissCallbacks dismissCallbacks;

    public interface DismissCallbacks {
        void onDismiss();
    }

    public SwipeDismissTouchListener(View view, DismissCallbacks dismissCallbacks) {
        this.dismissCallbacks = dismissCallbacks;
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getRawY();
                isMoving = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = event.getRawY() - startY;
                if (dy > SWIPE_DISTANCE_THRESHOLD && startY > MIN_START_Y) {
                    isMoving = true;
                    v.setTranslationY(dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isMoving) {
                    float dyUp = event.getRawY() - startY;
                    if (dyUp > v.getHeight() / 3) {
                        dismissCallbacks.onDismiss();
                    } else {
                        v.animate().translationY(0).setDuration(200);
                    }
                }
                break;
        }
        return true;
    }
}
