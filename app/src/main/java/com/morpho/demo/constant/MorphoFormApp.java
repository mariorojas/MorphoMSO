package com.morpho.demo.constant;


import java.util.TreeSet;

/**
 * Created by alex on 01/07/2015.
 */
public class MorphoFormApp{

    private static MorphoFormApp singleton;
    private boolean hasFingerprint;
    private boolean hasFacial;
    private boolean hasVoice;

    public static final int ALFAS_OPTION = 4000;
    public static final int FINGERPRINT_OPTION = 2000;
    public static final int FACIAL_OPTION = 3000;
    public static final int VOICE_OPTION = 1000;

    private Customer customer;

    private TreeSet<Integer> stackOperaions;

    public static MorphoFormApp getSingleton(){
        if(singleton == null)
            singleton = new MorphoFormApp();
        return singleton;
    }

    private MorphoFormApp(){}


    public Customer getCustomer(){
        if(this.customer == null)
            this.customer = new Customer();
        return customer;
    }

    public TreeSet<Integer> getStackOperaions(){
        if(stackOperaions == null )
            stackOperaions = new TreeSet<>();
        return stackOperaions;
    }

    public boolean isHasVoice() {
        return hasVoice;
    }

    public void setHasVoice(boolean hasVoice) {
        this.hasVoice = hasVoice;
    }

    public boolean isHasFingerprint() {
        return hasFingerprint;
    }

    public void setHasFingerprint(boolean hasFingerprint) {
        this.hasFingerprint = hasFingerprint;
    }

    public boolean isHasFacial() {
        return hasFacial;
    }

    public void setHasFacial(boolean hasFacial) {
        this.hasFacial = hasFacial;
    }


}
