package com.guojingbu.lsn06_drawing1.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.guojingbu.lsn06_drawing1.R;
import com.guojingbu.lsn06_drawing1.utils.Utils;

public class AtavarView extends View {
    private final static float WIDTH = Utils.dp2px(300);
    private final static float PADDING = Utils.dp2px(50);
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private  PorterDuffXfermode porterDuffXfermode;
    private RectF saveArea = new RectF();
    public AtavarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        mBitmap= getBitmap((int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        saveArea.set(PADDING,PADDING,PADDING+WIDTH,PADDING+WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int saveLayer = canvas.saveLayer(saveArea, mPaint);
        canvas.drawOval(PADDING,PADDING,PADDING+WIDTH,PADDING+WIDTH,mPaint);
        mPaint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(mBitmap,PADDING,PADDING,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    private Bitmap getBitmap(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(), R.drawable.atavar,options);
        options.inJustDecodeBounds=false;
        options.inDensity =options.outWidth;
        options.inTargetDensity=width;
        return BitmapFactory.decodeResource(getResources(),R.drawable.atavar,options);

    }


}
