package com.morpho.demo.frament;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class SectionFragment extends Fragment {


    private CustomEditText name;
    private CustomEditText lastname;
    private CustomEditText idCustomer;
    private CustomEditText apeMat;

    private CustomEditText anioRegistro;
    private CustomEditText emision;
    private CustomEditText claveElector;
    private CustomEditText curp;
    private CustomEditText sexo;
    private CustomEditText estado;
    private CustomEditText municipio;
    private CustomEditText localidad;
    private CustomEditText seccion;
    private CustomEditText vigencia;

    public SectionFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.section_framgment_layout, container,
                false);

        ActiviTyKeyboardBehavior.setupUI(layoutView
                .findViewById(R.id.content), getActivity());

        name = (CustomEditText) layoutView.findViewById(R.id.name);
        lastname = (CustomEditText) layoutView.findViewById(R.id.lastname);
        idCustomer = (CustomEditText) layoutView.findViewById(R.id.idCustomer);
        apeMat = (CustomEditText) layoutView.findViewById(R.id.apeMat);
        anioRegistro = (CustomEditText) layoutView.findViewById(R.id.anioRegistro);
        emision = (CustomEditText) layoutView.findViewById(R.id.emision);
        claveElector = (CustomEditText) layoutView.findViewById(R.id.claveElector);
        curp = (CustomEditText) layoutView.findViewById(R.id.curp);
        sexo = (CustomEditText) layoutView.findViewById(R.id.sexo);
        estado = (CustomEditText) layoutView.findViewById(R.id.estado);
        municipio = (CustomEditText) layoutView.findViewById(R.id.municipio);
        localidad = (CustomEditText) layoutView.findViewById(R.id.localidad);
        seccion = (CustomEditText) layoutView.findViewById(R.id.seccion);
        vigencia = (CustomEditText) layoutView.findViewById(R.id.vigencia);

        name.addTextChangedListener(new CharListener(name ,getResources(),new NameDelegate(), 3));
        lastname.addTextChangedListener(new CharListener(lastname ,getResources(),new LastNameDelegate(), 3));
        idCustomer.addTextChangedListener(new CharListener(idCustomer ,getResources(),new IdUserDelegate(), 3));
        apeMat.addTextChangedListener(new CharListener(apeMat ,getResources(),new ApeMatDelegate(), 3));
        anioRegistro.addTextChangedListener(new CharListener(anioRegistro ,getResources(),new AnioRegistroDelegate(), 3));
        emision.addTextChangedListener(new CharListener(emision ,getResources(),new EmisionDelegate(), 3));
        claveElector.addTextChangedListener(new CharListener(claveElector ,getResources(),new ClaveElectorDelegate(), 3));
        curp.addTextChangedListener(new CharListener(curp ,getResources(),new CURPDelegate(), 3));
        sexo.addTextChangedListener(new CharListener(sexo ,getResources(),new SexoDelegate(), 1));
        estado.addTextChangedListener(new CharListener(estado ,getResources(),new EstadoDelegate(), 2));
        municipio.addTextChangedListener(new CharListener(municipio ,getResources(),new MunicipioDelegate(), 3));
        localidad.addTextChangedListener(new CharListener(localidad ,getResources(),new LocalidadDelegate(), 3));
        seccion.addTextChangedListener(new CharListener(seccion ,getResources(),new SeccionDelegate(), 3));
        vigencia.addTextChangedListener(new CharListener(vigencia ,getResources(),new VigenciaDelegate(), 3));

        // Creado por Mario Rojas
        verifyProperty(name, MorphoFormApp.getSingleton().getCustomer().Name);
        verifyProperty(lastname, MorphoFormApp.getSingleton().getCustomer().Surname);
        verifyProperty(idCustomer, MorphoFormApp.getSingleton().getCustomer().ID);
        verifyProperty(apeMat, MorphoFormApp.getSingleton().getCustomer().apeMat);
        verifyProperty(anioRegistro, MorphoFormApp.getSingleton().getCustomer().anioRegistro);
        verifyProperty(emision, MorphoFormApp.getSingleton().getCustomer().emision);
        verifyProperty(claveElector, MorphoFormApp.getSingleton().getCustomer().claveElector);
        verifyProperty(curp, MorphoFormApp.getSingleton().getCustomer().curp);
        verifyProperty(sexo, MorphoFormApp.getSingleton().getCustomer().sexo);
        verifyProperty(estado, MorphoFormApp.getSingleton().getCustomer().estado);
        verifyProperty(municipio, MorphoFormApp.getSingleton().getCustomer().municipio);
        verifyProperty(localidad, MorphoFormApp.getSingleton().getCustomer().localidad);
        verifyProperty(seccion, MorphoFormApp.getSingleton().getCustomer().seccion);
        verifyProperty(vigencia, MorphoFormApp.getSingleton().getCustomer().vigencia);

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

    private class AnioRegistroDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().anioRegistro = anioRegistro.getText().toString();
        }

        @Override
        public void incorrectInfo() {

        }
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

    private class SexoDelegate implements DelegateValidation {

        @Override
        public void correctInfo() {
            MorphoFormApp.getSingleton().getCustomer().sexo = sexo.getText().toString();
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
