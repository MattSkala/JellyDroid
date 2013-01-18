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
			public void onComplete(String stringResponse, AjaxStatus status) {
				if (status.getCode() != AjaxStatus.SUCCESS) {
					listener.onComplete(null, status);
				}

				JSONObject jsonResponse = null;
				try {
					jsonResponse = new JSONObject(stringResponse);
				} catch (JSONException e) {
					status.setCode(AjaxStatus.PARSE_ERROR);
				}

				listener.onComplete(jsonResponse, status);
			}
		});
	}

	public interface StringResponseListener {
		public void onComplete(String response, AjaxStatus status);
	}

	public interface JsonResponseListener {
		public void onComplete(JSONObject response, AjaxStatus status);
	}
}
