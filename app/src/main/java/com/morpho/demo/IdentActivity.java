package com.morpho.demo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.Customer;
import com.morpho.demo.constant.CustomersFoundDelegate;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CloudCreate;
import com.morpho.demo.frament.CustomerListFragment;
import com.morpho.demo.frament.FingerprintAuthFragment;
import com.morpho.demo.frament.TakePhotoAuth;
import com.morpho.demo.helper.StartIden;

import java.util.Vector;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class IdentActivity extends ParentActivity  {

    private CloudCreate cloudCreate;
    private RelativeLayout square;
    private ImageButton saveButton;
    private Button next;
    private Button back;
    private TextView sectionTitle;
    private FrameLayout itemContent;
    private RelativeLayout resultados;
    private TextView textResultados;
    private ImageView customerIMG;

    private FingerprintAuthFragment fingerprintFragment;
    private TakePhotoAuth takePhoto;
    private CustomerListFragment customerListFragment;

    private int STATE;

    private static final int LIST_DATA = 50;
    private static final int FINGERPRINT_DATA = 20;
    private static final int PHOTO_DATA = 30;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_ident);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initUI();
        setCloudCreate();
        setFirstItem();
    }


    private void setFirstItem(){

        itemContent = (FrameLayout) findViewById(R.id.itemContent);

        fingerprintFragment = new FingerprintAuthFragment();

        takePhoto = new TakePhotoAuth();

        customerListFragment = new CustomerListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.itemContent, fingerprintFragment);
        fragmentTransaction.add(R.id.itemContent, takePhoto);
        fragmentTransaction.add(R.id.itemContent, customerListFragment);

        fragmentTransaction.hide(fingerprintFragment);
        fragmentTransaction.hide(takePhoto);
        fragmentTransaction.hide(customerListFragment);

        fragmentTransaction.commit();

        if(MorphoFormApp.getSingleton().isHasFingerprint())
            STATE = FINGERPRINT_DATA;
        else if(MorphoFormApp.getSingleton().isHasFacial())
            STATE = PHOTO_DATA;
        stateChange();

    }

    private void initUI(){

        resultados = (RelativeLayout) findViewById(R.id.resultados);
        textResultados = (TextView) findViewById(R.id.textResultados);
        customerIMG = (ImageView) findViewById(R.id.customerIMG);

        sectionTitle = (TextView) findViewById(R.id.sectionTitle);

        saveButton = (ImageButton)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = MorphoFormApp.getSingleton().getCustomer().isEmptyForIdent();
                if(type == 0){
                    showProgress(true);
                    //new SaveTask().execute();
                    MorphoFormApp.getSingleton().getCustomer().ID = ""+ System.currentTimeMillis();
                    new StartIden(IdentActivity.this,new CustomerDelegate()).execute();
                }else{
                    switch (type){
                        case 1:
                            showAlert("Alerta","El proceso de identificación no ha terminado, faltan valores alfanuméricos.");
                            break;
                        case 2:
                            showAlert("Alerta","El proceso de identificación no puede comenzar, falta confirmar las huellas digitales o tomar las huellas.");
                            break;
                        case 3:
                            showAlert("Alerta","El proceso de identificación no puede comenzar, falta tomar la foto o confirmarla.");
                            break;
                        case 4:
                            showAlert("Alerta","El proceso de identificación no pude comenzar, faltan grabar el audio.");
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
                    case FINGERPRINT_DATA:
                        if (MorphoFormApp.getSingleton().isHasFacial())
                            STATE = PHOTO_DATA;
                        stateChange();
                        break;
                    case PHOTO_DATA:
                        stateChange();
                        break;
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (STATE) {
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
            case LIST_DATA:
                itemContent.setVisibility(View.VISIBLE);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                fragmentTransaction.show(customerListFragment);
                back.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                saveButton.setVisibility(View.GONE);
                break;
            case FINGERPRINT_DATA:
                itemContent.setVisibility(View.VISIBLE);
                fragmentTransaction.hide(customerListFragment);
                fragmentTransaction.show(fingerprintFragment);
                fragmentTransaction.hide(takePhoto);
                break;
            case PHOTO_DATA:
                itemContent.setVisibility(View.VISIBLE);
                fragmentTransaction.hide(fingerprintFragment);
                fragmentTransaction.show(takePhoto);
                fragmentTransaction.hide(customerListFragment);
                break;
        }

        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        setResult(Constants.RESULT_FINISH);
        finish();
    }


    public class CustomerDelegate implements CustomersFoundDelegate {


        @Override
        public void showCustomersFound(final Vector<Customer> customers) {
            showProgress(false);
            if(customers != null && !customers.isEmpty()){
                IdentActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        STATE = LIST_DATA;
                        stateChange();
                        customerListFragment.setCustomers(customers);
                    }
                });

            }else{
                showAlert("Alerta", "No se encontró ningún candidato", new DialogUses() {
                    @Override
                    public void acceptButtonAction() {
                        onBackPressed();
                    }

                    @Override
                    public void cancelButtonAction() {

                    }
                });
            }

        }
    }


}
