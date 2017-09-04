/**
 *
 */
package com.morpho.demo.helper;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;

import com.morpho.demo.R;
import com.morpho.demo.customs.CustomEditText;
import com.morpho.demo.constant.DelegateValidation;


/**
 * @author Alex
 *
 */
public class CharListener implements TextWatcher{

    private CustomEditText editField;
    private DelegateValidation delegated;
    private int validLongitud;
    private Resources resources;
    private Drawable check;

    public CharListener(CustomEditText editField,Resources resources){
        this.editField = editField;
        this.resources = resources;
        validLongitud = 3;
        check = resources.getDrawable(R.drawable.check);
    }

    public CharListener(CustomEditText editField, Resources resources, DelegateValidation delegated,int validLongitud){
        this.editField = editField;
        this.delegated = delegated;
        this.validLongitud = validLongitud;
        this.resources = resources;
        check = resources.getDrawable(R.drawable.check);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        if(isValid(s)){
            editField.setCompoundDrawablesWithIntrinsicBounds(null, null, check, null);
            editField.setBackground(resources.getDrawable(R.drawable.rounded_curner_good_question));
            editField.setValidInfo(true);
            if(delegated != null)
                delegated.correctInfo();
        }else{
            editField.setBackground(resources.getDrawable(R.drawable.rounded_curner_question));
            editField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            editField.setValidInfo(false);
            if(delegated != null)
                delegated.incorrectInfo();
        }
    }

    private boolean isValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return target.length() >= validLongitud ;
        }
    }

}
