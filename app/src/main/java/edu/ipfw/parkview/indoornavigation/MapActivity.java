package edu.ipfw.parkview.indoornavigation;

import android.graphics.Matrix;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d("in", "in");
        buildMapFragment();
        Log.d("out", "out");
    }

    private void buildMapFragment() {
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY);
        MapOptions mapOptions = MapOptions.getDefaultOptions();
        //mapOptions.HIDE_OVERVIEW_BUTTON = true;
        mapBuilder.setMapOptions(mapOptions);
        final MapFragment mapFragment = mapBuilder.build();
        mapFragment.setMapEventListener(new MapView.MapEventListener() {
            @Override
            public void onMapLoadStart() {

            }

            @Override
            public void onMapLoadFinish() {

            }

            @Override
            public void onPlacemarksLoadFinish() {

            }

            @Override
            public void onMapRenderFinish() {

            }

            @Override
            public void onMapLoadFail(Throwable throwable) {

            }

            @Override
            public void onMapTransformChange(Matrix matrix) {

            }

            @Override
            public void onLocationUpdated(MeridianLocation meridianLocation) {

            }

            @Override
            public void onOrientationUpdated(MeridianOrientation meridianOrientation) {

            }

            @Override
            public boolean onLocationButtonClick() {
                // example of how to override the behavior of the location button
                final MapView mapView = mapFragment.getMapView();
                MeridianLocation location = mapView.getUserLocation();
                if (location != null) {
                    mapView.updateForLocation(location);

                } else {
                    LocationRequest.requestCurrentLocation(getApplicationContext(), Application.APP_KEY, new LocationRequest.LocationRequestListener() {
                        @Override
                        public void onResult(MeridianLocation location) {
                            mapView.updateForLocation(location);
                        }

                        @Override
                        public void onError(LocationRequest.ErrorType location) {
                            // handle the error
                        }
                    });
                }
                return true;
            }
        });

        final Fragment fragment = mapFragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


}
