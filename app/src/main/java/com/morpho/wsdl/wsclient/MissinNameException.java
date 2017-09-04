package com.morpho.wsdl.wsclient;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class MissinNameException extends Throwable{


    public MissinNameException(String detailMessage) {
        super(detailMessage);
    }

    public MissinNameException(Throwable throwable) {
        super(throwable);
    }

    public MissinNameException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
