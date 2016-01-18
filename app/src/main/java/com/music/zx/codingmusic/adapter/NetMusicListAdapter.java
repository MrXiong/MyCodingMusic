package com.music.zx.codingmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.zx.codingmusic.R;
import com.music.zx.codingmusic.entity.SearchResult;
import com.music.zx.codingmusic.entity.SearchResult;
import com.music.zx.codingmusic.utils.AdapterUtils;
import com.music.zx.codingmusic.utils.music.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zx on 2016/1/12.
 */
public class NetMusicListAdapter extends BaseAdapter {
    private List<SearchResult> mSearchResults;

    public NetMusicListAdapter(Context context, List<SearchResult> SearchResults) {
        this.mSearchResults = AdapterUtils.getList(SearchResults);
    }

    public void setSearchResults(List<SearchResult> SearchResultList) {
        this.mSearchResults = SearchResultList;
    }

    @Override
    public int getCount() {
        return mSearchResults.size();
    }

    @Override
    public SearchResult getItem(int position) {
        return mSearchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_music, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SearchResult SearchResult = getItem(position);
        holder.tvItemMusicName.setText(SearchResult.getMusicName());
        holder.tvItemSingerName.setText(SearchResult.getArtist());
        //holder.ivItemMusicIcon.setImageURI(SearchResult.getArbum());
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_my_music.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    static class ViewHolder {
        @Bind(R.id.iv_item_music_icon)
        ImageView ivItemMusicIcon;
        @Bind(R.id.tv_item_music_name)
        TextView tvItemMusicName;
        @Bind(R.id.tv_item_singer_name)
        TextView tvItemSingerName;
        @Bind(R.id.tv_item_music_time)
        TextView tvItemMusicTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
