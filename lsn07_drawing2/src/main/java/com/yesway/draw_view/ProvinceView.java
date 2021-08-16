package com.yesway.draw_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yesway.draw_view.utils.Utils;

/**
 * author : guojingbu
 * date   : 2021/8/4
 * desc   :
 */
public class ProvinceView extends View {
    private String province="北京市";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        mPaint.setTextSize(Utils.dp2px(30));
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(province,getWidth()/2,getHeight()/2,mPaint);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
        invalidate();
    }
}
