package com.morpho.demo.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.morpho.demo.R;
import com.morpho.demo.constant.DelegateValidation;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CustomEditText;
import com.morpho.demo.helper.CharListener;
import com.morpho.demo.tools.ActiviTyKeyboardBehavior;

/**
 * Modificada por Mario Rojas el 27/04/2017.
 */
public class INEPersonalDataFragment extends Fragment {


    private CustomEditText name;
    private CustomEditText lastname;
    private CustomEditText apeMat;
    private CustomEditText sexo;
    private CustomEditText telefono;
    //private Spinner sexo;

    //private final static String SEXO_MASC = "H";
    //private final static String SEXO_FEM = "M";

    public INEPersonalDataFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.ine_personal_data_fragment_layout, container,
                false);

        ActiviTyKeyboardBehavior.setupUI(layoutView
                .findViewById(R.id.content), getActivity());

        name = (CustomEditText) layoutView.findViewById(R.id.name);
        lastname = (CustomEditText) layoutView.findViewById(R.id.lastname);
        apeMat = (CustomEditText) layoutView.findViewById(R.id.apeMat);
        sexo = (CustomEditText) layoutView.findViewById(R.id.sexo);
        telefono = (CustomEditText) layoutView.findViewById(R.id.telefono);

        /*
        sexo = (Spinner) layoutView.findViewById(R.id.sexo);
        ArrayAdapter<CharSequence> sexoAdapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.INESexo, android.R.layout.simple_spinner_dropdown_item);
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(sexoAdapter);
        sexo.setOnItemSelectedListener(new SexoListener());
        */

        name.addTextChangedListener(new CharListener(name ,getResources(),new NameDelegate(), 3));
        lastname.addTextChangedListener(new CharListener(lastname ,getResources(),new LastNameDelegate(), 3));
        apeMat.addTextChangedListener(new CharListener(apeMat ,getResources(),new ApeMatDelegate(), 3));
        sexo.addTextChangedListener(new CharListener(sexo, getResources(), new SexoDelegate(), 1));
        //telefono.addTextChangedListener(new CharListener(telefono, getResources(), new TelefonoDelegate(), 10));

        name.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        lastname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        apeMat.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        sexo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        telefono.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        Log.d(this.getClass().getName(), "Verifying properties");
        Log.d(this.getClass().getName(), "apeMat: " + MorphoFormApp.getSingleton().getCustomer().apeMat);
        verifyProperty(name, MorphoFormApp.getSingleton().getCustomer().Name);
        verifyProperty(lastname, MorphoFormApp.getSingleton().getCustomer().Surname);
        verifyProperty(apeMat, MorphoFormApp.getSingleton().getCustomer().apeMat);
        verifyProperty(sexo, MorphoFormApp.getSingleton().getCustomer().sexo);
        //verifyProperty(telefono, MorphoFormApp.getSingleton().getCustomer().telefono);

        return layoutView;
    }

    private class NameDelegate implements DelegateValidation{

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().Name = name.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class LastNameDelegate implements DelegateValidation{

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().Surname = lastname.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class ApeMatDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().apeMat = apeMat.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    /*private class TelefonoDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().telefono = telefono.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }*/

    private class SexoDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().sexo = sexo.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    /*private class SexoListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position != 0) {
                switch (parent.getItemAtPosition(position).toString().toUpperCase()) {
                    case SEXO_MASC:
                        MorphoFormApp.getSingleton().getCustomer().sexo = SEXO_MASC;
                        break;
                    case SEXO_FEM:
                        MorphoFormApp.getSingleton().getCustomer().sexo = SEXO_FEM;
                        break;
                }
                MorphoFormApp.getSingleton().getCustomer().Genre = position;
                sexo.setBackground(getResources().getDrawable(R.drawable.rounded_curner_good_question));
            } else {
                MorphoFormApp.getSingleton().getCustomer().sexo = null;
                MorphoFormApp.getSingleton().getCustomer().Genre = 0;
                sexo.setBackground(getResources().getDrawable(R.drawable.rounded_curner_question));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }*/

    /**
     * Creado por Mario Rojas
     *
     * Verifica si una propiedad ya existe previamente
     * para poder colocarla en el formulario
     */
    private void verifyProperty(CustomEditText field, String property) {
        if ( property!=null && !property.isEmpty()) {
            Log.d(this.getClass().getName(), field.getId() + ": " + property);
            field.setText(property);
        }
    }

}
