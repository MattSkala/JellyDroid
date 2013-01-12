package com.skala.jellydroid.image;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.skala.jellydroid.file.FileDownloader;
import com.skala.jellydroid.file.FileDownloader.FileDownloadListener;

/**
 * ImageView that can load remote images quickly and easily.
 * @author Matous Skala
 *
 */
public class AsyncImageView extends ImageView {
	private final FileDownloader mDownloader = new FileDownloader();

	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param url Remote image URL.
	 */
	public void loadImage(String url) {
		loadImage(url, null);
	}

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param url Remote image URL.
	 * @param listener Listener to be notified when load is finished.
	 */
	public void loadImage(String url, final AsyncImageListener listener) {
		mDownloader.download(url, new FileDownloadListener() {
			@Override
			public void onSuccess(File file) {
				Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
				AsyncImageView.this.setImageBitmap(bm);

				if (listener != null) {
					listener.onLoad(bm);
				}
			}
		});
	}

	public interface AsyncImageListener {
		public void onLoad(Bitmap bitmap);
	}
}
