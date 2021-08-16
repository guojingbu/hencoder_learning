package com.yesway.draw_view;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yesway.draw_view.utils.ProvinceUtils;
import com.yesway.draw_view.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private ProvinceView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.view);
//        view.startAnim();

        //通过关键帧来控制动画
//        float LENGTH = Utils.dp2px(300);
//        Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
//        Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 0.4f*LENGTH);
//        Keyframe keyframe3 = Keyframe.ofFloat(0.8f, 0.6f*LENGTH);
//        Keyframe keyframe4 = Keyframe.ofFloat(1, 1*LENGTH);
//
//        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationX",keyframe1,keyframe2,keyframe3,keyframe4);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolder);
//        animator.setStartDelay(1000);
//        animator.setDuration(1500);
//        animator.start();


        //自定义小点移动使用TypeEvaluater定义动画过程
//        Point targetPoint = new Point((int) Utils.dp2px(300), (int) Utils.dp2px(200));
//        ObjectAnimator animator = ObjectAnimator.ofObject(view, "point", new PointEvaluator(), targetPoint);
//        animator.setStartDelay(1000);
//        animator.setDuration(1500);
//        animator.start();

        //通过动画切换城市
        ObjectAnimator animator = ObjectAnimator.ofObject(view, "province", new ProvinceEvaluator(), "安徽省");
        animator.setStartDelay(1000);
        animator.setDuration(2000);
        animator.start();
    }

    private class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            Point point = new Point();
            point.x=(int)(startValue.x+(endValue.x-startValue.x)*fraction);
            point.y=(int)(startValue.y+(endValue.y-startValue.y)*fraction);
            return point;
        }
    }
    private class ProvinceEvaluator implements  TypeEvaluator<String>{

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int index = (int)(ProvinceUtils.getProvince().indexOf(startValue)+(ProvinceUtils.getProvince().indexOf(endValue)-ProvinceUtils.getProvince().indexOf(startValue))*fraction);
            return ProvinceUtils.getProvince().get(index);
        }
    }
}