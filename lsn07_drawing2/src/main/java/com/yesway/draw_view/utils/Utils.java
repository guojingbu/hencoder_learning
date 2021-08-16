package com.yesway.draw_view.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.yesway.draw_view.R;

/**
 * author : guojingbu
 * date   : 2021/8/2
 * desc   :
 */
public class Utils {
    /**
     * dp转为px
     * @param dp
     * @return
     */
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
    public static  Bitmap getAvatar(Resources resources,int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(resources, R.drawable.avatar,options);
        options.inJustDecodeBounds=false;
        options.inDensity=options.outWidth;
        options.inTargetDensity = width;
        return  BitmapFactory.decodeResource(resources,R.drawable.avatar,options);
    }
    public static float getZCamera(){
        return -6*Resources.getSystem().getDisplayMetrics().density;
    }
}
