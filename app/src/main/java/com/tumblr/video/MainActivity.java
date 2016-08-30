package com.tumblr.video;

import com.tumblr.video.adapter.VideoAdapter;
import com.tumblr.video.base.BaseActivity;
import com.tumblr.video.bean.VideoData;
import com.tumblr.video.inject.InjectHelper;
import com.tumblr.video.inject.InjectView;
import com.tumblr.video.ui.recycler.CommonRecyclerView;
import com.tumblr.video.util.DBManager;
import com.tumblr.video.util.UIUtil;
import com.tumblr.video.util.VideoDataLoadHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends BaseActivity implements CommonRecyclerView.LoadMoreListener {

    @InjectView(id=R.id.video_swipe_refresh_layout)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(id=R.id.video_recycler_view)
    private CommonRecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private int mCurPage = 1;
    private int mPageSize = 10;

    @Override
    public View getContentView(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        InjectHelper.inject(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
            }
        });
        setTitle(getString(R.string.app_name));
        return view;
    }

    @Override
    public void refreshData() {

        long total = DBManager.getInstance().getSQLiteDB().queryTotal(VideoData.class);
        if(total == 0) {//还没有数据
            VideoDataLoadHelper.loadAssertVideoData(new Runnable() {
                @Override
                public void run() {

                    loadRefreshData();
                }
            });
        } else {
            loadRefreshData();
        }
    }

    private void loadRefreshData() {

        mCurPage = 1;
        List<VideoData> videoDatas = DBManager.getInstance().getSQLiteDB().queryPage(VideoData.class, mCurPage, mPageSize);
        if(mVideoAdapter == null) {
            mVideoAdapter = new VideoAdapter(videoDatas);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.reset(videoDatas);
        }
        mCurPage ++;
    }

    @Override
    public void onLoadMore() {

        List<VideoData> videoDatas = DBManager.getInstance().getSQLiteDB().queryPage(VideoData.class, mCurPage, mPageSize);
        mVideoAdapter.addAll(videoDatas);
        if(videoDatas.size() > 0) {
            mCurPage ++;
        }
    }

    @Override
    public void refreshFailed() {

        super.refreshFailed();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshSuccess() {

        super.refreshSuccess();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
