package com.music.zx.codingmusic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.zx.codingmusic.R;

/**
 * Created by zx on 2016/1/14.
 */
public class LrcFragment extends Fragment{

    public static LrcFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LrcFragment fragment = new LrcFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.layout_play_music_lrc,container,false);
        return parent;
    }
}
