package com.example.samplenetworknavermovie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

public class CacheManager {
	private static CacheManager instance;
	public static CacheManager getInstance() {
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	private CacheManager() {
		cacheDir = MyApplication.getContext().getExternalCacheDir();
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
	}
	
	HashMap<String, WeakReference<Bitmap>> mMemCache = new HashMap<String, WeakReference<Bitmap>>();
	
	public void saveMemoryCache(String key, Bitmap bitmap) {
		WeakReference<Bitmap> v = mMemCache.get(key);
		if (v != null) {
			mMemCache.remove(key);
		}
		v = new WeakReference<Bitmap>(bitmap);
		mMemCache.put(key, v);
	}
	
	public Bitmap getMemoryCache(String key) {
		WeakReference<Bitmap> v= mMemCache.get(key);
		if (v == null) {
			return null;
		}
		Bitmap bm = v.get();
		if (bm == null) {
			mMemCache.remove(key);
		}
		return bm;
	}

	File cacheDir;
	
	public void saveFile(String key, Bitmap bitmap) {
		File file = new File(cacheDir, key);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public Bitmap getFile(String key) {
		File file = new File(cacheDir, key);
		try {
			FileInputStream fis = new FileInputStream(file);
			Bitmap bm = BitmapFactory.decodeStream(fis);
			saveMemoryCache(key, bm);
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Bitmap getCache(String key) {
		Bitmap bm = getMemoryCache(key);
		if (bm == null) {
			bm = getFile(key);
		}
		return bm;
	}
	
	public void saveCache(String key, Bitmap bitmap) {
		saveFile(key, bitmap);
		saveMemoryCache(key, bitmap);
	}
}
