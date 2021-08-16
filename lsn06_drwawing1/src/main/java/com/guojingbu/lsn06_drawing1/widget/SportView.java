package com.guojingbu.lsn06_drawing1.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.guojingbu.lsn06_drawing1.utils.Utils;

public class SportView extends View {
    private static float RADIUS = Utils.dp2px(150);
    private static int CIRCLE_COLOR = Color.parseColor("#D9D9D9");
    private static float RING_WIDTH = Utils.dp2px(20);
    private static int PROGRESS_COLOR = Color.parseColor("#EE30A7");
    private static String DRAW_TEXT = "abab";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect bounds = new Rect();
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(Utils.dp2px(100));
        mPaint.getFontMetrics(fontMetrics);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆环
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(CIRCLE_COLOR);
        mPaint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, RADIUS, mPaint);
        //绘制进度
        mPaint.setColor(PROGRESS_COLOR);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS,-90,225,false,mPaint);
        //绘制文字
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(Utils.dp2px(100));
        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.getTextBounds(DRAW_TEXT,0,DRAW_TEXT.length(),bounds);//适合固定文字的情况。
//        int offset = (bounds.top+bounds.bottom)/2;
        float offset = (fontMetrics.ascent+fontMetrics.descent)/2;//这种方式也可以居中但是比较适合文字可变的情况
        canvas.drawText(DRAW_TEXT,getWidth()/2,getHeight()/2-offset,mPaint);
        //绘制文字2，左对齐问题
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTextSize(Utils.dp2px(150));
        mPaint.getTextBounds("aaaa",0,"aaaa".length(),bounds);
        canvas.drawText("aaaa",-bounds.left,300,mPaint);

        mPaint.setTextSize(Utils.dp2px(15));
        canvas.drawText("aaaa",0,300+mPaint.getFontSpacing(),mPaint);
    }
}
