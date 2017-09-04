package com.morpho.demo.frament;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.morpho.demo.R;


/**
 * Fragment para mostrar la pantalla de preferencias de usuario.
 * 
 * @author Ialamilla
 * 
 */
@SuppressLint("NewApi")
public class PrefFragment extends PreferenceFragment {

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Establecemos el xml de preferencias.
		addPreferencesFromResource(R.xml.preferences);
	}
}
