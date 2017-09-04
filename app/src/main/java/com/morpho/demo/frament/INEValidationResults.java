package com.morpho.demo.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.morpho.demo.INEVerificationActivity;
import com.morpho.demo.R;
import com.morpho.demo.constant.MorphoFormApp;

/**
 * Created by marojas on 24/05/2017.
 */

public class INEValidationResults extends Fragment {

    private TextView lblName;
    private TextView lblFirstSurname;
    private TextView lblSecondSurname;
    private TextView lblOcr;
    private TextView lblScore;
    private TextView lblPorc;
    private Button enroll;

    private String validationScore;
    private String validationPorc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ine_data_validation_results, container, false);

        lblName = (TextView) view.findViewById(R.id.lblValidationName);
        lblFirstSurname = (TextView) view.findViewById(R.id.lblValidationFirstSurname);
        lblSecondSurname = (TextView) view.findViewById(R.id.lblValidationSecondSurname);
        lblOcr = (TextView) view.findViewById(R.id.lblValidationOcr);
        lblScore = (TextView) view.findViewById(R.id.lblValidationScore);
        lblPorc = (TextView) view.findViewById(R.id.lblValidationPorc);

        lblName.setText(lblName.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().Name);
        lblFirstSurname.setText(lblFirstSurname.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().Surname);
        lblSecondSurname.setText(lblSecondSurname.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().apeMat);
        lblOcr.setText(lblOcr.getText().toString() + " " + MorphoFormApp.getSingleton().getCustomer().ID);
        lblScore.setText(lblScore.getText().toString() + " " + validationScore);
        lblPorc.setText(lblPorc.getText().toString() + " " + validationPorc + " %");

        enroll = (Button) view.findViewById(R.id.btnOk);
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((INEVerificationActivity) getActivity()).startEnrollment();
            }
        });

        return view;
    }
}
