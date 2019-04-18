package edu.ipfw.parkview.indoornavigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by ddely on 3/20/18.
 */


import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;

/*Inherit DialogFragment to create custom Dialog for user data input*/
public class UserInfoDialog extends DialogFragment {

    private FrameLayout frameLayout;
    private UserData temp;
    private String gender;

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog alertDialog;
        frameLayout = new FrameLayout(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(frameLayout);


        builder.setMessage("Please enter your info")
                //Listener for submit button click
                .setPositiveButton("Submit", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        if(((RadioButton) getDialog().findViewById(R.id.radioMale)).isChecked()){
                            gender = "male";
                        }

                        if(((RadioButton) getDialog().findViewById(R.id.radioFemale)).isChecked()){
                            gender = "female";
                        }
                        temp = new UserData( ((EditText) getDialog().findViewById(R.id.ageTextBox)).getText().toString(), gender);
                    }
                })
                //Listener for skip button click
                .setNegativeButton("Skip", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){

                    }
                });
        alertDialog = builder.create();
        LayoutInflater inflater = alertDialog.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.user_info_dialog, frameLayout);
        return alertDialog;
    }

    public UserData getTemp() {
        return temp;
    }

    public void setTemp(UserData temp) {
        this.temp = temp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
