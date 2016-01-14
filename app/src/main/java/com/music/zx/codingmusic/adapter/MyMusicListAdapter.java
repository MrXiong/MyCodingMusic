package com.music.zx.codingmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.music.zx.codingmusic.R;
import com.music.zx.codingmusic.entity.Mp3Info;
import com.music.zx.codingmusic.utils.AdapterUtils;
import com.music.zx.codingmusic.utils.music.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zx on 2016/1/12.
 */
public class MyMusicListAdapter extends BaseAdapter {
    private List<Mp3Info> mMp3Infos;

    public MyMusicListAdapter(Context context, List<Mp3Info> mp3Infos) {
        this.mMp3Infos = AdapterUtils.getList(mp3Infos);
    }

    public void setMp3Infos(List<Mp3Info> mp3InfoList) {
        this.mMp3Infos = mp3InfoList;
    }

    @Override
    public int getCount() {
        return mMp3Infos.size();
    }

    @Override
    public Mp3Info getItem(int position) {
        return mMp3Infos.get(position);
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
        Mp3Info mp3Info = getItem(position);
        holder.tvItemMusicName.setText(mp3Info.getTitle());
        holder.tvItemSingerName.setText(mp3Info.getArtist());
        holder.tvItemMusicTime.setText(TimeUtils.getTime(mp3Info.getDuration()));
        //holder.ivItemMusicIcon.setImageURI(mp3Info.getArbum());
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
