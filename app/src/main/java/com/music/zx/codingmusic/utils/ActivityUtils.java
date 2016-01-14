package com.music.zx.codingmusic.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by zx on 2015/12/25.
 */
public class ActivityUtils {
    public static void sendActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
