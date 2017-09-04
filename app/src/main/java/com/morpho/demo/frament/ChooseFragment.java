package com.morpho.demo.frament;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.ChooseDelegate;
import com.morpho.demo.constant.ChooserDelegate;
import com.morpho.demo.constant.Constants;
import com.morpho.demo.constant.MorphoFormApp;
import com.morpho.demo.customs.CustomToogleImag;
import com.morpho.demo.tools.AnimationManage;


/**
 * Created by alex on 30/06/2015.
 */
public class ChooseFragment extends ParentFragment implements View.OnClickListener,ChooseDelegate {

    private CustomToogleImag fingerprint;
    private CustomToogleImag facial;
    private CustomToogleImag voice;
    private Button continueBtn;
    private View root;

    private TextView title;

    public ChooseFragment(){}

    private ChooserDelegate chooserDelegate;

    public static ChooseFragment newInstance(ChooserDelegate chooserDelegate){
        ChooseFragment sectionFragment = new ChooseFragment();
        sectionFragment.setChooserDelegate(chooserDelegate);
        return(sectionFragment);
    }

    public void setChooserDelegate(ChooserDelegate chooserDelegate){
        this.chooserDelegate = chooserDelegate;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View layoutView = inflater.inflate(R.layout.settings_fragment_layout, container,
                false);

        continueBtn = (Button) layoutView.findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);

        root = layoutView;

        return layoutView;
    }

    private void setToogle(View root){
        fingerprint = (CustomToogleImag) root.findViewById(R.id.fingerprint);
        if(MorphoFormApp.getSingleton().isHasFingerprint()) {
            fingerprint.setVisibility(View.VISIBLE);
            fingerprint.setTYPE(Constants.FINGERPRING_TYPE);
            fingerprint.setAnimation(AnimationManage.getLeftInAnimBounce(getActivity(), 1000));
        }else{
            fingerprint.setVisibility(View.GONE);
        }

        facial = (CustomToogleImag) root.findViewById(R.id.facial);
        if(MorphoFormApp.getSingleton().isHasFacial()) {
            facial.setVisibility(View.VISIBLE);
            facial.setTYPE(Constants.FACIAL_TYPE);
            facial.setAnimation(AnimationManage.getRightInAnimBounce(getActivity(), 1000));
        }else{
            facial.setVisibility(View.GONE);
        }

        voice = (CustomToogleImag) root.findViewById(R.id.voice);
        if(MorphoFormApp.getSingleton().isHasVoice()) {
            voice.setVisibility(View.VISIBLE);
            voice.setTYPE(Constants.VOICE_TYPE);
            voice.setAnimation(AnimationManage.getRightInAnimBounce(getActivity(), 1000));
        }else{
            voice.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int val = 0;
        if(fingerprint.getIsSelected()) {
            val++;
            MorphoFormApp.getSingleton().setHasFingerprint(true);
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FINGERPRINT_OPTION);
        }else
            MorphoFormApp.getSingleton().setHasFingerprint(false);

        if(facial.getIsSelected()) {
            MorphoFormApp.getSingleton().setHasFacial(true);
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.FACIAL_OPTION);
            val++;
        }else
            MorphoFormApp.getSingleton().setHasFacial(false);

        if(voice.getIsSelected()) {
            MorphoFormApp.getSingleton().setHasVoice(true);
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.VOICE_OPTION);
            val++;
        }else
            MorphoFormApp.getSingleton().setHasVoice(false);

        if(val > 0) {
            MorphoFormApp.getSingleton().getStackOperaions().add(MorphoFormApp.ALFAS_OPTION);

            if (chooserDelegate != null)
                chooserDelegate.chooseOption();
        }else
            showAlert("","Debes elegir por lo menos una opción de autenticación");
    }

    @Override
    public void showOptions() {
        setToogle(root);
    }
}
