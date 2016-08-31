package com.tumblr.video.ui.video;

import com.tumblr.video.FullScreenPlayVideoActivity;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
public class VideoPlayerHelper {

    private static volatile  VideoPlayerHelper sVideoPlayerHelper;
    private VideoPlayerView mVideoPlayerView;

    private VideoPlayerHelper(Context context) {

        mVideoPlayerView = new VideoPlayerView(context);
    }

    public static VideoPlayerHelper getInstance(Context context) {

        if(sVideoPlayerHelper == null) {
            synchronized (VideoPlayerHelper.class) {
                if(sVideoPlayerHelper == null) {
                    sVideoPlayerHelper = new VideoPlayerHelper(context);
                }
            }
        }
        return sVideoPlayerHelper;
    }

    public void play(ViewGroup parent, String videoUrl) {

        if(getVideoPlayState() != VideoPlayState.STOP) {
            mVideoPlayerView.onDestroy();
        }
        parent.addView(mVideoPlayerView);
        mVideoPlayerView.play(videoUrl);
    }

    public void pause() {

        mVideoPlayerView.pause();
    }

    public void gotoFullScreen(Context context) {

        context.startActivity(new Intent(context, FullScreenPlayVideoActivity.class));
    }

    public void startFullScreenPlay(ViewGroup parent) {

        ((ViewGroup)mVideoPlayerView.getParent()).removeView(mVideoPlayerView);
        mVideoPlayerView.setPlayScreenState(PlayScreenState.FULL_SCREEN);
        parent.addView(mVideoPlayerView);
        mVideoPlayerView.play();
    }

    public VideoPlayState getVideoPlayState() {

        return mVideoPlayerView.getVideoPlayState();
    }

    public void stop() {

        mVideoPlayerView.onDestroy();
    }
}
