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
import com.morpho.demo.constant.DelegateValidation;
import com.morpho.demo.customs.CustomEditText;


/**
 * @author Alex
 *
 */
public class OpenListener implements TextWatcher{

    private CustomEditText editField;
    private DelegateValidation delegated;
    private Context context;
    private static int validLongitud = 2;
    private Resources resources;
    private Drawable check;

    public OpenListener(CustomEditText editField, Resources resources, Context context){
        this.editField = editField;
        this.context = context;
        this.resources = resources;
        check = resources.getDrawable(R.drawable.check);
    }

    public OpenListener(CustomEditText editField, Resources resources, DelegateValidation delegated, Context context){
        this.editField = editField;
        this.delegated = delegated;
        this.context = context;
        this.validLongitud = validLongitud;
        this.resources = resources;
        check = resources.getDrawable(R.drawable.check);
    }

    @Override
    public void afterTextChanged(Editable s) {
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
