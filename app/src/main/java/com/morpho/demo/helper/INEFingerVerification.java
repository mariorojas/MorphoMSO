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

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Creada por Mario Rojas el 26/04/2017.
 *
 * Clase para gestionar el envío de huellas dactilares hacia servidores del INE
 */
public class INEFingerVerification extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private Customer customer;
    private Context context;
    private ConsumeResponse consumeResponse;

    public INEFingerVerification(Customer customer, Context context, ConsumeResponse consumeResponse) {
        this.customer = customer;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (customer != null) {
            HashMap<String, Object> data = new HashMap<>();
            fillData(data);

            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),this);
            JSONObject jsonObject = new JSONObject(data);

            try {
                clientWS.postCon("/api/customer/verifyMinutia",jsonObject.toString(),Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
            }
        }
        return null;
    }

    /**
     * Asignación de huellas digitales al objeto que se enviará vía POST
     * @param data
     */
    private void fillData(HashMap<String, Object> data) {
        byte[] dataByte = null;
        try {
            if (data != null) {
                if (MorphoFormApp.getSingleton().getCustomer().ID != null) {
                    data.put(Constants.CUSTOMER_ID, MorphoFormApp.getSingleton().getCustomer().ID);
                }
                if (MorphoFormApp.getSingleton().getCustomer().indexLeftBuff != null) {
                    data.put(Constants.CUSTOMER_LEFT_I, Base64.encode(MorphoFormApp.getSingleton().getCustomer().indexLeftBuff));
                }
                if (MorphoFormApp.getSingleton().getCustomer().indexRightBuff != null) {
                    data.put(Constants.CUSTOMER_RIGHT_I, Base64.encode(MorphoFormApp.getSingleton().getCustomer().indexRightBuff));
                }
                customer.isFake = true;
                data.put("isFake", customer.isFake);
                Log.d(this.getClass().getName(), "isFake finger flag: " + data.get("isFake"));
                dataByte = IOUtil.serialize(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        Log.d(this.getClass().getName(), "response: " + response);
        if(consumeResponse != null){
            consumeResponse.consumeResponde(statusCode,response);
        }
    }
}
