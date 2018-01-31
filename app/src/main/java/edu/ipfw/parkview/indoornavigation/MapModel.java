package edu.ipfw.parkview.indoornavigation;

import android.content.Context;
import android.graphics.Matrix;

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
                final MapView mapView = mapFragment.getMapView();
                MeridianLocation location = mapView.getUserLocation();
                if (location != null) {
                    mapView.updateForLocation(location);
                } else {
                    LocationRequest.requestCurrentLocation(context, Application.APP_KEY, new LocationRequest.LocationRequestListener() {
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
    }


    private void buildMapFragment(Context context) {
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY)
                .setMapOptions(configureMapOptions());
        mapFragment = mapBuilder.build();
        //mapFragment.getMapView().setShowsUserLocation(true);

        renewMapFragment(context);

    }

    private MapOptions configureMapOptions() {
        MapOptions options = MapOptions.getDefaultOptions();
        options.HIDE_LEVELS_CONTROL = true;
        options.HIDE_OVERVIEW_BUTTON = true;
        return options;
    }


}

