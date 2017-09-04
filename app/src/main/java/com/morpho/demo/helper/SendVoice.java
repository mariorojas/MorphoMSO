package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.EnrollActivity;
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
public class SendVoice extends AsyncTask<Void,Void,Void> implements ConsumeResponse{

    private Context context;
    private byte[] audio1;
    private byte[] audio2;
    private byte[] audio3;
   // private byte[] audio4;
    private String id;
    private ConsumeResponse consumeResponse;

    public SendVoice(String id,byte[] audio1,byte[] audio2,byte[] audio3 /*,byte[] audio4*/ , Context context, ConsumeResponse consumeResponse){
        this.id = id;
        this.context = context;
        this.consumeResponse = consumeResponse;
        this.audio1 = audio1;
        this.audio2 = audio2;
        this.audio3 = audio3;
        //this.audio4 = audio4;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if(audio1 != null && audio2 != null && audio3 != null /*&& audio4 != null*/){

            // Sending Audio 1
            HashMap<String,Object> data = new HashMap<>(3);
            data.put(Constants.CUSTOMER_ID, id);
            data.put(Constants.CUSTOMER_FIN, false);
            data.put(Constants.CUSTOMER_AUDIO_1, Base64.encode(audio1));
            JSONObject dat = new JSONObject(data);
            boolean r = sendItem(dat.toString(), false);

            //Sending Audio 2
            if(r) {
                data = new HashMap<>(3);
                data.put(Constants.CUSTOMER_ID, id);
                data.put(Constants.CUSTOMER_FIN, false);
                data.put(Constants.CUSTOMER_AUDIO_2, Base64.encode(audio2));
                dat = new JSONObject(data);
                r = sendItem(dat.toString(), false);
            }

            if(r) {
                //Sending Audio 3
                data = new HashMap<>(3);
                data.put(Constants.CUSTOMER_ID, id);
                data.put(Constants.CUSTOMER_FIN, true);
                data.put(Constants.CUSTOMER_AUDIO_3, Base64.encode(audio3));
                dat = new JSONObject(data);
                sendItem(dat.toString(), true);
            }

        }

        return null;
    }


    private boolean sendItem(String dataB,boolean fin){

        if(dataB != null) {
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context), Util.getSettingWSHost(context),(fin?this:null));

            try {
                clientWS.postCon("/api/customer/setAudios", dataB,Util.getSettingWSPort(context));
            } catch (Exception e) {
                e.printStackTrace();
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        if(statusCode == 200){
            Log.d(EnrollActivity.class.getName(), "Response StoreCustomer: " + response);
        }

        if(consumeResponse != null)
            consumeResponse.consumeResponde(statusCode,response);
    }
}
