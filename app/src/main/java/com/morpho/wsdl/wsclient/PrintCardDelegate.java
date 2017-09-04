package com.morpho.wsdl.wsclient;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public interface PrintCardDelegate {

    public void invokeSuccessfully(PrintCardResponse printCardResponse);

    public void invokeError(String message);
}
