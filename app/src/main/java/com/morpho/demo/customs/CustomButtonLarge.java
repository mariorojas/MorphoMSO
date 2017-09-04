/**
 *
 */
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


/**
 * @author Alex
 *
 */
public class CustomButtonLarge extends FrameLayout{

    private TextView button;
    private OnClickListener listener;
    private boolean isDone;
    private boolean selected;
    private ImageView check;
    private RelativeLayout cumtomButton;

    public CustomButtonLarge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public CustomButtonLarge(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomButtonLarge(Context context) {
        super(context);
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.custom_button, null);
        isDone = false;
        button = (TextView) view.findViewById(R.id.button);
        check = (ImageView) view.findViewById(R.id.check);
        cumtomButton = (RelativeLayout) view.findViewById(R.id.cumtomButton);
        updateView();
        addView(view);
    }

    private void updateView(){
        if(button != null){
            if(isDone){
                cumtomButton.setBackground(getResources().getDrawable(R.drawable.main_button_done));
                button.setTextColor(getResources().getColor(R.color.white));
                check.setVisibility(VISIBLE);
                if(listener != null)
                    cumtomButton.setOnClickListener(listener);
            }else if(selected) {
                cumtomButton.setBackgroundColor(getResources().getColor(R.color.answer));
                button.setTextColor(getResources().getColor(R.color.white));
                check.setVisibility(GONE);
                if(listener != null)
                    cumtomButton.setOnClickListener(listener);
            }else{
                cumtomButton.setBackground(getResources().getDrawable(R.drawable.main_button_selector));
                button.setTextColor(getResources().getColor(R.color.white));
                check.setVisibility(GONE);
                if(listener != null)
                    cumtomButton.setOnClickListener(listener);
            }
        }
    }

    public void setIsDone(boolean isDone){
        this.isDone = isDone;
        updateView();
    }

    public void setSelected(boolean selected){
        this.selected = selected;
        updateView();
    }

    public void setText(String tag){
        if(button != null){
            button.setText(tag);
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.listener = l;
        button.setOnClickListener(new InnerOnClickListener());
    }

    private class InnerOnClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            if(listener != null){
                if(!selected) {
                    //selected = true;
                    updateView();
                }
                listener.onClick(v);
            }
        }
    }

}
