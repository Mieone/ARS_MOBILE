package heardun.in.ars.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import heardun.in.ars.config.Config;
import heardun.in.ars.utils.Utils;

/**
 * Created by sujith on 18/5/16.
 */
public class MainActivity extends Activity {

    public String TAG = MainActivity.class.getSimpleName();
    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils(this);

        utils.showLog(TAG, "main activity", Config.MainActivity);

        utils.getwindowsize();
        if (utils.usersession.getusersession().isEmpty())
            startActivity(new Intent(this, LoginActivity.class));
        else
            startActivity(new Intent(this, MenuAtivity.class));

    }
}


