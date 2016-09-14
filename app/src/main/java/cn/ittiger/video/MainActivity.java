package cn.ittiger.video;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ittiger.video.app.TumblrApplication;
import cn.ittiger.video.fragment.MainAdapter;
import cn.ittiger.video.fragment.NetEasyVideoFragment;
import cn.ittiger.video.fragment.VideoFragment;
import cn.ittiger.video.player.VideoPlayerHelper;
import cn.ittiger.video.util.UIUtil;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.indicator_tab_layout)
    TabLayout mTabPageIndicator;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    MainAdapter mAdapter;

    public static final Class[] FRAGMENTS = {NetEasyVideoFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mTabPageIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                setTitle(mAdapter.getItemTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        List<VideoFragment> fragments = new ArrayList<>();
        try {
            for(int i = 0; i < FRAGMENTS.length; i++) {
                VideoFragment fragment = (VideoFragment) FRAGMENTS[i].newInstance();
                fragments.add(fragment);
            }
            mAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
            mViewPager.setAdapter(mAdapter);
            mTabPageIndicator.setVisibility(View.VISIBLE);
            mTabPageIndicator.setupWithViewPager(mViewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ((TumblrApplication)getApplication()).onDestroy();
        VideoPlayerHelper.getInstance().stop();
    }

    private long exitTime = 0;

    @Override
    public void onBackPressed() {

        if(System.currentTimeMillis() - exitTime > 2000) {
            UIUtil.showToast(this, R.string.two_click_exit_app);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
