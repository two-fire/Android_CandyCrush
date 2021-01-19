package com.xample.candycrush;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {
    public GestureDetector gestureDetector; // 创建手势检测器变量
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    public OnSwipeListener(Context context) {
        // 初始化手势检测器
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public static final int SWIPE_THRESOLD =100;
        public static final int SWIPE_VELOCITY_THRESOLD =100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float yDiff = e2.getY() - e1.getY();
            float xDiff = e2.getX() - e1.getX();
            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                // 我们解除在水平方向上或者垂直方向上的权利
                if (Math.abs(xDiff) > SWIPE_THRESOLD &&
                        Math.abs(velocityX) > SWIPE_VELOCITY_THRESOLD) {
                    if (xDiff > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(yDiff) > SWIPE_THRESOLD &&
                    Math.abs(velocityY) > SWIPE_VELOCITY_THRESOLD) {
                if (yDiff > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
            return result;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }

    public void onSwipeLeft(){}
    public void onSwipeRight(){}
    public void onSwipeTop(){}
    public void onSwipeBottom(){}
}
