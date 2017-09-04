package com.morpho.wsdl.wsclient;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class XMLReaderPrintCard extends Throwable{
    public XMLReaderPrintCard(String detailMessage) {
        super(detailMessage);
    }

    public XMLReaderPrintCard(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public XMLReaderPrintCard(Throwable throwable) {
        super(throwable);
    }
}
