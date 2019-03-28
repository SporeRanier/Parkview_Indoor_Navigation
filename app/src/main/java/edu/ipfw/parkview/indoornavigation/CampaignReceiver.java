package edu.ipfw.parkview.indoornavigation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.arubanetworks.meridian.campaigns.CampaignBroadcastReceiver;


public class CampaignReceiver extends  CampaignBroadcastReceiver {

    @Override
    protected void onReceive(Context context, Intent intent, String title, String message) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify("com.arubanetworks.meridiansamples.CampaignReceiver".hashCode(), builder.build());
    }}