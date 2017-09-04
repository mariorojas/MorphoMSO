/**
 * 
 */
package com.morpho.demo.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import com.morpho.demo.R;
import com.morpho.demo.constant.DelegateValidation;
import com.morpho.demo.customs.CustomEditText;


/**
 * @author Alex
 *
 */
public class PhoneListener implements TextWatcher{

	private CustomEditText editField;
	private Resources resources;
	private DelegateValidation delegated;
    private Drawable check;
    private Context context;

	public PhoneListener(CustomEditText editField, Resources resources, Context context){
		this.editField = editField;
		this.resources = resources;
        this.context = context;
        check = resources.getDrawable(R.drawable.check);
	}

	public PhoneListener(CustomEditText editField, Resources resources, DelegateValidation delegated, Context context){
		this.editField = editField;
		this.resources = resources;
		this.delegated = delegated;
        this.context = context;
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
		if(isValidEmail(s)){
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
	
	private boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return Patterns.PHONE.matcher(target).matches();
		}
	}
	
}
