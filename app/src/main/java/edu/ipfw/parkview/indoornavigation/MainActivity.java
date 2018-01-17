package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.internal.analytics.MeridianAnalytics;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;

public class MainActivity extends AppCompatActivity {

    //TODO: Put these strings in values/strings.xml
    public static final EditorKey APP_KEY = EditorKey.forApp("f477d4c144c1541e6452b812bc3b194d4770b6c4");
    public static final EditorKey MAP_KEY = EditorKey.forMap("6555052652625920", APP_KEY);
    //public static final String EDITOR_TOKEN = "mysterious token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // configure Meridian services
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setForceSimulatedLocation(true);
        //MeridianAnalytics.setAppId("");
        //Meridian.getShared().setEditorToken(EDITOR_TOKEN);
        //EditorKey key = new EditorKey("needkey");


    }

    public void onButtonClick(View v) {
        Intent startMapActivity = new Intent(this, MapActivity.class);
        startActivity(startMapActivity);
    }

}
