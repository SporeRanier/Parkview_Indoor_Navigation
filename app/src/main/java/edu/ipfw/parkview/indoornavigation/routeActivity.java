package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class routeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
    }

    public void onMapClick(View v){
        Intent startMapActivity = new Intent(this, MapActivity.class);
        startActivity(startMapActivity);
    }

    public void onUpcomingEventsClick(View v){
        Intent startUpcomingEventsActivity = new Intent( this, UpcomingEvents.class);
        startActivity(startUpcomingEventsActivity);
    }

    public void onMostVisitedClick(View v){
        Intent startMostVisitedActivity = new Intent( this, MostVisitedLocations.class);
        startActivity(startMostVisitedActivity);
    }
}
