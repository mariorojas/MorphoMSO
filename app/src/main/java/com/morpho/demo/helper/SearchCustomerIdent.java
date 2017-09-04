package com.morpho.demo.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.morpho.demo.IdentActivity;
import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.tools.NumericTool;
import com.morpho.demo.tools.Util;
import com.morpho.wsdl.wsclient.ClientWS;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;


/**
 * Created by Alejandro Ruiz on 25/02/2016.
 */
public class SearchCustomerIdent extends AsyncTask<Void,Void,Void> implements ConsumeResponse{

    private LinkedHashSet<String> ids;
    private Context context;
    private IdentActivity.CustomerDelegate consumeResponse;
    private Vector<Customer> customers;
    private boolean fin = false;

    public SearchCustomerIdent(LinkedHashSet<String> ids, Context context, IdentActivity.CustomerDelegate consumeResponse){
        this.ids = ids;
        this.context = context;
        this.consumeResponse = consumeResponse;
    }

    @Override
    protected Void doInBackground(Void...params) {
        if(ids != null && ids.size() > 0){
            Iterator<String> it = ids.iterator();
            int i = 0;
            String id = null;
            while(it.hasNext()){
                id = it.next();
                fin = (i == (ids.size() - 1));
                i++;
                getCustomer(id);
            }

        }else{
            consumeResponse.showCustomersFound(customers);
        }

        return null;
    }

    private void getCustomer(String id){
        ClientWS clientWS = new ClientWS(Util.getSettingWSURI(context), Util.getSettingWSHost(context),this);

        try {
            clientWS.get("/api/customer/searchIdFull/customer_id="+id,Util.getSettingWSPort(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void consumeResponde(int statusCode, String response) {
        if(statusCode == 200){
            Customer customer = null;

            try {
                Log.d(SearchCustomerIdent.class.getName(), "Response StoreCustomer: " + response);

                JSONObject cs = new JSONObject(response);

                String id = cs.getString(Constants.CUSTOMER_ID);
                String name = cs.getString(Constants.CUSTOMER_NAME);
                String lastname = cs.getString(Constants.CUSTOMER_SURNAME);
                String date = cs.getString(Constants.CUSTOMER_BIRTHDAY);
                int genre = cs.getInt(Constants.CUSTOMER_GENRE);
                long balance = cs.getLong(Constants.CUSTOMER_BALANCE);
                String img = cs.optString(Constants.CUSTOMER_IMG, null);

                customer = new Customer();

                customer.ID = id;
                customer.Name = name;
                customer.Surname = lastname;
                customer.Genre = genre;
                customer.BirthDay = date;
                customer.Balance = balance;

                if(!NumericTool.isStringNunnable(img)){
                    customer.imgSrc = img;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                customer = null;
            }

            if(customer != null){
                if(customers == null)
                    customers = new Vector<>();
                customers.add(customer);
            }

            if(fin && consumeResponse != null){
                consumeResponse.showCustomersFound(customers);
            }
        }
    }
}
