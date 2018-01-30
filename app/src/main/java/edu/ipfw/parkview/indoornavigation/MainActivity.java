package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity  {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle; //must use v7.app
    private FragmentManager fragmentManager;
    private Class fragmentClass;
    private Fragment fragment;
    private MenuItem menuItem;

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
        Intent intent;
        switch(menuItem.getItemId()) {

            case R.id.drawer_main_menu:
                needFrag = false;
                intent = new Intent(MainActivity.this, MainActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;
            case R.id.drawer_map:
                needFrag = false;
                intent = new Intent(MainActivity.this, MapActivity.class);
                this.finishActivity(0);
                startActivity(intent);
                break;

            case R.id.drawer_upcoming_events:
                fragmentClass = UpcomingEventsFragment.class;
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
        Intent startMapActivity = new Intent(this, MapActivity.class);
        startActivity(startMapActivity);
    }

    public void onEventsButtonClick(View v){
        try{
            fragmentClass = UpcomingEventsFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.clMainMenu, fragment).commit();
            menuItem = (MenuItem)findViewById(R.id.drawer_upcoming_events);
            menuItem.setChecked(true);

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

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
