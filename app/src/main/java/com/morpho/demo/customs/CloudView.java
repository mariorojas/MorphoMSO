package com.morpho.demo.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.morpho.demo.R;

import java.util.Random;

/**
 * Created by Alex on 23/01/2015.
 */
public class CloudView extends FrameLayout{

    private ImageView center;
    private int type;
    private int size;
    private RelativeLayout centerC;

    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
    private static final int TYPE_3 = 3;

    public static final int SIZE_1 = 10;
    public static final int SIZE_2 = 20;
    public static final int SIZE_3 = 30;

    public CloudView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public CloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CloudView(Context context) {
        super(context);
        initView();
    }

    public CloudView(Context context,int size) {
        super(context);
        this.size = size;
        initView();
    }

    private void setType(int type,int size){
        this.type = type;
        this.size = size;
        if(center != null) {
            switch (type) {
                case TYPE_1:
                    //center.setImageResource(R.drawable.logo_morpho);
                    center.setImageResource(R.drawable.logo_teknei);
                    break;
                case TYPE_2:
                    //center.setImageResource(R.drawable.logo_morpho);
                    center.setImageResource(R.drawable.logo_teknei);
                    break;
                case TYPE_3:
                    //center.setImageResource(R.drawable.logo_morpho);
                    center.setImageResource(R.drawable.logo_teknei);
                    break;
            }
            /*
            RelativeLayout.LayoutParams params = null;

            switch (size){
                case SIZE_1:
                    params = new RelativeLayout.LayoutParams(40* TypedValue.COMPLEX_UNIT_DIP,40* TypedValue.COMPLEX_UNIT_DIP);
                    break;
                case SIZE_2:
                    params = new RelativeLayout.LayoutParams(75* TypedValue.COMPLEX_UNIT_DIP,75* TypedValue.COMPLEX_UNIT_DIP);
                    break;
                case SIZE_3:
                    params = new RelativeLayout.LayoutParams(100* TypedValue.COMPLEX_UNIT_DIP,100* TypedValue.COMPLEX_UNIT_DIP);
                    break;
            }


            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            center.setScaleType(ImageView.ScaleType.FIT_CENTER);
            center.setLayoutParams(params);
            */
        }
    }

    private void initView(){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.cloud_layout, null);

        center = (ImageView) view.findViewById(R.id.center);
        centerC = (RelativeLayout) view.findViewById(R.id.centerC);

        type = getRandumNumber(1,3);

        addView(view);
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
        switch (type) {
            case TYPE_1:
                type = TYPE_2;
                break;
            case TYPE_2:
                type = TYPE_3;
                break;
            case TYPE_3:
                type = TYPE_1;
                break;
        }
        setType(type,size);
    }

    private static int getRandumNumber(int low, int high){
        Random r = new Random();
        int Low = low;
        int High = high;
        int R = r.nextInt(High-Low) + Low;

        return R;
    }
}
