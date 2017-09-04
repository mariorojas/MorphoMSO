package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.IOUtil;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Creada por Mario Rojas el 26/04/2017.
 *
 * Clase para gestionar el envío de datos hacia servidores del INE
 */

public class INEDataVerification extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private Customer customer;
    private Context context;
    private ConsumeResponse consumeResponse;

    public INEDataVerification(Customer customer, Context context, ConsumeResponse consumeResponse) {
        this.customer = customer;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (customer != null) {
            Log.d(this.getClass().getName(), "Inicia envío");
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),
                    Util.getSettingWSHost(context),this);
            try {
                customer.isFake = true;
                Log.d(this.getClass().getName(), "isFake data flag: " + customer.isFake);
                clientWS.postCon("/api/customer/verifyData",customer.getJSON().toString(),
                        Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if(consumeResponse != null){
                    consumeResponse.consumeResponde(500,e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        Log.d(this.getClass().getName(), "Código de respuesta para envío de huellas: " + statusCode);
        Log.d(this.getClass().getName(), "response: " + response);
        if(consumeResponse != null){
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(response);
                int option = Integer.parseInt(jsonObject.getString("responseCode"));
                boolean validOCR = Boolean.parseBoolean(jsonObject.getString("ocr"));
                statusCode = (option==91 && validOCR==true) ? 200 : 404;
            } catch (JSONException e) {
                statusCode = 500;
                response = "Hubo problemas al interpretar la respuesta del servidor, inténtelo nuevamente";
            }

            consumeResponse.consumeResponde(statusCode,response);
        }
    }
}
