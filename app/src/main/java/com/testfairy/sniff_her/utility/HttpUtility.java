package com.testfairy.sniff_her.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

// Copied from "https://developer.android.com/training/volley/requestqueue#java"
public class HttpUtility {

    private static @NonNull HttpUtility mInstance;
    private @NonNull RequestQueue mRequestQueue;
    private @NonNull ImageLoader mImageLoader;

    private HttpUtility(@NonNull Context context) {
        ObjectUtil.assertNotNull(context);

        mRequestQueue = getRequestQueue(context);
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20);

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

    @NonNull
    public static synchronized HttpUtility getInstance(@NonNull Context context) {
        ObjectUtil.assertNotNull(context);

        if (mInstance == null) {
            mInstance = new HttpUtility(context);
        }

        return mInstance;
    }

    @NonNull
    public RequestQueue getRequestQueue(@NonNull Context context) {
        ObjectUtil.assertNotNull(context);

        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(@NonNull Context context, @NonNull Request<T> req) {
        ObjectUtil.assertNotNull(context, req);

        getRequestQueue(context).add(req);
    }

    @NonNull
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    // HTTP utility methods
    @NonNull
    public static Observable<JSONObject> httpGetJsonObject(@NonNull final Context context, @NonNull final String url) {
        ObjectUtil.assertNotNull(context, url);
        StringUtil.assertIsUrl(url);

        return Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(final ObservableEmitter<JSONObject> emitter) {
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emitter.onError(error);
                    }
                });

                getInstance(context).addToRequestQueue(context, req);
            }
        });
    }

    @NonNull
    public static Observable<JSONArray> httpGetJsonArray(@NonNull final Context context, @NonNull final String url) {
        ObjectUtil.assertNotNull(context, url);
        StringUtil.assertIsUrl(url);

        return Observable.create(new ObservableOnSubscribe<JSONArray>() {
            @Override
            public void subscribe(final ObservableEmitter<JSONArray> emitter) {
                JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emitter.onError(error);
                    }
                });

                getInstance(context).addToRequestQueue(context, req);
            }
        });
    }

    @NonNull
    public static Observable<JSONObject> httpPostJsonObject(@NonNull final Context context, @NonNull final String url, @Nullable final JSONObject body) {
        ObjectUtil.assertNotNull(context, url);
        StringUtil.assertIsUrl(url);

        return Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(final ObservableEmitter<JSONObject> emitter) {
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        emitter.onNext(response);
                        emitter.onComplete();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emitter.onError(error);
                    }
                });

                getInstance(context).addToRequestQueue(context, req);
            }
        });
    }
}
