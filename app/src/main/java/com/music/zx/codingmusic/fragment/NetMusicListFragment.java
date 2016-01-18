package com.music.zx.codingmusic.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.music.zx.codingmusic.R;
import com.music.zx.codingmusic.adapter.NetMusicListAdapter;
import com.music.zx.codingmusic.base.Contant;
import com.music.zx.codingmusic.entity.SearchResult;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zx on 2016/1/12.
 */
public class NetMusicListFragment extends Fragment {

    private static final String TAG = NetMusicListFragment.class.getSimpleName();
    @Bind(R.id.search_musics)
    SearchView mSearchMusics;
    @Bind(R.id.progressBar_load)
    ProgressBar mProgressBarLoad;
    @Bind(R.id.lv_musics)
    ListView mLvMusics;
    private List<SearchResult> mSearchRs;

    public static NetMusicListFragment newInstance() {

        Bundle args = new Bundle();

        NetMusicListFragment fragment = new NetMusicListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_net_music_list, container, false);
        ButterKnife.bind(this, parent);
        loadNetData();
        return parent;
    }

    private void loadNetData() {
        mSearchRs = new ArrayList<>();
        mProgressBarLoad.setVisibility(View.VISIBLE);
        LoadDataTast loadDataTast = new LoadDataTast();
        loadDataTast.execute(Contant.BAIDU_URL);
        mSearchMusics.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    //加载网络音乐的异步任务
    class LoadDataTast extends AsyncTask<String,Integer,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBarLoad.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if(result == 1) {
                NetMusicListAdapter adapter = new NetMusicListAdapter(getActivity(),mSearchRs);
                mLvMusics.setAdapter(adapter);
            }
            mProgressBarLoad.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBarLoad.setProgress(values[0]);
        }

        @Override
        protected Integer doInBackground(String... params) {
        String url = params[0];
            //使用jsoup请求网络
            try {
                Document doc = Jsoup.connect(url).userAgent(Contant.USER_AGENT).timeout(6*1000).get();
                Elements els = doc.select("span.song-title");
                Elements artist = doc.select("span.author_list");
                for (int i = 0; i < els.size(); i++) {
                    SearchResult sr = new SearchResult();
                    //获取a链接
                    Elements urls = els.get(i).getElementsByTag("a");
                    //取属性
                    sr.setUrl(urls.get(0).attr("href"));
                    sr.setMusicName(urls.get(0).text());

                    Elements artistEls = artist.get(i).getElementsByTag("a");
                    sr.setArtist(artistEls.get(0).text());
                    sr.setAlbum("热歌榜");
                    mSearchRs.add(sr);
                    Log.v(TAG,mSearchRs+"");
                }

            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }

            return 1;
        }
    }
}
