package com.music.zx.codingmusic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.music.zx.codingmusic.adapter.PlayMusicPagerAdapter;
import com.music.zx.codingmusic.base.BaseActivity;
import com.music.zx.codingmusic.entity.Mp3Info;
import com.music.zx.codingmusic.service.Contant;
import com.music.zx.codingmusic.utils.music.MusicUtils;
import com.music.zx.codingmusic.utils.music.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.trinea.android.common.util.ToastUtils;

public class PlayMusicActivity extends BaseActivity {

    //更新播放时间标记
    private static final int UPDATE_TIME = 0x1;
    @Bind(R.id.tv_play_name)
    TextView mTvPlayName;
    @Bind(R.id.seekbar_play)
    AppCompatSeekBar mSeekbarPlay;
    @Bind(R.id.tv_playtime_start)
    TextView mTvPlaytimeStart;
    @Bind(R.id.tv_playtime_end)
    TextView mTvPlaytimeEnd;
    @Bind(R.id.iv_pattern)
    ImageView mIvPattern;
    @Bind(R.id.iv_play_prev)
    ImageView mIvPlayPrev;
    @Bind(R.id.iv_play)
    ImageView mIvPlay;
    @Bind(R.id.iv_play_next)
    ImageView mIvPlayNext;
    @Bind(R.id.vp_play_music)
    ViewPager mVpPlayMusic;
    @Bind(R.id.iv_like)
    ImageView mIvLike;
    private int position;
    private List<Mp3Info> mMp3Infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        ButterKnife.bind(this);
        initPlayMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //绑定播放服务,是一个异步的过程
        bindPlayService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBindPlayService();
    }

    private void initPlayMusic() {
        initIntent();
        mMp3Infos = MusicUtils.getMp3Infos(this);
        myHandler = new MyHandler(this);
        mVpPlayMusic.setAdapter(new PlayMusicPagerAdapter(getSupportFragmentManager()));
        mSeekbarPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //是否是用户手动拖动的，不是代码设置的
                if (fromUser) {
                    mPlayService.pause();
                    mPlayService.seekTo(progress);
                    mPlayService.start();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initIntent() {
    }

    @OnClick({R.id.iv_pattern, R.id.iv_play_prev, R.id.iv_play, R.id.iv_play_next,R.id.iv_like})
    public void OnClicks(View v) {
        switch (v.getId()) {
            case R.id.iv_pattern:
                int mode = (int) mIvPattern.getTag();
                switch (mode) {
                    //点击顺序播放，切换到随机播放
                    case Contant.ORDER_PLAY_LOOP:
                        mIvPattern.setImageResource(R.mipmap.random);
                        mIvPattern.setTag(Contant.RANDOM_PLAY);
                        mPlayService.setPlay_mode(Contant.RANDOM_PLAY);
                        ToastUtils.show(this, getString(R.string.random_play));
                        break;
                    case Contant.RANDOM_PLAY:
                        mIvPattern.setImageResource(R.mipmap.single);
                        mIvPattern.setTag(Contant.SINGLE_PLAY_LOOP);
                        mPlayService.setPlay_mode(Contant.SINGLE_PLAY_LOOP);
                        ToastUtils.show(this, getString(R.string.single_play));
                        break;
                    case Contant.SINGLE_PLAY_LOOP:
                        mIvPattern.setImageResource(R.mipmap.order);
                        mIvPattern.setTag(Contant.ORDER_PLAY_LOOP);
                        mPlayService.setPlay_mode(Contant.ORDER_PLAY_LOOP);
                        ToastUtils.show(this, getString(R.string.order_play));
                        break;
                }
                break;
            case R.id.iv_play_prev:
                mPlayService.piev();
                break;
            case R.id.iv_play:
                if (mPlayService.isPlaying()) {
                    mIvPlay.setImageResource(R.mipmap.a1);
                    mPlayService.pause();
                } else {
                    if (mPlayService.isPause()) {
                        mIvPlay.setImageResource(R.mipmap.a2);
                        mPlayService.start();
                    } else {
                        //从第一首开始播放
                        mPlayService.play(mPlayService.getThisCurrentPosition());
                    }
                }
                break;
            case R.id.iv_play_next:
                mPlayService.next();
                break;
            case R.id.iv_like:

                break;
            default:
                break;
        }
    }

    private MyHandler myHandler;

    private static class MyHandler extends Handler {
        WeakReference<AppCompatActivity> weakReference;

        public MyHandler(AppCompatActivity activity) {
            weakReference = new WeakReference<AppCompatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PlayMusicActivity playMusicActivity = (PlayMusicActivity) weakReference.get();
            if (playMusicActivity != null) {
                switch (msg.what) {
                    case UPDATE_TIME:
                        playMusicActivity.mTvPlaytimeStart.setText(TimeUtils.getTime(msg.arg1));
                        break;
                }
            }
        }
    }

    //实时更新，接收进度
    @Override
    public void publish(int progress) {
        Message msg = myHandler.obtainMessage(UPDATE_TIME);
        msg.arg1 = progress;
        myHandler.sendMessage(msg);
        mSeekbarPlay.setProgress(progress);
    }

    @Override
    public void change(int position) {
        Mp3Info mp3Info = mMp3Infos.get(position);
        //if(mPlayService.isPlaying()) {
        //歌名
        mTvPlayName.setText(mp3Info.getTitle());
        //播放进度
        //mSeekbarPlay.setProgress(0);
        mSeekbarPlay.setMax((int) mp3Info.getDuration());
        //结束时间
        mTvPlaytimeEnd.setText(TimeUtils.getTime(mp3Info.getDuration()));
        mIvPlay.setImageResource(R.mipmap.a2);
        if (mPlayService.isPlaying()) {
            mIvPlay.setImageResource(R.mipmap.a2);
        } else {
            mIvPlay.setImageResource(R.mipmap.a1);
        }
        //根据播放模式更新UI
        switch (mPlayService.getPlay_mode()) {
            case Contant.ORDER_PLAY_LOOP:
                mIvPattern.setImageResource(R.mipmap.order);
                mIvPattern.setTag(Contant.ORDER_PLAY_LOOP);
                break;
            case Contant.RANDOM_PLAY:
                mIvPattern.setImageResource(R.mipmap.random);
                mIvPattern.setTag(Contant.RANDOM_PLAY);
                break;
            case Contant.SINGLE_PLAY_LOOP:
                mIvPattern.setImageResource(R.mipmap.single);
                mIvPattern.setTag(Contant.SINGLE_PLAY_LOOP);
                break;
            default:
                break;
        }

        // }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
