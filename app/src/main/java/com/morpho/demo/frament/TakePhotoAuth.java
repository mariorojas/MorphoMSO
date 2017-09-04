package com.morpho.demo.frament;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.morpho.demo.R;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;

import java.io.IOException;

/**
 * Created by Alejandro Ruiz on 09/10/2015.
 */
@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class TakePhotoAuth extends ParentFragment implements SurfaceHolder.Callback, CompoundButton.OnCheckedChangeListener{

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
    private FaceListener faceListener2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faceListener = new FaceListener();
        faceListener2 = new FaceListener();
    }

    /** Called when the activity is first created. */
    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.auth_photo, container,
                false);

        getActivity().getWindow().setFormat(PixelFormat.UNKNOWN);

        root = layoutView;

        cameraSelect = (ToggleButton) layoutView.findViewById(R.id.cameraSelect);
        cameraSelect.setChecked(true);
        cameraSelect.setVisibility(View.GONE);
        //cameraSelect.setOnCheckedChangeListener(this);

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
        previewPhoto.setVisibility(View.GONE);

        btnTakePicture = (Button)layoutView.findViewById(R.id.takepicture);
        recapture = (Button)layoutView.findViewById(R.id.recapture);
        confirm = (Button)layoutView.findViewById(R.id.confirm);
        confirm.setVisibility(View.GONE);
        recapture.setVisibility(View.GONE);
        btnTakePicture.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                txtFaceCount.setText("");
                cameraSelect.setVisibility(View.GONE);
                camera.takePicture(mShutterCallback,
                        mRawPictureCallback, mJPGPictureCallback);
            }
        });

        recapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoBuffer = null;
                recapture.setVisibility(View.GONE);
                confirm.setVisibility(View.GONE);
                cameraSelect.setVisibility(View.VISIBLE);
                previewPhoto.setImageBitmap(null);
                previewPhoto.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
                surfaceHolder = surfaceView.getHolder();
                surfaceHolder.addCallback(TakePhotoAuth.this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
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
        surfaceView = (SurfaceView)root.findViewById(R.id.camPreview);
        surfaceView.setVisibility(View.VISIBLE);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(TakePhotoAuth.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /*
    @SuppressLint("NewApi")
    Camera.FaceDetectionListener faceDetectionListener
            = new Camera.FaceDetectionListener(){

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {



        }};*/

    private class FaceListener implements Camera.FaceDetectionListener{

        @Override
        public void onFaceDetection(Camera.Face[] faces, Camera camera) {
            Log.d(TakePhotoAuth.class.getName(),"CARAS: "+faces.length);
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
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback mRawPictureCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub

        }};

    Camera.PictureCallback mJPGPictureCallback = new Camera.PictureCallback(){

        @Override
        public void onPictureTaken(byte[] data, Camera arg1) {
            // TODO Auto-generated method stub
            Bitmap bmp;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            if(bmp != null){
                photoBuffer = data;
                previewPhoto.setVisibility(View.VISIBLE);
                previewPhoto.setImageBitmap(bmp);
                confirm.setVisibility(View.VISIBLE);
            }else{
                txtFaceCount.setText("Error al momento de tomar foto");
            }

            btnTakePicture.setVisibility(View.GONE);
            recapture.setVisibility(View.VISIBLE);
            /*
            try {

                int imageNum = 0;

                Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(), "FaceDetection");
                if (!imagesFolder.exists()) {
                    imagesFolder.mkdirs();
                }

                String fileName = "image_" + String.valueOf(imageNum) + ".jpg";
                File output = new File(imagesFolder, fileName);

                while (output.exists()){
                    imageNum++;
                    fileName = "image_" + String.valueOf(imageNum) + ".jpg";
                    output = new File(imagesFolder, fileName);
                }

                Uri uriSavedImage = Uri.fromFile(output);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                OutputStream imageFileOS;

                imageFileOS = getActivity().getContentResolver().openOutputStream(uriSavedImage);
                imageFileOS.write(arg0);
                imageFileOS.flush();
                imageFileOS.close();

                txtImagePath.setText("Image saved to:" + " " +uriSavedImage.toString());

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

            //camera.startPreview();
            //camera.startFaceDetection();
            surfaceView.setVisibility(View.GONE);
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
                camera.startPreview();
                camera.startFaceDetection();
                preview = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
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
                camera.setFaceDetectionListener(faceListener2);
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
