package com.morpho.demo.constant;

import android.util.Log;

import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.NumericTool;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class Customer {

    public String scanID;

    public String ID;
    public String Name;
    public String Surname;
    public String imgSrc;
    public int Genre;
    public String BirthDay;
    public long Balance;

    public String apeMat;
    public String anioRegistro;
    public String emision;
    public String claveElector;
    public String curp;
    public String sexo;
    public String estado;
    public String municipio;
    public String localidad;
    public String seccion;
    public String vigencia;
    //public String telefono;

    public boolean isFake;

    public byte[] photoBuffer;

    public byte[] littleLeftBuff;
    public byte[] littleRightBuff;
    public byte[] middletLeftBuff;
    public byte[] middletRightBuff;
    public byte[] indexLeftBuff;
    public byte[] indexRightBuff;
    public byte[] ringLeftBuff;
    public byte[] ringRightBuff;
    public byte[] thumbLeftBuff;
    public byte[] thumbRightBuff;

    public byte[] audio1;
    public byte[] audio2;
    public byte[] audio3;
    public byte[] audio4;
    public byte[] audioAuth;

    public boolean isEmptyAudios(){
        if(audio1 == null || audio2 == null || audio3 == null /* || audio4 == null */ )
            return true;
        return false;
    }

    public int isEmpty() {

        int type = 0;
        Log.d(Customer.class.getName(), ID + "\n" + Name + "\n" + Surname + "\n"
                + apeMat + "\n"
                + anioRegistro + "\n"
                + emision + "\n"
                + claveElector + "\n"
                + curp + "\n"
                + sexo + "\n"
                + estado + "\n"
                + municipio + "\n"
                + localidad + "\n"
                + seccion + "\n"
                + vigencia// + "\n"
                //+ telefono
        );

        if (NumericTool.isStringNunnable(ID)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(Name)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(Surname)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(apeMat)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(anioRegistro)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(emision)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(claveElector)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(curp)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(sexo)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(estado)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(municipio)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(localidad)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(seccion)) {
            type = 1;
            return type;
        }
        if (NumericTool.isStringNunnable(vigencia)) {
            type = 1;
            return type;
        }
        /*if (NumericTool.isStringNunnable(telefono)) {
            type = 1;
            return type;
        }*/

        if (MorphoFormApp.getSingleton().isHasFingerprint()) {
            if (littleLeftBuff == null && littleRightBuff == null && ringLeftBuff == null && ringRightBuff == null &&
                    middletLeftBuff == null && middletRightBuff == null && indexLeftBuff == null && indexRightBuff == null
                    && thumbLeftBuff == null && thumbRightBuff == null) {
                type = 2;
                return type;
            }
        }

        if (MorphoFormApp.getSingleton().isHasFacial()) {
            if (photoBuffer == null) {
                type = 3;
                return type;
            }
        }

        if(MorphoFormApp.getSingleton().isHasVoice()) {
            if (isEmptyAudios()) {
                type = 4;
                return type;
            }
        }

        Log.d(Customer.class.getName(),"Resultado final "+type);

        return type;
    }

    public int isEmptyForAuth() {

        int type = 0;

        if (MorphoFormApp.getSingleton().isHasFingerprint()) {
            if (littleLeftBuff == null && littleRightBuff == null && ringLeftBuff == null && ringRightBuff == null &&
                    middletLeftBuff == null && middletRightBuff == null && indexLeftBuff == null && indexRightBuff == null
                    && thumbLeftBuff == null && thumbRightBuff == null) {
                type = 2;
                return type;
            }
        }

        if (MorphoFormApp.getSingleton().isHasFacial()) {
            if (photoBuffer == null) {
                type = 3;
                return type;
            }
        }

        if(MorphoFormApp.getSingleton().isHasVoice()) {
            if (audioAuth == null) {
                type = 4;
                return type;
            }
        }

        Log.d(Customer.class.getName(),"Resultado final "+type);

        return type;
    }

    public int isEmptyForIdent() {

        int type = 0;

        if (MorphoFormApp.getSingleton().isHasFingerprint()) {
            if (littleLeftBuff == null && littleRightBuff == null && ringLeftBuff == null && ringRightBuff == null &&
                    middletLeftBuff == null && middletRightBuff == null && indexLeftBuff == null && indexRightBuff == null
                    && thumbLeftBuff == null && thumbRightBuff == null) {
                type = 2;
                return type;
            }
        }

        if (MorphoFormApp.getSingleton().isHasFacial()) {
            if (photoBuffer == null) {
                type = 3;
                return type;
            }
        }

        Log.d(Customer.class.getName(),"Resultado final "+type);

        return type;
    }

    public JSONObject getJSON(){

        JSONObject json = null;

        HashMap<String,Object> data = new HashMap<>();
        data.put(Constants.CUSTOMER_ID,ID);
        data.put(Constants.CUSTOMER_NAME,Name);
        data.put(Constants.CUSTOMER_GENRE,0);
        data.put(Constants.CUSTOMER_BIRTHDAY,"");
        if(photoBuffer != null)
            data.put(Constants.CUSTOMER_IMG, Base64.encode(photoBuffer));
        data.put(Constants.CUSTOMER_SURNAME,Surname);
        data.put(Constants.CUSTOMER_HAS_FINGERPRINT, MorphoFormApp.getSingleton().isHasFingerprint());
        data.put(Constants.CUSTOMER_HAS_FACIAL,MorphoFormApp.getSingleton().isHasFacial());
        data.put(Constants.CUSTOMER_HAS_VOICE,MorphoFormApp.getSingleton().isHasVoice());

        data.put("ocr", ID);
        data.put("nombre", Name);
        data.put("apellidoPaterno", Surname);
        data.put("apellidoMaterno",apeMat);
        data.put("anioRegistro",anioRegistro);
        data.put("emision",emision);
        data.put("claveElector",claveElector);
        data.put("curp",curp);
        data.put("sexo",sexo);
        data.put("estado",estado);
        data.put("municipio",municipio);
        data.put("localidad",localidad);
        data.put("seccion",seccion);
        data.put("vigencia",vigencia);
        data.put("isFake", isFake);

        json = new JSONObject(data);

        return json;

    }


}
