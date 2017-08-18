package com.example.bilal.arksolutions_jomwork.Helper;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by cyber on 5/12/2016.
 */
public class LruBitmapCache extends LruCache<String,Bitmap> implements ImageLoader.ImageCache {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */

public static int getDefaultCacheSize(){
    int memory=(int)(Runtime.getRuntime().maxMemory())/1024;
    int cacheSize=memory/8;
    return cacheSize;
}
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    public LruBitmapCache() {
        this(getDefaultCacheSize());
    }

    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
put (url, bitmap);
    }
}
