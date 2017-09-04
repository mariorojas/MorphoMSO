/**
 *
 */
package com.morpho.demo.customs;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.morpho.demo.constant.DialogUses;


/**
 * @author Alex
 *
 */
public class AlertDialogTool {

    public static void showAlertDialog(
            String title,
            String txt,
            String positiveTextButton,
            String negativeTextButton,
            TextView input,
            final Context c,
            final DialogUses dialogUser){


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(c);

        // set title
        if(title != null && !title.equals(""))
            alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(txt)
                .setCancelable(false);


        if(input != null)
            alertDialogBuilder.setView(input);

        //First button
        if(positiveTextButton != null && !positiveTextButton.equals("")){
            alertDialogBuilder.setPositiveButton(positiveTextButton,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, close
                    if(dialogUser != null)
                        dialogUser.acceptButtonAction();
                    dialog.dismiss();
                }
            });
        }

        //Second button
        if(negativeTextButton != null && !negativeTextButton.equals("")){
            alertDialogBuilder.setNegativeButton(negativeTextButton,new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    if(dialogUser != null)
                        dialogUser.cancelButtonAction();
                    dialog.cancel();
                }
            });
        }

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


}
