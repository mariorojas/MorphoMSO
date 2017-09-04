package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SendFaceIden extends AsyncTask<Void,Void,Void> {

    private Context context;
    private byte[] foto;
    private String id;
    private ConsumeResponse consumeResponse;

    public SendFaceIden(String id, byte[] foto, Context context, ConsumeResponse consumeResponse){
        this.id = id;
        this.context = context;
        this.consumeResponse = consumeResponse;
        this.foto = foto;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(foto != null && consumeResponse != null ){

            HashMap<String,Object> data = new HashMap<>(2);
            data.put(Constants.CUSTOMER_ID, id);
            data.put(Constants.CUSTOMER_IMG, Base64.encode(foto));
            data.put(Constants.CUSTOMER_BO, MorphoFormApp.getSingleton().isHasFingerprint());
            JSONObject dat = new JSONObject(data);
            sendItem(dat.toString());

        }
        return null;
    }


    private void sendItem(String dataB){

        if(dataB != null) {
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),consumeResponse);

            try {
                clientWS.postCon("/api/customer/setPhotoIden", dataB,Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
            }
        }

    }

}
