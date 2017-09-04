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
import com.morpho.demo.customs.CustomEditText;
import com.morpho.demo.constant.DelegateValidation;

import java.util.regex.Pattern;


/**
 * @author Alex
 *
 */
public class NumberListener implements TextWatcher{

	private CustomEditText editField;
	private Resources resources;
	private DelegateValidation delegated;
    private Drawable check;
    private Context context;
    private Pattern patterns;
    private int lengthAllowed;

	public NumberListener(CustomEditText editField, Resources resources, Context context, int lengthAllowed){
		this.editField = editField;
		this.resources = resources;
        this.context = context;
        patterns = Pattern.compile("[0-9]+");
        check = resources.getDrawable(R.drawable.check);
        this.lengthAllowed = lengthAllowed;
	}

	public NumberListener(CustomEditText editField, Resources resources, DelegateValidation delegated, Context context,
                          int lengthAllowed){
		this.editField = editField;
		this.resources = resources;
		this.delegated = delegated;
        this.context = context;
        patterns = Pattern.compile("[0-9]+");
        check = resources.getDrawable(R.drawable.check);
        this.lengthAllowed = lengthAllowed;
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
            boolean res = false;
            if(lengthAllowed > 0)
			    res = (patterns.matcher(target).matches() && target.length() == lengthAllowed)?true:false;
            else
                res = patterns.matcher(target).matches();
            return res;
		}
	}
	
}
