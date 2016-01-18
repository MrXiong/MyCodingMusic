package com.music.zx.codingmusic.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by zx on 2016/1/14.
 */
public class IApplication extends Application {

    public static Context mContext;
    public static SharedPreferences mSp;
    public DbManager.DaoConfig mDaoConfig;
    public DbManager mDb;

    @Override
    public void onCreate() {
        super.onCreate();
        mSp = getSharedPreferences(Contant.SP_NAME, Context.MODE_PRIVATE);
        // TODO: ...
// db.addColumn(...);
// db.dropTable(...);
// ...
        mDaoConfig = new DbManager.DaoConfig()
                .setDbName(Contant.DB_NAME)
                .setDbDir(new File("/sdcard"))
                .setDbVersion(2)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                    }
                });
        mDb = x.getDb(mDaoConfig);
        mContext = getApplicationContext();
    }
}
