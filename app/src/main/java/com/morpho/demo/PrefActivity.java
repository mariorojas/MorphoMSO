package com.morpho.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.morpho.demo.frament.PrefFragment;

/**
 * Activity que muestra y almacena las preferencias de usuario de la aplicacin.
 * 
 * @author alruiz
 * 
 */
public class PrefActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background_bar));

		// Mostramos el contenido de la pantalla de preferencias.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PrefFragment()).commit();
	}

}
