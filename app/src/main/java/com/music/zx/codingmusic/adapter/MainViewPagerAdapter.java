package com.music.zx.codingmusic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.music.zx.codingmusic.fragment.MyMusicListFragment;
import com.music.zx.codingmusic.fragment.NetMusicListFragment;


/**
 * Created by zx on 2016/1/6.
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private MyMusicListFragment mMyMusicListFragment;
    private NetMusicListFragment mNetMusicListFragment;
    private String[] TITLES;

    public MainViewPagerAdapter(FragmentManager fm, String[] TITLES) {
        super(fm);
        this.TITLES = TITLES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                if(mMyMusicListFragment == null) {
                    mMyMusicListFragment = MyMusicListFragment.newInstance();
                    return mMyMusicListFragment;
                }
                break;
            case 1 :
                if(mNetMusicListFragment == null) {
                    mNetMusicListFragment = NetMusicListFragment.newInstance();
                    return mNetMusicListFragment;
                }
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public void changeMyUIState(int position){
        mMyMusicListFragment.changeUIState(position);
    }
    public void changeNetUIState(int position){
    }
}
