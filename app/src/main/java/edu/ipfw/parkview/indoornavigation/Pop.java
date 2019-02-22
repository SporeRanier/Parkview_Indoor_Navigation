package edu.ipfw.parkview.indoornavigation;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class Pop extends Activity {

    String roomName;
    String roomDesc;
    String roomURL;


    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDesc() {
        return roomDesc;
    }

    public void setRoomDesc(String roomDesc) {
        this.roomDesc = roomDesc;
    }

    public String getRoomURL() {
        return roomURL;
    }

    public void setRoomURL(String roomURL) {
        this.roomURL = roomURL;
    }


    @Override
    protected void  onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;



        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }
}
