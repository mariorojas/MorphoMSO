/**
 *
 */
package com.morpho.demo.customs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.DateSelection;
import com.morpho.demo.tools.AnimationManage;

import java.util.Calendar;


public class DateDialog extends Dialog implements DatePicker.DateWatcher {

    private DateSelection uses;
    private RelativeLayout dateContent;
    private TextView outSide;
    private TextView title;
    private DatePicker dateSelecter;
    private Button selectButton;
    private int initYear = 0;
    private int endYear = 0;
    private Calendar initDate;
    private LinearLayout center;


    public DateDialog(Context context,
                      DateSelection uses) {
        super(context);
        this.uses = uses;
    }

    public DateDialog(Context context,int initYear, int endYear,
                      DateSelection uses) {
        super(context);
        this.uses = uses;
        this.initYear = initYear;
        this.endYear = endYear;
    }

    public DateDialog(Context context,int initYear, int endYear, Calendar initDate,
                      DateSelection uses) {
        super(context);
        this.uses = uses;
        this.initYear = initYear;
        this.endYear = endYear;
        this.initDate = initDate;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.date_dialog);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        setCancelable(true);

        title = (TextView) findViewById(R.id.title);

        dateSelecter = (DatePicker) findViewById(R.id.dateSelecter);
        dateSelecter.setDateChangedListener(this);

        try {
            dateSelecter.setFirstYear(initYear);
            dateSelecter.setEndYear(endYear);
            dateSelecter.setStartDate(initDate);


        } catch (Exception e) {
            Log.e("", e.toString());
        }

        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uses != null){
                    uses.dateSelected(dateSelecter.getStartDate());
                }
                onBackPressed();
            }
        });

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

        center = (LinearLayout) findViewById(R.id.center);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dateContent.setVisibility(View.VISIBLE);
                dateContent.startAnimation(AnimationManage.getUpAnim(getContext(), 450, false));
            }
        },300);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                center.setVisibility(View.VISIBLE);
                center.startAnimation(AnimationManage.getLeftInAnim(getContext(), 450));
            }
        },755);

    }


    @Override
    public void onBackPressed() {

        dateContent.startAnimation(AnimationManage.getDownAnim(getContext(),450,false));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },455);
    }

    @Override
    public void onDateChanged(Calendar c) {
        if(uses != null){
            uses.dateSelected(c);
        }
    }
}
