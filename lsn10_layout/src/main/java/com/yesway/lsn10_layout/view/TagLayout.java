package com.yesway.lsn10_layout.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * author : guojingbu
 * date   : 2021/8/11
 * desc   :
 */
public class TagLayout extends ViewGroup {
    List<Rect> childrenBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int usedWidth = getPaddingLeft()+getPaddingRight();
        int lineUsedWidth = getPaddingLeft()+getPaddingRight();
        int usedHeight = getPaddingTop()+getPaddingBottom();
        int lineMaxHeight = 0;
        int specWidth= MeasureSpec.getSize(widthMeasureSpec)- getPaddingLeft()-getPaddingRight();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量子view widthUsed不设置为0时，会使view压小，设置为0：告诉子view它的可用空间很大可以随便显示。
            //我们需要手动计算从哪开始折行
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, usedHeight);
            if(lineUsedWidth+child.getMeasuredWidth()>specWidth){
                lineUsedWidth = getPaddingLeft()+getPaddingRight();
                usedHeight+=lineMaxHeight;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, usedHeight);
                lineMaxHeight=0;
            }
            Rect childBound;
            if (childrenBounds.size() <= i) {
                childBound = new Rect();
                childrenBounds.add(childBound);
            } else {
                childBound = childrenBounds.get(i);
            }
            childBound.set(lineUsedWidth, usedHeight, lineUsedWidth + child.getMeasuredWidth(), usedHeight + child.getMeasuredHeight());
            lineUsedWidth += child.getMeasuredWidth();
            usedWidth=Math.max(usedWidth,lineUsedWidth);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }
        //计算自己的宽高
        int width = usedWidth;
        int height = lineMaxHeight;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect childBound = childrenBounds.get(i);
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
