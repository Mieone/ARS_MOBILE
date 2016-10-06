package heardun.in.ars.firebase;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 3/10/16.
 */
public class ARSFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private String TAG = ARSFirebaseInstanceIDService.class.getSimpleName();

    Utils utils;

    public ARSFirebaseInstanceIDService() {


        // utils = new Utils(getApplicationContext());
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        utils.showLog(TAG, "Refreshed token: " + refreshedToken, Config.ARSFirebaseInstanceIDService);

        utils = new Utils();

        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token", refreshedToken);
        broadcastManager.sendBroadcast(intent);

        // Toast.makeText(this, refreshedToken, Toast.LENGTH_LONG).show();
        // sendRegistrationToServer(refreshedToken);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);

        utils.showLog(TAG, "reggister call back" + callback.toString(), Config.ARSFirebaseInstanceIDService);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        utils.showLog(TAG, "send to server Refreshed token: " + token, Config.ARSFirebaseInstanceIDService);
        utils.update_FCMToken(token);
    }
}
