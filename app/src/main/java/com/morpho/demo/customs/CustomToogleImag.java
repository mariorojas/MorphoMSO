package com.morpho.demo.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.Constants;

/**
 * Created by Alex on 11/02/2015.
 */
public class CustomToogleImag extends FrameLayout {

    private RelativeLayout root;

    private boolean isSelected;

    private TextView brandName;
    private ImageView brandImage;
    private int TYPE;

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
        updateSelectStatus();

    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public CustomToogleImag(Context context) {
        super(context);
        initView();
    }

    public CustomToogleImag(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomToogleImag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int type) {
        this.TYPE = type;
        updateView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.giro_filter_button, null);
        root = (RelativeLayout) view.findViewById(R.id.root);
        brandName = (TextView) view.findViewById(R.id.brandName);
        brandImage = (ImageView) view.findViewById(R.id.brandImage);
        root.setOnClickListener(new InnerSelectListener());
        addView(view);
    }

    private void updateView() {

        switch (TYPE){
            case Constants.FINGERPRING_TYPE:
                brandName.setText("FINGERPRINT");
                brandImage.setImageDrawable(getResources().getDrawable(R.drawable.fingerprint));
                break;
            case Constants.FACIAL_TYPE:
                brandName.setText("FACIAL");
                brandImage.setImageDrawable(getResources().getDrawable(R.drawable.facial));
                break;
            case Constants.VOICE_TYPE:
                brandName.setText("VOICE");
                brandImage.setImageDrawable(getResources().getDrawable(R.drawable.voice));
                break;
        }
    }


    private void updateSelectStatus(){
        if(isSelected){
            root.setBackground(getResources().getDrawable(R.drawable.rounded_finger_ok));
        }else {
            root.setBackground(getResources().getDrawable(R.drawable.rounded_finger_normal));
        }
    }

    private class InnerSelectListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            isSelected = !isSelected;
            updateSelectStatus();
        }
    }
}
