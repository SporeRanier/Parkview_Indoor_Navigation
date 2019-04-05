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

import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;


public class CampaignReceiver extends  CampaignBroadcastReceiver {
    private final int NOTIFICATION_ID = 001;
    public static final String CHANNEL_ID = "Meridian Notification";
    int hasRun;
    protected void  onCreate(Bundle savedInstanceState) {
    hasRun = 0;
    }

    @Override
    protected void onReceive(Context context, Intent intent, String title, String message) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

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

    public int getHasRun() {
        return hasRun;
    }

    public void setHasRun(int hasRun) {
        this.hasRun = hasRun;
    }
}