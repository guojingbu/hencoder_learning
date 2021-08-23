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
 * desc   :多个手机交替完成
 */
public class MultiTouchView1 extends View {
    private static final int IMAGE_WIDTH = (int) Utils.dp2px(200);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;

    float downX;
    float downY;
    private float offsetX;
    private float offsetY;
    float originOffsetX;
    float originOffsetY;
    private int trackingId;

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(),IMAGE_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,offsetX,offsetY,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getActionMasked()){
            case  MotionEvent.ACTION_DOWN:
                trackingId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originOffsetX = offsetX;
                originOffsetY = offsetY;

            break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(trackingId);
                offsetX = originOffsetX+event.getX(index)-downX;
                offsetY = originOffsetY+event.getY(index)-downY;
                invalidate();
            break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);
                originOffsetX = offsetX;
                originOffsetY = offsetY;
                break;
            case MotionEvent .ACTION_POINTER_UP:
                 actionIndex = event.getActionIndex();
                 int pointerId = event.getPointerId(actionIndex);
                 int newIntex;
                 if(pointerId==trackingId){
                     if(actionIndex==event.getPointerCount()-1){
                         newIntex = event.getPointerCount()-2;
                     }else{
                         newIntex = event.getPointerCount()-1;
                     }
                     trackingId = event.getPointerId(newIntex);
                     downX = event.getX(newIntex);
                     downY = event.getY(newIntex);
                     originOffsetX = offsetX;
                     originOffsetY = offsetY;
                 }

                break;
        }

        return true;
    }
}
