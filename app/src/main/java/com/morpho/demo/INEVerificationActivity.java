package com.morpho.demo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MenuItemSelecter;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.frament.INEFingerVerificationFragment;
import com.morpho.demo.frament.INEPersonalDataFragment;
import com.morpho.demo.frament.INESecondStepFragment;
import com.morpho.demo.frament.INEThirdStepFragment;
import com.morpho.demo.frament.INEValidationResults;
import com.morpho.demo.frament.TakePhoto;
import com.morpho.demo.helper.INEDataVerification;
import com.morpho.demo.helper.INEFingerVerification;
import com.morpho.demo.helper.INEMatchPhoto;
import com.morpho.demo.helper.RecordCreation;
import com.morpho.demo.helper.SearchID;
import com.morpho.demo.helper.SearchLastScanId;
import com.morpho.demo.helper.SendAlfas;
import com.morpho.demo.helper.SendFace;
import com.morpho.demo.helper.SendFingers;
import com.morpho.demo.helper.TekneiUtils;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Modificada por Mario Rojas el 26/04/2017.
 *
 * Clase principal para gestionar la captura de huellas digitales y datos de usuario
 */
public class INEVerificationActivity extends ParentActivity /*implements MenuItemSelecter*/ {

    private CloudCreate cloudCreate;
    private RelativeLayout square;
    private ImageButton saveButton;
    private Button next;
    private Button back;

    private INEFingerVerificationFragment ineFingerVerificationFragment;
    //private INEPersonalDataFragment inePersonalDataFragment;
    //private INESecondStepFragment ineSecondStepFragment;
    //private INEThirdStepFragment ineThirdStepFragment;
    private TakePhoto takePhoto;
    private INEValidationResults ineValidationResults;

    private int STATE;

    private static final int FINGERPRINT_DATA = 20;
    private static final int PHOTO_DATA = 30;
    //private static final int PERSONAL_DATA = 40;
    //private static final int SECOND_STEP_DATA = 50;
    //private static final int THIRD_STEP_DATA = 60;

    String validationScore;
    String validationPorc;

    @SuppressLint("MissingSuperCall")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.ine_finger_verification_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        validationScore = null;
        validationPorc = null;

        setCloudCreate();
        initUI();
        setFirstItem();

        new SearchLastScanId(INEVerificationActivity.this, new SearchLastScanIdDelegate()).execute();

        Log.d(this.getClass().getName(), "Tamaño de pila: " + MorphoFormApp.getSingleton().getStackOperaions().size());
        int option = MorphoFormApp.getSingleton().getStackOperaions().first();
        Log.d(this.getClass().getName(), "Primera opción: " + option);
    }

    /**
     * Muestra la pantalla para captura biométrica de 10 huellas dactilares
     */
    private void setFirstItem(){
        //inePersonalDataFragment = new INEPersonalDataFragment();
        //ineSecondStepFragment = new INESecondStepFragment();
        //ineThirdStepFragment = new INEThirdStepFragment();
        takePhoto = new TakePhoto();
        ineFingerVerificationFragment = new INEFingerVerificationFragment();
        ineValidationResults = new INEValidationResults();

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //fragmentTransaction.add(R.id.itemContent, inePersonalDataFragment);
        //fragmentTransaction.add(R.id.itemContent, ineSecondStepFragment);
        //fragmentTransaction.add(R.id.itemContent, ineThirdStepFragment);
        fragmentTransaction.add(R.id.itemContent, takePhoto);
        fragmentTransaction.add(R.id.itemContent, ineFingerVerificationFragment);
        fragmentTransaction.add(R.id.itemContent, ineValidationResults);

        //fragmentTransaction.show(inePersonalDataFragment);
        //fragmentTransaction.hide(ineSecondStepFragment);
        //fragmentTransaction.hide(ineThirdStepFragment);
        fragmentTransaction.show(takePhoto);
        fragmentTransaction.hide(ineFingerVerificationFragment);
        fragmentTransaction.hide(ineValidationResults);

        fragmentTransaction.commit();

        //STATE = PERSONAL_DATA;
        STATE = PHOTO_DATA;
    }

    private void stateChange() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (STATE) {
            /*case PERSONAL_DATA:
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                fragmentTransaction.show(inePersonalDataFragment);
                fragmentTransaction.hide(ineSecondStepFragment);
                fragmentTransaction.hide(ineThirdStepFragment);
                fragmentTransaction.hide(takePhoto);
                fragmentTransaction.hide(ineFingerVerificationFragment);
                break;
            case SECOND_STEP_DATA:
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                fragmentTransaction.hide(inePersonalDataFragment);
                fragmentTransaction.show(ineSecondStepFragment);
                fragmentTransaction.hide(ineThirdStepFragment);
                fragmentTransaction.hide(takePhoto);
                fragmentTransaction.hide(ineFingerVerificationFragment);
                break;
            case THIRD_STEP_DATA:
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);
                fragmentTransaction.hide(inePersonalDataFragment);
                fragmentTransaction.hide(ineSecondStepFragment);
                fragmentTransaction.show(ineThirdStepFragment);
                fragmentTransaction.hide(takePhoto);
                fragmentTransaction.hide(ineFingerVerificationFragment);
                break;*/
            case PHOTO_DATA:
                //back.setVisibility(View.VISIBLE);
                back.setVisibility(View.GONE);
                next.setVisibility(View.VISIBLE);
                //fragmentTransaction.hide(inePersonalDataFragment);
                //fragmentTransaction.hide(ineSecondStepFragment);
                //fragmentTransaction.hide(ineThirdStepFragment);
                fragmentTransaction.show(takePhoto);
                fragmentTransaction.hide(ineFingerVerificationFragment);
                fragmentTransaction.hide(ineValidationResults);
                break;
            case FINGERPRINT_DATA:
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.GONE);
                //fragmentTransaction.hide(inePersonalDataFragment);
                //fragmentTransaction.hide(ineSecondStepFragment);
                //fragmentTransaction.hide(ineThirdStepFragment);
                fragmentTransaction.hide(takePhoto);
                fragmentTransaction.show(ineFingerVerificationFragment);
                fragmentTransaction.hide(ineValidationResults);
                break;
        }
        fragmentTransaction.commit();
    }

    /*private void setItem(int item) {
        switch (item) {
            case MenuItemSelecter.FINGER_PRINTS:
                STATE = PHOTO_DATA;
                break;
        }
    }*/

    /**
     * Inicia los eventos y las propiedades de los botones en la interfaz
     */
    private void initUI() {
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = MorphoFormApp.getSingleton().getCustomer().isEmpty();
                if (type == 0) {
                    showProgress(true);
                    Log.d(this.getClass().getName(), "Inicia proceso de envío de datos");
                    new INEDataVerification(
                            MorphoFormApp.getSingleton().getCustomer(),
                            INEVerificationActivity.this,
                            new INEDataVerificationDelegate()).execute();
                } else {
                    switch (type) {
                        case 1:
                            showAlert("Alerta", "El proceso de enrolamiento no puede comenzar, faltan valores alfanuméricos.");
                            break;
                        case 2:
                            showAlert("Alerta", "El proceso de enrolamiento no puede comenzar, falta confirmar las huellas digitales o tomar las huellas.");
                            break;
                        case 3:
                            showAlert("Alerta", "El proceso de enrolamiento no puede comenzar, falta tomar la foto o confirmarla.");
                            break;
                        case 4:
                            showAlert("Alerta", "El proceso de enrolamiento no puede comenzar, faltan grabar los audios.");
                            break;

                    }
                }
            }
        });

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE) {
                    /*case PERSONAL_DATA:
                        STATE = SECOND_STEP_DATA;
                        stateChange();
                        break;
                    case SECOND_STEP_DATA:
                        STATE = THIRD_STEP_DATA;
                        stateChange();
                        break;
                    case THIRD_STEP_DATA:
                        STATE = PHOTO_DATA;
                        stateChange();
                        break;*/
                    case PHOTO_DATA:
                        STATE = FINGERPRINT_DATA;
                        stateChange();
                        break;
                }
            }
        });

        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE) {
                    /*case SECOND_STEP_DATA:
                        STATE = PERSONAL_DATA;
                        stateChange();
                        break;
                    case THIRD_STEP_DATA:
                        STATE = SECOND_STEP_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        STATE = THIRD_STEP_DATA;
                        stateChange();
                        break;*/
                    case FINGERPRINT_DATA:
                        STATE = PHOTO_DATA;
                        stateChange();
                        break;
                }
            }
        });

        next.setText(">");
        back.setText("<");

        back.setVisibility(View.GONE);
    }

    /**
     * Imprime una marca de agua en movimiento
     */
    private void setCloudCreate() {
        square = (RelativeLayout) findViewById(R.id.square);
        cloudCreate = new CloudCreate(this,square);
        cloudCreate.setClouds(true);
        cloudCreate.setClouds(false);
    }

    /*@Override
    public void onQuestionarySelected(int questionary) {
        Log.d(INEVerificationActivity.class.getName(), "Option selected: " + questionary);
        setItem(questionary);
    }*/

    @Override
    public void onBackPressed() {
        /*MorphoFormApp.getSingleton().setHasFingerprint(true);
        MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);
        MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.ALFAS_OPTION);
        */
        setResult(Constants.RESULT_FINISH);
        TekneiUtils.cleanPreviousUserData();
        finish();
    }

    /**
     * Comienza el enrolamiento del usuario a la base de datos local
     * después que ya ha sido comprobada su identidad por el INE
     */
    public void startEnrollment() {
        /*setResult(Constants.RESULT_FINISH);
        MorphoFormApp.getSingleton().setHasFingerprint(true);
        MorphoFormApp.getSingleton().setHasFacial(true);
        Intent enroll = new Intent(INEVerificationActivity.this, EnrollActivity.class);
        startActivity(enroll);
        finish();*/
        showProgress(true);
        new SearchID(MorphoFormApp.getSingleton().getCustomer().ID, thisContext,
                new INEVerificationActivity.SearchIDDelegate()).execute();
    }

    private void showINEDataValidationResults() {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        back.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        fragmentTransaction.hide(takePhoto);
        fragmentTransaction.hide(ineFingerVerificationFragment);
        fragmentTransaction.show(ineValidationResults);*/

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(thisContext);

                dialog.setContentView(R.layout.ine_data_validation_results);
                dialog.setTitle("Resultado de validaciones INE");

                TextView lblName = (TextView) dialog.findViewById(R.id.lblValidationName);
                TextView lblFirstSurname = (TextView) dialog.findViewById(R.id.lblValidationFirstSurname);
                TextView lblSecondSurname = (TextView) dialog.findViewById(R.id.lblValidationSecondSurname);
                TextView lblOcr = (TextView) dialog.findViewById(R.id.lblValidationOcr);
                TextView lblScore = (TextView) dialog.findViewById(R.id.lblValidationScore);
                TextView lblPorc = (TextView) dialog.findViewById(R.id.lblValidationPorc);


                lblName.setText(lblName.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().Name);
                lblFirstSurname.setText(lblFirstSurname.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().Surname);
                lblSecondSurname.setText(lblSecondSurname.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().apeMat);
                lblOcr.setText(lblOcr.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().ID);
                lblScore.setText(lblScore.getText().toString() + " " + validationScore);
                lblPorc.setText(lblPorc.getText().toString() + " " + validationPorc + " %");

                Button dialogButton = (Button) dialog.findViewById(R.id.btnOk);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startEnrollment();
                    }
                });

                dialog.show();
            }
        });
    }

    /**
     * Delegado para gestión de datos en INE
     */
    private class INEDataVerificationDelegate implements ConsumeResponse {

        @Override
        public void consumeResponde(int statusCode, String response) {
            switch (statusCode) {
                case 200:
                    new INEFingerVerification(
                            MorphoFormApp.getSingleton().getCustomer(),
                            INEVerificationActivity.this,
                            new INEFingerVerificationDelegate()).execute();
                    break;
                case 404:
                    showProgress(false);
                    showAlert("Alerta", "El usuario no se encontró registrado en INE", new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            onBackPressed();
                        }

                        @Override
                        public void cancelButtonAction() {

                        }
                    });
                    break;
                default:
                    showProgress(false);
                    showAlert("Error", "Error: " + response, new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            onBackPressed();
                        }

                        @Override
                        public void cancelButtonAction() {

                        }
                    });
                    break;
            }
        }

    }

    private class INEFingerVerificationDelegate implements ConsumeResponse {

        @Override
        public void consumeResponde(int statusCode, String response) {
            switch (statusCode) {
                case 200:
                    /*showProgress(false);
                    showINEDataValidationResults();
                    MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FINGERPRINT_OPTION);*/
                    new INEMatchPhoto(MorphoFormApp.getSingleton().getCustomer(),
                            INEVerificationActivity.this,
                            new INEMatchPhotoDelegate()).execute();
                    break;
                default:
                    showProgress(false);
                    showAlert("Error", "Error: " + response, new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            onBackPressed();
                        }

                        @Override
                        public void cancelButtonAction() {

                        }
                    });
                    break;
            }
        }

    }

    private class INEMatchPhotoDelegate implements ConsumeResponse {

        @Override
        public void consumeResponde(int statusCode, String response) {

            if (statusCode == 200) {
                Log.d(this.getClass().getName(), "response: " + response);
                showProgress(false);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    validationScore = jsonObject.getString("score");
                    validationPorc = jsonObject.getString("porc");
                    //MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FACIAL_OPTION);
                    showINEDataValidationResults();
                } catch (JSONException e) {
                    e.printStackTrace();
                    showINEDataValidationResults();
                    /*showAlert("Error", "Algunas propiedades del match de fotos no han sido recibidas",
                            new DialogUses() {
                        @Override
                        public void acceptButtonAction() {
                            showINEDataValidationResults();
                            //onBackPressed();
                        }

                        @Override
                        public void cancelButtonAction() {

                        }
                    });*/
                }
            } else {
                showProgress(false);
                showAlert("Error", "Error: " + response, new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        onBackPressed();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            }
        }

    }

    public class SearchLastScanIdDelegate implements ConsumeResponse {

        @Override
        public void consumeResponde(int statusCode, String response) {
            if (statusCode != 200) {
                showAlert("Error", "No se ha podido obtener el usuario escaneado, intente enrolar nuevamente por favor", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        onBackPressed();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            } else if (MorphoFormApp.getSingleton().getCustomer().ID == null) {
                showAlert("Error", "No se ha podido obtener correctamente el OCR de la credencial INE, captúrela nuevamente por favor", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        onBackPressed();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            }
        }

    }

    /**
     * Sección de Enrolamiento
     */
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

            if(statusCode == 200) {
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.ALFAS_OPTION);
                showAlert("Alerta", "Enrolamiento EXITOSO!", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        //onBackPressed();
                        new RecordCreation(MorphoFormApp.getSingleton().getCustomer().scanID, thisContext, new INEVerificationActivity.RecordCreationDelegate()).execute();
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
                showProgress(false);
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
                showProgress(false);
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
                    Log.d(this.getClass().getName(), "Envío de huellas dactilares");
                    new SendFingers(INEVerificationActivity.this, new INEVerificationActivity.SendFingersDelegate()).execute();
                    break;
                case MorphoFormApp.FACIAL_OPTION:
                    Log.d(this.getClass().getName(), "Envío de captura facial");
                    new SendFace(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().photoBuffer,
                            INEVerificationActivity.this, new INEVerificationActivity.SendFaceDelegate()).execute();
                    break;
                case MorphoFormApp.ALFAS_OPTION:
                    Log.d(this.getClass().getName(), "Envío de datos de usuario");
                    new SendAlfas(MorphoFormApp.getSingleton().getCustomer(),INEVerificationActivity.this, new INEVerificationActivity.SendAlfaDelegate()).execute();
                    break;
            }
        }
    }

}
