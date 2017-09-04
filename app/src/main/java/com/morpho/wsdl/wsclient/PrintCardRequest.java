package com.morpho.wsdl.wsclient;

import com.morpho.demo.tools.NumericTool;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class PrintCardRequest {

    private String nameInPrintCard;

    public PrintCardRequest(String nameInPrintCard){
        this.nameInPrintCard = nameInPrintCard;
    }

    public StringBuilder getPrintCardEnvelope() throws MissinNameException{


        if(NumericTool.isStringNunnable(nameInPrintCard))
            throw new MissinNameException("Missing the name to print in credit card");

        StringBuilder buiderEnvelope = new StringBuilder("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"https://morpho/ws/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<ws:ProcesarRegistro>" +
                "<!--Optional:-->");
        buiderEnvelope.append("<ws:nombre>"+nameInPrintCard+"</ws:nombre>");
        buiderEnvelope.append("</ws:ProcesarRegistro>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>");

        return buiderEnvelope;
    }
}
