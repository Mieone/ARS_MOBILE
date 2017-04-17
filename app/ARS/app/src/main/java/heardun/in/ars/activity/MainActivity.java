package heardun.in.ars.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 18/5/16.
 */
public class MainActivity extends Activity {

    public String TAG = MainActivity.class.getSimpleName();
    Utils utils;
    private FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        utils.showLog(TAG, "device id is " + utils.getDeviceID(), Config.MainActivity);

        utils.getwindowsize();
        if (utils.usersession.getusersession().isEmpty())
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, MenuAtivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}


