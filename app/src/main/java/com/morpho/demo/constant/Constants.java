package com.morpho.demo.constant;

import android.os.Environment;

/**
 * Created by alex on 30/06/2015.
 */
public interface Constants {

    public static final int FORM_ID = 1;


    public static final String CURRENT = "current";
    public static final int LAST_YEAR = 2015;

    public static final int RESULT_FINISH = 10000;

    public static final int Q_NOMRAL = 1;
    public static final int Q_BOOLEAN = 2;
    public static final int Q_MULTIVALS = 3;
    public static final int Q_SEPECIFIC_CHARS = 4;
    public static final int Q_MIXED = 5;
    public static final int Q_DATE = 6;
    public static final int Q_BOOLEAN_OTHER = 7;
    public static final int Q_EMAIL = 8;
    public static final int Q_PHONE = 9;
    public static final int Q_NUMBER = 10;
    public static final int Q_INFORMATIVE_TXT = 11;
    public static final int Q_SIGNATURE_SRC = 12;
    public static final int Q_CONTRACT = 13;
    public static final int Q_VERIFCATION = 14;

    public static final int timeOutConnection = 720000000;
    public static final int timeOutSocket = 800000000;

    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_NAME = "name";
    public static final String CUSTOMER_SURNAME = "surname";
    public static final String CUSTOMER_BIRTHDAY = "date_of_birth";
    public static final String CUSTOMER_GENRE = "genre";
    public static final String KIVOX_ID = "kivox_id";
    public static final String CUSTOMER_IMG = "image_src";
    public static final String CUSTOMER_BALANCE = "balance";
    public static final String CUSTOMER_TABLE = "m_user_multi";
    public static final String CUSTOMER_HAS_FINGERPRINT = "has_fingerprint";
    public static final String CUSTOMER_HAS_FACIAL = "has_facial";
    public static final String CUSTOMER_HAS_VOICE = "has_voice";

    public static final String CUSTOMER_STATUS = "statusCode";
    public static final String CUSTOMER_IDS = "ids_customers";

    public static final String CUSTOMER_LEFT_I = "left_index";
    public static final String CUSTOMER_RIGHT_I = "right_index";
    public static final String CUSTOMER_LEFT_M = "left_middlet";
    public static final String CUSTOMER_RIGHT_M = "right_middlet";
    public static final String CUSTOMER_LEFT_R = "left_ring";
    public static final String CUSTOMER_RIGHT_R = "right_ring";
    public static final String CUSTOMER_LEFT_L = "left_little";
    public static final String CUSTOMER_RIGHT_L = "right_little";
    public static final String CUSTOMER_LEFT_T = "left_thumb";
    public static final String CUSTOMER_RIGHT_T = "right_thumb";
    public static final String CUSTOMER_AUDIO_1 = "audio_1";
    public static final String CUSTOMER_AUDIO_2 = "audio_2";
    public static final String CUSTOMER_AUDIO_3 = "audio_3";
    public static final String CUSTOMER_AUDIO_4 = "audio_4";
    public static final String CUSTOMER_AUDIO_AUT = "audio_aut";
    public static final String CUSTOMER_FIN = "customer_fin";
    public static final String CUSTOMER_BO = "customer_bo";
    public static final String CUSTOMER_FIRST = "customer_first";

    public static final String outputFile1 = "enroll1.wav";
    public static final String outputFile2 = "enroll2.wav";
    public static final String outputFile3 = "enroll3.wav";
    public static final String outputFile4 = "enroll4.wav";

    public static final String outputFileAut = "authentic.wav";


    public static final int FINGERPRING_TYPE = 1000;
    public static final int FACIAL_TYPE = 2000;
    public static final int VOICE_TYPE = 3000;

    public static final int ENROLL_CASE = 100;
    public static final int AUTHENTICATE_CASE = 200;
    public static final int IDENTIFY_CASE = 300;

    public static final int PORT_TO_CONNECT = 8081;

}
