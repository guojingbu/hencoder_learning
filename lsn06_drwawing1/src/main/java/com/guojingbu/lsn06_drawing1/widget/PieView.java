package com.guojingbu.lsn06_drawing1.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.guojingbu.lsn06_drawing1.utils.Utils;

public class PieView  extends View {
    private static final float RADIUS= Utils.dp2px(150);
    private Paint mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF bounds = new RectF();
    private float OFFSET=Utils.dp2px(10);
    int startAngle=0;
    int[] angles = {60,100,120,80};
    int[] colors ={Color.parseColor("#ef5b9c"),
            Color.parseColor("#f7acbc"),
            Color.parseColor("#fedcbd"),
            Color.parseColor("#8f4b2e")};
    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#000000"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < angles.length; i++) {
            mPaint.setColor(colors[i]);
            canvas.save();
            if(i==2){
                canvas.translate((float)(OFFSET*Math.cos(Math.toRadians(startAngle+angles[i]/2))),(float)(OFFSET*Math.sin(Math.toRadians(startAngle+angles[i]/2))));
            }
            canvas.drawArc(bounds,startAngle,angles[i],true,mPaint);
            canvas.restore();
            startAngle+=angles[i];
        }

    }
}
