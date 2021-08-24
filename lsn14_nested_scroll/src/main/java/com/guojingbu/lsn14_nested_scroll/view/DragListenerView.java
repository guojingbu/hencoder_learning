package com.guojingbu.lsn14_nested_scroll.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过draglistner实现拖拽
 */
public class DragListenerView extends ViewGroup {
    private static final String TAG = DragListenerView.class.getSimpleName();
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;
    private GdragListener gdragListener = new GdragListener();
    private List<View> orderedChildren = new ArrayList<>();
    private final ViewConfiguration viewConfiguration;
    private View draggedView;

    public DragListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewConfiguration = ViewConfiguration.get(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        Log.i(TAG, "onDragEvent: ------------");
        return super.onDragEvent(event);
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
            View child = getChildAt(i);
            childLeft = i % COLUMNS * childWidth;
            childTop = i / COLUMNS * childHeight;
            child.layout(0, 0, childWidth, childHeight);
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            orderedChildren.add(child);
            child.setOnLongClickListener(v -> {
                draggedView = v;
                child.startDrag(null, new DragShadowBuilder(v), v, 0);
                return false;
            });
            child.setOnDragListener(gdragListener);
        }
    }

    private class GdragListener implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //开始拖拽
                    Log.i(TAG, "onDrag: ------ACTION_DRAG_STARTED----");
                    if (event.getLocalState() == v) {
                        v.setVisibility(INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //拖拽的view进入到View的边界包括它自己的边界。
                    Log.i(TAG, "onDrag: ------ACTION_DRAG_ENTERED----");
                    if (event.getLocalState() != v) {
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i(TAG, "onDrag: ------ACTION_DRAG_EXITED----");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i(TAG, "onDrag: ------ACTION_DRAG_LOCATION----");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //拖拽结束
                    Log.i(TAG, "onDrag: ------ACTION_DRAG_ENDED----");
                    v.setVisibility(VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.i(TAG, "onDrag: ------ACTION_DROP----");
                    break;
            }


            return true;
        }
    }

    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = orderedChildren.get(i);
            if (targetView == child) {
                targetIndex = i;
            } else if (draggedView == child) {
                draggedIndex = i;
            }
        }
        orderedChildren.remove(draggedIndex);
        orderedChildren.add(targetIndex, draggedView);
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < getChildCount(); index++) {
            View child = orderedChildren.get(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }
    }
}
