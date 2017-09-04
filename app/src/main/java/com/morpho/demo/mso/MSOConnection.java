package com.morpho.demo.mso;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;

import com.morpho.android.usb.USBManager;
import com.morpho.demo.ParentActivity;
import com.morpho.morphosmart.sdk.CallbackMask;
import com.morpho.morphosmart.sdk.CallbackMessage;
import com.morpho.morphosmart.sdk.CompressionAlgorithm;
import com.morpho.morphosmart.sdk.DetectionMode;
import com.morpho.morphosmart.sdk.ErrorCodes;
import com.morpho.morphosmart.sdk.LatentDetection;
import com.morpho.morphosmart.sdk.MorphoDevice;
import com.morpho.morphosmart.sdk.MorphoImage;

import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alex on 21/07/16.
 */

public class MSOConnection implements Observer{

    MorphoDevice morphoDevice;

    private String sensorName;

    private Handler mHandler = new Handler();

    private MSOShower msoShower;

    private String strMessage = null;

    private ParentActivity activity;

    private int	callbackCmd	= CallbackMask.MORPHO_CALLBACK_IMAGE_CMD.getValue()
            | CallbackMask.MORPHO_CALLBACK_ENROLLMENT_CMD.getValue()
            | CallbackMask.MORPHO_CALLBACK_COMMAND_CMD.getValue()
            | CallbackMask.MORPHO_CALLBACK_CODEQUALITY.getValue()
            | CallbackMask.MORPHO_CALLBACK_DETECTQUALITY.getValue();

    private static MSOConnection singleton;

    public static MSOConnection getInstance(){
        if(singleton == null)
            singleton = new MSOConnection();

        return singleton;
    }


    private MSOConnection(){
        morphoDevice = new MorphoDevice();
    }


    public void setMsoShower(MSOShower msoShower){
        this.msoShower = msoShower;
    }



    public int getCallbackCmd(){
        return callbackCmd;
    }

    public void getUSBPermission(Activity activity) throws MSOException{

        USBManager.getInstance().initialize(activity, "com.morpho.morphosample.USB_ACTION");

        if(USBManager.getInstance().isDevicesHasPermission() != true)
        {
            throw  new MSOException("USB permission is not allowed");
        }


    }

    public boolean connectMSO(ParentActivity activity){

        this.activity = activity;




        boolean res = false;
        Integer nbUsbDevice = new Integer(0);

        int ret = morphoDevice.initUsbDevicesNameEnum(nbUsbDevice);

        if (ret == ErrorCodes.MORPHO_OK) {

            if (nbUsbDevice > 0) {
                sensorName = morphoDevice.getUsbDeviceName(0);
                if(nbUsbDevice == 2)
                    sensorName = morphoDevice.getUsbDeviceName(1);

                try {
                    ret = morphoDevice.openUsbDevice(sensorName, 0);
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(activity.getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(ret == 0) {
                    res = true;
                    Toast.makeText(activity, "MSO connected: " + sensorName, Toast.LENGTH_LONG).show();
                }
            } else {
                activity.showAlert("Error:","The device is not detected, or you have not asked USB permissions");
            }
        }

        return res;
    }

    public void Capture()
    {

        boolean res = false;
        try {
            getUSBPermission(activity);

            Thread.sleep(400);

            res = connectMSO(activity);

            if(!res) {
                Thread.sleep(1000);
                res = connectMSO(activity);
                if(!res) {
                    Thread.sleep(3500);
                    connectMSO(activity);
                }
            }

        }catch (MSOException e){
            e.printStackTrace();
        }catch (InterruptedException E){
            E.printStackTrace();
        }

        if(res) {

            Thread commandThread = (new Thread(new Runnable() {
                @Override
                public void run() {
                    int timeOut = 20;
                    int acquisitionThreshold = 0;
                    CompressionAlgorithm compressAlgo;
                    int compressRate = 10;
                    int detectModeChoice;
                    LatentDetection latentDetection;
                    MorphoImage morphoImage = new MorphoImage();

                    int callbackCmd = MSOConnection.getInstance().getCallbackCmd();

                    callbackCmd &= ~CallbackMask.MORPHO_CALLBACK_ENROLLMENT_CMD.getValue();

                    compressAlgo = CompressionAlgorithm.MORPHO_COMPRESS_WSQ;

                    detectModeChoice = DetectionMode.MORPHO_ENROLL_DETECT_MODE.getValue();

                    detectModeChoice |= DetectionMode.MORPHO_FORCE_FINGER_ON_TOP_DETECT_MODE.getValue();

                    latentDetection = LatentDetection.LATENT_DETECT_DISABLE;

                    final int ret = morphoDevice.getImage(timeOut, acquisitionThreshold, compressAlgo, compressRate, detectModeChoice, latentDetection, morphoImage, callbackCmd, MSOConnection.getInstance());

                    if (msoShower != null)
                        msoShower.updateImageView(morphoImage.getCompressedImage(), ret);

                }
            }));
            commandThread.start();
        }else{
            if (msoShower != null)
                msoShower.updateImageView(null, ErrorCodes.MORPHOERR_USB_PERMISSION_DENIED);
        }
    }


    @Override
    public synchronized void update(Observable o, Object arg)
    {
        try
        {
            // convert the object to a callback back message.
            CallbackMessage message = (CallbackMessage) arg;


            int type = message.getMessageType();

            switch (type)
            {

                case 1:
                    // message is a command.
                    Integer command = (Integer) message.getMessage();

                    // Analyze the command.
                    switch (command)
                    {
                        case 0:
                            strMessage = "move-no-finger";
                            break;
                        case 1:
                            strMessage = "move-finger-up";
                            break;
                        case 2:
                            strMessage = "move-finger-down";
                            break;
                        case 3:
                            strMessage = "move-finger-left";
                            break;
                        case 4:
                            strMessage = "move-finger-right";
                            break;
                        case 5:
                            strMessage = "press-harder";
                            break;
                        case 6:
                            strMessage = "move-latent";
                            break;
                        case 7:
                            strMessage = "remove-finger";
                            break;
                        case 8:
                            strMessage = "finger-ok";
                            // switch live acquisition ImageView

                    }

                    if(msoShower != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public synchronized void run() {
                                msoShower.updateSensorMessage(strMessage);
                            }
                        });
                    }

                    break;
                case 2:
                    // message is a low resolution image, display it.
                    byte[] image = (byte[]) message.getMessage();

                    MorphoImage morphoImage = MorphoImage.getMorphoImageFromLive(image);
                    int imageRowNumber = morphoImage.getMorphoImageHeader().getNbRow();
                    int imageColumnNumber = morphoImage.getMorphoImageHeader().getNbColumn();
                    final Bitmap imageBmp = Bitmap.createBitmap(imageColumnNumber, imageRowNumber, Bitmap.Config.ALPHA_8);

                    imageBmp.copyPixelsFromBuffer(ByteBuffer.wrap(morphoImage.getImage(), 0, morphoImage.getImage().length));

                    if(msoShower != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public synchronized void run() {
                                msoShower.updateImage(imageBmp);
                            }
                        });
                    }
                    break;
                case 3:
                    if(msoShower != null) {
                        // message is the coded image quality.
                        final Integer quality = (Integer) message.getMessage();
                        mHandler.post(new Runnable() {
                            @Override
                            public synchronized void run() {
                                msoShower.updateSensorProgressBar(quality);
                            }
                        });

                    }
                    break;
                //case 4:
                //byte[] enrollcmd = (byte[]) message.getMessage();
            }
        }
        catch (Exception e)
        {
            if(msoShower != null)
                msoShower.showAlert(""+e.getMessage());
        }
    }
}
