package com.techbuddies.thalassemiabloodbank.FCMNotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.techbuddies.thalassemiabloodbank.R;

import java.util.Random;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

        private final String ADMIN_CHANNEL_ID ="admin_channel";

        String title,message;

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {

          //  final Intent intent = new Intent(this, MainActivity.class);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationID = new Random().nextInt(3000);

            if (VERSION.SDK_INT >= VERSION_CODES.O) {
                setupChannels(notificationManager);
            }

            title=remoteMessage.getNotification().getTitle();
            message=remoteMessage.getNotification().getBody();

            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getApplicationContext() , ADMIN_CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle(title)
                            .setContentText(message);
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }

        @RequiresApi(api = VERSION_CODES.O)
        private void setupChannels(NotificationManager notificationManager){
            CharSequence adminChannelName = "New notification";
            String adminChannelDescription = "Device to devie notification";

            NotificationChannel adminChannel;
            adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_HIGH);
            adminChannel.setDescription(adminChannelDescription);
            adminChannel.enableLights(true);
            adminChannel.setLightColor(Color.RED);
            adminChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(adminChannel);
            }
        }
}