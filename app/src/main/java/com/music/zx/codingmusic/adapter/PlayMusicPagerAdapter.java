package com.music.zx.codingmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.music.zx.codingmusic.fragment.AlbumFragment;
import com.music.zx.codingmusic.fragment.LrcFragment;

/**
 * Created by zx on 2016/1/14.
 */
public class PlayMusicPagerAdapter extends FragmentPagerAdapter {

    private AlbumFragment mAlbumFragment;
    private LrcFragment mLrcFragment;

    public PlayMusicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                if(mAlbumFragment == null) {
                    mAlbumFragment = AlbumFragment.newInstance();
                    return mAlbumFragment;
                }
                break;
            case 1 :
                if(mLrcFragment == null) {
                    mLrcFragment = LrcFragment.newInstance();
                    return mLrcFragment;
                }
                break;
        }
        return null;
    }

}
