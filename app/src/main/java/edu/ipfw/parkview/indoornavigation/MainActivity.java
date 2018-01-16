package edu.ipfw.parkview.indoornavigation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;

public class MainActivity extends AppCompatActivity {

    //TODO: Put these strings in values/strings.xml
    public static final EditorKey APP_KEY = EditorKey.forApp("f477d4c144c1541e6452b812bc3b194d4770b6c4");
    public static final EditorKey MAP_KEY = EditorKey.forMap("6555052652625920", APP_KEY.getId());
    //public static final String EDITOR_TOKEN = "Not a real token yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // configure Meridian services
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        //Meridian.getShared().setEditorToken(EDITOR_TOKEN);
        //EditorKey key = new EditorKey("needkey");

        MapFragment.Builder builder = new MapFragment.Builder()
                    .setMapKey(MAP_KEY);
        MapOptions mapOptions = MapOptions.getDefaultOptions();
        builder.setMapOptions(mapOptions);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
