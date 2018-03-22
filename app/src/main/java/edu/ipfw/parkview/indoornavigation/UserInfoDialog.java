package edu.ipfw.parkview.indoornavigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ddely on 3/20/18.
 */



public class UserInfoDialog extends DialogFragment {

    private FrameLayout frameLayout;

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog alertDialog;
        frameLayout = new FrameLayout(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(frameLayout);

        builder.setMessage("a message here")
                .setPositiveButton("test", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                })
                .setNegativeButton("Negative button", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                });

        alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.user_info_dialog, frameLayout);
        return alertDialog;
    }

}
