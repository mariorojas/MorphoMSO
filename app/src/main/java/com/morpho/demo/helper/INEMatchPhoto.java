package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.Customer;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marojas on 09/05/2017.
 */

public class INEMatchPhoto extends AsyncTask<Void, Void, Void> implements ConsumeResponse {

    private Customer customer;
    private Context context;
    private ConsumeResponse consumeResponse;

    public INEMatchPhoto(Customer customer, Context context, ConsumeResponse consumeResponse) {
        this.customer = customer;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (consumeResponse != null) {
            Log.d(this.getClass().getName(), "Inicia envío de foto para comparación");
            HashMap<String, Object> data = new HashMap<>();
            fillData(data);

            Log.d(this.getClass().getName(), "scanId: " + MorphoFormApp.getSingleton()
                    .getCustomer().scanID);
            Log.d(this.getClass().getName(), "photo: " + Base64.encode(
                    MorphoFormApp.getSingleton().getCustomer().photoBuffer));

            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),
                    Util.getSettingWSHost(context),this);

            JSONObject jsonObject = new JSONObject(data);

            try {
                clientWS.postCon("/api/customer/comparePhotos",
                        jsonObject.toString(),Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
            }
        }
        return null;
    }

    private void fillData(HashMap<String, Object> data) {
        if (data != null && MorphoFormApp.getSingleton().getCustomer().photoBuffer != null) {
            data.put("scanId", MorphoFormApp.getSingleton().getCustomer().scanID);
            data.put("photo", Base64.encode(
                    MorphoFormApp.getSingleton().getCustomer().photoBuffer));
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
