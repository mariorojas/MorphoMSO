package com.morpho.demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.customs.CustomToogleImag;
import com.morpho.demo.tools.AnimationManage;
import com.morpho.demo.tools.Util;

/**
 * Created by alex on 09/07/2015.
 */
public class SettingsActivity extends ParentActivity implements View.OnClickListener{

    private CloudCreate cloudCreate;
    private RelativeLayout square;
    private CustomToogleImag fingerprint;
    private CustomToogleImag facial;
    private CustomToogleImag voice;
    private Button continueBtn;
    private TextView title;
    private String rest;

    public static int CASE;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.form_incomplete_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_bar));

        setCloudCreate();

        title = (TextView) findViewById(R.id.title);
        switch (CASE){
            case Constants.ENROLL_CASE:
                rest = "ENROLAMIENTO";
                break;
            case Constants.AUTHENTICATE_CASE:
                rest = "AUTENTICACIÓN";
                break;
            case Constants.IDENTIFY_CASE:
                rest = "IDENTIFICACIÓN";
                break;
        }


        title.setText("Elige con que quieres inciar tu: "+rest);

        continueBtn = (Button) findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);
        setToogles();

    }



    private void setToogles(){

        fingerprint = (CustomToogleImag) findViewById(R.id.fingerprint);
        if(Util.getSettingFigerprint(thisContext)) {
            fingerprint.setVisibility(View.VISIBLE);
            fingerprint.setTYPE(Constants.FINGERPRING_TYPE);
            fingerprint.setAnimation(AnimationManage.getLeftInAnimBounce(this, 1400));
        }else{
            fingerprint.setVisibility(View.GONE);
        }

        facial = (CustomToogleImag) findViewById(R.id.facial);
        if(Util.getSettingFacial(thisContext)) {
            facial.setVisibility(View.VISIBLE);
            facial.setTYPE(Constants.FACIAL_TYPE);
            facial.setAnimation(AnimationManage.getRightInAnimBounce(this, 1400));
        }else{
            facial.setVisibility(View.GONE);
        }


        voice = (CustomToogleImag) findViewById(R.id.voice);
        if(Util.getSettingVoice(thisContext)) {
            voice.setVisibility(View.VISIBLE);
            voice.setTYPE(Constants.VOICE_TYPE);
            voice.setAnimation(AnimationManage.getRightInAnimBounce(this, 1000));
        }else{
            voice.setVisibility(View.GONE);
        }

        if(CASE == Constants.IDENTIFY_CASE){
            voice.setVisibility(View.GONE);
        }

        continueBtn.setAnimation(AnimationManage.getFadeIn(1500));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate our menu which can gather user input for switching settings
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.config_uri, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.settings_mbss) {
            swithSettings_Mbss();
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void setCloudCreate(){

        square = (RelativeLayout) findViewById(R.id.square);

        cloudCreate = new CloudCreate(this,square);
        cloudCreate.setClouds(true);
        cloudCreate.setClouds(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Constants.RESULT_FINISH)
            finish();
    }

    @Override
    public void onClick(View v) {

        int val = 0;
        if(fingerprint != null && fingerprint.getIsSelected()){
            MorphoFormApp.getSingleton().setHasFingerprint(true);
            val++;
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);
        }else{
            MorphoFormApp.getSingleton().setHasFingerprint(false);
        }

        if(facial != null && facial.getIsSelected()){
            MorphoFormApp.getSingleton().setHasFacial(true);
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FACIAL_OPTION);
            val++;
        }else{
            MorphoFormApp.getSingleton().setHasFacial(false);
        }

        if(voice != null && voice.getIsSelected()){
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.VOICE_OPTION);
            MorphoFormApp.getSingleton().setHasVoice(true);
            val++;
        }else{
            MorphoFormApp.getSingleton().setHasVoice(false);
        }

        if(val > 0) {

            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.ALFAS_OPTION);

            Intent newIntent = null;
            switch (CASE) {
                case Constants.ENROLL_CASE:
                    newIntent = new Intent(SettingsActivity.this, EnrollActivity.class);
                    break;
                case Constants.AUTHENTICATE_CASE:
                    newIntent = new Intent(SettingsActivity.this, AuthenticActivity.class);
                    break;
                case Constants.IDENTIFY_CASE:
                    newIntent = new Intent(SettingsActivity.this, IdentActivity.class);
                    break;
            }

            startActivityForResult(newIntent, 1);
        }else{
            showAlert("","Debes elegir por lo menos una opción");
        }
    }


}
