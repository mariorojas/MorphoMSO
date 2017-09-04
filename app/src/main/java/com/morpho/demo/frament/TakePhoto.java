package com.morpho.demo.frament;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.morpho.demo.R;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.tools.BitmapHelper;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Alejandro Ruiz on 09/10/2015.
 */
@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TakePhoto extends ParentFragment implements SurfaceHolder.Callback, CompoundButton.OnCheckedChangeListener{

    private Camera camera;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    boolean preview = false;
    private Button btnTakePicture;
    private TextView txtFaceCount;
    private ImageView previewPhoto;
    private Button confirm;
    private Button recapture;
    private byte[] photoBuffer;
    private View root;
    private ToggleButton cameraSelect;
    private int currentCameraId;
    private FaceListener faceListener;

    //keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    //keep track of cropping intent
    final int CAMERA_PERMITION = 2;
    //captured picture uri
    private Uri picUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faceListener = new FaceListener();


        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},CAMERA_PERMITION
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.activity_photo, container,
                false);

        getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);

        root = layoutView;

        cameraSelect = (ToggleButton) layoutView.findViewById(R.id.cameraSelect);
        cameraSelect.setChecked(true);
        cameraSelect.setVisibility(View.GONE);
        cameraSelect.setOnCheckedChangeListener(this);

        surfaceView = (SurfaceView)root.findViewById(R.id.camPreview);
        surfaceView.setVisibility(View.GONE);

        txtFaceCount = (TextView)layoutView.findViewById(R.id.tvFaceCount);
        txtFaceCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFaceCount.setText("");
                txtFaceCount.setOnClickListener(null);
                startCamera();
            }
        });

        previewPhoto = (ImageView) layoutView.findViewById(R.id.previewPhoto);
        //previewPhoto.setVisibility(View.GONE);

        btnTakePicture = (Button)layoutView.findViewById(R.id.takepicture);
        recapture = (Button)layoutView.findViewById(R.id.recapture);
        confirm = (Button)layoutView.findViewById(R.id.confirm);
        confirm.setVisibility(View.GONE);
        recapture.setVisibility(View.GONE);
        btnTakePicture.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                txtFaceCount.setText("");
                cameraSelect.setVisibility(View.GONE);
                /*
                camera.takePicture(mShutterCallback,
                        mRawPictureCallback, mJPGPictureCallback);*/



            }
        });

        recapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoBuffer = null;
                recapture.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                //cameraSelect.setVisibility(View.VISIBLE);
                previewPhoto.setImageBitmap(null);
                previewPhoto.setVisibility(View.GONE);
                /*
                surfaceView.setVisibility(View.VISIBLE);
                surfaceHolder = surfaceView.getHolder();
                surfaceHolder.addCallback(TakePhoto.this);
                //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);*/
                startCamera();

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Alerta", "¿Confirmas el uso de esta foto para el proceso de enronalimento?", "Confirmar", "Cancelar", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        confirm.setVisibility(View.GONE);
                        btnTakePicture.setVisibility(View.GONE);
                        recapture.setVisibility(View.GONE);
                        cameraSelect.setVisibility(View.GONE);
                        MorphoFormApp.getSingleton().getCustomer().photoBuffer = photoBuffer;
                    }

                    @Override
                    public void cancelButtonAction() {
                        recapture.setVisibility(View.VISIBLE);
                    }
                });

            }
        });


        return layoutView;
    }

    @SuppressWarnings("deprecation")
    private void startCamera(){

        //use standard intent to capture an image
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //we will handle the returned data in onActivityResult
        startActivityForResult(captureIntent, CAMERA_CAPTURE);

        /*
        surfaceView = (SurfaceView)root.findViewById(R.id.camPreview);
        surfaceView.setVisibility(View.VISIBLE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(TakePhoto.this);
        //surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);*/
    }

    /**
     * Handle user returning from both capturing and cropping the image
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            //user is returning from capturing an image using the camera
            if(requestCode == CAMERA_CAPTURE){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                photoBuffer = BitmapHelper.bitmapToByteArray(photo);
                previewPhoto.setImageBitmap(photo);
                previewPhoto.setVisibility(View.VISIBLE);
                recapture.setVisibility(View.VISIBLE);
                confirm.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMITION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private class FaceListener implements Camera.FaceDetectionListener{

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            Log.d(TakePhoto.class.getName(),"CARAS: "+faces.length);
            if (faces.length == 0){
                btnTakePicture.setVisibility(View.GONE);
                //Toast.makeText(getActivity().getApplicationContext(), "Cara no detectada..!", Toast.LENGTH_SHORT).show();
                txtFaceCount.setText("Cara NO detectada!");
            }else if(faces.length > 1){
                btnTakePicture.setVisibility(View.GONE);
                txtFaceCount.setText("Sólo se permite tomar foto de una sóla cara, caras detectadas:" + " " +String.valueOf(faces.length));
            }else if(faces.length == 1){
                txtFaceCount.setText("Cara detectada!");
                btnTakePicture.setVisibility(View.VISIBLE);
            }
        }
    }

    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
        @Override
        public void onShutter() {

        }};

    Camera.PictureCallback mRawPictureCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera arg1) {

            Bitmap bmp;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            if(bmp != null){

                bmp = BitmapHelper.RotateBitmap(bmp,180);

                photoBuffer = BitmapHelper.bitmapToByteArray(bmp);
                previewPhoto.setVisibility(View.VISIBLE);
                previewPhoto.setImageBitmap(bmp);
                confirm.setVisibility(View.VISIBLE);
            }else{
                txtFaceCount.setText("Error al momento de tomar foto");
            }

            btnTakePicture.setVisibility(View.GONE);
            recapture.setVisibility(View.VISIBLE);
            surfaceView.setVisibility(View.GONE);

        }};

    Camera.PictureCallback mJPGPictureCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera arg1) {

        }};

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
        // TODO Auto-generated method stub
        if(preview){
            camera.stopFaceDetection();
            camera.stopPreview();
            preview = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.setFaceDetectionListener(faceListener);
                camera.startPreview();
                camera.startFaceDetection();
                preview = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (Camera.getNumberOfCameras() >= 2) {

            currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            //if you want to open front facing camera use this line
            camera = Camera.open(currentCameraId);

            //if you want to use the back facing camera
            //camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);

            android.hardware.Camera.CameraInfo info =
                    new android.hardware.Camera.CameraInfo();


            int result;
            result = (info.orientation + 180) % 360;
            result = (360 - result) % 360;  // compensate the mirror
            camera.setDisplayOrientation(result);
        }
        camera.setFaceDetectionListener(faceListener);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        camera.stopFaceDetection();
        camera.stopPreview();
        camera.release();
        camera = null;
        preview = false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        startCamera();
        if(camera != null){
            if (preview) {
                camera.stopPreview();
            }
            //NB: if you don't release the current camera before switching, you app will crash
            camera.release();

            //swap the id of the camera to be used
            if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
                currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
            } else {
                currentCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
            }
            camera = Camera.open(currentCameraId);
            if(currentCameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
                camera.setFaceDetectionListener(faceListener);
            } else {
                camera.setFaceDetectionListener(faceListener);
            }

            //Code snippet for this method from somewhere on android developers, i forget where
            try {
                //this step is critical or preview on new camera will no know where to render to
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            camera.startPreview();
        }
    }
}
