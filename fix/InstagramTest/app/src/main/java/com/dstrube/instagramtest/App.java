package com.dstrube.instagramtest;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


//
//On the app class is the request queue and the image loader needed to use volley
//
public class App  extends Application {
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    //
    //Volley related
    //
    public static ImageLoader getImageLoader() {
        if (imageLoader == null){
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            imageLoader = new ImageLoader(getRequestQueue(), imageCache);
        }
        return imageLoader;
    }
    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(instance.getApplicationContext());
        }
        return requestQueue;
    }
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    //This class is the cache for the images
    private static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
        public BitmapLruCache() {
            this(getDefaultLruCacheSize());
        }

        public BitmapLruCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(@NonNull String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        public static int getDefaultLruCacheSize() {
            final int maxMemory =
                    (int) (Runtime.getRuntime().maxMemory() / 1024);

            return maxMemory / 8;
        }
    }

}
