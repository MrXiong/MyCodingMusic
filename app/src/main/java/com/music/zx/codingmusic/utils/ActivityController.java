package com.music.zx.codingmusic.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

	public static List<Activity> listActivity = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		if(!listActivity.contains(activity))
		listActivity.add(activity);
	}

	public static void removeActivity(Activity activity) {
		listActivity.remove(activity);
	}

	public static void finishAll() {
		for (Activity activity : listActivity) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}

}
