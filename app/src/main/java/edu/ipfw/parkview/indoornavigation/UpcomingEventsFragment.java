package edu.ipfw.parkview.indoornavigation;

/*  must extend android.support.v4.app.Fragment;  */
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/* Fragment subclasss for staff directory utilizing Directory class for feed*/
public class UpcomingEventsFragment extends Fragment {

    private ListView mListView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming_events, container, false);

        mListView = (ListView) view.findViewById(R.id.lvEvents);
        final ArrayList<UpcomingEvent> eventList = UpcomingEvent.getRecipesFromFile("upcomingevents.json", view.getContext());

        EventsAdapter adapter = new EventsAdapter(this.getContext(), eventList);
        mListView.setAdapter(adapter);
        return view;
    }
}
