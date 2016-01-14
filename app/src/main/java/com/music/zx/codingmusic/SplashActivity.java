package com.music.zx.codingmusic;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.music.zx.codingmusic.base.BaseActivity;
import com.music.zx.codingmusic.service.Contant;
import com.music.zx.codingmusic.service.PlayService;

import cn.trinea.android.common.util.PreferencesUtils;

public class SplashActivity extends AppCompatActivity {

    private static final int START_ACTIVITY = 0X1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initSplash();

    }
    //handle和timer的方式
    private void initSplash() {
        //启动服务，先启动后绑定(那么ui组件（activity）要使用的话，就绑定就好了，用完了解绑，这样的话，service不会被销毁，一直存在，谁用谁绑定)
       Intent intent = new Intent(this, PlayService.class);
        startService(intent);
        handler.sendEmptyMessageDelayed(START_ACTIVITY,1000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_ACTIVITY :
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                    break;
            }
        }
    };
}
