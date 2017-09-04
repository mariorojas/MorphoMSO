package com.morpho.demo.helper;

import com.morpho.demo.constant.MorphoFormApp;

/**
 * Created by marojas on 27/04/2017.
 */

public class TekneiUtils {

    /**
     * Limpia los datos de un usuario previamente capturado
     */
    public static void cleanPreviousUserData() {
        MorphoFormApp.getSingleton().getCustomer().Name = null;
        MorphoFormApp.getSingleton().getCustomer().Surname = null;
        MorphoFormApp.getSingleton().getCustomer().ID = null;
        MorphoFormApp.getSingleton().getCustomer().apeMat = null;
        MorphoFormApp.getSingleton().getCustomer().Genre = 0;
        MorphoFormApp.getSingleton().getCustomer().sexo = null;
        MorphoFormApp.getSingleton().getCustomer().anioRegistro = null;
        MorphoFormApp.getSingleton().getCustomer().claveElector = null;
        MorphoFormApp.getSingleton().getCustomer().curp = null;
        MorphoFormApp.getSingleton().getCustomer().estado = null;
        MorphoFormApp.getSingleton().getCustomer().municipio = null;
        MorphoFormApp.getSingleton().getCustomer().localidad = null;
        MorphoFormApp.getSingleton().getCustomer().seccion = null;
        MorphoFormApp.getSingleton().getCustomer().emision = null;
        MorphoFormApp.getSingleton().getCustomer().vigencia = null;

        MorphoFormApp.getSingleton().getCustomer().littleLeftBuff = null;
        MorphoFormApp.getSingleton().getCustomer().ringLeftBuff = null;
        MorphoFormApp.getSingleton().getCustomer().middletLeftBuff = null;
        MorphoFormApp.getSingleton().getCustomer().indexLeftBuff = null;
        MorphoFormApp.getSingleton().getCustomer().thumbLeftBuff = null;

        MorphoFormApp.getSingleton().getCustomer().thumbRightBuff = null;
        MorphoFormApp.getSingleton().getCustomer().indexRightBuff = null;
        MorphoFormApp.getSingleton().getCustomer().middletRightBuff = null;
        MorphoFormApp.getSingleton().getCustomer().ringRightBuff = null;
        MorphoFormApp.getSingleton().getCustomer().littleRightBuff = null;
    }

    public static int capturedFingers() {
        int i = 0;

        if (MorphoFormApp.getSingleton().getCustomer().littleLeftBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().ringLeftBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().middletLeftBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().indexLeftBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().thumbLeftBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().thumbRightBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().indexRightBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().middletRightBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().ringRightBuff != null)
            i++;

        if (MorphoFormApp.getSingleton().getCustomer().littleRightBuff != null)
            i++;

        return i;
    }

}
