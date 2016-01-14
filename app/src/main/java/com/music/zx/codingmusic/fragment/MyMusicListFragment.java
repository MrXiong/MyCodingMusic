package com.music.zx.codingmusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.music.zx.codingmusic.MainActivity;
import com.music.zx.codingmusic.PlayMusicActivity;
import com.music.zx.codingmusic.R;
import com.music.zx.codingmusic.adapter.MyMusicListAdapter;
import com.music.zx.codingmusic.entity.Mp3Info;
import com.music.zx.codingmusic.utils.music.MusicUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zx on 2016/1/12.
 */
public class MyMusicListFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.lv_my_music_list)
    ListView mLvMyMusicList;
    @Bind(R.id.tv_music_name)
    TextView mTvMusicName;
    @Bind(R.id.tv_singer_name)
    TextView mTvSingerName;
    @Bind(R.id.iv_music_start)
    ImageView mIvMusicStart;
    @Bind(R.id.iv_music_next)
    ImageView mIvMusicNext;
    @Bind(R.id.iv_music_picture)
    ImageView mIvMusicPicture;
    //是否处于暂停状态
    private int position;

    private List<Mp3Info> mMp3Infos;
    private MyMusicListAdapter mMyMusicListAdapter;
    private MainActivity mMainActivity;
    private Mp3Info mMp3Info;

    public static MyMusicListFragment newInstance() {

        Bundle args = new Bundle();

        MyMusicListFragment fragment = new MyMusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_my_music_list, container, false);
        ButterKnife.bind(this, parent);
        mMainActivity = (MainActivity) getActivity();
        loadData();
        mIvMusicStart.setOnClickListener(this);
        mIvMusicNext.setOnClickListener(this);
        mIvMusicPicture.setOnClickListener(this);
        return parent;
    }

    @Override
    public void onResume() {
        super.onResume();
        //绑定播放服务,生命周期缩短，之前放在onCreateView中去，但是activity返回到当前activity的时候，不会再次调用oncreateview
        //onResume在返回的时候也会调用，所以放在这里
        //bindPlayService执行之后会回调onServiceConnected方法继而调用onChange(mPlayService.getCurrentPosition())方法来更新UI
        mMainActivity.bindPlayService();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMainActivity.unBindPlayService();
    }

    private void loadData() {
        final Context context = getActivity().getApplicationContext();
        mMp3Infos = MusicUtils.getMp3Infos(context);
        mMyMusicListAdapter = new MyMusicListAdapter(context, mMp3Infos);
        mLvMyMusicList.setAdapter(mMyMusicListAdapter);

        mLvMyMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainActivity.start(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void changeUIState(int position) {
        if (position >= 0 && position < mMp3Infos.size()) {
            mMp3Info = mMp3Infos.get(position);
            mTvMusicName.setText(mMp3Info.getTitle());
            mTvSingerName.setText(mMp3Info.getArtist());
            if(mMainActivity.isPlaying()) {
                mIvMusicStart.setImageResource(R.mipmap.a2);
            } else {
                mIvMusicStart.setImageResource(R.mipmap.a1);
            }
            Bitmap bitmap = MusicUtils.getArtwork(mMainActivity,mMp3Info.getId(),mMp3Info.getArbumId(),true);
            //mIvMusicPicture.setImageBitmap(bitmap);
            this.position = position;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_music_start :
                if(mMainActivity.isPlaying()) {
                    mIvMusicStart.setImageResource(R.mipmap.a1);
                    mMainActivity.mPlayService.pause();
                } else {
                    if(mMainActivity.mPlayService.isPause()) {
                        //从第一首开始播放
                        mMainActivity.mPlayService.start();
                        mIvMusicStart.setImageResource(R.mipmap.a2);
                    } else {
                        mMainActivity.mPlayService.play(mMainActivity.mPlayService.getThisCurrentPosition());
                    }
                }
                break;
            case R.id.iv_music_next :
                mMainActivity.mPlayService.next();
                break;
            case R.id.iv_music_picture :
                Intent intent = new Intent(getActivity(), PlayMusicActivity.class);
                startActivity(intent);
                break;
        }
    }
}
