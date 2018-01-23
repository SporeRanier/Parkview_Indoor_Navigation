package edu.ipfw.parkview.indoornavigation;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;

public class Application extends android.app.Application {

    //TODO: Put these strings in values/strings.xml
    public static final EditorKey APP_KEY = EditorKey.forApp("f477d4c144c1541e6452b812bc3b194d4770b6c4");
    public static final EditorKey MAP_KEY = EditorKey.forMap("6555052652625920", APP_KEY);

    @Override
    public void onCreate() {
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setForceSimulatedLocation(true);
        super.onCreate();
    }

}
