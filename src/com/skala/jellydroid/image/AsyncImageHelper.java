package com.skala.jellydroid.image;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.skala.jellydroid.file.FileDownloader;
import com.skala.jellydroid.file.FileDownloader.FileDownloadListener;

/**
 * Helper that makes loading remote images quick and easy.
 * @author Matous Skala
 *
 */
public class AsyncImageHelper {
	private final FileDownloader mDownloader = new FileDownloader();

	public AsyncImageHelper() { }

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param image ImageView to which load the image.
	 * @param url Remote image URL.
	 */
	public void loadImage(ImageView image, String url) {
		loadImage(image, url, null);
	}

	/**
	 * Loads image from URL into ImageView asynchronously.
	 * @param image ImageView to which load the image.
	 * @param url Remote image URL.
	 * @param listener Listener to be notified when load is finished.
	 */
	public void loadImage(final ImageView image, String url, final AsyncImageListener listener) {
		mDownloader.download(url, new FileDownloadListener() {
			@Override
			public void onSuccess(File file) {
				Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
				image.setImageBitmap(bm);

				if (listener != null) {
					listener.onLoad(bm);
				}
			}
		});
	}

	public void cancel() {
		mDownloader.cancel();
	}

	public interface AsyncImageListener {
		public void onLoad(Bitmap bitmap);
	}
}
