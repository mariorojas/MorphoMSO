/**
 *
 */
package com.morpho.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.customs.AlertDialogTool;
import com.morpho.demo.customs.LoaderDialog;

/**
 * @author Alex
 */
public class ParentActivity extends FragmentActivity {

    protected Context thisContext;
    protected LoaderDialog progress;

    protected void onCreate(Bundle savedInstanceState, int layout) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(layout);
        thisContext = this;
    }

    protected void swithSettings_Mbss() {

        Intent i = new Intent(this, PrefActivity.class);
        startActivity(i);

    }

    public void showAlert(final String title, final String message) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, null, thisContext, new DialogUses() {
                            @Override
                            public void cancelButtonAction() {
                            }

                            @Override
                            public void acceptButtonAction() {
                            }
                        });
            }
        });

    }

    public void showAlert(final String title, final String message, final TextView input) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, input, thisContext, new DialogUses() {
                            @Override
                            public void cancelButtonAction() {
                            }

                            @Override
                            public void acceptButtonAction() {
                            }
                        });
            }
        });

    }

    public void showAlert(final String title, final String message, final DialogUses uses) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, null, thisContext, uses);
            }
        });

    }

    public void showAlert(final String title, final String message, final String positve, final String negative,
                          final DialogUses uses) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        positve, negative, null, thisContext, uses);
            }
        });

    }

    public void showProgress(final boolean show) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progress == null)
                        progress = new LoaderDialog(ParentActivity.this, false);
                    if (show)
                        progress.show();
                    else
                        progress.dismiss();
                } catch (IllegalArgumentException a) {
                    a.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
        Log.d(SettingsActivity.class.getName(),"CERRANDO SETTINGS");
    }
}
