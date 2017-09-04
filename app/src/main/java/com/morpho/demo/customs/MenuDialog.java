/**
 *
 */
package com.morpho.demo.customs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.MenuItemSelecter;
import com.morpho.demo.tools.DimensionValuesTools;
import com.morpho.demo.tools.AnimationManage;

import java.util.Vector;


public class MenuDialog extends Dialog implements DialogInterface.OnShowListener{

    private RelativeLayout dateContent;
    private TextView outSide;
    private MenuItemSelecter uses;
    private LinearLayout listOptions;
    private Vector<String> options;

    public MenuDialog(Context context,
                      MenuItemSelecter uses) {
        super(context);
        this.uses = uses;
        options = new Vector<>(3);
        options.add("TOMAR DATOS GENERALES");
        options.add("TOMAR HUELLAS DIGITALES");
        options.add("TOMAR FOTO");
        options.add("GRABAR AUDIOS");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setCancelable(true);

        listOptions = (LinearLayout) findViewById(R.id.listOptions);

        outSide = (TextView) findViewById(R.id.outSide);
        outSide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        dateContent = (RelativeLayout) findViewById(R.id.dateContent);
        dateContent.setAlpha(1);
        dateContent.setBackground(new ColorDrawable(Color.WHITE));
        dateContent.setVisibility(View.GONE);
        dateContent.setOnClickListener(null);

        setOnShowListener(this);

        setUpQuestionarySections();

    }

    private void setUpQuestionarySections(){
        if(options != null){
            CustomButtonLarge button = null;
            for(int i=0; i < options.size(); i++){
                button = new CustomButtonLarge(getContext());
                button.setText(options.get(i));
                switch (i){
                    case 0:
                        button.setOnClickListener(new InnerListener(MenuItemSelecter.TAKE_GENERALS));
                        break;
                    case 1:
                        button.setOnClickListener(new InnerListener(MenuItemSelecter.FINGER_PRINTS));
                        break;
                    case 2:
                        button.setOnClickListener(new InnerListener(MenuItemSelecter.TAKE_PICTURE));
                        break;
                    case 3:
                        button.setOnClickListener(new InnerListener(MenuItemSelecter.REQUET_CREDIT_CARD));
                        break;
                }


                listOptions.addView(button);
            }

            View view = null;
            LinearLayout.LayoutParams params = null;
            for(int i=0; i< listOptions.getChildCount();i++){
                view = listOptions.getChildAt(i);
                params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0,0,0, DimensionValuesTools.getSizeToDP(30, getContext().getResources()));
            }
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        dateContent.setVisibility(View.GONE);
        listOptions.setVisibility(View.GONE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dateContent.setVisibility(View.VISIBLE);
                dateContent.startAnimation(AnimationManage.getLeftInAnim(getContext(), 450));
            }
        }, 300);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listOptions.setVisibility(View.VISIBLE);
                listOptions.startAnimation(AnimationManage.getLeftInAnim(getContext(), 450));
            }
        }, 755);
    }

    private class InnerListener implements View.OnClickListener{

        private int optionValue;

        public InnerListener(int optionValue){
            this.optionValue = optionValue;
        }

        @Override
        public void onClick(View view) {
            if(uses != null)
                uses.onQuestionarySelected(optionValue);
            onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {

        dateContent.startAnimation(AnimationManage.getLeftOutAnim(getContext(), 450));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },455);
    }
}
