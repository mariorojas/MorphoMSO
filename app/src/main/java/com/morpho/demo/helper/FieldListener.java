/**
 *
 */
package com.morpho.demo.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;

import com.morpho.demo.R;
import com.morpho.demo.constant.DelegateValidationField;
import com.morpho.demo.customs.CustomEditText;


/**
 * @author Alex
 *
 */
public class FieldListener implements TextWatcher{

    private CustomEditText editField;
    private DelegateValidationField delegated;
    private Context context;
    private static int validLongitud = 2;
    private Resources resources;
    private Drawable check;
    private int fieldId;

    public FieldListener(CustomEditText editField, int fieldId,Resources resources, Context context){
        this.editField = editField;
        this.context = context;
        this.resources = resources;
        this.fieldId = fieldId;
        check = resources.getDrawable(R.drawable.check);
    }

    public FieldListener(CustomEditText editField, int fieldId,Resources resources, DelegateValidationField delegated, Context context){
        this.editField = editField;
        this.delegated = delegated;
        this.context = context;
        this.resources = resources;
        this.fieldId = fieldId;
        check = resources.getDrawable(R.drawable.check);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(isValid(s)){
            editField.setCompoundDrawablesWithIntrinsicBounds(null, null, check, null);
            editField.setBackground(resources.getDrawable(R.drawable.rounded_curner_good_question));
            editField.setValidInfo(true);
            if(delegated != null)
                delegated.correctInfo(fieldId);
        }else{
            editField.setBackground(resources.getDrawable(R.drawable.rounded_curner_question));
            editField.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            editField.setValidInfo(false);
            if(delegated != null)
                delegated.incorrectInfo(fieldId);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

    }

    private boolean isValid(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return target.length() >= validLongitud;
        }
    }

}
