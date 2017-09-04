/**
 * 
 */
package com.morpho.demo.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author Alex
 *
 */
public class CustomEditText extends EditText{
	
	private boolean isValidContent;

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CustomEditText(Context context) {
		super(context);
	}
	
	public void setValidInfo(boolean isValidContent){
		this.isValidContent = isValidContent;
	}
	
	public boolean isValidInfo(){
		return isValidContent;
	}

}
