package com.skala.jellydroid.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.os.Environment;

/**
 * FileDownloader helps with downloading and storing remote files.
 * @author Matous Skala
 *
 */
public class FileDownloader {
	private final static String CACHE_DIR = "Android/.jellydroid";

	private FileDownloadTask mDownloadTask;

	/**
	 * Downloads a file from URL and saves in external storage or loads from cache if already downloaded.
	 * @param url URL of the file.
	 * @param listener Listener to be notified about status.
	 */
	public void download(String url, FileDownloadListener listener) {
		File cachedFile = getCachedFile(url);
		if (cachedFile.exists()) {
			listener.onSuccess(cachedFile);
		} else {
			mDownloadTask = new FileDownloadTask(url, listener);
			mDownloadTask.execute();
		}
	}

	private File getCachedFile(String url) {
		return new File(getFileName(url));
	}

	private String getPath() {
		return Environment.getExternalStorageDirectory() + "/" + CACHE_DIR;
	}

	private String getFileName(String url) {
		return getPath() + "/" + url.hashCode();
	}

	private class FileDownloadTask extends AsyncTask<Object, Object, File> {
		private final String mUrl;
		private final FileDownloadListener mListener;

		public FileDownloadTask(String url, FileDownloadListener listener) {
			mUrl = url;
			mListener = listener;
		}

		@Override
		protected File doInBackground(Object... args) {
			File file = null;
			try {
				URL url = new URL(mUrl);
				URLConnection connection = url.openConnection();
				connection.connect();

				InputStream input = new BufferedInputStream(url.openStream());
				File path = new File(getPath());
				path.mkdirs();
				String fileName = getFileName(mUrl);
				OutputStream output = new FileOutputStream(fileName);

				byte data[] = new byte[1024];
				int count;
				while ((count = input.read(data)) != -1) {
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();

				file = new File(fileName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return file;
		}

		@Override
		protected void onPostExecute(File result) {
			if (result != null) {
				mListener.onSuccess(result);
			}
		}
	}

	public void cancel() {
		if (mDownloadTask != null) {
			mDownloadTask.cancel(true);
		}
	}

	public interface FileDownloadListener {
		public void onSuccess(File file);
	}
}
