package com.morpho.demo.constant;


/**
 * Created by alex on 07/07/2015.
 */
public interface MenuItemSelecter {

    public static final int FINGER_PRINTS = 10;
    public static final int TAKE_PICTURE = 20;
    public static final int TAKE_GENERALS = 30;
    public static final int REQUET_CREDIT_CARD = 40;

    public static final int TAKE_PERSONAL_DATA = 50;
    public static final int TAKE_INE_SECOND_DATA = 60;
    public static final int TAKE_INE_THIRD_DATA = 70;

    public void onQuestionarySelected(int questionary);

}
