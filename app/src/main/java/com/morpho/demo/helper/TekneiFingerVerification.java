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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Creada por Mario Rojas el 26/04/2017.
 *
 * Clase para gestionar el envío de huellas digitales hacia servidores del INE
 */

public class TekneiFingerVerification extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private Customer customer;
    private Context context;
    private ConsumeResponse consumeResponse;

    public TekneiFingerVerification(Customer customer, Context context, ConsumeResponse consumeResponse) {
        this.customer = customer;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (customer != null) {
            HashMap<String, Object> fingerprints = new HashMap<>();
            fillFingerprints(fingerprints);

            //int count = fingerprints.keySet().size();
            Iterator<String> iterator = fingerprints.keySet().iterator();

            String key;
            String fingerprint;

            while (iterator.hasNext()) {
                key = iterator.next();
                fingerprint = (String) fingerprints.get(key);
                Log.i(this.getClass().getName(), "Huella: " + key + ": " + fingerprint);
            }

            sendINEVerificationRequest();
        }
        return null;
    }

    /**
     * Llenado de las huellas diitales capturadas en el objeto
     * que se pretende enviar a los servidores de INE para cotejamiento
     */
    private void fillFingerprints(HashMap<String, Object> data) {
        byte[] fingerprints = null;

        try {
            if (data !=null) {
                if (MorphoFormApp.getSingleton().getCustomer().indexLeftBuff != null)
                    data.put(Constants.CUSTOMER_LEFT_I, Base64.encode(
                            MorphoFormApp.getSingleton().getCustomer().indexLeftBuff));
                if (MorphoFormApp.getSingleton().getCustomer().indexRightBuff != null)
                    data.put(Constants.CUSTOMER_RIGHT_I, Base64.encode(
                            MorphoFormApp.getSingleton().getCustomer().indexRightBuff));
                fingerprints = IOUtil.serialize(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Envío de petición hacia el webservice expuesto por el INE
     */
    private void sendINEVerificationRequest() {
        ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),
                Util.getSettingWSHost(context), this);
        try {
            clientWS.postCon("/api/customer/setCustomer", customer.getJSON().toString(),
                    Util.getSettingWSPort(context));
        } catch (Exception e) {
            e.printStackTrace();
            executeConsumeResponse(500, e.getMessage());
        }
    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        executeConsumeResponse(statusCode, response);
    }

    /**
     * Ejecutar consumeResponde del objeto ConsumeResponse enviado
     * al momento de invocar new TekneiFingerVerification(...);
     */
    private void executeConsumeResponse(int statusCode, String response) {
        if (consumeResponse != null)
            consumeResponse.consumeResponde(statusCode, response);
    }
}
