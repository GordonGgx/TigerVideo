package com.tumblr.video;

import com.tumblr.video.adapter.VideoAdapter;
import com.tumblr.video.app.TumblrApplication;
import com.tumblr.video.base.BaseActivity;
import com.tumblr.video.bean.VideoData;
import com.tumblr.video.inject.InjectHelper;
import com.tumblr.video.inject.InjectView;
import com.tumblr.video.ui.recycler.CommonRecyclerView;
import com.tumblr.video.ui.recycler.SpacesItemDecoration;
import com.tumblr.video.ui.video.VideoPlayerHelper;
import com.tumblr.video.util.Callback;
import com.tumblr.video.util.DBManager;
import com.tumblr.video.util.UIUtil;
import com.tumblr.video.util.VideoDataLoadHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends BaseActivity implements
        CommonRecyclerView.LoadMoreListener {

    @InjectView(id=R.id.video_swipe_refresh_layout)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(id=R.id.video_recycler_view)
    private CommonRecyclerView mRecyclerView;
    @InjectView(id=R.id.small_video_player_container)
    private RelativeLayout mSmallVideoPlayerContainer;
    @InjectView(id=R.id.iv_video_close)
    private ImageView mVideoClose;
    private VideoAdapter mVideoAdapter;
    private int mCurPage = 1;
    private int mPageSize = 10;

    @Override
    public View getContentView(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        InjectHelper.inject(this, view);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.d_10)));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnLoadMoreListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
            }
        });
        setTitle(getString(R.string.app_name));
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int curPlayPosition = VideoPlayerHelper.getInstance().getCurrPlayPosition();
                int lastPlayPosition = VideoPlayerHelper.getInstance().getLastPlayPosition();
                if (curPlayPosition != -1 && (curPlayPosition < mRecyclerView.getFirstVisiblePosition() ||
                        curPlayPosition > mRecyclerView.getLastVisiblePosition())) {
                    VideoPlayerHelper.getInstance().smallWindowPlay();//移除屏幕之后进入小窗口播放
                } else if(curPlayPosition == -1 && lastPlayPosition >= mRecyclerView.getFirstVisiblePosition()
                    && lastPlayPosition <= mRecyclerView.getLastVisiblePosition()) {
                    VideoPlayerHelper.getInstance().smallWindowToListPlay();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mVideoClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VideoPlayerHelper.getInstance().stop();
            }
        });
        VideoPlayerHelper.init(mContext, mSmallVideoPlayerContainer);
        return view;
    }

    @Override
    protected void onPause() {

        VideoPlayerHelper.getInstance().pause();
        super.onPause();
    }

    @Override
    public void refreshData() {

        long total = DBManager.getInstance().getSQLiteDB().queryTotal(VideoData.class);
        if(total == 0) {//还没有数据
            VideoDataLoadHelper.loadAssertVideoData(new Callback<Boolean>() {
                @Override
                public void callback(Boolean isSuccess) {

                    if(isSuccess.booleanValue()) {
                        loadRefreshData();
                    } else {
                        refreshFailed();
                    }
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
            mVideoAdapter = new VideoAdapter(this, videoDatas);
            mVideoAdapter.enableFooterView();
            View footerView = LayoutInflater.from(this).inflate(R.layout.video_list_item, mRecyclerView, false);
            mVideoAdapter.addFooterView(footerView);
            mRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.reset(videoDatas);
        }
        mCurPage ++;
        refreshSuccess();
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

    @Override
    public boolean isNavigationButtonEnable() {

        return false;
    }
}
