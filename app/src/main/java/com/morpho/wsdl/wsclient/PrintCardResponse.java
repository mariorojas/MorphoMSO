package com.morpho.wsdl.wsclient;

import com.morpho.demo.tools.NumericTool;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class PrintCardResponse {

    private String xmlResponse;
    private String parsed;

    public PrintCardResponse(String xmlResponse){
        this.xmlResponse = xmlResponse;
    }

    public void parseReult() throws XMLReaderPrintCard{
        if(NumericTool.isStringNunnable(xmlResponse))
            new XMLReaderPrintCard("XML Response is null");
        else{

        }

    }
}
