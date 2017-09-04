package com.morpho.demo.helper;

import android.os.AsyncTask;

import com.morpho.demo.IdentActivity;
import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.NumericTool;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashSet;

/**
 * Created by Alejandro Ruiz on 04/03/2016.
 */
public class StartIden extends AsyncTask<Void,Void,Void>{

    private IdentActivity activity;
    private IdentActivity.CustomerDelegate customerDelegate;
    private LinkedHashSet<String> ids;

    public StartIden(IdentActivity activity, IdentActivity.CustomerDelegate customerDelegate){
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
            switch (option){
                case MorphoFormApp.FINGERPRINT_OPTION:
                    new SendFingersIden(activity,new FingersAuth()).execute();
                    break;
                case MorphoFormApp.FACIAL_OPTION:
                    new SendFaceIden(MorphoFormApp.getSingleton().getCustomer().ID,
                            MorphoFormApp.getSingleton().getCustomer().photoBuffer,
                            activity, new FacialAuth()).execute();
                    break;
                case MorphoFormApp.ALFAS_OPTION:
                    new SearchCustomerIdent(ids, activity , customerDelegate).execute();
                    break;
            }
        }
    }

    private class FacialAuth implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            if (statusCode == 200) {
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FACIAL_OPTION);
                if(!NumericTool.isStringNunnable(response)) {
                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray idsJ = res.getJSONArray(Constants.CUSTOMER_IDS);
                        if (idsJ != null && idsJ.length() > 0) {
                            if (ids == null)
                                ids = new LinkedHashSet<>();
                            for (int i = 0; i < idsJ.length(); i++) {
                                ids.add(idsJ.getString(i));
                            }
                        }

                        sendingData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        activity.showProgress(false);
                        activity.showAlert("Error", "Error en proceso de identificación por huealla");
                    }
                }
            }else{
                activity.showProgress(false);
                switch (statusCode){
                    case 404:
                        activity.showAlert("Alerta", "Identificación, resultado: NO HIT", new DialogUses() {
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
                        activity.showAlert("Error", "Error en proceso de identificación por facial");
                        break;
                }
            }
        }
    }


    private class FingersAuth implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, String response) {
            if(statusCode == 200){
                MorphoFormApp.getSingleton().getStackOperaions().remove(MorphoFormApp.FINGERPRINT_OPTION);
                if(!NumericTool.isStringNunnable(response)) {
                    try {
                        JSONObject res = new JSONObject(response);
                        JSONArray idsJ = res.getJSONArray(Constants.CUSTOMER_IDS);
                        if (idsJ != null && idsJ.length() > 0) {
                            ids = new LinkedHashSet<>();
                            for (int i = 0; i < idsJ.length(); i++) {
                                ids.add(idsJ.getString(i));
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        activity.showProgress(false);
                        activity.showAlert("Error", "Error en proceso de identificación por huealla");
                    }
                }
                sendingData();
            }else {
                activity.showProgress(false);
                switch (statusCode){
                    case 404:
                        activity.showAlert("Alerta", "Identificación, resultado: NO HIT", new DialogUses() {
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
                        activity.showAlert("Error","Error en proceso de identificación por huealla");
                        break;
                }
            }
        }
    }


}
