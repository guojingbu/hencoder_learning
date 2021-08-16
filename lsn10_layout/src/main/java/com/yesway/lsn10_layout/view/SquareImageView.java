package com.yesway.lsn10_layout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * author : guojingbu
 * date   : 2021/8/11
 * desc   :
 */
public class SquareImageView extends androidx.appcompat.widget.AppCompatImageView {
    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        int size = Math.min(measuredWidth, measuredHeight);
        setMeasuredDimension(size,size);
    }

    //一般不会这个干，因为这样相当于子view没有使用父view传进来的尺寸，直接使用了自己计算的尺寸。
    // 这样会导致父view并不知道你使用的是什么寸，父view还以为你使用的是它要求你使用的尺寸。
//    @Override
//    public void layout(int l, int t, int r, int b) {
//        int width = r - l;
//        int height = b - t;
//        int size = Math.min(width, height);
//        super.layout(l, t, l + size, t + size);
//    }
}
