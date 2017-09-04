package com.morpho.demo.tools;

import android.content.res.Resources;

/**
 * Created by Alex on 11/02/2015.
 */
public class DimensionValuesTools {

    public static int getSizeToDP(int size, Resources resources){
        final float scale = resources.getDisplayMetrics().density;
        int scaled = (int) (size * scale + 0.5f);

        return scaled;
    }
}
