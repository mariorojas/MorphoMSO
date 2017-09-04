package com.morpho.demo.customs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.morpho.demo.R;

/**
 * Created by Alejandro Ruiz on 01/10/2015.
 */
public class FingerIndicator extends FrameLayout {

    private RelativeLayout content;
    private ImageView center;
    private boolean adquisitionOn = false;
    private Drawable fingerOK;
    private Drawable fingerKO;
    private boolean status;

    public FingerIndicator(Context context) {
        super(context);
        initView();
    }

    public FingerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FingerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.finger_layout, null);

        status = true;

        content = (RelativeLayout) view.findViewById(R.id.content);
        center = (ImageView) view.findViewById(R.id.center);

        fingerOK = getResources().getDrawable(R.drawable.rounded_finger_ok);
        fingerKO = getResources().getDrawable(R.drawable.rounded_finger_ko);

        addView(view);

    }

    public void initAdquisition() {
        adquisitionOn = true;
        final Handler handler = new Handler();


        new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                try {
                    while (adquisitionOn) {
                        if (i == 0) {
                            i++;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    content.setBackground(fingerOK);
                                }
                            });

                            Thread.sleep(400);
                        } else {
                            i--;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    content.setBackground(fingerKO);
                                }
                            });
                            Thread.sleep(400);
                        }
                    }

                    if (status) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                center.setVisibility(VISIBLE);
                                content.setBackground(fingerOK);
                                center.setImageDrawable(getResources().getDrawable(R.drawable.ok));
                            }
                        });

                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                center.setVisibility(VISIBLE);
                                content.setBackground(fingerKO);
                                center.setImageDrawable(getResources().getDrawable(R.drawable.ko));
                            }
                        });

                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }).start();

    }

    public void stopAdquisition() {
        adquisitionOn = false;
    }


    public void setStatus(boolean ok) {
        status = ok;

    }
}
