package com.guojingbu.lsn14_nested_scroll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class DragHelperGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;
    ViewDragHelper viewDragHelper;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper=ViewDragHelper.create(this,new DragCallback());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childLeft = i % COLUMNS * childWidth;
            childTop = i / COLUMNS * childHeight;
            childView.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return  viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        //这个方法和overscroller的computeScrollOffset方法类似如果动画执行完就会返回true。
        //来结束这个死循环。
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {

        private int captureLeft;
        private int captureTop;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            Log.i("guojingbu", "tryCaptureView: ------------------");
            return true;
        }

        //拖拽的状态改变时调用。
        @Override
        public void onViewDragStateChanged(int state) {
            Log.i("guojingbu", "onViewDragStateChanged: -----------state = "+state);
            if(state==ViewDragHelper.STATE_IDLE){
                View capturedView = viewDragHelper.getCapturedView();
                if(capturedView!=null){
                    capturedView.setElevation(getElevation()-1);
                }
            }
        }
        //启用水平拖拽
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Log.i("guojingbu", "clampViewPositionHorizontal: -------------------");
            return left;
        }
        //启用纵向拖拽
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            Log.i("guojingbu", "clampViewPositionVertical: -------------------");
            return top;
        }
        //手指触摸以后就会调用，主要做一些准备工作。
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            Log.i("guojingbu", "onViewCaptured: -------------------");
            capturedChild.setElevation(getElevation()+1);
            captureLeft = capturedChild.getLeft();
            captureTop = capturedChild.getTop();
        }

        //移动位置变化时调用
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            Log.i("guojingbu", "onViewPositionChanged: -------------------");
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }
        //拖拽松手后会调用
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            Log.i("guojingbu", "onViewReleased: -------------------");
            //这个方法类似于overscroller的startScroll方法
            viewDragHelper.settleCapturedViewAt(captureLeft,captureTop);
            postInvalidateOnAnimation();
        }
    }
}
