package edu.ipfw.parkview.indoornavigation;

import com.arubanetworks.meridian.Meridian;
import com.arubanetworks.meridian.editor.EditorKey;

/* Initialize static and constant fields of our application*/
public class Application extends android.app.Application {

    public static final EditorKey APP_KEY = EditorKey.forApp("6555052652625920");
    public static final EditorKey MAP_KEY = EditorKey.forMap("5649391675244544", APP_KEY);
    public static final String PLACEMARK_UID = "5649391675244544_5738600293466112";
    public static final String CAMPAIGN_ID = "5664248772427776"; // unique id for one of your campaigns here.
    public static final String EDITOR_TOKEN = "f477d4c144c1541e6452b812bc3b194d4770b6c4"; // your editor token here
    public UserData userData;
    static boolean firstStart;
    @Override
    public void onCreate() {
        firstStart = true;
        Meridian.configure(this);
        Meridian.getShared().initGoalsForLocation(APP_KEY.toString());
        Meridian.getShared().setEditorToken(EDITOR_TOKEN);
        CampaignReceiver.CreateNotificationChannel(this);
        super.onCreate();
    }

    public static void setFirstStart() {
        firstStart = false;
    }


    public void setUserData(UserData userData){
        this.userData = userData;
    }

    public static boolean firstStart() {
        return firstStart;
    }
}
