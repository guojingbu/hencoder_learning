package com.yesway.lsn11_scaleable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;


/**
 * author : guojingbu
 * date   : 2021/8/13
 * desc   :
 */
public class ScaleableImageView extends View {
    private static final int IMAGE_WIDTH = (int) Utils.dp2px(300);
    public static final float SCALE_FACTOR = 1.5f;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Bitmap bitmap;
    private float originOffsetX;
    private float originOffsetY;
    private float offsetX;
    private float offsetY;
    private float bigScale;
    private float smallScale;
    private final GestureDetectorCompat detector;
    GGestureListener gGestureListener = new GGestureListener();
    private ScaleGestureDetector scaleDetector;
    private ScaleDetectorListener  scaleDetectorListener = new ScaleDetectorListener();
    private GFlingRunner flingRunner = new GFlingRunner();
    boolean big;
    private float currentScale;
    private final OverScroller scroller;
    private ObjectAnimator scaleAnimation;



    public ScaleableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), IMAGE_WIDTH);
        detector = new GestureDetectorCompat(context, gGestureListener);
        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context,scaleDetectorListener);
//        detector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener(){
//            @Override
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//                return super.onFling(e1, e2, velocityX, velocityY);
//            }
//
//            @Override
//            public boolean onDoubleTap(MotionEvent e) {
//                return super.onDoubleTap(e);
//            }
//        });
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        originOffsetX = (getWidth() - bitmap.getWidth()) / 2f;
        originOffsetY = (getHeight() - bitmap.getHeight()) / 2f;
        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            //???????????????,?????????????????????????????????????????????????????????
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * SCALE_FACTOR;
            Log.i("guojingbu", "if-------------smallScale = " + smallScale + "----bigScale = " + bigScale);
        } else {
            //??????????????????,?????????????????????????????????????????????????????????
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * SCALE_FACTOR;
            Log.i("guojingbu", "else-------------smallScale = " + smallScale + "----bigScale = " + bigScale);
        }
        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(offsetX, offsetY);
        float scaleFraction = (currentScale-smallScale)/(bigScale-smallScale);
        canvas.translate(offsetX*scaleFraction, offsetY*scaleFraction);
//        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(currentScale, currentScale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        //??????????????????
        if(!scaleDetector.isInProgress()){
            result = detector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getAnimoator().cancel();
    }

    public ObjectAnimator getAnimoator() {
        if (scaleAnimation == null) {
            scaleAnimation = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimation.setFloatValues(smallScale,bigScale);
        return scaleAnimation;
    }

    private class GFlingRunner implements Runnable{
        @Override
        public void run() {
            //????????????true????????????????????????????????????????????????????????????????????????
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    private class GGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

        //?????????????????????????????????????????????????????????????????????true?????????????????????
        //?????????????????????????????????????????????????????????true??????false?????????
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        //???????????????????????????
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("guojingbu", "--------------onSingleTapUp---------------");
            return false;
        }

        /**
         * ?????????????????????
         *
         * @param down      ?????????????????????
         * @param event     ????????????????????????
         * @param distanceX x???????????????????????????????????????distanceX = ?????????x???-?????????x???
         * @param distanceY y???????????????????????????????????????distanceY = ?????????Y???-?????????Y???
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            Log.i("guojingbu", "--------------onScroll---------------");
            if (big) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }
            return false;
        }

        //???????????????????????????
        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("guojingbu", "--------------onLongPress---------------");
        }

        //????????????
        @Override
        public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
            Log.i("guojingbu", "--------------onFling---------------");
            if (big) {
                //???view????????????????????????????????????offsetX???offsetY?????????startX???startY?????????
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);
                postOnAnimation(flingRunner);
            }
            return false;
        }

        //??????????????????????????????????????????????????????????????????????????????onSingleTapUp????????????????????????,
        // ??????????????????????????????onSingleTapUp???????????????onSingleTapConfirmed????????????????????????????????????onSingleTapConfirmed
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("guojingbu", "--------------onSingleTapConfirmed---------------");
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("guojingbu", "--------------onDoubleTap---------------");
            big = !big;
            if (big) {
                //
                offsetX =(e.getX()-getWidth()/2f)-(e.getX()-getWidth()/2f)*bigScale/smallScale;
                offsetX =(e.getX()-getHeight()/2f)-(e.getX()-getHeight()/2f)*bigScale/smallScale;
                fixOffsets();
                getAnimoator().start();
            } else {
                getAnimoator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i("guojingbu", "--------------onDoubleTapEvent---------------");
            return false;
        }
    }

    private void fixOffsets() {
        offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    private class ScaleDetectorListener  implements ScaleGestureDetector.OnScaleGestureListener{

        float initialScale;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //?????????????????????
            currentScale =initialScale*detector.getScaleFactor();
            if(currentScale<=smallScale){
                currentScale = smallScale;
                big = false;
            }
            if(currentScale>=bigScale){
                big = true;
                currentScale = bigScale;
            }
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
