package com.morpho.demo.mso;

import android.graphics.Bitmap;

/**
 * Created by alex on 22/07/16.
 */

public interface MSOShower {


    public void updateSensorProgressBar(int level);

    public void updateSensorMessage(String sensorMessage);

    public void updateImage(Bitmap bitmap);

    public void showAlert(String message);

    public void updateImageView(final byte[] imgeSrc,
                                final int captureError);
}
