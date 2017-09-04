package com.morpho.demo.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morpho.demo.R;
import com.morpho.demo.constant.DelegateValidation;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CustomEditText;
import com.morpho.demo.helper.CharListener;
import com.morpho.demo.tools.ActiviTyKeyboardBehavior;

/**
 * Modificada por Mario Rojas el 27/04/2017.
 */
public class INESecondStepFragment extends Fragment {


    private CustomEditText anioRegistro;
    private CustomEditText claveElector;
    private CustomEditText curp;
    private CustomEditText estado;
    private CustomEditText municipio;

    public INESecondStepFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.ine_second_step_fragment_layout, container,
                false);

        ActiviTyKeyboardBehavior.setupUI(layoutView
                .findViewById(R.id.content), getActivity());

        anioRegistro = (CustomEditText) layoutView.findViewById(R.id.anioRegistro);
        claveElector = (CustomEditText) layoutView.findViewById(R.id.claveElector);
        curp = (CustomEditText) layoutView.findViewById(R.id.curp);
        estado = (CustomEditText) layoutView.findViewById(R.id.estado);
        municipio = (CustomEditText) layoutView.findViewById(R.id.municipio);

        anioRegistro.addTextChangedListener(new CharListener(anioRegistro ,getResources(),new AnioRegistroDelegate(), 4));
        claveElector.addTextChangedListener(new CharListener(claveElector ,getResources(),new ClaveElectorDelegate(), 18));
        curp.addTextChangedListener(new CharListener(curp ,getResources(),new CURPDelegate(), 18));
        estado.addTextChangedListener(new CharListener(estado ,getResources(),new EstadoDelegate(), 2));
        municipio.addTextChangedListener(new CharListener(municipio ,getResources(),new MunicipioDelegate(), 3));

        anioRegistro.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        claveElector.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        curp.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        estado.setFilters(new InputFilter[] {new InputFilter.LengthFilter(2)});
        municipio.setFilters(new InputFilter[] {new InputFilter.LengthFilter(3)});

        // Creado por Mario Rojas
        verifyProperty(anioRegistro, MorphoFormApp.getSingleton().getCustomer().anioRegistro);
        verifyProperty(claveElector, MorphoFormApp.getSingleton().getCustomer().claveElector);
        verifyProperty(curp, MorphoFormApp.getSingleton().getCustomer().curp);
        verifyProperty(estado, MorphoFormApp.getSingleton().getCustomer().estado);
        verifyProperty(municipio, MorphoFormApp.getSingleton().getCustomer().municipio);

        return layoutView;
    }

    private class AnioRegistroDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().anioRegistro = anioRegistro.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class ClaveElectorDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().claveElector = claveElector.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class CURPDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().curp = curp.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class EstadoDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().estado = estado.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class MunicipioDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().municipio = municipio.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    /**
     * Creado por Mario Rojas
     *
     * Verifica si una propiedad ya existe previamente
     * para poder colocarla en el formulario
     */
    private void verifyProperty(CustomEditText field, String property) {
        if ( property!=null && !property.isEmpty())
            field.setText(property);
    }

}
