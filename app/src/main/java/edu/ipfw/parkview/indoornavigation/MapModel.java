package edu.ipfw.parkview.indoornavigation;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;

import com.arubanetworks.meridian.location.MeridianLocationManager;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;

/**
 * Created by mcshdd01 on 1/30/2018.
 */

public class MapModel {
    private MapFragment mapFragment;
    private MeridianLocationManager locationManager;
    private boolean isListening;
    private final MeridianLocationManager.LocationUpdateListener listener = new MeridianLocationManager.LocationUpdateListener() {
        @Override
        public void onLocationUpdate(MeridianLocation location) {
            Log.d("t","t");
        }

        @Override
        public void onLocationError(Throwable tr) {
            Log.d("t","t");
        }

        @Override
        public void onEnableBluetoothRequest() {

        }

        @Override
        public void onEnableWiFiRequest() {
        }

        @Override
        public void onEnableGPSRequest() {

        }
    };

    public MapModel(Context context) {
        buildMapFragment(context);
    }

    public MapFragment getMapFragment(){
        return mapFragment;
    }

    public void renewMapFragment(final Context context){

        mapFragment.setMapEventListener(new MapView.MapEventListener() {
            @Override
            public void onMapLoadStart() {

            }

            @Override
            public void onMapLoadFinish() {
                mapFragment.getMapView().setShowsUserLocation(true);
                setListening(true);
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
                Log.d("t","t");
                /*
                final MapView mapView = mapFragment.getMapView();
                MeridianLocation location = mapView.getUserLocation();
                if (location != null) {
                    Log.d("HEYLLADJSDADLAD", "READINGUSERLOCATION");
                    mapView.updateForLocation(location);
                } else {
                    LocationRequest.requestCurrentLocation(mapView.getContext(), Application.APP_KEY, new LocationRequest.LocationRequestListener() {
                        @Override
                        public void onResult(MeridianLocation location) {
                            mapView.updateForLocation(location);
                        }

                        @Override
                        public void onError(LocationRequest.ErrorType location) {
                            // handle the error
                        }
                    });
                }*/
                return true;
            }
        });
    }


    private void buildMapFragment(Context context) {
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY)
                .setMapOptions(configureMapOptions());
        mapFragment = mapBuilder.build();
        //mapFragment.getMapView().setShowsUserLocation(true);
        locationManager = new MeridianLocationManager(context, Application.APP_KEY, listener);
        isListening = false;
        //locationManager.startListeningForLocation();
        //renewMapFragment(context);

    }

    private boolean isListening(){
        return isListening;
    }

    private void setListening(boolean listen) {
        isListening = listen;
        if(listen){
            locationManager.startListeningForLocation();
        }
        else{
            locationManager.stopListeningForLocation();
        }
    }
    private MapOptions configureMapOptions() {
        MapOptions options = MapOptions.getDefaultOptions();
        options.HIDE_LEVELS_CONTROL = true;
        options.HIDE_OVERVIEW_BUTTON = true;
        return options;
    }


}

