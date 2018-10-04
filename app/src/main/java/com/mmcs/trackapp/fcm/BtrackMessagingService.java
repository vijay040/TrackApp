package com.mmcs.trackapp.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mmcs.trackapp.R;
import com.mmcs.trackapp.activity.DrawerActivity;
import com.mmcs.trackapp.activity.MessageActivity;
import com.mmcs.trackapp.activity.ReminderdetailActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class BtrackMessagingService extends FirebaseMessagingService {

    private static final String TAG = "BTRack";
    private static Intent intent;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e("dataChat", remoteMessage.getData() + "");
        Log.e("remoteMessage","************************"+remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.e(TAG, "Message getBodyLocalizationKey " + remoteMessage.getNotification().getBodyLocalizationKey());
            Log.e(TAG, "Message getTag " + remoteMessage.getNotification().getTag());
            Log.e(TAG, "Message getBodyLocalizationArgs " + remoteMessage.getNotification().getBodyLocalizationArgs());
            Log.e(TAG, "Message getTitle " + remoteMessage.getNotification().getTitle());

        }

        for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Log.e("*****************", "key, " + key + " value " + value);
        }

        String str = remoteMessage.getNotification().getTag();
        try {
            JSONObject o = new JSONObject(str);
            JSONArray a = o.getJSONArray("types");
            for (int i = 0; i < a.length(); i++) {
                Log.d("Type", a.getString(i));
            }
        }catch (Exception e){}
        Log.e("str****************",""+str);
        intent = new Intent(this, DrawerActivity.class);
        if( remoteMessage.getNotification().getTitle().contains("Messaged"))
            intent = new Intent(this, MessageActivity.class);
        /* if (remoteMessage.getNotification().getTag().equalsIgnoreCase("meeting"))
                intent = new Intent(this, ReminderdetailActivity.class);
            else if (params.get("type").equalsIgnoreCase("message"))
                intent = new Intent(this, MessageActivity.class);*/

        intent.putExtra("NOTIFICATION_VALUE", remoteMessage);

        sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());

    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, String title) {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}