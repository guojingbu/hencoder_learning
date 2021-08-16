package com.yesway.draw_view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yesway.draw_view.utils.Utils;

/**
 * author : guojingbu
 * date   : 2021/8/2
 * desc   :
 */
public class CamraView extends View {
    private static final int PADING = 100;
    private static final int IMAGE_WIDTH = 600;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Camera camera = new Camera();
    private float topFlip = 0;
    private float bottomFlip = 0;
    private float flipRotation = 0;

    public CamraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        camera.setLocation(0, 0, Utils.getZCamera());//-8=-8*72px -8的单位是英尺。
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制上半部分（注意这一块可以不移动直接切割但是可能切割的坐标需要重新计算（不移动之前））
        canvas.save();
        canvas.translate(PADING + IMAGE_WIDTH / 2, PADING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADING + IMAGE_WIDTH / 2), -(PADING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), IMAGE_WIDTH), PADING, PADING, mPaint);
        canvas.restore();

        //绘制的下半部分
        canvas.save();
//        canvas.clipRect(0,100+600/2,getWidth(),getHeight());
        canvas.translate(PADING + IMAGE_WIDTH / 2, PADING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.clipRect(-IMAGE_WIDTH, 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADING + IMAGE_WIDTH / 2), -(PADING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), IMAGE_WIDTH), PADING, PADING, mPaint);
        canvas.restore();
    }

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    public void startAnim() {
        ObjectAnimator bottomFlipAnim = ObjectAnimator.ofFloat(this, "bottomFlip", 45);
        bottomFlipAnim.setDuration(1500);

        ObjectAnimator flipAnim = ObjectAnimator.ofFloat(this, "flipRotation", 270);
        flipAnim.setDuration(1500);

        ObjectAnimator topFlipAnim = ObjectAnimator.ofFloat(this, "topFlip", -45);
        topFlipAnim.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(bottomFlipAnim,flipAnim,topFlipAnim);
        animatorSet.setStartDelay(1000);
        animatorSet.start();
    }
}
