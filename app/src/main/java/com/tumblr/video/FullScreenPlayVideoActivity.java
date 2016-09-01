package com.tumblr.video;

import com.tumblr.video.ui.video.VideoPlayState;
import com.tumblr.video.ui.video.VideoPlayerHelper;
import com.tumblr.video.ui.video.VideoPlayerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * @author: laohu on 2016/8/31
 * @site: http://ittiger.cn
 */
public class FullScreenPlayVideoActivity extends AppCompatActivity
            implements VideoPlayerView.ExitFullScreenListener{

    private VideoPlayState mCurrPlayState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_play_video);
        VideoPlayerHelper.getInstance(this).fullScreen((ViewGroup) findViewById(R.id.root), this);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mCurrPlayState = VideoPlayerHelper.getInstance(this).getVideoPlayState();
        VideoPlayerHelper.getInstance(this).pause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(mCurrPlayState == VideoPlayState.PLAY) {
            VideoPlayerHelper.getInstance(this).play();
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        VideoPlayerHelper.getInstance(this).exitFullScreen(mCurrPlayState);
    }

    @Override
    public void exitFullScreen() {

        finish();
    }
}
