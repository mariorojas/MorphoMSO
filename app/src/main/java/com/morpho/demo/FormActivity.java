package com.morpho.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.mso.MSOConnection;
import com.morpho.demo.mso.MSOException;
import com.morpho.demo.tools.AnimationManage;

/**
 * Created by alex on 08/07/2015.
 */
public class FormActivity extends ParentActivity{

    //private Button newForm;
    private Button seeMissing;
    private Button seeDone;
    private CloudCreate cloudCreate;
    private RelativeLayout square;

    // Creado por Mario Rojas
    private Button fingerVerification;

    @SuppressLint("MissingSuperCall")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.form_layout);

        initNewFormButton();
        initIncompleteFormButton();
        initCompleteFormButton();
        setCloudCreate();

        // Creado por Mario Rojas
        initFingerVerificationButton();

        try {
            MSOConnection.getInstance().getUSBPermission(this);
        }catch (MSOException | Exception e){
            e.printStackTrace();
            showAlert("Alert", "Es necesario dar permisos para el uso de USB, antes de continuar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    setResult(Constants.RESULT_FINISH);
                    finish();
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }

                @Override
                public void cancelButtonAction() {

                }
            });
        }
    }

    private void setCloudCreate(){

        square = (RelativeLayout) findViewById(R.id.square);

        cloudCreate = new CloudCreate(this,square);
        cloudCreate.setClouds(true);
        cloudCreate.setClouds(false);

        findViewById(R.id.settings_mbss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swithSettings_Mbss();
            }
        });
    }

    private void initCompleteFormButton(){
        seeDone = (Button) findViewById(R.id.seeDone);
        seeDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                SettingsActivity.CASE = Constants.IDENTIFY_CASE;
                Intent complete = new Intent(FormActivity.this,SettingsActivity.class);
                startActivity(complete);*/

                MorphoFormApp.getSingleton().setHasFingerprint(true);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);

                Intent enroll = new Intent(FormActivity.this,IdentActivity.class);
                startActivity(enroll);
            }
        });
        seeDone.setAnimation(AnimationManage.getFadeIn(1500));
    }

    private void initNewFormButton(){
        //newForm = (Button) findViewById(R.id.newForm);
        /*newForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {*/
                /*
                SettingsActivity.CASE = Constants.ENROLL_CASE;
                Intent enroll = new Intent(FormActivity.this,SettingsActivity.class);
                startActivity(enroll);*/

                /*MorphoFormApp.getSingleton().setHasFingerprint(true);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.ALFAS_OPTION);

                Intent enroll = new Intent(FormActivity.this,EnrollActivity.class);
                startActivity(enroll);*/
            /*}
        });

        newForm.setAnimation(AnimationManage.getFadeIn(1500));*/
    }

    private void initIncompleteFormButton(){

        seeMissing = (Button) findViewById(R.id.seeMissing);
        seeMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent incomplete = new Intent(FormActivity.this,AuthenticActivity.class);
                startActivity(incomplete);
            }
        });

        seeMissing.setAnimation(AnimationManage.getFadeIn(1500));

    }

    @Override
    public void onBackPressed() {
        setResult(Constants.RESULT_FINISH);
        finish();
    }

    /**
     * Creado por Mario Rojas
     */
    private void initFingerVerificationButton(){
        fingerVerification = (Button) findViewById(R.id.fingerVerification);
        fingerVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorphoFormApp.getSingleton().setHasFingerprint(true);
                MorphoFormApp.getSingleton().setHasFacial(true);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.ALFAS_OPTION);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);
                MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FACIAL_OPTION);
                Intent enroll = new Intent(FormActivity.this, INEVerificationActivity.class);
                startActivity(enroll);
            }
        });
        fingerVerification.setAnimation(AnimationManage.getFadeIn(1500));
    }
}
