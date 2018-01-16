package edu.ipfw.parkview.indoornavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        EditorKey test = new EditorKey("ASD");
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setEditorToken(EDITOR_TOKEN);
        EditorKey key = new EditorKey("needkey");

        1f99b33e9a46ec027e264916d30c3b27dc9d77a6
    }


}
