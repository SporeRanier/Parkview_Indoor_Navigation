package edu.ipfw.parkview.indoornavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_map);

        EditorKey appKey = new EditorKey("f477d4c144c1541e6452b812bc3b194d4770b6c4");
        EditorKey mapKey = EditorKey.forMap("6555052652625920", appKey);
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setAppKey(appKey)
                .setMapKey(mapKey);

        MapFragment mapView = mapBuilder.build();
        setContentView(mapView.getMapView());
    }


}
