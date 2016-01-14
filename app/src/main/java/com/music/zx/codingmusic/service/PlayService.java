package com.music.zx.codingmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import com.music.zx.codingmusic.entity.Mp3Info;
import com.music.zx.codingmusic.utils.music.MusicUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.trinea.android.common.util.PreferencesUtils;

//注意：所有涉及到公共状态值的改变都放在service中去做，其他地方就可以绑定获得了，从而解决每个页面的同步问题
//音乐播放的服务组件（后台播放）
//实现功能：播放，暂停，下一首，上一首，当前播放进度
public class PlayService extends Service implements MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener {
    private MediaPlayer mMediaPlayer;
    private int currentPosition;//表示当前正在播放哪首歌
    private List<Mp3Info> mMp3Infos;
    private MusicUpdateListener mMusicUpdateListener;
    private boolean isPause;
    private ExecutorService mEs = Executors.newSingleThreadExecutor();
    //播放模式
    public int play_mode = Contant.ORDER_PLAY_LOOP;

    public int getPlay_mode() {
        return play_mode;
    }

    public void setPlay_mode(int play_mode) {
        this.play_mode = play_mode;
    }

    public PlayService() {
    }
    Runnable updateState = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if(mMusicUpdateListener != null) {
                        mMusicUpdateListener.onPublish(getCurrentProgress());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
       return new PlayBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentPosition = PreferencesUtils.getInt(this,Contant.SP_THIS_CURRENTPOSITION,0);
        play_mode = PreferencesUtils.getInt(this,Contant.SP_PLAY_MODE,Contant.ORDER_PLAY_LOOP);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMp3Infos = MusicUtils.getMp3Infos(this);
        mEs.execute(updateState);

    }
    //播放
    public void play(int position){
        if(position >= 0 && position < mMp3Infos.size()) {
            Mp3Info mp3Info = mMp3Infos.get(position);
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(this, Uri.parse(mp3Info.getUrl()));
                mMediaPlayer.prepare();
                mMediaPlayer.start();
                currentPosition = position;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(mMusicUpdateListener != null) {
                mMusicUpdateListener.onChange(position);
            }
        }
    }
    public boolean isPause(){
        return isPause;
    }
    //暂停
    public void pause(){
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
        }

    }
    //下一首
    public void next(){
        if(currentPosition+1 >= mMp3Infos.size() -1) {
            currentPosition = 0;
        } else {
            currentPosition ++;
        }
        play(currentPosition);
    }
    //上一首
    public void piev(){
    if(currentPosition -1 < 0) {
        currentPosition = mMp3Infos.size() -1;
    } else {
        currentPosition --;
    }
        play(currentPosition);
    }
    //开始播放,从头播放
    public void start(){
        if(mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private Random random = new Random();
    //OnCompletionListener接口实现的方法
    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (play_mode) {
            case Contant.ORDER_PLAY_LOOP :
                next();
                break;
            case Contant.RANDOM_PLAY :
                int position = random.nextInt(mMp3Infos.size());
                play(position);
                break;
            case Contant.SINGLE_PLAY_LOOP :
                play(currentPosition);
                break;
            default:
                break;
        }
    }
    //OnErrorListener接口实现的方法
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    public class PlayBinder extends Binder{
        public PlayService getPlayService(){
            return PlayService.this;
        }
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }
    public boolean isPlaying(){
        if(mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }
    //跳到什么位置
    public void seekTo(int mesc){
        mMediaPlayer.seekTo(mesc);
    }
    public int getCurrentProgress(){
        //不管播放或者是不播放状态都要更新progress，不然就有问题
        if(mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    //回调接口更新状态
    public interface MusicUpdateListener{
        void onPublish(int progress);
        void onChange(int position);
    }

    public void setMusicUpdateListener(MusicUpdateListener musicUpdateListener) {
        this.mMusicUpdateListener = musicUpdateListener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //线程开了，就要关
        if(mEs != null) {
            if(mEs != null && !mEs.isShutdown()){
                mEs.shutdown();
                mEs = null;
            }
        }
    }
    public int getThisCurrentPosition(){
        return currentPosition;
    }
}
