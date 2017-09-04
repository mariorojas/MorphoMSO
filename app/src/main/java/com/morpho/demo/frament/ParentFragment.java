package com.morpho.demo.frament;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.morpho.demo.R;
import com.morpho.demo.constant.DialogUses;
import com.morpho.demo.customs.AlertDialogTool;
import com.morpho.demo.customs.LoaderDialog;

/**
 * Created by Alejandro Ruiz on 07/10/2015.
 */
public class ParentFragment extends Fragment{

    protected LoaderDialog progress;

    public void showAlert(final String title, final String message){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, null, getActivity(), new DialogUses() {
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

    public void showAlert(final String title, final String message, final TextView input){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, input, getActivity(), new DialogUses() {
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

    public void showAlert(final String title, final String message, final DialogUses uses){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        getString(R.string.accept), null, null, getActivity(), uses);
            }
        });

    }

    public void showAlert(final String title, final String message, final String positve, final String negative,
                          final DialogUses uses){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialogTool.showAlertDialog(title,
                        message,
                        positve, negative, null, getActivity(), uses);
            }
        });

    }

    public void showProgress(final boolean show) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progress == null)
                        progress = new LoaderDialog(getActivity(), false);
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
}
