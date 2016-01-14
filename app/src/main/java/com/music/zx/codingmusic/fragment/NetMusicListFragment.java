package com.music.zx.codingmusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by zx on 2016/1/12.
 */
public class NetMusicListFragment extends Fragment {

    public static NetMusicListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NetMusicListFragment fragment = new NetMusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
