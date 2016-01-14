package com.music.zx.codingmusic.utils;

/**
 * Created by zx on 2015/12/14.
 */
public class ArrayUtils {
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }
}
