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

import android.os.AsyncTask;

import com.skala.jellydroid.net.ApiHelper.StringResponseListener;

class FetchUrlTask extends AsyncTask<Object, Void, String> {

	private StringResponseListener mListener;

	@Override
	protected String doInBackground(Object... params) {
		// TODO: check internet connection

		String url = (String) params[0];
		mListener = (StringResponseListener) params[1];

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

			return sb.toString();
		} catch (IOException e) {
			mListener.onError(e);
		} catch (URISyntaxException e) {
			mListener.onError(e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		mListener.onSuccess(result);
	}

}
