package com.skala.jellydroid.ui;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.skala.jellydroid.net.Cancelable;
import com.skala.jellydroid.net.FileDownloader;
import com.skala.jellydroid.net.FileDownloader.FileDownloadListener;

/**
 * ImageView that can load remote images quickly and easily.
 * @author Matous Skala
 *
 */
public class AsyncImageView extends ImageView {
	private final FileDownloader mDownloader;
	private Cancelable mCancelable;

	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mDownloader = new FileDownloader(context);
	}

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param url Remote image URL.
	 */
	public Cancelable loadImage(String url) {
		return loadImage(url, null);
	}

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param url Remote image URL.
	 * @param listener Listener to be notified when load is finished.
	 */
	public Cancelable loadImage(String url, final AsyncImageListener listener) {
		setImageResource(0);
		if (mCancelable != null) {
			mCancelable.cancel();
		}
		mCancelable = mDownloader.download(url, new FileDownloadListener() {
			@Override
			public void onSuccess(File file) {
				Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
				AsyncImageView.this.setImageBitmap(bm);

				if (listener != null) {
					listener.onLoad(bm);
				}
			}
		});
		return mCancelable;
	}

	public interface AsyncImageListener {
		public void onLoad(Bitmap bitmap);
	}
}
