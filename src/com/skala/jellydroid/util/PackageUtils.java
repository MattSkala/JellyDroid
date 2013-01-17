package com.skala.jellydroid.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils {

	/**
	 * Returns app data directory path.
	 * @param context Application context.
	 */
	public static String getDataDir(Context context) {
		String dataDir = "Android/data/.jellydroid";
		try {
			PackageManager pm = context.getPackageManager();
			String packageName = context.getPackageName();
			PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
			dataDir = packageInfo.applicationInfo.dataDir;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return dataDir;
	}

}
