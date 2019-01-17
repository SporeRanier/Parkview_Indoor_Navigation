package edu.ipfw.parkview.indoornavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.arubanetworks.meridian.location.LocationRequest;
import com.arubanetworks.meridian.location.MeridianLocation;
import com.arubanetworks.meridian.location.MeridianLocationManager;
import com.arubanetworks.meridian.location.MeridianOrientation;
import com.arubanetworks.meridian.maps.MapFragment;
import com.arubanetworks.meridian.maps.MapOptions;
import com.arubanetworks.meridian.maps.MapView;
import com.arubanetworks.meridian.campaigns.Campaign;
import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;
import com.arubanetworks.meridian.campaigns.CampaignsService;


/*Implement MeridianLocationManager to asynchronously update user location while running*/
public class MainActivity extends AppCompatActivity implements MeridianLocationManager.LocationUpdateListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle; //must use v7.app
    private FragmentManager fragmentManager;
    private Class fragmentClass;
    private Fragment fragment;
    private MenuItem menuItem;
    //private MapFragment mapFragment;
    private MeridianLocationManager locationManager;
    private Campaign campaign;
    private CampaignBroadcastReceiver campaignReceiver;
    private CampaignsService campaignServicer;
    private UserInfoDialog userInfo;

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

        //initialize fragment utilities
        fragmentManager = getSupportFragmentManager();
        fragment = null;
        fragmentClass = null;
        locationManager = null;
        campaign = null;
        campaignReceiver = null;
        campaignServicer = null;

        //ensure required permissions are enabled
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        else {
            locationManager = new MeridianLocationManager(getApplicationContext(), Application.APP_KEY, this);
            campaignServicer = new CampaignsService();

            buildMapFragment();
        }

        //only query user info if fresh start
        if (Application.firstStart()) {
            Application.setFirstStart();
            userInfo = new UserInfoDialog();
            userInfo.show(fragmentManager, "User Data Dialog");

        }

    }

    protected void onStart() {
        locationManager.startListeningForLocation();
        //campaignServicer.startMonitoring();
        super.onStart();
    }

    protected void onStop() {
        locationManager.stopListeningForLocation();
        //campaignServicer.stopMonitoring();
        super.onStop();
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
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
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

    //initialize class' fragment to Meridian MapFragment and set credentials
    private void buildMapFragment() {
        MapFragment.Builder builder = new MapFragment.Builder()
                .setMapKey(Application.MAP_KEY);
        MapOptions mapOptions = MapOptions.getDefaultOptions();
        mapOptions.HIDE_OVERVIEW_BUTTON = true;
        builder.setMapOptions(mapOptions);

        final MapFragment mapFragment = builder.build();
        mapFragment.setMapEventListener(new MapView.MapEventListener() {

            @Override
            public void onMapLoadFinish() {

            }

            @Override
            public void onMapLoadStart() {

            }

            @Override
            public void onPlacemarksLoadFinish() {

            }

            @Override
            public void onMapRenderFinish() {

            }

            @Override
            public void onMapLoadFail(Throwable tr) {

            }

            @Override
            public void onMapTransformChange(Matrix transform) {

            }

            @Override
            public void onLocationUpdated(MeridianLocation location) {

            }

            @Override
            public void onOrientationUpdated(MeridianOrientation orientation) {

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
        fragment = mapFragment;

    }

    @Override
    public void onLocationUpdate(MeridianLocation meridianLocation) {

    }

    @Override
    public void onLocationError(Throwable throwable) {

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

/*============================ App Navigation Methods ================================================*/

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Boolean needFrag = true;
        Intent intent;
        switch (menuItem.getItemId()) {

            //if navigating to main memu from drawer close current activity
            case R.id.drawer_main_menu:
                needFrag = false;
                intent = new Intent(MainActivity.this, MainActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;

            //if opening map fragment call buildMapFragment to reinitialize class' fragment to a MapFragment
            case R.id.drawer_map:
                buildMapFragment();
                fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
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

        //if map or main activity was not selected do fragment transaction
        if (needFrag) {
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
        buildMapFragment();
        fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
    }

    public void onEventsButtonClick(View v) {
        try {
            //create and display events fragment
            fragmentClass = UpcomingEventsFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem) findViewById(R.id.drawer_upcoming_events);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDirectoryButtonClick(View v) {
        try {
            //create and display directory fragment
            fragmentClass = DirectoryFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem) findViewById(R.id.drawer_directory);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWaitTimeButtonClick(View v) {
        try {
            //create and display wait time fragment
            fragmentClass = WaitTimeFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem) findViewById(R.id.drawer_wait_time);
            menuItem.setChecked(true);
            setTitle(menuItem.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onContactUsClick(View v) {
        try {
            //create and display contact fragment
            fragmentClass = ContactUsActivity.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem) findViewById(R.id.drawer_contact_us);
            menuItem.setChecked(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
