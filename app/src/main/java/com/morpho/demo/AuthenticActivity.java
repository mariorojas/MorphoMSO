package com.morpho.demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.constant.ChooseDelegate;
import com.morpho.demo.constant.ChooserDelegate;
import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.frament.AudioAuthFragment;
import com.morpho.demo.frament.ChooseFragment;
import com.morpho.demo.frament.FingerprintAuthFragment;
import com.morpho.demo.frament.TakePhotoAuth;
import com.morpho.demo.helper.SearchID;
import com.morpho.demo.helper.StartAuth;
import com.morpho.demo.tools.Base64;
import com.morpho.demo.tools.NumericTool;
import com.morpho.wsdl.wsclient.ConsumeResponse;

import org.json.JSONException;
import org.json.JSONObject;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class AuthenticActivity extends ParentActivity implements ChooserDelegate,ConsumeResponse {

    private CloudCreate cloudCreate;
    private RelativeLayout square;
    private ImageButton saveButton;
    private Button next;
    private Button back;
    private Button checkID;
    private TextView sectionTitle;
    private EditText clientID;
    private FrameLayout itemContent;
    private LinearLayout idContent;
    private RelativeLayout resultados;
    private TextView textResultados;
    private ImageView customerIMG;

    private ChooseFragment chooseFragment;
    private AudioAuthFragment audioSectionFragment;
    private FingerprintAuthFragment fingerprintFragment;
    private TakePhotoAuth takePhoto;
    private ChooseDelegate chooseDelegate;

    private LinearLayout rejected;
    private TextView rejectedTxt;
    private Button pass;

    private int STATE;

    private static final int GENERAL_DATA = 10;
    private static final int FINGERPRINT_DATA = 20;
    private static final int PHOTO_DATA = 30;
    private static final int VOICE_DATA = 40;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint({"NewApi", "MissingSuperCall"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_authenctic);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initUI();
        setCloudCreate();
        setFirstItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (clientID != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(clientID.getWindowToken(), 0);
        }
    }

    private void setFirstItem(){

        itemContent = (FrameLayout) findViewById(R.id.itemContent);

        chooseFragment = ChooseFragment.newInstance(this);

        audioSectionFragment = AudioAuthFragment
                .newInstance();
        fingerprintFragment = new FingerprintAuthFragment();

        takePhoto = new TakePhotoAuth();

        chooseDelegate = chooseFragment;

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.itemContent, chooseFragment);
        fragmentTransaction.add(R.id.itemContent, audioSectionFragment);
        fragmentTransaction.add(R.id.itemContent, fingerprintFragment);
        fragmentTransaction.add(R.id.itemContent, takePhoto);

        fragmentTransaction.hide(chooseFragment);
        fragmentTransaction.hide(audioSectionFragment);
        fragmentTransaction.hide(fingerprintFragment);
        fragmentTransaction.hide(takePhoto);

        fragmentTransaction.commit();

        STATE = GENERAL_DATA;

        itemContent.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
    }

    private void initUI(){

        resultados = (RelativeLayout) findViewById(R.id.resultados);
        textResultados = (TextView) findViewById(R.id.textResultados);
        customerIMG = (ImageView) findViewById(R.id.customerIMG);

        rejected = (LinearLayout) findViewById(R.id.rejected);
        rejectedTxt = (TextView) findViewById(R.id.rejectedTxt);
        sectionTitle = (TextView) findViewById(R.id.sectionTitle);
        pass = (Button) findViewById(R.id.pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        idContent = (LinearLayout) findViewById(R.id.idContent);
        clientID = (EditText) findViewById(R.id.clientID);
        checkID = (Button) findViewById(R.id.checkID);
        checkID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = clientID.getText().toString();
                if(!NumericTool.isStringNunnable(id)){
                    showProgress(true);
                    new SearchID(id,AuthenticActivity.this,AuthenticActivity.this).execute();
                }else {
                    showAlert("Error","El id de cliente no puede ser vacío.");
                }
            }
        });


        saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(this.getClass().getName(), "Comienza el proceso para autenticar");
                int type = MorphoFormApp.getSingleton().getCustomer().isEmptyForAuth();
                if(type == 0){
                    showProgress(true);
                    //new SaveTask().execute();
                    new StartAuth(AuthenticActivity.this,new CustomerDelegate()).execute();
                }else{
                    switch (type){
                        case 1:
                            showAlert("Alerta","El proceso de autenticación no ha terminado, faltan valores alfanuméricos.");
                            break;
                        case 2:
                            showAlert("Alerta","El proceso de autenticación no puede comenzar, falta confirmar las huellas digitales o tomar las huellas.");
                            break;
                        case 3:
                            showAlert("Alerta","El proceso de autenticación no puede comenzar, falta tomar la foto o confirmarla.");
                            break;
                        case 4:
                            showAlert("Alerta","El proceso de autenticación no pude comenzar, faltan grabar el audio.");
                            break;

                    }

                }

            }
        });

        next = (Button) findViewById(R.id.next);
        back = (Button) findViewById(R.id.back);

        next.setText(">");
        back.setText("<");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE) {
                    case GENERAL_DATA:
                        if (MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        else if (MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if (MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;
                        stateChange();
                        break;
                    case FINGERPRINT_DATA:
                        if (MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if (MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        if (MorphoFormApp.getSingleton().isHasVoice())
                            STATE = VOICE_DATA;
                        stateChange();
                        break;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE) {
                    case VOICE_DATA:
                        if (MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        else if (MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        if (MorphoFormApp.getSingleton().isHasFingerprint())
                            STATE = FINGERPRINT_DATA;
                        stateChange();
                        break;
                    case FINGERPRINT_DATA:
                        //STATE = GENERAL_DATA;
                        //stateChange();
                        break;
                }
            }
        });
    }

    private void setCloudCreate(){
        square = (RelativeLayout) findViewById(R.id.square);
        cloudCreate = new CloudCreate(this,square);
        cloudCreate.setClouds(true);
        cloudCreate.setClouds(false);
    }

    private void stateChange(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (STATE){
            case GENERAL_DATA:
                itemContent.setVisibility(View.VISIBLE);
                idContent.setVisibility(View.GONE);
                fragmentTransaction.show(chooseFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);

                break;
            case VOICE_DATA:
                itemContent.setVisibility(View.VISIBLE);
                idContent.setVisibility(View.GONE);
                fragmentTransaction.show(audioSectionFragment);
                fragmentTransaction.hide(chooseFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                break;
            case FINGERPRINT_DATA:
                itemContent.setVisibility(View.VISIBLE);
                idContent.setVisibility(View.GONE);
                fragmentTransaction.hide(chooseFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.show(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                if (MorphoFormApp.getSingleton().isHasFacial() || MorphoFormApp.getSingleton().isHasVoice())
                    next.setVisibility(View.VISIBLE);
                else
                    next.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                break;
            case PHOTO_DATA:
                itemContent.setVisibility(View.VISIBLE);
                idContent.setVisibility(View.GONE);
                fragmentTransaction.hide(chooseFragment);
                fragmentTransaction.hide(audioSectionFragment);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.show(takePhoto);
                if (MorphoFormApp.getSingleton().isHasVoice())
                    next.setVisibility(View.VISIBLE);
                else
                    next.setVisibility(View.GONE);
                back.setVisibility(View.VISIBLE);
                break;
        }

        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        setResult(Constants.RESULT_FINISH);
        finish();
    }

    @Override
    public void chooseOption() {

        itemContent.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);

        next.performClick();
    }

    @Override
    public void consumeResponde(final int statusCode, String response) {
        showProgress(false);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(statusCode == 200){
                    sectionTitle.setText("Autenticación para el\ncliente con ID: "+
                            MorphoFormApp.getSingleton().getCustomer().ID);
                    chooseDelegate.showOptions();
                    STATE = GENERAL_DATA;
                    stateChange();
                }else{
                    rejectedTxt.setText("No se encuentra enrolado\nel cliente con ID: " +
                            MorphoFormApp.getSingleton().getCustomer().ID);
                    MorphoFormApp.getSingleton().getCustomer().ID = null;
                    idContent.setVisibility(View.GONE);
                    rejected.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public void resetAudio(){
        audioSectionFragment.resetAudio();
    }

    public class CustomerDelegate implements ConsumeResponse{

        @Override
        public void consumeResponde(int statusCode, final String response) {
            showProgress(false);
            if(statusCode == 200){

                AuthenticActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Customer customer = null;

                        try {
                            Log.d(AuthenticActivity.class.getName(), "Response StoreCustomer: " + response);

                            JSONObject cs = new JSONObject(response);

                            String id = cs.getString(Constants.CUSTOMER_ID);
                            String name = cs.getString(Constants.CUSTOMER_NAME);
                            String lastname = cs.getString(Constants.CUSTOMER_SURNAME);
                            String date = cs.optString(Constants.CUSTOMER_BIRTHDAY);
                            int genre = cs.optInt(Constants.CUSTOMER_GENRE);
                            long balance = cs.getLong(Constants.CUSTOMER_BALANCE);
                            String img = cs.optString(Constants.CUSTOMER_IMG, null);


                            customer = new Customer();

                            customer.ID = id;
                            customer.Name = name;
                            customer.Surname = lastname;
                            customer.Genre = genre;
                            customer.BirthDay = date;
                            customer.Balance = balance;

                            if(!NumericTool.isStringNunnable(img)){
                                customer.imgSrc = img;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            customer = null;
                        }

                        if(customer != null) {
                            StringBuilder restBuild = new StringBuilder("Información de cliente:\n");
                            resultados.setVisibility(View.VISIBLE);
                            itemContent.setVisibility(View.GONE);
                            next.setVisibility(View.GONE);
                            back.setVisibility(View.GONE);
                            saveButton.setVisibility(View.GONE);

                            restBuild.append("ID: " + customer.ID + "\n");
                            restBuild.append("Nombre: " + customer.Name + "\n");
                            restBuild.append("Apellidos: " + customer.Surname + "\n");
                            if(!NumericTool.isStringNunnable(customer.BirthDay))
                                restBuild.append("Fecha Nacimiento: " + customer.BirthDay + "\n");
                            if(NumericTool.isLong(""+customer.Balance))
                                restBuild.append("Saldo: " + customer.Balance + "\n");
                            //restBuild.append("Genero: " + (customer.Genre == 1 ? "Masculino" : "Femenino") + "\n");

                            if (!NumericTool.isStringNunnable(customer.imgSrc)) {
                                byte[] data = Base64.decode(customer.imgSrc);
                                Bitmap bmp;
                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inMutable = true;
                                bmp = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                                if (bmp != null)
                                    customerIMG.setImageBitmap(bmp);
                            }else{
                                customerIMG.setVisibility(View.GONE);
                            }
                            textResultados.setText(restBuild.toString());
                        }
                    }
                });

            }else{
                showAlert("Error", "Error: " + response);
            }

        }
    }


}
