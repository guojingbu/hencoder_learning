package com.guojingbu.lsn09_bitamap_drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.guojingbu.lsn09_bitamap_drawable.utils.Utils;

public class MaterialEditText extends androidx.appcompat.widget.AppCompatEditText {
    private static final int TEXT_SIZE = (int) Utils.dp2px(12);
    private static final int TEXT_MARGIN = (int) Utils.dp2px(8);
    private static final int TEXT_LEFT_PADDING = (int) Utils.dp2px(5);
    private static final int TEXT_VERTICAL_OFFSET = (int) Utils.dp2px(22);
    public static final int TEXT_ANIMATION_OFFSET = (int) Utils.dp2px(16);
    private static final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean floatLabelShown = false;
    float floatingLabelFraction;
    private ObjectAnimator animator;
    private boolean useFloatingLabel = true;
    private final Rect backgroundPadding = new Rect();

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        useFloatingLabel= typedArray.getBoolean(R.styleable.MaterialEditText_useFloatingLabel,true);
        typedArray.recycle();
        if(useFloatingLabel){
            onFloatingLoabelChanged();
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(useFloatingLabel){
                        if (floatLabelShown && TextUtils.isEmpty(s)) {
                            getAnimator().reverse();
                            floatLabelShown = false;
                        } else if (!floatLabelShown && !TextUtils.isEmpty(s)) {
                            getAnimator().start();
                            floatLabelShown = true;
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        mPaint.setTextSize(TEXT_SIZE);

    }

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel!=useFloatingLabel){
            this.useFloatingLabel = useFloatingLabel;
            //改变控件大小和重新布局需要调用requestLayout
            onFloatingLoabelChanged();
        }

    }
    private void onFloatingLoabelChanged(){
        getBackground().getPadding(backgroundPadding);
        if(useFloatingLabel){
//                setPadding(getPaddingLeft(), getPaddingTop() + TEXT_SIZE + TEXT_MARGIN, getPaddingRight(), getPaddingBottom());
            setPadding(getPaddingLeft(), backgroundPadding.top+ TEXT_SIZE + TEXT_MARGIN, getPaddingRight(), getPaddingBottom());
        }else{
            setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(), getPaddingBottom());
        }
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0,1);
        }

        return animator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_LEFT_PADDING, TEXT_VERTICAL_OFFSET + extraOffset, mPaint);

    }


}
