package com.dstrube.paypalphotoviewer.service;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * This class represents a memory cache
 * @author david.strube
 *(Copied from Nirmal Thakur)
 */
public class MemoryCache {
	private final Map<String, SoftReference<Bitmap>> cache=Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());
    
    public Bitmap get(String id){
        if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref=cache.get(id);
        assert ref != null;
        return ref.get();
    }
    
    public void put(String id, Bitmap bitmap){
        cache.put(id, new SoftReference<>(bitmap));
    }

    public void clear() {
        cache.clear();
    }
}
