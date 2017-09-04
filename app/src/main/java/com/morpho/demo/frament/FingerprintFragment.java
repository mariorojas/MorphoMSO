package com.morpho.demo.frament;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morpho.demo.ParentActivity;
import com.morpho.demo.R;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.FingerIndicator;
import com.morpho.demo.mso.MSOConnection;
import com.morpho.demo.mso.MSOShower;


/**
 * Modificada por Mario Rojas el 27/04/2017.
 */
public class FingerprintFragment extends ParentFragment implements View.OnClickListener, MSOShower{

    private ImageView imgFP;
    private TextView txtMensaje;
    private TextView txtResultado;
    private byte[] littleLeftBuff = null;
    private byte[] ringLeftBuff = null;
    private byte[] middletLeftBuff = null;
    private byte[] indexLeftBuff = null;
    private byte[] thumbLeftBuff = null;
    private byte[] thumbRightBuff = null;
    private byte[] indexRightBuff = null;
    private byte[] middletRightBuff = null;
    private byte[] ringRightBuff = null;
    private byte[] littleRightBuff = null;

    private static int STATE;
    private static final int LITTLE_LEFT = 10;
    private static final int RING_LEFT = 20;
    private static final int MIDDLET_LEFT = 30;
    private static final int INDEX_LEFT = 40;
    private static final int THUMB_LEFT = 50;
    private static final int THUMB_RIGHT = 60;
    private static final int INDEX_RIGHT = 70;
    private static final int MIDDLET_RIGHT = 80;
    private static final int RING_RIGHT = 90;
    private static final int LITTLE_RIGHT = 100;

    private ProgressBar pg;
    private View root;
    private boolean isWorking = false;
    private Button enrolButton;
    private TextView sectionTitle;

    ///Fingers indicators
    private FingerIndicator littleLeft;
    private FingerIndicator ringLeft;
    private FingerIndicator middletLeft;
    private FingerIndicator indexLeft;
    private FingerIndicator thumbLeft;
    private FingerIndicator thumbRight;
    private FingerIndicator indexRight;
    private FingerIndicator middletRight;
    private FingerIndicator ringRight;
    private FingerIndicator littleRight;

    private LinearLayout fingerCont;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.finger_framgment_layout, container,
                false);

        root = layoutView;

        initUI(layoutView);
        initFingers(layoutView);

        MSOConnection.getInstance().connectMSO((ParentActivity) getActivity());

        MSOConnection.getInstance().setMsoShower(this);


        return layoutView;
    }

    private void initUI(View root){

        sectionTitle = (TextView) root.findViewById(R.id.sectionTitle);

        enrolButton = (Button) root.findViewById(R.id.enrolButton);
        enrolButton.setVisibility(View.GONE);
        enrolButton.setOnClickListener(new EnrollListener());

        txtMensaje = (TextView) root.findViewById(R.id.txtMensaje);
        txtResultado = (TextView) root.findViewById(R.id.txtResultado);

        pg = (ProgressBar)root.findViewById(R.id.progressBar1);

        imgFP = (ImageView) root.findViewById(R.id.imgFP);

        fingerCont = (LinearLayout) root.findViewById(R.id.fingerCont);

    }

    private void initFingers(View root){
        littleLeft = (FingerIndicator) root.findViewById(R.id.littleLeft);
        littleLeft.setOnClickListener(this);

        ringLeft = (FingerIndicator) root.findViewById(R.id.ringLeft);
        ringLeft.setOnClickListener(this);

        middletLeft = (FingerIndicator) root.findViewById(R.id.middletLeft);
        middletLeft.setOnClickListener(this);

        indexLeft = (FingerIndicator) root.findViewById(R.id.indexLeft);
        indexLeft.setOnClickListener(this);

        thumbLeft = (FingerIndicator) root.findViewById(R.id.thumbLeft);
        thumbLeft.setOnClickListener(this);

        thumbRight = (FingerIndicator) root.findViewById(R.id.thumbRight);
        thumbRight.setOnClickListener(this);

        indexRight = (FingerIndicator) root.findViewById(R.id.indexRight);
        indexRight.setOnClickListener(this);

        middletRight = (FingerIndicator) root.findViewById(R.id.middletRight);
        middletRight.setOnClickListener(this);

        ringRight = (FingerIndicator) root.findViewById(R.id.ringRight);
        ringRight.setOnClickListener(this);

        littleRight = (FingerIndicator) root.findViewById(R.id.littleRight);
        littleRight.setOnClickListener(this);

        // Creado por Mario Rojas, evaluación de huella existente
        if (MorphoFormApp.getSingleton().getCustomer().indexLeftBuff != null) {
            indexLeft.setVisibility(View.GONE);
            indexLeftBuff = MorphoFormApp.getSingleton().getCustomer().indexLeftBuff;
        }

        if (MorphoFormApp.getSingleton().getCustomer().indexRightBuff != null) {
            indexRight.setVisibility(View.GONE);
            indexRightBuff = MorphoFormApp.getSingleton().getCustomer().indexLeftBuff;
        }
    }


    private void ResetCapture(boolean deleteImageFingerPosition) {

        imgFP.setImageBitmap(null);

        ((ProgressBar) root.findViewById(R.id.vertical_progressbar)).setProgress(0);
        txtResultado.setText("");
        txtMensaje.setText("");

    }



    public void setButtonEnabled(boolean enabled){

        isWorking =!enabled;
    }

    @Override
    public void onClick(View v) {

        if (isWorking) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Por favor espera a que el proceso termine.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (v.equals(littleLeft)) {
            STATE = LITTLE_LEFT;
            littleLeft.initAdquisition();
            setButtonEnabled(false);
            littleLeftBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(ringLeft)) {
            STATE = RING_LEFT;
            ringLeft.initAdquisition();
            setButtonEnabled(false);
            ringLeftBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(middletLeft)) {
            STATE = MIDDLET_LEFT;
            middletLeft.initAdquisition();
            setButtonEnabled(false);
            middletLeftBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(indexLeft)) {
            STATE = INDEX_LEFT;
            indexLeft.initAdquisition();
            setButtonEnabled(false);
            indexLeftBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(thumbLeft)) {
            STATE = THUMB_LEFT;
            thumbLeft.initAdquisition();
            setButtonEnabled(false);
            thumbLeftBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(littleRight)) {
            STATE = LITTLE_RIGHT;
            littleRight.initAdquisition();
            setButtonEnabled(false);
            littleRightBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(ringRight)) {
            STATE = RING_RIGHT;
            ringRight.initAdquisition();
            setButtonEnabled(false);
            ringRightBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(middletRight)) {
            STATE = MIDDLET_RIGHT;
            middletRight.initAdquisition();
            setButtonEnabled(false);
            middletRightBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(indexRight)) {
            STATE = INDEX_RIGHT;
            indexRight.initAdquisition();
            setButtonEnabled(false);
            indexRightBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        } else if (v.equals(thumbRight)) {
            STATE = THUMB_RIGHT;
            thumbRight.initAdquisition();
            setButtonEnabled(false);
            thumbRightBuff = null;
            imgFP.setImageDrawable(null);
            ResetCapture(false);
            MSOConnection.getInstance().Capture();

        }
    }

    public void updateImageView(final byte[] imgeSrc,
                                  final int captureError) {

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (captureError == com.morpho.morphosmart.sdk.ErrorCodes.MORPHOERR_TIMEOUT) {
                    setButtonEnabled(true);
                    switch (STATE){
                        case LITTLE_LEFT:
                            littleLeft.stopAdquisition();
                            littleLeft.setStatus(false);
                            break;
                        case RING_LEFT:
                            ringLeft.stopAdquisition();
                            ringLeft.setStatus(false);
                            break;
                        case MIDDLET_LEFT:
                            middletLeft.stopAdquisition();
                            middletLeft.setStatus(false);
                            break;
                        case INDEX_LEFT:
                            indexLeft.stopAdquisition();
                            indexLeft.setStatus(false);
                            break;
                        case THUMB_LEFT:
                            thumbLeft.stopAdquisition();
                            thumbLeft.setStatus(false);
                            break;
                        case LITTLE_RIGHT:
                            littleRight.stopAdquisition();
                            littleRight.setStatus(false);
                            break;
                        case RING_RIGHT:
                            ringRight.stopAdquisition();
                            ringRight.setStatus(false);
                            break;
                        case MIDDLET_RIGHT:
                            middletRight.stopAdquisition();
                            middletRight.setStatus(false);
                            break;
                        case INDEX_RIGHT:
                            indexRight.stopAdquisition();
                            indexRight.setStatus(false);
                            break;
                        case THUMB_RIGHT:
                            thumbRight.stopAdquisition();
                            thumbRight.setStatus(false);
                            break;
                    }

                    Toast.makeText(getActivity().getApplicationContext(), "Capture Timeout", Toast.LENGTH_SHORT).show();
                    return;

                } else if (captureError == com.morpho.morphosmart.sdk.ErrorCodes.MORPHOERR_CMDE_ABORTED) {
                    return;
                }

                if (captureError == com.morpho.morphosmart.sdk.ErrorCodes.MORPHO_OK) {
                    setButtonEnabled(true);

                    switch (STATE){
                        case LITTLE_LEFT:
                            littleLeftBuff = imgeSrc;
                            littleLeft.stopAdquisition();

                            break;
                        case RING_LEFT:
                            ringLeftBuff = imgeSrc;
                            ringLeft.stopAdquisition();

                            break;
                        case MIDDLET_LEFT:
                            middletLeftBuff = imgeSrc;
                            middletLeft.stopAdquisition();

                            break;
                        case INDEX_LEFT:
                            indexLeftBuff = imgeSrc;
                            indexLeft.stopAdquisition();

                            break;
                        case THUMB_LEFT:
                            thumbLeftBuff =imgeSrc;
                            thumbLeft.stopAdquisition();

                            break;
                        case LITTLE_RIGHT:
                            littleRightBuff = imgeSrc;
                            littleRight.stopAdquisition();

                            break;
                        case RING_RIGHT:
                            ringRightBuff = imgeSrc;
                            ringRight.stopAdquisition();

                            break;
                        case MIDDLET_RIGHT:
                            middletRightBuff = imgeSrc;
                            middletRight.stopAdquisition();

                            break;
                        case INDEX_RIGHT:
                            indexRightBuff = imgeSrc;
                            indexRight.stopAdquisition();

                            break;
                        case THUMB_RIGHT:
                            thumbRightBuff = imgeSrc;
                            thumbRight.stopAdquisition();

                            break;
                    }
                    enableEnroll();
                }

            }
        });

    }

    private void enableEnroll(){
        if(enrolButton != null) {
            if (littleLeftBuff != null || littleRightBuff != null
                    || ringLeftBuff != null || ringRightBuff != null
                    || middletLeftBuff != null || middletRightBuff != null
                    || indexLeftBuff != null || indexRightBuff != null
                    || thumbLeftBuff != null || thumbRightBuff != null) {
                enrolButton.setVisibility(View.VISIBLE);
            }else {
                enrolButton.setVisibility(View.GONE);
            }
        }
    }





    private class EnrollListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            ResetCapture(true);
            showAlert("Alerta", "Si terminas la captura ya no se permitirá modificar o agregar huellas. ¿Deseas continuar?"
                    , "Continuar", "Cancelar", new DialogUses() {
                @Override
                public void acceptButtonAction() {
                    blockEnroll();
                }

                @Override
                public void cancelButtonAction() {
                }
            });
        }
    }

    private void blockEnroll(){
        enrolButton.setVisibility(View.GONE);

        fingerCont.setVisibility(View.GONE);

        sectionTitle.setText("CAPTURA DE HUELLAS CONFIRMADA");
        sectionTitle.setTextColor(getResources().getColor(R.color.white));
        sectionTitle.setBackground(getResources().getDrawable(R.drawable.background_bar));

        thumbRight.setOnClickListener(null);
        thumbLeft.setOnClickListener(null);
        littleLeft.setOnClickListener(null);
        littleRight.setOnClickListener(null);
        ringLeft.setOnClickListener(null);
        ringRight.setOnClickListener(null);
        middletLeft.setOnClickListener(null);
        middletRight.setOnClickListener(null);
        indexLeft.setOnClickListener(null);
        indexRight.setOnClickListener(null);

        MorphoFormApp.getSingleton().getCustomer().littleLeftBuff = littleLeftBuff;
        MorphoFormApp.getSingleton().getCustomer().littleRightBuff = littleRightBuff;
        MorphoFormApp.getSingleton().getCustomer().ringLeftBuff = ringLeftBuff;
        MorphoFormApp.getSingleton().getCustomer().ringRightBuff = ringRightBuff;
        MorphoFormApp.getSingleton().getCustomer().middletLeftBuff = middletLeftBuff;
        MorphoFormApp.getSingleton().getCustomer().middletRightBuff = middletRightBuff;
        MorphoFormApp.getSingleton().getCustomer().indexLeftBuff = indexLeftBuff;
        MorphoFormApp.getSingleton().getCustomer().indexRightBuff = indexRightBuff;
        MorphoFormApp.getSingleton().getCustomer().thumbLeftBuff = thumbLeftBuff;
        MorphoFormApp.getSingleton().getCustomer().thumbRightBuff = thumbRightBuff;
    }


    @Override
    public void updateSensorProgressBar(int level)
    {
        try
        {
            ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.vertical_progressbar);

            final float[] roundedCorners = new float[] { 1, 1, 1, 1, 1, 1, 1, 1 };
            ShapeDrawable pgDrawable = new ShapeDrawable(new RoundRectShape(roundedCorners, null, null));

            int color = Color.GREEN;

            if (level <= 25)
            {
                color = Color.RED;
            }
            else if (level <= 50)
            {
                color = Color.YELLOW;
            }
            pgDrawable.getPaint().setColor(color);
            ClipDrawable progress = new ClipDrawable(pgDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
            progressBar.setProgressDrawable(progress);
            //progressBar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.progress_horizontal));
            progressBar.setProgress(level);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void updateSensorMessage(String sensorMessage)
    {
        try
        {
            txtMensaje.setText(sensorMessage);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void updateImage(Bitmap bitmap)
    {
        try
        {
            imgFP.setImageBitmap(bitmap);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public void showAlert(String message) {
        showAlert("Error:",message);
    }


}
