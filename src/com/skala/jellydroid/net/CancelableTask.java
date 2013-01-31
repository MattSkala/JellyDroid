package com.skala.jellydroid.net;


import android.os.AsyncTask;

class CancelableTask implements Cancelable {
	AsyncTask<?, ?, ?> mAsyncTask;

	CancelableTask(AsyncTask<?, ?, ?> task) {
		mAsyncTask = task;
	}

	@Override
	public void cancel() {
		if (mAsyncTask != null) {
			mAsyncTask.cancel(true);
		}
	}
}
