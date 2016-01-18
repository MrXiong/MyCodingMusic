package com.music.zx.codingmusic.entity;

import java.io.Serializable;

/**
 * Created by zx on 2016/1/12.
 */
public class Mp3Info implements Serializable {
    public static final String MP3_INFO = "Mp3Info";
    private long id;
    private long mp3InfoId;//在收藏音乐的时候用于保存原始id
    //歌名
    private String title;
    //艺术家
    private String artist;
    //专辑
    private String arbum;
    private long arbumId;
    //时长
    private long duration;
    //大小
    private long size;
    //路径
    private String url;
    //是否是音乐
    private boolean isMusic;

    public long getMp3InfoId() {
        return mp3InfoId;
    }

    public void setMp3InfoId(long mp3InfoId) {
        this.mp3InfoId = mp3InfoId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArbum() {
        return arbum;
    }

    public void setArbum(String arbum) {
        this.arbum = arbum;
    }

    public long getArbumId() {
        return arbumId;
    }

    public void setArbumId(long arbumId) {
        this.arbumId = arbumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }

    @Override
    public String toString() {
        return "Mp3Info{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", arbum='" + arbum + '\'' +
                ", arbumId=" + arbumId +
                ", duration=" + duration +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", isMusic=" + isMusic +
                '}';
    }
}
