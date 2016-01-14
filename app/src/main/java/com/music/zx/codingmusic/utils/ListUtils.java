package com.music.zx.codingmusic.utils;

import java.util.List;

/**
 * Created by zx on 2015/12/14.
 */
public class ListUtils {
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.isEmpty());
    }
}
