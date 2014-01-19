package com.android.volleydemo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity extends Activity implements OnClickListener {
	private static final String DATA_URL = "http://graph.facebook.com/MadLabUK";
	private static final String IMAGE_URL1 = "http://scontent-a.xx.fbcdn.net/hphotos-ash2/t1/s720x720/293792_541411519206596_1968206097_n.jpg";
	private static final String IMAGE_URL2 = "https://fbcdn-sphotos-g-a.akamaihd.net/hphotos-ak-frc3/1461709_810088515672227_1726228651_n.jpg";

	private RequestQueue mRequestQueue;
	ImageLoader mImageLoader;

	private TextView mName, mAbout;
	private NetworkImageView mAvatar;
	private Button mImageOne, mImageTwo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		MyApplication myApplication = (MyApplication) getApplicationContext();
		// get RequestQueue and ImageLoader
		mRequestQueue = myApplication.getRequestQueue();
		mImageLoader = myApplication.getImageLoader();

		mName = (TextView) findViewById(R.id.textView_name);
		mAvatar = (NetworkImageView) findViewById(R.id.networkImageView_avatar);
		mAbout = (TextView) findViewById(R.id.textView_about);
		mImageOne = (Button) findViewById(R.id.button_image1);
		mImageOne.setOnClickListener(this);
		mImageTwo = (Button) findViewById(R.id.button_image2);
		mImageTwo.setOnClickListener(this);

		getData();
		getImage(IMAGE_URL1);
	}

	private void getData() {

		// JsonObjectRequest jsonRequest = new JsonObjectRequest(method, url,
		// jsonObject, listener, errorListener)
		JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, DATA_URL, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						setData(response);
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("Volley ERROR", "Message: " + error.getMessage());
					}
				});

		mRequestQueue.add(jsonRequest);
	}

	private void setData(JSONObject data) {
		String name = "";
		String about = "";

		try {
			name = data.getString("name");
			about = data.getString("about");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		mName.setText(name);
		mAbout.setText(about);
	}

	private void getImage(String imageUrl) {
		// NetworkImageView.setImageUrl(url, imageLoader)
		mAvatar.setImageUrl(imageUrl, mImageLoader);
	}

	@Override
	public void onClick(View v) {

		mName.setText("");
		mAbout.setText("");
		getData();
		if (v == mImageOne) {
			getImage(IMAGE_URL1);
		} else {
			getImage(IMAGE_URL2);
		}
	}
}
