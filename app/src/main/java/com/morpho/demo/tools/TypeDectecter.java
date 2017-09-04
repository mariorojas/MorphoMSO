package com.morpho.demo.tools;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by Alex on 20/01/2015.
 */
public class TypeDectecter {

    public static boolean isSmall(Activity context) {

        boolean small = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL);
        if (!small) {
            //boolean middle = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL);
            Display display = context.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            float res = (float)height / width;
            if (res <= 1.65f)
                small = true;
            else if(height <= 850){
                small = true;
            }
        }

        return small;
    }


    public static boolean isSmallResolution(Activity activity){
        boolean isSmall = true;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density  = activity.getResources().getDisplayMetrics().density;
        if(density >= 3.0)
            isSmall = false;

        return isSmall;
    }
}
