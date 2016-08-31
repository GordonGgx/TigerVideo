package com.tumblr.video.ui.video;

import android.content.Context;
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

    public VideoPlayState getVideoPlayState() {

        return mVideoPlayerView.getVideoPlayState();
    }
}
