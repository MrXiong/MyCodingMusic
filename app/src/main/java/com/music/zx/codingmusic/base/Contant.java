package com.music.zx.codingmusic.base;

/**
 * Created by zx on 2016/1/14.
 */
public class Contant {

    //顺序播放
    //public static final int ORDER_PLAY = 1;
    //顺序循环
    public static final int ORDER_PLAY_LOOP = 2;
    //随机播放
    public static final int RANDOM_PLAY = 3;
    //单曲播放
    //public static final int SINGLE_PLAY = 4;
    //单曲循环
    public static final int SINGLE_PLAY_LOOP = 5;

    //sharepreference
    public static final String SP_NAME = "CodingMusic";
    public static final String DB_NAME = "Coding.db";
    public static final String SP_THIS_CURRENTPOSITION = "ThisCurrentPosition";
    public static final String SP_PLAY_MODE = "Play_mode";


    //百度音乐
    public static final String BAIDU_URL = "http://music.baidu.com/";
    //热歌榜
    public static final String BAIDU_DAYHOT = "top/dayhot/?pst=shouyeTop";
    //搜索
    public static final String BAIDU_SEARCH = "/search/song";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";



    public static final int SUCCESS =1;
    public static final int FAILED = 2;

}
