package edu.ipfw.parkview.indoornavigation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arubanetworks.meridian.campaigns.CampaignsService;
import com.arubanetworks.meridian.editor.EditorKey;

public class CampaignFragment extends Fragment {

    private static final String APP_KEY = "CampaignFragment.AppKey";

    public static CampaignFragment newInstance(EditorKey appKey) {
        CampaignFragment fragment = new CampaignFragment();
        if(appKey.getParent() != null) throw new IllegalArgumentException("appKey mus have null parent.");
        Bundle b = fragment.getArguments();
        if (b == null) b = new Bundle();
        b.putSerializable(APP_KEY, appKey);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final EditorKey appKey;
        Bundle arg = getArguments();
        if (arg == null || ((appKey = (EditorKey) arg.getSerializable(APP_KEY)) == null)){
            Toast.makeText(getActivity(), "No EditorKey passed to Fragment!", Toast.LENGTH_LONG).show();
            return new FrameLayout(getActivity());
        }

        View view = inflater.inflate(R.layout.campaign_example, container, false);
        Button startButton  = view.findViewById(R.id.start_button);
        if (startButton != null) startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignsService.startMonitoring(getActivity(), appKey);
            }
        });
        Button stopButton  = view.findViewById(R.id.stop_button);
        if (stopButton != null) stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignsService.stopMonitoring(getActivity());
            }
        });
        Button resetButton  = view.findViewById(R.id.reset_button);
        if (resetButton != null) resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignsService.resetAllCampaigns(getActivity(),appKey, null, null);
            }
        });
        Button resetIndividualButton  = view.findViewById(R.id.reset_single_button);
        if (resetIndividualButton != null) resetIndividualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CampaignsService.resetCampaign(getActivity(), appKey, Application.CAMPAIGN_ID, null, null);
            }
        });
        return view;
    }
}
