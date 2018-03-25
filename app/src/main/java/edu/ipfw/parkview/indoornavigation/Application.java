package edu.ipfw.parkview.indoornavigation;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;

public class Application extends android.app.Application {

    public static final EditorKey APP_KEY = EditorKey.forApp("6555052652625920");
    public static final EditorKey MAP_KEY = EditorKey.forMap("5649391675244544", APP_KEY);
    public UserData userData;
    @Override
    public void onCreate() {
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setForceSimulatedLocation(true);
        super.onCreate();
    }

    public void setUserData(UserData userData){
        this.userData = userData;
    }

}
