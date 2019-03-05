package edu.ipfw.parkview.indoornavigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;
import java.io.InputStream;

import javax.net.ssl.HttpsURLConnection;

public class Pop extends Activity {

    String roomName;
    String roomDesc;
    String roomURL;
    TextView textView;
    TextView textView2;
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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

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
        textView2 =(TextView)findViewById(R.id.textView2);
        textView2.setText(roomDesc);
        Drawable pic = LoadImageFromWebOperations(roomURL);
        imageView =(ImageView)findViewById(R.id.imageView);
        imageView.setImageDrawable(pic);


        int width = dm.widthPixels;
        int height = dm.heightPixels;



        getWindow().setLayout((int)(width*.8),(int)(height*.6));
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            String httpsURL = url;
            URL myUrl = new URL(httpsURL);
            HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
            InputStream is = conn.getInputStream();
            //InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    public void openPhoto(View view){
        
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://goo.gl/maps/JWfNBqQthE52"));
        startActivity(browserIntent);

    }
}