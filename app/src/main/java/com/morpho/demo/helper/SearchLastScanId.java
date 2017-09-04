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

public class SearchLastScanId extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private Context context;
    private ConsumeResponse consumeResponse;

    public SearchLastScanId(Context context, ConsumeResponse consumeResponse) {
        this.consumeResponse = consumeResponse;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d(this.getClass().getName(), "Inicia envío");

        ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),this);
        try {
            clientWS.get("/api/customer/searchLastScanId", Util.getSettingWSPort(context));
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
            try {
                JSONObject jsonResponse;
                jsonResponse = new JSONObject(response);

                JSONObject jsonDocument = new JSONObject(jsonResponse.getString("document"));

                MorphoFormApp.getSingleton().getCustomer().Name = getValue(jsonDocument.getString("name"));
                MorphoFormApp.getSingleton().getCustomer().Surname = getValue(jsonDocument.getString("firstSurname"));
                MorphoFormApp.getSingleton().getCustomer().apeMat = getValue(jsonDocument.getString("secondSurname"));
                MorphoFormApp.getSingleton().getCustomer().sexo = getValue(jsonDocument.getString("gender"));
                MorphoFormApp.getSingleton().getCustomer().anioRegistro = "SIN AÑO DE REGISTRO";
                MorphoFormApp.getSingleton().getCustomer().claveElector = "SIN CLAVE DE ELECTOR";
                MorphoFormApp.getSingleton().getCustomer().curp = getValue(jsonDocument.getString("curp"));
                MorphoFormApp.getSingleton().getCustomer().estado = "SIN ESTADO";//getValue(jsonDocument.getString("stateCode"));
                MorphoFormApp.getSingleton().getCustomer().municipio = "SIN MUNICIPIO";
                MorphoFormApp.getSingleton().getCustomer().localidad = "SIN LOCALIDAD";//getValue(jsonDocument.getString("cityCode"));
                MorphoFormApp.getSingleton().getCustomer().seccion = getValue(jsonDocument.getString("section"));
                MorphoFormApp.getSingleton().getCustomer().emision = "SIN EMISION";
                MorphoFormApp.getSingleton().getCustomer().vigencia = getValue(jsonDocument.getString("dateOfExpiry"));
                MorphoFormApp.getSingleton().getCustomer().ID = getOcr(jsonDocument.getString("mrz"));
                MorphoFormApp.getSingleton().getCustomer().scanID = jsonResponse.getString("scanId");

                Log.d(this.getClass().getName(), "ocr: " + MorphoFormApp.getSingleton().getCustomer().ID);
                Log.d(this.getClass().getName(), "scanId: " + MorphoFormApp.getSingleton().getCustomer().scanID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            consumeResponse.consumeResponde(statusCode, response);
        }
    }

    private String getValue(String mobbscanValue) {
        return (mobbscanValue.equals("null")) ? null : mobbscanValue;
    }

    private String getOcr(String mrz) {
        mrz = getValue(mrz);
        int index = 17;
        int ocrLength = 13;

        if (mrz==null || mrz.length()<(index+ocrLength))
            return null;

        mrz = mrz.substring(index, index+ocrLength);

        return (mrz.matches("[0-9]+")) ? mrz : null;
    }

}
