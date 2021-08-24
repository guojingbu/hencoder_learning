package com.guojingbu.lsn14_nested_scroll.view;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.guojingbu.lsn14_nested_scroll.R;

public class DragToCollectLayout extends RelativeLayout {

    private ImageView mIvAvatar;
    private ImageView mIvLogo;
    private DragListener dragListener=new DragListener();
    OnLongClickListener dragStarter = v -> {
        ClipData imageData = ClipData.newPlainText("name", v.getContentDescription());
        //startDragAndDrop第二个参数是支持跨进程的，只有在松手时候给可以获取到，
        //第四个参数localState在任何时候都可以获取到，比较轻量级。
        return ViewCompat.startDragAndDrop(v, imageData, new DragShadowBuilder(v), null, 0);
    };
    private LinearLayout mLlCollectLayout;

    public DragToCollectLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mIvAvatar = findViewById(R.id.iv_avatar);
        mIvLogo = findViewById(R.id.iv_logo);
        mLlCollectLayout = findViewById(R.id.ll_cllect_layout);

        mIvAvatar.setOnLongClickListener(dragStarter);
        mIvLogo.setOnLongClickListener(dragStarter);
        mLlCollectLayout.setOnDragListener(dragListener);

    }

    private class DragListener implements OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()){
                case DragEvent.ACTION_DROP:
                    if(v instanceof LinearLayout){
                        LinearLayout linearLayout = (LinearLayout)v;
                        TextView textView = new TextView(getContext());
                        textView.setTextSize(16);
                        textView.setText(event.getClipData().getItemAt(0).getText());
                        linearLayout.addView(textView);
                    }

                    break;
            }
            return true;
        }
    }
}
