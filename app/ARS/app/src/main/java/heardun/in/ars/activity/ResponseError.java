package heardun.in.ars.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


import butterknife.BindView;
import butterknife.ButterKnife;
import heardun.in.ars.R;

/**
 * Created by headrun on 12/10/15.
 */
public class ResponseError extends Activity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.response_error);
        ButterKnife.bind(this);

        Bundle data=getIntent().getExtras();
        String error_data=data.getString("error");

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);


        webview.loadData(error_data, "text/html", "UTF-8");

    }
}
