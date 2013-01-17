package com.skala.jellydroid.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ApiHelper {
	FetchUrlTask mFetchUrlTask;
	Context mContext;

	public ApiHelper(Context context) {
		mContext = context;
	}

	public void fetchString(String url, StringResponseListener listener) {
		new FetchUrlTask(mContext).execute(url, listener);
	}

	public void fetchJson(String url, final JsonResponseListener listener) {
		fetchString(url, new StringResponseListener() {
			@Override
			public void onSuccess(String stringResponse) {
				JSONObject jsonResponse = null;
				if (stringResponse == null || stringResponse.length() == 0) {
					listener.onSuccess(null);
					return;
				}

				try {
					jsonResponse = new JSONObject(stringResponse);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				listener.onSuccess(jsonResponse);
			}

			@Override
			public void onError(Exception e) {
				listener.onError(e);
			}
		});
	}

	public interface StringResponseListener {
		public void onSuccess(String response);
		public void onError(Exception e);
	}

	public interface JsonResponseListener {
		public void onSuccess(JSONObject response);
		public void onError(Exception e);
	}
}
