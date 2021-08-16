package com.yesway.draw_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yesway.draw_view.utils.Utils;

/**
 * author : guojingbu
 * date   : 2021/8/2
 * desc   :
 */
public class ImageTextView extends View {
    private TextPaint textPaint = new TextPaint();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;
    private float[] cutWidth;

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        textPaint.setTextSize(Utils.dp2px(12));
        bitmap = getAvatar((int) Utils.dp2px(100));
        mPaint.setTextSize(Utils.dp2px(16));
        cutWidth = new float[1];
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //简单的多行文字绘制第一种
//        StaticLayout staticLayout = new StaticLayout("近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”" +
//                "这个假设作为研究重点之一。这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，" +
//                "其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。" +
//                "近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
//                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
//                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
//                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。",textPaint,getWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,false);
//        staticLayout.draw(canvas);
        //指定位置换行

        canvas.drawBitmap(bitmap,getWidth()-Utils.dp2px(100),Utils.dp2px(50),mPaint);
        String text = "近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”" +
                "这个假设作为研究重点之一。这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，" +
                "其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。" +
                "近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。近日，世界卫生组织秘书处单方面提出第二阶段病毒溯源工作计划，其中将“中国违反实验室规程造成病毒泄漏”这个假设作为研究重点之一。" +
                "这与此前世卫组织国际专家和中国专家联合研究得出的结论与建议并不相符。";
        int index = mPaint.breakText(text, true, getWidth(), cutWidth);
        canvas.drawText(text,0,index,0,50,mPaint);
        int oldIndex = index;
        index = mPaint.breakText(text,index,text.length(),true,getWidth(),cutWidth);
        canvas.drawText(text,oldIndex,index+oldIndex,0,50+mPaint.getFontSpacing(),mPaint);
        oldIndex = index;
        index = mPaint.breakText(text,index,text.length(),true,getWidth()-Utils.dp2px(100),cutWidth);
        canvas.drawText(text,oldIndex,index+oldIndex,0,50+mPaint.getFontSpacing()*2,mPaint);
    }

    public Bitmap getAvatar(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),R.drawable.avatar,options);
        options.inJustDecodeBounds=false;
        options.inDensity=options.outWidth;
        options.inTargetDensity = width;
        return  BitmapFactory.decodeResource(getResources(),R.drawable.avatar,options);
    }


}
