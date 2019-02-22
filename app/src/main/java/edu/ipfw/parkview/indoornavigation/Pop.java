package edu.ipfw.parkview.indoornavigation;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import java.io.InputStream;

public class Pop extends Activity {

    String roomName;
    String roomDesc;
    String roomURL;
    TextView textView;
    ImageView imageView;


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
        roomName = "test";
        roomDesc = "test";
        roomURL = "test";

        Bundle b = getIntent().getExtras();
        if(b != null){
            roomName = b.getString("name");
            roomDesc = b.getString("desc");
            roomURL = b.getString("url");
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.popwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        textView =(TextView)findViewById(R.id.textView);
        textView.setText(roomName);
        Drawable pic = LoadImageFromWebOperations(roomURL);
        imageView.setImageDrawable(pic);


        int width = dm.widthPixels;
        int height = dm.heightPixels;



        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}
