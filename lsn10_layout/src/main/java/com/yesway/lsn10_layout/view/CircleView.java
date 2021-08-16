package com.yesway.lsn10_layout.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.yesway.lsn10_layout.Utils;

/**
 * author : guojingbu
 * date   : 2021/8/11
 * desc   :
 */
public class CircleView extends View {
    private static final int PADDING = (int) Utils.dp2px(30);
    private static final int RADIUS = (int) Utils.dp2px(80);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width =2*(PADDING+RADIUS);
        int height = 2*(PADDING+RADIUS);
        Log.i("guojingbu","specWidth = "+MeasureSpec.getSize(widthMeasureSpec));
        Log.i("guojingbu","specHeight = "+MeasureSpec.getSize(heightMeasureSpec));
        Log.i("guojingbu","specMode = "+(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.AT_MOST));
        width = resolveSizeAndState(width, widthMeasureSpec, 0);
        height = resolveSizeAndState(height,heightMeasureSpec,0);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(PADDING+RADIUS,PADDING+RADIUS,RADIUS,mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
