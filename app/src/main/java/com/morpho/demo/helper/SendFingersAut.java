package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SendFingersAut extends AsyncTask<Void,Void,Void> implements ConsumeResponse {

    private Context context;
    private ConsumeResponse consumeResponse;

    private boolean allGood = true;

    public SendFingersAut(Context context, ConsumeResponse consumeResponse){
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if(consumeResponse != null) {

            HashMap<String, Object> data = new HashMap<>();
            fillData(data);

            int cout = data.keySet().size();
            Iterator<String> it = data.keySet().iterator();
            int i = 0;
            while (it.hasNext() && allGood) {
                String key = it.next();
                String dat = (String) data.get(key);
                boolean fin = (i == (cout - 1) ? true : false);
                boolean first = (i == 0?true:false);
                sendItem(dat, key, fin, MorphoFormApp.getSingleton().isHasFacial(),first);
                i++;
            }
        }

        return null;
    }

    private void sendItem(String dataB,String key,boolean fin,boolean bo,boolean first){

        if(dataB != null) {
            ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context), Util.getSettingWSHost(context),(fin?consumeResponse:this));

            HashMap<String, Object> data = null;
            data = getItem(dataB, key, fin,bo,first);

            JSONObject dat = new JSONObject(data);

            try {
                clientWS.postCon("/api/customer/setFingersAut", dat.toString(),Util.getSettingWSPort(context));
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Checkpoint 1");
                e.printStackTrace();
                allGood = false;
                if (consumeResponse != null)
                    consumeResponse.consumeResponde(500, e.getMessage());
            }
        }
    }

    private HashMap<String,Object> getItem(String data,String key,boolean fin,boolean bo,boolean first){
        HashMap<String,Object> dat = null;
        if(data != null) {
            dat = new HashMap<>();
            dat.put(Constants.CUSTOMER_ID, MorphoFormApp.getSingleton().getCustomer().ID);
            dat.put(Constants.CUSTOMER_FIN,fin);
            dat.put(key, data);
            dat.put(Constants.CUSTOMER_BO, bo);
            dat.put(Constants.CUSTOMER_FIRST, first);
        }

        return dat;
    }


    private void fillData(HashMap<String,Object> data){
        if (data != null) {
            if (MorphoFormApp.getSingleton().getCustomer().indexLeftBuff != null) {
                data.put(Constants.CUSTOMER_LEFT_I, Base64.encode(MorphoFormApp.getSingleton().getCustomer().indexLeftBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().indexRightBuff != null) {
                data.put(Constants.CUSTOMER_RIGHT_I, Base64.encode(MorphoFormApp.getSingleton().getCustomer().indexRightBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().middletLeftBuff != null) {
                data.put(Constants.CUSTOMER_LEFT_M, Base64.encode(MorphoFormApp.getSingleton().getCustomer().middletLeftBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().middletRightBuff != null) {
                data.put(Constants.CUSTOMER_RIGHT_M, Base64.encode(MorphoFormApp.getSingleton().getCustomer().middletRightBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().ringLeftBuff != null) {
                data.put(Constants.CUSTOMER_LEFT_R, Base64.encode(MorphoFormApp.getSingleton().getCustomer().ringLeftBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().ringRightBuff != null) {
                data.put(Constants.CUSTOMER_RIGHT_R, Base64.encode(MorphoFormApp.getSingleton().getCustomer().ringRightBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().littleLeftBuff != null) {
                data.put(Constants.CUSTOMER_LEFT_L, Base64.encode(MorphoFormApp.getSingleton().getCustomer().littleLeftBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().littleRightBuff != null) {
                data.put(Constants.CUSTOMER_RIGHT_L, Base64.encode(MorphoFormApp.getSingleton().getCustomer().littleRightBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().thumbLeftBuff != null) {
                data.put(Constants.CUSTOMER_LEFT_T, Base64.encode(MorphoFormApp.getSingleton().getCustomer().thumbLeftBuff));
            }
            if (MorphoFormApp.getSingleton().getCustomer().thumbRightBuff != null) {
                data.put(Constants.CUSTOMER_RIGHT_T, Base64.encode(MorphoFormApp.getSingleton().getCustomer().thumbRightBuff));
            }
        }

    }

    @Override
    public void consumeResponde(int statusCode, String response) {
        if(statusCode != 200)
            allGood = false;
    }
}
