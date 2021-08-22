package com.guojingbu.lsn14_nested_scroll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 通过draglistner实现拖拽
 */
public class DragListenerView extends ViewGroup {

    private GdragListener gdragListener = new GdragListener();
    public DragListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnDragListener(gdragListener);
    }

    private class GdragListener implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    //开始拖拽

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //拖拽的view进入到View的边界包括它自己的边界。

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DRAG_LOCATION:

                    break;
                case DragEvent.ACTION_DROP:

                    break;
            }


            return false;
        }
    }
}
