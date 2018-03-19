package edu.ipfw.parkview.indoornavigation;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianLocationManager;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;
import com.arubanetworks.meridian.maps.directions.Directions;
import com.arubanetworks.meridian.maps.directions.DirectionsDestination;
import com.arubanetworks.meridian.maps.directions.DirectionsResponse;
import com.arubanetworks.meridian.maps.directions.DirectionsSource;
import com.arubanetworks.meridian.maps.directions.TransportType;


public class MainActivity extends AppCompatActivity   {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle; //must use v7.app
    private FragmentManager fragmentManager;
    private Class fragmentClass;
    private Fragment fragment;
    private MenuItem menuItem;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main_menu);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        fragmentManager = getSupportFragmentManager();
        fragment = null;
        fragmentClass = null;
        buildMapFragment();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Boolean needFrag = true;
        Boolean needMap = false;
        Intent intent;
        switch(menuItem.getItemId()) {

            case R.id.drawer_main_menu:
                needFrag = false;
                intent = new Intent(MainActivity.this, MainActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;
            case R.id.drawer_map:
                fragmentManager.beginTransaction().replace(R.id.clMainMenu, mapFragment).commit();
                needFrag = false;
                break;

            case R.id.drawer_upcoming_events:
                fragmentClass = UpcomingEventsFragment.class;
                break;
            case R.id.drawer_directory:
                fragmentClass = DirectoryFragment.class;
                break;
            case R.id.drawer_contact_us:
                fragmentClass = ContactUsActivity.class;
                break;
            case R.id.drawer_wait_time:
                fragmentClass = WaitTimeFragment.class;
                break;
            default:
                fragmentClass = UpcomingEventsFragment.class;
                break;
        }

        if(needFrag) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();

                fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    public void onDirectionsButtonClick(View v) {
        fragmentManager.beginTransaction().replace(R.id.clMainMenu, mapFragment).commit();
    }

    public void onEventsButtonClick(View v){
        try{
            fragmentClass = UpcomingEventsFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem)findViewById(R.id.drawer_upcoming_events);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onDirectoryButtonClick(View v){
        try{
            fragmentClass = DirectoryFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem)findViewById(R.id.drawer_directory);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onWaitTimeButtonClick(View v) {
        try{
            fragmentClass = WaitTimeFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem)findViewById(R.id.drawer_wait_time);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void onContactUsClick(View v) {
        try{

            fragmentClass = ContactUsActivity.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem)findViewById(R.id.drawer_contact_us);
            menuItem.setChecked(true);

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void buildMapFragment() {
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY)
                .setMapOptions(configureMapOptions());
        mapFragment = mapBuilder.build();
        //mapFragment.getMapView().setShowsUserLocation(true);
        //locationManager.startListeningForLocation();
        //renewMapFragment(context);

        mapFragment.setMapEventListener(new MapView.MapEventListener() {
            @Override
            public void onMapLoadStart() {

            }

            @Override
            public void onMapLoadFinish() {
                mapFragment.getMapView().setShowsUserLocation(true);
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

    private MapOptions configureMapOptions() {
        MapOptions options = MapOptions.getDefaultOptions();
        options.HIDE_LEVELS_CONTROL = true;
        options.HIDE_OVERVIEW_BUTTON = true;
        return options;
    }

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



}
