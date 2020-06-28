package com.adisalagic.fuelratsirc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.adisalagic.fuelratsirc.ui.fuelrats.FuelRats;

import java.util.Objects;

public class Listener extends Service {
    private static final int    NOTIF_ID         = 1;
    private static       String NOTIF_CHANNEL_ID = "com.adisalagic.fuelratsirc";
    private Thread thread;
    public Listener() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                IRClient.getInstance().startBot();
//                System.exit(0);
            }
        });
        thread.start();
        startForeground();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NOTIF_CHANNEL_ID = createNotificationChannel("IRC Chat", "ListenerService");
        }
        Intent        notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent      = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        startForeground(NOTIF_ID, new Notification.Builder(this, NOTIF_CHANNEL_ID)
                .setOngoing(true)
                .setSmallIcon(R.drawable.fuel_rats_logo_2)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Chat is running on background")
                .setContentIntent(pendingIntent)
                .build());
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel(String channelId, String channelName) {
        NotificationChannel chan = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);
        return channelId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null){
            thread.interrupt();
        }
    }
}
