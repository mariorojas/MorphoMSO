/**
 * 
 */
package com.morpho.demo.customs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.morpho.demo.R;


/**
 * @author Alex
 *
 */
public class LoaderDialog extends Dialog implements DialogInterface.OnDismissListener{

	private ImageView imgContent;
	private AnimationDrawable frameAnimation;
	private int advance = 0;

    private RelativeLayout loadingContent;

	public LoaderDialog(Context context, boolean cancelable) {
		super(context);
		setCancelable(cancelable);
		setOnDismissListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.indeterminated_loading_layout);
		Drawable bg = new ColorDrawable(android.graphics.Color.TRANSPARENT);
		bg.setAlpha(200);
		getWindow().setBackgroundDrawable(bg);

        loadingContent = (RelativeLayout) findViewById(R.id.loadingContent);

		frameAnimation = (AnimationDrawable)getContext().getResources().getDrawable(R.drawable.loading_inderminated);

		imgContent = (ImageView) findViewById(R.id.loadingImg);
		imgContent.setImageDrawable(frameAnimation);

		// Get the background, which has been compiled to an AnimationDrawable object.
		if(!frameAnimation.isRunning()){
			frameAnimation.start();
		}
		
	}

	public int getProgress(){
		return advance;
	}

	
	@Override
	public void onDismiss(DialogInterface dialog) {
		if(frameAnimation != null)
			frameAnimation.stop();
	}

}
