package com.morpho.demo.mso;

/**
 * Created by alex on 21/07/16.
 */

public class MSOException extends Throwable{


    private String mess;

    public MSOException(String mess) {
        super();
        this.mess = mess;
    }



    @Override
    public String getMessage() {
        return mess+"\n"+super.getMessage();
    }
}
