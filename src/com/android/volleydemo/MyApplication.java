package com.android.volleydemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {
	RequestQueue mRequestQueue;
	ImageLoader mImageLoader;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		mRequestQueue = Volley.newRequestQueue(this);
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLruCache());
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

}
