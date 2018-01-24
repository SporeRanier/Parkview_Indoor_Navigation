package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by mcshdd01 on 12/26/2017.
 */

public class NavBarFragment extends Fragment {



    public void changeActivity(View view){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navbar_fragment, container, false);
        return view;
    }

    //TODO: redirect to proper activites
    private class NavListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), RouteActivity.class);
            startActivity(intent);
        }
    }
}
