package edu.ipfw.parkview.indoornavigation;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.Menu;
import android.widget.ListView;


import com.arubanetworks.meridian.editor.Placemark;
import com.arubanetworks.meridian.internal.util.Strings;
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
import com.arubanetworks.meridian.maps.Marker;
import com.arubanetworks.meridian.requests.MeridianRequest;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.app.PendingIntent.getActivity;


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
    private MapFragment mapFragment;
    private MeridianLocationManager locationManager;
    private Campaign campaign;
    private CampaignBroadcastReceiver campaignReceiver;
    private CampaignsService campaignServicer;
    private NotificationManagerCompat notificationManager;
    private EditText editTextMessage;
    private EditText editTextTitle;
    private NotificationChannel notificationChannel;
    private HttpHandler handler;
    private String url;
    private String response;
    private String placemarkInfo;
    private JSONArray placeArray;
    private Map<String, PFWPlacemark> placemarkMap = new HashMap<>();
    private String placeName;
    private String placeDesc;
    private String placeURL;
    private String placeSphere;
    private Button start;
    private String header;

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    private final int NOTIFICATION_ID = 001;




    private UserInfoDialog userInfo;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getPlacemarkInfo() {
        return placemarkInfo;
    }

    public void setPlacemarkInfo(String placemarkInfo) {
        this.placemarkInfo = placemarkInfo;
    }

    public JSONArray getPlaceArray() {
        return placeArray;
    }

    public void setPlaceArray(JSONArray placeArray) {
        this.placeArray = placeArray;
    }

    public Map<String, PFWPlacemark> getPlacemarkMap() {
        return placemarkMap;
    }

    public void setPlacemarkMap(Map<String, PFWPlacemark> placemarkMap) {
        this.placemarkMap = placemarkMap;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDesc() {
        return placeDesc;
    }

    public void setPlaceDesc(String placeDesc) {
        this.placeDesc = placeDesc;
    }

    public String getPlaceURL() {
        return placeURL;
    }

    public void setPlaceURL(String placeURL) {
        this.placeURL = placeURL;
    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main_menu);
        createNotificationChannels();
        start = (Button) findViewById(R.id.button3);
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
        placeName = null;
        placeDesc = null;
        placeURL = null;
        placeSphere = null;
        Map<String,Object> map = null;
        HttpHandler handler = new HttpHandler();
        url = "https://edit.meridianapps.com/api/locations/6555052652625920/placemarks?format=api";
        response = handler.makeServiceCall(url);
        placemarkInfo = loadJSONFromAsset();
        ObjectMapper mapper = new ObjectMapper();
        placeArray = null;
        try {
            map = mapper.readValue(placemarkInfo, Map.class);
            for(String key:map.keySet()) {
                Map placemarkInfoMap = new HashMap<String, Object>();

                List<HashMap<String,ArrayList>> listlist = new ArrayList<>();
                listlist = (List<HashMap<String, ArrayList>>) map.get(key);
                Iterator it = listlist.iterator();
                while(it.hasNext()) {
                    Map<String,String> dataMap = (Map<String, String>) it.next();
                    PFWPlacemark placemark = new PFWPlacemark();
                    placemark.setName((String) dataMap.get("name"));
                    placemark.setDescription((String) dataMap.get("description"));
                    placemark.setImageURL((String) dataMap.get("image_url"));
                    placemarkMap.put(placemark.getName(), placemark);
                }


            }
            placeArray = new JSONArray(placemarkInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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

        notificationManager = NotificationManagerCompat.from(this);

    }
    public void sendOnChannel1(View v){
        String title = "Parkview Navigation";
        String message = "Welcome! Where would you like to go?";
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("placemarks.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    private void testNotification(){
        String title = "Parkview Navigation";
        String message = "Welcome to Parkview Navigation! Where would you like to go?";
        //CampaignReceiver.CreateNotificationChannel(this);
        //Notification notification = new NotificationCompat.Builder(this, CampaignReceiver.notificationChannel);

    }

    private void resetCampaigns() {

        CampaignsService.resetAllCampaigns(this, Application.APP_KEY, new MeridianRequest.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                Toast.makeText(MainActivity.this, "Reset Campaigns Succeeded.", Toast.LENGTH_SHORT).show();
                CampaignsService.startMonitoring(MainActivity.this, Application.APP_KEY);
            }
        }, new MeridianRequest.ErrorListener() {
            @Override
            public void onError(Throwable tr) {
                Toast.makeText(MainActivity.this, "Reset Campaigns Failed: " + tr.getMessage(), Toast.LENGTH_LONG).show();
                CampaignsService.startMonitoring(MainActivity.this, Application.APP_KEY);
            }
        });
    }
    private void selectItem(int position) {

        // update the main content by replacing fragments
        final Fragment fragment;

        switch (position) {
            case 0:
                MapFragment.Builder builder = new MapFragment.Builder()
                        .setMapKey(Application.MAP_KEY);
                MapOptions mapOptions = MapOptions.getDefaultOptions();
                mapOptions.HIDE_OVERVIEW_BUTTON = true;
                builder.setMapOptions(mapOptions);
                // example: how to set placemark markers text size
                /*
                    MapOptions mapOptions = ((MapFragment) fragment).getMapOptions();
                    mapOptions.setTextSize(14);
                    builder.setMapOptions(mapOptions);
                */
                // example: how to start directions programmatically

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
                        /*for (PFWPlacemark placemark : mapFragment.getMapView().getPlacemarks()) {
                            if ("APPLE".equals(placemark.getName())) {
                                mapFragment.startDirections(DirectionsDestination.forPlacemarkKey(placemark.getKey()));
                            }
                        }*/
                    }

                    @Override
                    public void onMapRenderFinish() {

                    }

                    @Override
                    public void onMapLoadFail(Throwable tr) {
                        if (mapFragment.isAdded() && mapFragment.getActivity() != null) {
                            new AlertDialog.Builder(mapFragment.getActivity())
                                    .setTitle(getString(com.arubanetworks.meridian.R.string.mr_error_title))
                                    .setMessage(tr != null && !Strings.isNullOrEmpty(tr.getLocalizedMessage())
                                            ? tr.getLocalizedMessage()
                                            : getString(com.arubanetworks.meridian.R.string.mr_error_invalid_map))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(com.arubanetworks.meridian.R.string.mr_ok, null)
                                    .show();
                        }
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

                break;
            case 1:
                fragment = NearbyFragment.newInstance(Application.APP_KEY);
                break;
            case 2:
                fragment = SearchFragment.newInstance(Application.APP_KEY);
                break;
            case 3:
                fragment = CustomMarkerFragment.newInstance(Application.MAP_KEY);
                break;
            case 4:
                fragment = CampaignFragment.newInstance(Application.APP_KEY);
                break;
            case 5:
                //fragment = ScrollingFragment.newInstance(Application.MAP_KEY);
                break;
            case 6:
                fragment = LocationFragment.newInstance(Application.APP_KEY);
                break;
            case 7:
                //fragment = LocationSharingFragment.newInstance();
                break;
            case 8:
                //fragment = SingleMarkerIDFragment.newInstance(Application.APP_KEY, Application.PLACEMARK_UID, null);
                break;
            case 9:
                //fragment = TagsFragment.newInstance(Application.MAP_KEY);
                break;
            case 10:
                //fragment = SingleTagFragment.newInstance(Application.MAP_KEY, Application.TAG_MAC);
                break;
            case 11:
                //fragment = LocationSharingMapFragment.newInstance(Application.MAP_KEY);
                break;
            default:
                return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                //   .replace(R.id.container, fragment)
                .commit();

        //setTitle(getResources().getStringArray(R.array.section_titles)[position]);
        //drawerLayout.closeDrawer(drawerList);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Begin monitoring for Aruba Beacon-based Campaign events
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // This odd delay thing is due to a bug with 23 currently.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CampaignsService.startMonitoring(MainActivity.this, Application.APP_KEY);
                selectItem(0);
            }
        },1000);
    }
    protected void onStart() {
        locationManager.startListeningForLocation();
        campaignServicer.startMonitoring(MainActivity.this, Application.APP_KEY);
        selectItem(0);
        super.onStart();
    }

    protected void onStop() {
        locationManager.stopListeningForLocation();
        campaignServicer.stopMonitoring(getApplicationContext());
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
        mapOptions.HIDE_OVERVIEW_BUTTON = false;
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
        mapFragment.setMarkerEventListener(new MapView.MarkerEventListener() {
            @Override
            public boolean onMarkerSelect(Marker marker) {

                String stringo = marker.getCalloutTitle();
                String stringy = marker.getCalloutDetails();
                String stringu = marker.getDetails();
                String stringa = marker.getName();
                PFWPlacemark placemark = placemarkMap.get(stringa);
                placeName = placemark.getName();
                placeDesc = placemark.getDescription();
                stringo = Jsoup.parse(placeDesc).text();
                placeDesc = stringo;
                placeURL = placemark.getImageURL();
                placeSphere = "test";
                placemark.removeParagraph();
                if(stringa.equals("Bon Bons Coffee")){
                    placeSphere = "https://goo.gl/maps/JWfNBqQthE52";
                }

                //web service call
                Intent i = new Intent(MainActivity.this,Pop.class);
                Bundle b = new Bundle();
                b.putString("name",placeName);
                b.putString("desc",placeDesc);
                b.putString("url",placeURL);
                b.putString("photoSphere", placeSphere);
                i.putExtras(b);
                startActivity(i);

                return false;
            }

            @Override
            public boolean onMarkerDeselect(Marker marker) {
                return false;
            }

            @Override
            public Marker markerForPlacemark(Placemark placemark) {
                return null;
            }

            @Override
            public boolean onCalloutClick(Marker marker) {
                return false;
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

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"Startup Channel", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is the startup channel");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_1_ID,"Click Channel", NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("This is the click channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

    public void displayNotification(View v)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_2_ID);
        builder.setSmallIcon(R.drawable.ic_message_black_24dp);
        builder.setContentTitle("Parkview Navigation");
        builder.setContentText("Welcome to Parkview Navigation!");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());
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
                start.setVisibility(View.INVISIBLE);
                fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
                needFrag = false;
                break;

            case R.id.drawer_upcoming_events:
                fragmentClass = UpcomingEventsFragment.class;
                start.setVisibility(View.INVISIBLE);

                break;
            case R.id.drawer_directory:
                fragmentClass = DirectoryFragment.class;
                start.setVisibility(View.INVISIBLE);
                break;
            case R.id.drawer_contact_us:
                fragmentClass = ContactUsActivity.class;
                start.setVisibility(View.INVISIBLE);
                break;
            case R.id.drawer_wait_time:
                fragmentClass = WaitTimeFragment.class;
                start.setVisibility(View.INVISIBLE);
                break;
            default:
                start.setVisibility(View.INVISIBLE);
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
        start.setVisibility(View.INVISIBLE);
        sendOnChannel1(v);
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

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}