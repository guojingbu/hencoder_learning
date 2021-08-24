package com.guojingbu.lsn14_nested_scroll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.guojingbu.lsn14_nested_scroll.R;

public class DragUpDownLayout extends FrameLayout {

    private final ViewDragHelper viewDragHelper;
    private final ViewConfiguration viewConfiguration;
    private DragListener dragListener = new DragListener();
    private View view;

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewDragHelper = ViewDragHelper.create(this,dragListener);
        viewConfiguration = ViewConfiguration.get(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理收到的事件序列
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if(viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    private class  DragListener extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return view==child;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if(Math.abs(yvel)>viewConfiguration.getScaledMinimumFlingVelocity()){
                if(yvel>0){
                    viewDragHelper.settleCapturedViewAt(0,getHeight()-releasedChild.getHeight());
                }else{
                    viewDragHelper.settleCapturedViewAt(0,0);
                }
            }else{
                if(releasedChild.getTop()<(getHeight()-releasedChild.getBottom())){
                    viewDragHelper.settleCapturedViewAt(0,0);
                }else{
                    viewDragHelper.settleCapturedViewAt(0,getHeight()-releasedChild.getHeight());
                }
            }
          ViewCompat.postInvalidateOnAnimation(DragUpDownLayout.this);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }
    }
}
