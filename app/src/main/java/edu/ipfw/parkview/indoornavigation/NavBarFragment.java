package edu.ipfw.parkview.indoornavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by mcshdd01 on 12/26/2017.
 */

public class NavBarFragment extends Fragment {
    private NavListener listener;
    private Button DirectoryBtn;
    private Button WaitBtn;
    private Button DirectionsBtn;
    private Button ContactBtn;
    private Button HelpBtn;


    public void changeActivity(View view){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navbar_fragment, container, false);
        DirectoryBtn = (Button) view.findViewById(R.id.DirectoryBtn);
        WaitBtn = (Button) view.findViewById(R.id.WaitBtn);
        DirectionsBtn = (Button) view.findViewById(R.id.DirectionsBtn);
        ContactBtn = (Button) view.findViewById(R.id.ContactBtn);
        HelpBtn = (Button) view.findViewById(R.id.HelpBtn);
        listener = new NavListener();
        DirectoryBtn.setOnClickListener(listener);
        WaitBtn.setOnClickListener(listener);;
        DirectionsBtn.setOnClickListener(listener);;
        ContactBtn.setOnClickListener(listener);;
        HelpBtn.setOnClickListener(listener);;

        return view;
    }

    //TODO: redirect to proper activites
    private class NavListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(getActivity(), RouteActivity.class);
            //startActivity(intent);
        }
    }
}
