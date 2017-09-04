package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.morpho.demo.AuthenticActivity;
import com.morpho.demo.tools.NumericTool;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;


/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SearchCustomer extends AsyncTask<Void,Void,Void> {

    private String id;
    private Context context;
    private AuthenticActivity.CustomerDelegate consumeResponse;

    public SearchCustomer(String id, Context context, AuthenticActivity.CustomerDelegate consumeResponse){
        this.id = id;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void...params) {
        if(!NumericTool.isStringNunnable(id)){

            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),consumeResponse);

            try {
                clientWS.get("/api/customer/searchIdFull/customer_id="+id,Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if(consumeResponse != null){
                    consumeResponse.consumeResponde(500,e.getMessage());
                }
            }
        }

        return null;
    }


}
