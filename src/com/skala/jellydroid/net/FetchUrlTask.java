package com.skala.jellydroid.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;

import com.skala.jellydroid.net.ApiHelper.StringResponseListener;
import com.skala.jellydroid.util.Utils;

class FetchUrlTask extends AsyncTask<Object, Void, String> {
	private final Context mContext;
	private StringResponseListener mListener;
	private final AjaxStatus mStatus;

	public FetchUrlTask(Context context) {
		mContext = context;
		mStatus = new AjaxStatus();
	}

	@Override
	protected String doInBackground(Object... params) {
		String url = (String) params[0];
		mListener = (StringResponseListener) params[1];

		if (!Utils.isOnline(mContext)) {
			mStatus.setCode(AjaxStatus.NETWORK_ERROR);
			return null;
		}

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips, "UTF-8"));

			StringBuilder sb = new StringBuilder();
			String s;
			while (true) {
				s = buf.readLine();
				if (s == null || s.length() == 0)
					break;
				sb.append(s);
			}
			buf.close();
			ips.close();

			mStatus.setCode(AjaxStatus.SUCCESS);
			return sb.toString();
		} catch (IOException e) {
			mStatus.setCode(AjaxStatus.NETWORK_ERROR);
		} catch (URISyntaxException e) {
			mStatus.setCode(AjaxStatus.NETWORK_ERROR);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		mListener.onComplete(result, mStatus);
	}

}
