package com.morpho.demo.customs;

import android.app.Activity;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.morpho.demo.tools.AnimationManage;
import com.morpho.demo.tools.CloudAnimListener;
import com.morpho.demo.tools.TypeDectecter;

import java.util.Random;

/**
 * Created by Alex on 25/01/2015.
 */
public class CloudCreate {

    private Activity thisContext;
    private RelativeLayout square;

    public CloudCreate(Activity thisContext, RelativeLayout square){
        this.thisContext = thisContext;
        this.square = square;
    }

    public void setClouds(boolean isRight){
        int index = 0;
        while (index < 3){
            setCloud(isRight,index);
            index++;
        }
    }

    private void setCloud(boolean isRight,int index){
        int h = getHeightRandomly();
        CloudView cloud = null;
        RelativeLayout.LayoutParams params = null;
        switch (h){
            case 80:
                cloud = new CloudView(thisContext,CloudView.SIZE_1);
                if(TypeDectecter.isSmallResolution(thisContext))
                    params = new RelativeLayout.LayoutParams(80* TypedValue.COMPLEX_UNIT_DIP, 60*TypedValue.COMPLEX_UNIT_DIP);
                else
                    params = new RelativeLayout.LayoutParams(150* TypedValue.COMPLEX_UNIT_DIP, 100*TypedValue.COMPLEX_UNIT_DIP);
                break;
            case 160:
                cloud = new CloudView(thisContext,CloudView.SIZE_2);
                if(TypeDectecter.isSmallResolution(thisContext))
                    params = new RelativeLayout.LayoutParams(100*TypedValue.COMPLEX_UNIT_DIP, 140*TypedValue.COMPLEX_UNIT_DIP);
                else
                    params = new RelativeLayout.LayoutParams(160*TypedValue.COMPLEX_UNIT_DIP, 120*TypedValue.COMPLEX_UNIT_DIP);
                break;
            case 200:
                cloud = new CloudView(thisContext,CloudView.SIZE_3);
                if(TypeDectecter.isSmallResolution(thisContext))
                    params = new RelativeLayout.LayoutParams(220*TypedValue.COMPLEX_UNIT_DIP, 220*TypedValue.COMPLEX_UNIT_DIP);
                else
                    params = new RelativeLayout.LayoutParams(310*TypedValue.COMPLEX_UNIT_DIP, 310*TypedValue.COMPLEX_UNIT_DIP);
                break;
        }
        switch (index) {
            case 0:
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                params.addRule(isRight ? RelativeLayout.ALIGN_PARENT_RIGHT : RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case 1:
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.addRule(isRight ? RelativeLayout.ALIGN_PARENT_RIGHT : RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case 2:
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                params.addRule(isRight ? RelativeLayout.ALIGN_PARENT_RIGHT : RelativeLayout.ALIGN_PARENT_LEFT);
                break;
        }

        Animation animation = null;

        if(!isRight) {
            animation = AnimationManage.getCloudAnimRight(thisContext, CloudAnimListener.getAnimationVelocity());
        }else {
            animation = AnimationManage.getCloudAnimLeft(thisContext, CloudAnimListener.getAnimationVelocity());
        }

        animation.setAnimationListener(new CloudAnimListener(cloud,isRight,index));
        cloud.setLayoutParams(params);
        square.addView(cloud);
        cloud.startAnimation(animation);
    }

    private int getRandumNumber(int low, int high){
        Random r = new Random();
        int Low = low;
        int High = high;
        int R = r.nextInt(High-Low) + Low;

        return R;
    }

    private int getHeightRandomly(){
        int height = 50;
        int random = getRandumNumber(0, 10);
        switch (random){
            case 0:
                height = 80;
                break;
            case 1:
                height = 160;
                break;
            case 2:
                height = 200;
                break;
            case 3:
                height = 80;
                break;
            case 4:
                height = 160;
                break;
            case 5:
                height = 200;
                break;
            case 6:
                height = 160;
                break;
            case 7:
                height = 200;
                break;
            case 8:
                height = 80;
                break;
            case 9:
                height = 200;
                break;
            case 10:
                height = 160;
                break;

        }

        return height * TypedValue.COMPLEX_UNIT_DIP;
    }
}
