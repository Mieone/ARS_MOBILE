package heardun.in.ars.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import heardun.in.ars.R;
import heardun.in.ars.activity.MainActivity;
import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 3/10/16.
 */
public class ARSFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = ARSFirebaseMessagingService.class.getSimpleName();

    Utils utils = new Utils();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        utils.showLog(TAG, "message " + remoteMessage.toString(), Config.ARSFirebaseMessagingService);

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            utils.showLog(TAG, "Message data payload: " + remoteMessage.getData(), Config.ARSFirebaseMessagingService);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            utils.showLog(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody(), Config.ARSFirebaseMessagingService);
            sendNotification(remoteMessage.getNotification().getBody());
        }

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.img_logo)
                .setContentTitle("ARS Alert")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
