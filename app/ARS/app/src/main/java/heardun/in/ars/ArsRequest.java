package heardun.in.ars;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Created by headrun on 9/9/15.
 */


public class ArsRequest {
    private static ArsRequest mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private ArsRequest(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
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

    public static synchronized ArsRequest getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ArsRequest(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        if (BuildConfig.DEBUG)
            Log.i("Log_TAG", "request" + req);
        getRequestQueue().add(req);
    }

    public void cancelRequestQueue(Object req) {
        if (mRequestQueue != null)
            getRequestQueue().cancelAll(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
