package com.skala.jellydroid.api;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiHelper {
	FetchUrlTask mFetchUrlTask;

	public ApiHelper() { }

	public void fetchString(String url, StringResponseListener listener) {
		new FetchUrlTask().execute(url, listener);
	}

	public void fetchJson(String url, final JsonResponseListener listener) {
		fetchString(url, new StringResponseListener() {
			@Override
			public void onSuccess(String stringResponse) {
				JSONObject jsonResponse = null;
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
