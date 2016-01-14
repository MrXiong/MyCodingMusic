package com.music.zx.codingmusic.utils.music;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zx on 2016/1/12.
 */
public class TimeUtils {
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("mm:ss");
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }
}
