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
public class INEThirdStepFragment extends Fragment {


    private CustomEditText idCustomer;
    private CustomEditText emision;
    private CustomEditText localidad;
    private CustomEditText seccion;
    private CustomEditText vigencia;

    public INEThirdStepFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.ine_third_step_fragment, container,
                false);

        ActiviTyKeyboardBehavior.setupUI(layoutView
                .findViewById(R.id.content), getActivity());

        idCustomer = (CustomEditText) layoutView.findViewById(R.id.idCustomer);
        emision = (CustomEditText) layoutView.findViewById(R.id.emision);
        localidad = (CustomEditText) layoutView.findViewById(R.id.localidad);
        seccion = (CustomEditText) layoutView.findViewById(R.id.seccion);
        vigencia = (CustomEditText) layoutView.findViewById(R.id.vigencia);

        idCustomer.addTextChangedListener(new CharListener(idCustomer ,getResources(),new IdUserDelegate(), 13));
        emision.addTextChangedListener(new CharListener(emision ,getResources(),new EmisionDelegate(), 4));
        localidad.addTextChangedListener(new CharListener(localidad ,getResources(),new LocalidadDelegate(), 4));
        seccion.addTextChangedListener(new CharListener(seccion ,getResources(),new SeccionDelegate(), 4));
        vigencia.addTextChangedListener(new CharListener(vigencia ,getResources(),new VigenciaDelegate(), 4));

        idCustomer.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});
        emision.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        localidad.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        seccion.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});
        vigencia.setFilters(new InputFilter[] {new InputFilter.LengthFilter(4)});

        verifyProperty(idCustomer, MorphoFormApp.getSingleton().getCustomer().ID);
        verifyProperty(emision, MorphoFormApp.getSingleton().getCustomer().emision);
        verifyProperty(localidad, MorphoFormApp.getSingleton().getCustomer().localidad);
        verifyProperty(seccion, MorphoFormApp.getSingleton().getCustomer().seccion);
        verifyProperty(vigencia, MorphoFormApp.getSingleton().getCustomer().vigencia);

        return layoutView;
    }

    private class EmisionDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().emision = emision.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class LocalidadDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().localidad = localidad.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class SeccionDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().seccion = seccion.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class VigenciaDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().vigencia = vigencia.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
    }

    private class IdUserDelegate implements DelegateValidation{

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().ID = idCustomer.getText().toString();
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
