package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapInfo;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;

public class MapActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle; //must use v7.app

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        buildMapFragment();
    }

    private void buildMapFragment() {
        MapFragment.Builder mapBuilder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY)
                .setMapOptions(configureMapOptions());
        final MapFragment mapFragment = mapBuilder.build();
        //mapFragment.getMapView().setShowsUserLocation(true);
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

    private MapOptions configureMapOptions() {
        MapOptions options = MapOptions.getDefaultOptions();
        options.HIDE_LEVELS_CONTROL = true;
        options.HIDE_OVERVIEW_BUTTON = true;
        return options;
    }


    /*=====DRAWER METHODS======*/

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null; //must be Fragment.v4
        Class fragmentClass = null;
        Boolean needFrag = true;
        Intent intent;
        switch(menuItem.getItemId()) {

            case R.id.drawer_main_menu:
                needFrag = false;
                intent = new Intent(MapActivity.this, MainActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;
            case R.id.drawer_map:
                needFrag = false;
                intent = new Intent(MapActivity.this, MapActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;

            case R.id.drawer_upcoming_events:
                fragmentClass = UpcomingEvents.class;
                break;
            case R.id.drawer_wait_time:
                fragmentClass = WaitTimeFragment.class;
                break;
            default:
                fragmentClass = UpcomingEvents.class;
                break;
        }

        if(needFrag) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.dlMap, fragment).commit();
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }
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


}
