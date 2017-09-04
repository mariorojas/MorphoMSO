package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.NumericTool;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SearchID extends AsyncTask<Void,Void,Void> implements ConsumeResponse{

    private String id;
    private Context context;
    private ConsumeResponse consumeResponse;

    public SearchID(String id, Context context, ConsumeResponse consumeResponse){
        this.id = id;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void...params) {
        if(!NumericTool.isStringNunnable(id)){

            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),this);

            try {
                clientWS.get("/api/customer/searchId/customer_id="+id,Util.getSettingWSPort(context));
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Fallo petición de búsqueda a usuario");
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
        if(statusCode == 200) {
            try {
                Log.d(SearchID.class.getName(), "Response StoreCustomer: " + response);

                JSONObject cs = new JSONObject(response);

                boolean hasFinger = cs.getBoolean(Constants.CUSTOMER_HAS_FINGERPRINT);
                boolean hasFacial = cs.getBoolean(Constants.CUSTOMER_HAS_FACIAL);
                boolean hasVo = cs.getBoolean(Constants.CUSTOMER_HAS_VOICE);

                MorphoFormApp.getSingleton().getCustomer().ID = id;

                if (hasFinger)
                    MorphoFormApp.getSingleton().setHasFingerprint(true);
                else
                    MorphoFormApp.getSingleton().setHasFingerprint(false);

                if (hasFacial)
                    MorphoFormApp.getSingleton().setHasFacial(true);
                else
                    MorphoFormApp.getSingleton().setHasFacial(false);

                if (hasVo)
                    MorphoFormApp.getSingleton().setHasVoice(true);
                else
                    MorphoFormApp.getSingleton().setHasVoice(false);

                if(consumeResponse != null){
                    consumeResponse.consumeResponde(statusCode,"");
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if(consumeResponse != null)
                    consumeResponse.consumeResponde(500,e.getMessage());
            }
        } else if(consumeResponse != null){
            consumeResponse.consumeResponde(statusCode,response);
        }
    }
}
