package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main_menu);
    }

    public void onButtonClick(View v) {
        Intent startMapActivity = new Intent(this, MapActivity.class);
        startActivity(startMapActivity);
    }

}
