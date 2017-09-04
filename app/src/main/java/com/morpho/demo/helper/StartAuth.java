package com.morpho.demo.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.AuthenticActivity;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.wsdl.wsclient.ConsumeResponse;

/**
 * Created by Alejandro Ruiz on 04/03/2016.
 */
public class StartAuth extends AsyncTask<Void,Void,Void>{

    private AuthenticActivity activity;
    private AuthenticActivity.CustomerDelegate customerDelegate;

    public StartAuth(AuthenticActivity activity,AuthenticActivity.CustomerDelegate customerDelegate){
        this.activity = activity;
        this.customerDelegate = customerDelegate;
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        sendingData();
        return null;
    }

    private void sendingData(){

        int num = MorphoFormApp.getSingleton().getStackOperaions().size();
        if(num > 0) {
            int option = MorphoFormApp.getSingleton().getStackOperaions().first();
            Log.e(this.getClass().getName(), "Opción a evaluar: " + option);
            switch (option){
                case MorphoFormApp.FINGERPRINT_OPTION:
                    new SendFingersAut(activity,new FingersAuth()).execute();
                    break;
                case MorphoFormApp.FACIAL_OPTION:
                    new SendFaceAut(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().photoBuffer,
                            activity, new FacialAuth()).execute();
                    break;
                case MorphoFormApp.VOICE_OPTION:
                    new SendVoiceAut(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().audioAuth,
                            activity, new VoiceAuth()).execute();
                    break;
                case MorphoFormApp.ALFAS_OPTION:
                    new SearchCustomer(MorphoFormApp.getSingleton().getCustomer().ID, activity , customerDelegate).execute();
                    break;
            }
        }
    }

    private class FacialAuth implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            if (statusCode == 200) {
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FACIAL_OPTION);
                sendingData();
            }else{
                activity.showProgress(false);
                switch (statusCode){
                    case 404:
                        activity.showAlert("Alerta", "Autenticación, resultado: NO HIT", new DialogUses() {
                            @Override
                            public void acceptButtonAction() {
                                activity.onBackPressed();
                            }

                            @Override
                            public void cancelButtonAction() {

                            }
                        });
                        break;
                    default:
                        activity.showAlert("Error","Error en proceso de autenciaciòn por facial");
                        break;
                }
            }
        }
    }

    private class VoiceAuth implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            if (statusCode == 200) {
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.VOICE_OPTION);
                sendingData();
            }else{
                activity.showProgress(false);
                switch (statusCode){
                    case 401:
                        activity.showAlert("Alerta", "Autenticación por voz, resultado: NO HIT", new DialogUses() {
                            @Override
                            public void acceptButtonAction() {
                                activity.onBackPressed();
                            }

                            @Override
                            public void cancelButtonAction() {

                            }
                        });
                        break;
                    default:
                        activity.showAlert("Error","Error en proceso de autenticación por voz falló");
                        activity.resetAudio();
                        break;
                }
            }
        }
    }

    private class FingersAuth implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            Log.e(this.getClass().getName(), "Se ejecutará el consumeResponde de FingersAuth");
            if(statusCode == 200){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FINGERPRINT_OPTION);
                sendingData();
            }else {
                activity.showProgress(false);
                switch (statusCode){
                    case 404:
                        activity.showAlert("Alerta", "Autenticación, resultado: NO HIT", new DialogUses() {
                            @Override
                            public void acceptButtonAction() {
                                activity.onBackPressed();
                            }

                            @Override
                            public void cancelButtonAction() {

                            }
                        });
                        break;
                    default:
                        activity.showAlert("Error","Error en proceso de autenciaciòn por huealla");
                        break;
                }
            }
        }
    }


}
