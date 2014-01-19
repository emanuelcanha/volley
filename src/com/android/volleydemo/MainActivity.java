package com.android.volleydemo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import com.android.players.R;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends Activity {
	private static final String IMAGE_URL = "http://emanuelrc.herokuapp.com/assets/homer.png";
	private static final String DATA_URL = "http://emanuelrc.herokuapp.com/players/1.json";

	private MyApplication mMyApplication;
	private RequestQueue mRequestQueue;

	private TextView mName;
	private NetworkImageView mAvatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// MyApplication singleton object
		mMyApplication = (MyApplication) getApplicationContext();

		// RequestQueue singleton object
		mRequestQueue = mMyApplication.getRequestQueue();

		mName = (TextView) findViewById(R.id.textView_name);
		mAvatar = (NetworkImageView) findViewById(R.id.networkImageView_avatar);

		getData();
		getImage();

	}

	// JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url, jsonRequest, listener, errorListener)
	private void getData() {

		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, DATA_URL, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						setUserData(response);
					}

				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("HTTP error: " + String.valueOf(error.networkResponse.statusCode),
								"Something went wrong: " + error.getMessage());
						// TODO: Handle errors
					}
				});

		mRequestQueue.add(jsonRequest);
	}

	private void getImage() {
		ImageLoader imageLoader = mMyApplication.getImageLoader();
		mAvatar.setImageUrl(IMAGE_URL, imageLoader);
	}

	private void setUserData(JSONObject data) {
		String name = "";

		try {
			name = data.getString("name");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		mName.setText(name);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mRequestQueue.cancelAll(mMyApplication);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
