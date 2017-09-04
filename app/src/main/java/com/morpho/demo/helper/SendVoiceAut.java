package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SendVoiceAut extends AsyncTask<Void,Void,Void>{

    private Context context;
    private byte[] audio1;
    private String id;
    private ConsumeResponse consumeResponse;

    public SendVoiceAut(String id, byte[] audio1, Context context, ConsumeResponse consumeResponse){
        this.id = id;
        this.context = context;
        this.consumeResponse = consumeResponse;
        this.audio1 = audio1;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(audio1 != null){

            // Sending Audio 1
            HashMap<String,Object> data = new HashMap<>(2);
            data.put(Constants.CUSTOMER_ID, id);
            data.put(Constants.CUSTOMER_AUDIO_AUT, Base64.encode(audio1));
            JSONObject dat = new JSONObject(data);
            sendItem(dat.toString());

        }

        return null;
    }


    private boolean sendItem(String dataB){

        if(dataB != null) {
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context),Util.getSettingWSHost(context),consumeResponse);

            try {
                clientWS.postCon("/api/customer/setAudiosAut", dataB,Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
                return false;
            }
        }

        return true;
    }

}
