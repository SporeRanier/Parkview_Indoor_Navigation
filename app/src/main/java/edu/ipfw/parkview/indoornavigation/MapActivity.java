package edu.ipfw.parkview.indoornavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.maps.MapFragment;

public class MapActivity extends AppCompatActivity {

    //TODO: Put these strings in values/strings.xml
    public static final EditorKey APP_KEY = EditorKey.forApp("f477d4c144c1541e6452b812bc3b194d4770b6c4");
    public static final EditorKey MAP_KEY = EditorKey.forMap("6555052652625920", APP_KEY.getId());
    //public static final String EDITOR_TOKEN = "Not a real token yet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //EditorKey test = new EditorKey("ASD");
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        //Meridian.getShared().setEditorToken(EDITOR_TOKEN);
        //EditorKey key = new EditorKey("needkey");
        MapFragment m = MapFragment.newInstance(APP_KEY, null);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }


}
