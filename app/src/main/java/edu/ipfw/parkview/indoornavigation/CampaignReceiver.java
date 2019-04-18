package edu.ipfw.parkview.indoornavigation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;

import java.util.HashMap;
import java.util.Map;


public class CampaignReceiver extends  CampaignBroadcastReceiver {
    public static String NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL";
    private final int NOTIFICATION_ID = 001;
    public static final String CHANNEL_ID = "Meridian Notification";
    private NotificationManagerCompat nm;
    int hasRun;
    protected void  onCreate(Bundle savedInstanceState) {
    hasRun = 0;
    }

    @Override


    protected void onReceive(Context context, Intent intent, String title, String message) {



        //NotificationManager nm = NotificationManagerCompat.from(this);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        nm.notify(1, notification);


        //nm.notify("com.arubanetworks.meridiansamples.CampaignReceiver".hashCode(), builder.build());
        //MainActivity.setHeader("onRecieve has run");
    }

    public static void receiveTest(Context context, Intent intent, String title, String message) {



        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_message_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        //nm.notify(1, notification);


        //nm.notify("com.arubanetworks.meridiansamples.CampaignReceiver".hashCode(), builder.build());
        //MainActivity.setHeader("onRecieve has run");
    }


    protected Map<String, String> getUserInfoForCampaign(Context context, String campaignIdentifier) {
        HashMap<String, String> map = new HashMap<>();
        map.put("UserKey1", "userData1");
        map.put("UserKey2", "userData2");
        map.put("UserKey3", "userData3");
        return map;
    }

    public void CreateNotificationManager(){
        //nm = NotificationManagerCompat.from(this);
    }

    public static void CreateNotificationChannel(Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, "Campaign Notifications", NotificationManager.IMPORTANCE_HIGH);


            // Configure the notification channel.
            notificationChannel.setDescription("Campaign Channel");
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public int getHasRun() {
        return hasRun;
    }

    public void setHasRun(int hasRun) {
        this.hasRun = hasRun;
    }
}