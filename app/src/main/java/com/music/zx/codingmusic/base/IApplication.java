package com.music.zx.codingmusic.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.music.zx.codingmusic.service.Contant;

/**
 * Created by zx on 2016/1/14.
 */
public class IApplication extends Application {

    public static SharedPreferences mSp;
    @Override
    public void onCreate() {
        super.onCreate();
        mSp = getSharedPreferences(Contant.SP_NAME, Context.MODE_PRIVATE);
    }
}
