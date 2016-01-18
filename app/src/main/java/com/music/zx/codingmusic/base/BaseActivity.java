package com.music.zx.codingmusic.base;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.music.zx.codingmusic.service.PlayService;

/**
 * Created by zx on 2016/1/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements PlayService.MusicUpdateListener {

    private boolean isBind;
    public PlayService mPlayService;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayService.PlayBinder binder = (PlayService.PlayBinder) service;
            mPlayService = binder.getPlayService();
            mPlayService.setMusicUpdateListener(BaseActivity.this);
            onChange(mPlayService.getThisCurrentPosition());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPlayService = null;
            isBind = false;
        }
    };
    //绑定服务
    public void bindPlayService(){
        if(isBind == false) {
            Intent intent = new Intent(this,PlayService.class);
            bindService(intent,connection, Context.BIND_AUTO_CREATE);
            isBind = true;
        }
    }
    public void unBindPlayService(){
        if(isBind) {
            unbindService(connection);
            isBind = false;
        }
    }
    public void start(int position){
        if(mPlayService != null) {
            mPlayService.play(position);
        }
    }
    public boolean isPlaying(){
        if(mPlayService != null) {
            return mPlayService.isPlaying();
        }
        return false;
    }

    @Override
    public void onPublish(int progress) {
        publish(progress);
    }

    @Override
    public void onChange(int position) {
        change(position);
    }

    public abstract void publish(int progress);
    public abstract void change(int position);
}
