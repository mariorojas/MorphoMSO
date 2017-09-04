package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.Customer;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;


/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SendAlfas extends AsyncTask<Void,Void,Void> implements ConsumeResponse{

    private Customer customer;
    private Context context;
    private ConsumeResponse consumeResponse;

    public SendAlfas(Customer customer, Context context, ConsumeResponse consumeResponse){
        this.customer = customer;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void...params) {
        if(customer != null){
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),this);
            try {
                clientWS.postCon("/api/customer/setCustomer",customer.getJSON().toString(),Util.getSettingWSPort(context));
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

        Log.d(SendAlfas.class.getName(), "Response StoreCustomer: " + response);

        if(consumeResponse != null){
            consumeResponse.consumeResponde(statusCode,response);
        }
    }
}
