package heardun.in.ars;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by headrun on 31/8/15.
 */
public class ArsApplication extends Application {

    private static final String TAG = ArsApplication.class.getSimpleName();

    private static ArsApplication instance = null;


    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public static ArsApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();


        instance = this;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final android.support.v4.util.LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });


    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}


