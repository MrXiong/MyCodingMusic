package com.music.zx.codingmusic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;
import com.music.zx.codingmusic.adapter.MainViewPagerAdapter;
import com.music.zx.codingmusic.base.BaseActivity;
import com.music.zx.codingmusic.base.HomeData;
import com.music.zx.codingmusic.service.Contant;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

public class MainActivity extends BaseActivity {
    @Bind(R.id.tab_fragmens)
    PagerSlidingTabStrip mTabFragmens;
    @Bind(R.id.vp_fragments)
    ViewPager mVpFragments;
    private MainViewPagerAdapter mMainViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initPagerTab();
    }
    private void initPagerTab() {
        mMainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), HomeData.PHOTO_CATEGORY);
        mVpFragments.setAdapter(mMainViewPagerAdapter);
        mTabFragmens.setViewPager(mVpFragments);
        mTabFragmens.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ToastUtils.show(MainActivity.this, "position" + position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                ToastUtils.show(MainActivity.this, "state" + state);
            }
        });
        initTabsValue();
    }
    private void initTabsValue() {

        // 底部游标颜色
        mTabFragmens.setIndicatorColor(Color.BLUE);
        // tab的分割线颜色
        mTabFragmens.setDividerColor(Color.TRANSPARENT);
        // tab背景
        mTabFragmens.setBackgroundColor(Color.parseColor("#4876FF"));
        // tab底线高度
        mTabFragmens.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        mTabFragmens.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        // 选中的文字颜色
        // mTabFragmens.setSelectedTextColor(Color.WHITE);
        // 正常文字颜色
        mTabFragmens.setTextColor(Color.BLACK);

    }

    @Override
    public void publish(int progress) {
    //更新进度条

    }

    @Override
    public void change(int position) {
        //切换播放位置
        switch (mVpFragments.getCurrentItem()) {
            case 0 :
                mMainViewPagerAdapter.changeMyUIState(position);
                break;
            case 1 :
                mMainViewPagerAdapter.changeNetUIState(position);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存当前播放的一些状态值
        PreferencesUtils.putInt(this, Contant.SP_THIS_CURRENTPOSITION,mPlayService.getThisCurrentPosition());
        PreferencesUtils.putInt(this,Contant.SP_PLAY_MODE,mPlayService.getPlay_mode());
    }
}
