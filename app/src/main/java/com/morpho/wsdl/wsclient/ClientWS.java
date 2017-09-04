package com.morpho.wsdl.wsclient;

import android.util.Log;

import com.morpho.demo.constant.Constants;


import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class ClientWS {

    private ConsumeResponse consumeResponde;

    private String IP;
    private String HOST_NAME;

    public ClientWS(String IP, String HOST_NAME, ConsumeResponse consumeResponde) {
        this.IP = IP;
        this.HOST_NAME = HOST_NAME;
        this.consumeResponde = consumeResponde;
    }

    public void get(String request,String port) throws Exception {
        String response = "";
        int statusCode = 0;
        request = "http://"+IP+":"+port+"/"+HOST_NAME+request;
        try {

            HttpURLConnection conn = null;

            URL url = new URL(request);

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000000);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(300000);

            conn.connect();
            statusCode = conn.getResponseCode();

            try {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Log.d(this.getClass().getName(), "inputStreamReader: " + conn.getInputStream().toString());
                while ((line = br.readLine()) != null) {
                    Log.d(this.getClass().getName(), "line: " + line);
                    response += line;
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = null;
            }
            if(consumeResponde != null) {
                consumeResponde.consumeResponde(statusCode, response);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("HA HABIDO UN ERROR EN LA PLATAFORMA, FAVOR DE ESPERAR UN MOMENTO Y VOLVER A INTENTAR");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("PARECE QUE TIENE UN ERROR DE CONEXIÓN, FAVOR DE VERIFICARLO PARA CONTINUAR");
        } catch (Exception e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("PARECE QUE HA HABIDO UN ERROR EN LA CONSULTA, FAVOR DE ESPERAR UN MOMENTO Y VOLVER A INTENTAR");
        }
    }


    public int postCon(String request, String data,String port) throws Exception {
        String response = "";
        int statusCode = 0;
        request = "http://" + IP + ":" + port + "/" + HOST_NAME + request;
        Log.d(this.getClass().getName(), "request: " + request);
        try {
            URL url = new URL(request);
            Log.d(getClass().getSimpleName(), "JSON: \n" + data);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("authorization", "Basic dGVrbmVpOnRla24zMQ==");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setReadTimeout(300000);
            OutputStream output = null;
            try {
                output = conn.getOutputStream();
                if (data !=null && !data.isEmpty())
                    output.write(data.getBytes());
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        // ignore
                        e.printStackTrace();
                    }
                }
            }
            statusCode = conn.getResponseCode();
            if (statusCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }

            if(consumeResponde != null) {
                consumeResponde.consumeResponde(statusCode, response);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("HA HABIDO UN ERROR EN LA PLATAFORMA, FAVOR DE ESPERAR UN MOMENTO Y VOLVER A INTENTAR");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("PARECE QUE TIENE UN ERROR DE CONEXIÓN, FAVOR DE VERIFICARLO PARA CONTINUAR");
        } catch (Exception e){
            e.printStackTrace();
            Log.e(this.getClass().getName(), ""+e.getMessage());
            throw new Exception("PARECE QUE HA HABIDO UN ERROR EN LA CONSULTA, FAVOR DE ESPERAR UN MOMENTO Y VOLVER A INTENTAR");
        }

        return statusCode;
    }


}
