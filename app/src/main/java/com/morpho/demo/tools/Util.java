package com.morpho.demo.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;




import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.morpho.demo.R;


public class Util {

    public static byte[] pathToByte(String path)
    {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }


    //cargar configuracin aplicacin Android usando SharedPreferences
    public static String getSettingURI(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getString(contexto.getString(R.string.mbssUrl_KEY), "NoURI");


    }

    //cargar configuracin aplicacin Android usando SharedPreferences
    public static String getSettingWSURI(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getString(contexto.getString(R.string.demoUrl_KEY), null);


    }

    public static String getSettingWSHost(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getString(contexto.getString(R.string.demoHost), null);


    }

    public static String getSettingWSPort(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getString(contexto.getString(R.string.portHost), "8081");


    }

    //cargar configuracin aplicacin Android usando SharedPreferences
    public static String getSettingWSURICreditCard(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getString(contexto.getString(R.string.cardUrl_KEY), null);


    }

    //cargar configuracin aplicacin Android usando SharedPreferences
    public static boolean getSettingFigerprint(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getBoolean(contexto.getString(R.string.fingerprint_KEY), false);

    }

    public static boolean getSettingFacial(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getBoolean(contexto.getString(R.string.facial_KEY), false);

    }

    public static boolean getSettingVoice(Context contexto){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        return prefs.getBoolean(contexto.getString(R.string.voice_KEY), false);

    }


}
