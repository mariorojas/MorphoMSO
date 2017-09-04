package com.morpho.demo.tools;

import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.morpho.demo.customs.CloudView;
import com.morpho.demo.customs.CloudCreate;

import java.util.Random;

/**
 * Created by Alex on 23/01/2015.
 */
public class CloudAnimListener implements Animation.AnimationListener{

    private CloudView imageView;
    private boolean isRight;
    private int type;
    private boolean start;
    private RelativeLayout.LayoutParams params;
    private CloudCreate cloudCreate;

    public CloudAnimListener(CloudView imageView,boolean isRight, int type){
        this.imageView = imageView;
        this.isRight = isRight;
        this.type = type;
        start = true;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(imageView != null) {
            params = (RelativeLayout.LayoutParams)imageView.getLayoutParams();
            int w = imageView.getWidth() * (-1);
            if(!isRight){
                params.setMargins(0,0,w,0);
            }else {
                params.setMargins(w,0,0,0);
            }

            imageView.setLayoutParams(params);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(imageView != null){
            imageView.setVisibility(View.GONE);
            animation.setDuration(getAnimationVelocity());
            imageView.startAnimation(animation);
            //square.removeView(imageView);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public static long getAnimationVelocity(){
        long velocity = 50000;
        long velocity2 = 38000;

        int r = getRandumNumber(0,10000);
        boolean w = r % 2==0;

        return w?velocity:velocity2;
    }

    private static int getRandumNumber(int low, int high){
        Random r = new Random();
        int Low = low;
        int High = high;
        int R = r.nextInt(High-Low) + Low;

        return R;
    }
}
