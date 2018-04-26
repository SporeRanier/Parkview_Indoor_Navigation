package edu.ipfw.parkview.indoornavigation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.arubanetworks.meridian.editor.EditorKey;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.maps.directions.DirectionsSource;

/* Use Meridian API to host Blue-dot enable map fragment*/
public class DirectionsActivity extends AppCompatActivity {

    //Meridian API editor keys
    public static final String APP_KEY = "meridian.AppKey";
    public static final String DIRECTIONS_SOURCE = "meridian.DirectionsSource";
    public static final String DIRECTIONS_DESTINATION = "meridian.DirectionsDestination";

    public static Intent createIntent(Context context, EditorKey appKey, DirectionsSource source, DirectionsDestination destination) {
        return new Intent(context, DirectionsActivity.class)
                .putExtra(APP_KEY, appKey)
                .putExtra(DIRECTIONS_SOURCE, source)
                .putExtra(DIRECTIONS_DESTINATION, destination);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        if (savedInstanceState == null) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            else {
                    MapFragment mapFragment = new MapFragment.Builder()
                            .setAppKey(Application.APP_KEY)
                            .setMapKey(Application.MAP_KEY)
                            .build();

                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(android.R.id.content, mapFragment)
                            .commit();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // this will pass through to our map fragment
        super.onActivityResult(requestCode, resultCode, data);
    }

}