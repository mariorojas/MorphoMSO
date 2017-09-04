package com.morpho.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MenuItemSelecter;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.customs.MenuDialog;
import com.morpho.demo.frament.AudioSectionFragment;
import com.morpho.demo.frament.FingerprintFragment;
import com.morpho.demo.frament.SectionFragment;
import com.morpho.demo.frament.TakePhoto;
import com.morpho.demo.helper.RecordCreation;
import com.morpho.demo.helper.SearchID;
import com.morpho.demo.helper.SendAlfas;
import com.morpho.demo.helper.SendFace;
import com.morpho.demo.helper.SendFingers;
import com.morpho.demo.helper.SendVoice;
import com.morpho.demo.helper.TekneiUtils;
import com.morpho.wsdl.wsclient.ConsumeResponse;

/**
 * Created by alex on 09/07/2015.
 */
public class EnrollActivity extends ParentActivity implements MenuItemSelecter{

    private CloudCreate cloudCreate;
    private RelativeLayout square;
    private ImageButton menuButton;
    private ImageButton saveButton;
    private Button next;
    private Button back;
    private MenuDialog menuDialog;

    private SectionFragment sectionFragment;
    private AudioSectionFragment audioSectionFragment;
    private FingerprintFragment fingerprintFragment;
    private TakePhoto takePhoto;

    private int STATE;

    private static final int GENERAL_DATA = 10;
    private static final int FINGERPRINT_DATA = 20;
    private static final int PHOTO_DATA = 30;
    private static final int VOICE_DATA = 40;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.form_complete_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setCloudCreate();
        initUI();
        setFirstItem();

        Log.d(this.getClass().getName(), "scanId: " + MorphoFormApp.getSingleton().getCustomer().scanID);
    }

    private void setFirstItem(){
        sectionFragment = new SectionFragment();

        audioSectionFragment = AudioSectionFragment.newInstance();
        fingerprintFragment = new FingerprintFragment();

        takePhoto = new TakePhoto();

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.itemContent, sectionFragment);
        fragmentTransaction.add(R.id.itemContent, audioSectionFragment);
        fragmentTransaction.add(R.id.itemContent, fingerprintFragment);
        fragmentTransaction.add(R.id.itemContent, takePhoto);

        //fragmentTransaction.show(sectionFragment);
        fragmentTransaction.hide(audioSectionFragment);
        fragmentTransaction.hide(fingerprintFragment);
        fragmentTransaction.show(takePhoto);

        fragmentTransaction.commit();

        //STATE = GENERAL_DATA;
        STATE = PHOTO_DATA;
        //next.setVisibility(View.GONE);
    }

    private void stateChange(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (STATE){
            case GENERAL_DATA:
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                fragmentTransaction.show(sectionFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                break;
            case VOICE_DATA:
                fragmentTransaction.hide(sectionFragment);
                fragmentTransaction.show(audioSectionFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                break;
            case FINGERPRINT_DATA:
                fragmentTransaction.hide(sectionFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.show(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                /*back.setVisibility(View.GONE);
                if(MorphoFormApp.getSingleton().isHasFacial() || MorphoFormApp.getSingleton().isHasVoice())
                    next.setVisibility(View.VISIBLE);
                else
                    next.setVisibility(View.GONE);*/
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                break;
            case PHOTO_DATA:
                fragmentTransaction.hide(sectionFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.show(takePhoto);
                /*back.setVisibility(View.VISIBLE);
                if(MorphoFormApp.getSingleton().isHasVoice())
                    next.setVisibility(View.VISIBLE);
                else
                    next.setVisibility(View.GONE);*/
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                break;
        }

        fragmentTransaction.commit();
    }

    private void setItem(int item){
        switch (item){
            case MenuItemSelecter.TAKE_GENERALS:
                STATE = GENERAL_DATA;
                stateChange();
                break;
            case MenuItemSelecter.REQUET_CREDIT_CARD:
                STATE = VOICE_DATA;
                stateChange();
                break;
            case MenuItemSelecter.FINGER_PRINTS:
                STATE = FINGERPRINT_DATA;
                stateChange();
                break;
            case MenuItemSelecter.TAKE_PICTURE:
                STATE = PHOTO_DATA;
                stateChange();
                break;
        }

    }


    private void initUI(){
        saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = MorphoFormApp.getSingleton().getCustomer().isEmpty();
                if(type == 0){
                    showProgress(true);
                    //new SaveTask().execute();
                    new SearchID(MorphoFormApp.getSingleton().getCustomer().ID,EnrollActivity.this,new SearchIDDelegate()).execute();
                }else{
                    switch (type){
                        case 1:
                            showAlert("Alerta","El proceso de enrolamiento no puede comenzar, faltan valores alfanuméricos.");
                            break;
                        case 2:
                            showAlert("Alerta","El proceso de enrolamiento no puede comenzar, falta confirmar las huellas digitales o tomar las huellas.");
                            break;
                        case 3:
                            showAlert("Alerta","El proceso de enrolamiento no puede comenzar, falta tomar la foto o confirmarla.");
                            break;
                        case 4:
                            showAlert("Alerta","El proceso de enrolamiento no puede comenzar, faltan grabar los audios.");
                            break;

                    }
                }

            }
        });

        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);

        next.setText(">");
        back.setText("<");

        back.setVisibility(View.GONE);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE){
                    case GENERAL_DATA:
                        back.setVisibility(View.VISIBLE);
                        if(MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        else if(MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if(MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;
                        stateChange();
                        break;
                    case FINGERPRINT_DATA:
                        if(MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if(MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        /*if(MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;*/
                        STATE = FINGERPRINT_DATA;
                        stateChange();
                        break;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE){
                    case VOICE_DATA:
                        if(MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if(MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        else
                            STATE = GENERAL_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        if(MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        else
                            STATE = GENERAL_DATA;
                        stateChange();
                        break;
                    case FINGERPRINT_DATA:

                        //STATE = GENERAL_DATA;
                        STATE = PHOTO_DATA;
                        stateChange();
                        break;
                }
            }
        });

        menuDialog = new MenuDialog(EnrollActivity.this,this);

        menuButton = (ImageButton) findViewById(R.id.menuButton);
        menuButton.setVisibility(View.GONE);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menuDialog != null)
                    menuDialog.show();
            }
        });
    }


    private void setCloudCreate(){

        square = (RelativeLayout) findViewById(R.id.square);

        cloudCreate = new CloudCreate(this,square);
        cloudCreate.setClouds(true);
        cloudCreate.setClouds(false);
    }


    @Override
    public void onQuestionarySelected(int questionary) {
        Log.d(EnrollActivity.class.getName(), "Option selected: " + questionary);
        setItem(questionary);
    }


    @Override
    public void onBackPressed() {
        setResult(Constants.RESULT_FINISH);
        TekneiUtils.cleanPreviousUserData();
        finish();
    }

    private class SearchIDDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            switch (statusCode){
                case 200:
                    showProgress(false);
                    showAlert("Alerta", "El cliente con ID: " + MorphoFormApp.getSingleton().getCustomer().ID +
                            " ya existe.\nNo se puede volver a enrolar.", new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            onBackPressed();
                        }

                        @Override
                        public void cancelButtonAction() {

                        }
                    });
                    break;
                case 400:
                    showProgress(false);
                    showAlert("Error","No se ha enviado el id de cliente");
                    break;
                case 404:
                    sendingData();
                    break;
                default:
                    showProgress(false);
                    showAlert("Error","Error: "+response);
                    break;
            }
        }
    }

    private class SendFingersDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            if(statusCode == 200 ){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FINGERPRINT_OPTION);
                sendingData();
            } else if(statusCode == 203){
                showProgress(false);
                showAlert("Error","El usuario ya existe en el sistema.");
            } else{
                showProgress(false);
                showAlert("Error","Error en el proceso de enrolamiento de huellas dactilares");
            }
        }
    }

    private class SendVoiceDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {

            if(statusCode == 200){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.VOICE_OPTION);
                sendingData();
            }else{
                showProgress(false);
                showAlert("Error","Error en el proceso de enrolamiento por voz");
                audioSectionFragment.reStartAudios();
            }
        }
    }

    private class SendFaceDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {

            if(statusCode == 200){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FACIAL_OPTION);
                sendingData();
            } else if(statusCode == 203){
                showProgress(false);
                showAlert("Error","Erro: El usuario ya existe en el sistema.");
            }else{
                showProgress(false);
                showAlert("Error","Error en el proceso de enrolamiento de facial");
            }
        }
    }

    private class SendAlfaDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            showProgress(false);

            if(statusCode == 200){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.ALFAS_OPTION);
                showAlert("Alerta", "Enrolamiento EXITOSO!", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        //onBackPressed();
                        new RecordCreation(MorphoFormApp.getSingleton().getCustomer().scanID, thisContext, new RecordCreationDelegate()).execute();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            }else{
                showAlert("Error","Error en el proceso de enrolamiento de valores alfanumericos.");
            }
        }
    }

    private class RecordCreationDelegate implements ConsumeResponse {

        @Override
        public void consumeResponde(int statusCode, String response) {
            Log.d(this.getClass().getName(), "response: " + response);
            if (statusCode == 200) {
                showAlert("Alerta", "Se ha generado el expendiente exitosamente", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        onBackPressed();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            } else {
                showAlert("Error","No se ha podido generar el expediente.");
            }
        }
    }


    private void sendingData(){
        int num = MorphoFormApp.getSingleton().getStackOperaions().size();
        if(num > 0) {
            int option = MorphoFormApp.getSingleton().getStackOperaions().first();
            switch (option){
                case MorphoFormApp.FINGERPRINT_OPTION:
                    new SendFingers(EnrollActivity.this, new SendFingersDelegate()).execute();
                    break;
                case MorphoFormApp.FACIAL_OPTION:
                    new SendFace(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().photoBuffer,
                            EnrollActivity.this, new SendFaceDelegate()).execute();
                    break;
                case MorphoFormApp.VOICE_OPTION:
                    new SendVoice(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().audio1,
                            MorphoFormApp.getSingleton().getCustomer().audio2,
                            MorphoFormApp.getSingleton().getCustomer().audio3,
                            /*MorphoFormApp.getSingleton().getCustomer().audio4,*/
                            EnrollActivity.this, new SendVoiceDelegate()).execute();
                    break;
                case MorphoFormApp.ALFAS_OPTION:
                    new SendAlfas(MorphoFormApp.getSingleton().getCustomer(),EnrollActivity.this, new SendAlfaDelegate()).execute();
                    break;
            }
        }
    }


}
