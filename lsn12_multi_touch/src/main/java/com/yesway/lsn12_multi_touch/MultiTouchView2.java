package com.yesway.lsn12_multi_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : guojingbu
 * date   : 2021/8/16
 * desc   : 多个手指协作完成
 */
public class MultiTouchView2 extends View {
    private static final int IMAGE_WIDTH = (int) Utils.dp2px(200);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;

    float downX;
    float downY;
    private float offsetX;
    private float offsetY;
    float originOffsetX;
    float originOffsetY;

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), IMAGE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float focusX;
        float focusY;
        float sumX = 0;
        float sumY = 0;
        int pointerCount = event.getPointerCount();
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }
        //由于在手指抬起的时候还是会返回之前数量的pointer，
        // 只有在下一个move到来的时候才会减掉抬起的pointer个数，
        // 所以这里我们需要把抬起的pointer个数减掉。
        if (isPointerUp) {
            pointerCount -= 1;
        }
        focusX = sumX / pointerCount;
        focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = focusX;
                downY = focusY;
                originOffsetX = offsetX;
                originOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = originOffsetX + focusX - downX;
                offsetY = originOffsetY + focusY - downY;
                invalidate();
                break;
        }

        return true;
    }
}
