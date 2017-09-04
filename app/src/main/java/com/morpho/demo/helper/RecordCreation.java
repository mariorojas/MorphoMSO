package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creada por Mario Rojas el 26/04/2017.
 *
 * Clase para gestionar el envío de datos hacia servidores del INE
 */

public class RecordCreation extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private String mobbscanID;
    private Context context;
    private ConsumeResponse consumeResponse;

    public RecordCreation(String mobbscanID, Context context, ConsumeResponse consumeResponse) {
        this.mobbscanID = mobbscanID;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(this.getClass().getName(), "Inicia envío");

        //String tasIP = "192.168.1.100";
        String tasIP = Util.getSettingWSURI(context);
        String tasPort = "28080";
        String tasHostname = "DemoServer";
        String tasRequest = "/rest/v1/expedientes?scanId=";

        ClientWS clientWS = new ClientWS(tasIP, tasHostname, this);
        try {
            Log.d(this.getClass().getName(), "Entrando a record creation");
            clientWS.postCon(tasRequest + mobbscanID, null, tasPort);
        } catch (Exception e) {
            e.printStackTrace();
            if(consumeResponse != null) {
                consumeResponse.consumeResponde(500,e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        Log.d(this.getClass().getName(), "statusCode: " + statusCode);
        if(consumeResponse != null) {
            consumeResponse.consumeResponde(statusCode, response);
        }
    }

}
