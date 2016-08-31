package com.tumblr.video.ui.video;

import com.tumblr.video.R;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.io.IOException;

/**
 * Created by laohu on 16-7-29.
 */
public class VideoPlayerView extends RelativeLayout
        implements View.OnClickListener, TextureView.SurfaceTextureListener {

    private RelativeLayout mVideoContainer;
    private TextureView mTextureView;//视频播放容器
    private VideoPlayerControllerView mVideoPlayerControllerView;//视频底部的播放控制器
    private ProgressBar mProgressBar;//视频加载过程中的进度条
    private VideoPlayState mVideoPlayState = VideoPlayState.STOP;//视频播放的当前状态：播放，暂停

    private Surface mSurface = null;
    private MediaPlayer mPlayer;
    private String mVideoUrl = "/storage/emulated/0/download/1461625479791.mp4";

    private int mSecProgress = 0;

    public VideoPlayerView(Context context) {

        this(context, null);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        inflate(context, R.layout.video_player_view_layout, this);
        mVideoContainer = (RelativeLayout) findViewById(R.id.video_player_view_container);
        mVideoPlayerControllerView = (VideoPlayerControllerView) findViewById(R.id.videoControllerView);
        mProgressBar = (ProgressBar) findViewById(R.id.video_loading);

        mVideoPlayerControllerView.setVideoControlListener(mVideoControlListener);

        createMediaPlayer();
    }

    /**
     * 创建视频播放器
     */
    private void createMediaPlayer() {

        mPlayer = new MediaPlayer();
        addVideoPlayListener();
    }

    /**
     * 创建视频显示介质
     */
    private void createVideoSurface() {

        if(mTextureView == null) {
            mTextureView = new TextureView(getContext());
            mTextureView.setId(R.id.id_video_texture_view);
            mTextureView.setOnClickListener(this);
            mTextureView.setSurfaceTextureListener(this);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mTextureView.setLayoutParams(params);
            mVideoContainer.addView(mTextureView, 0);
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.iv_video_item_play_btn:
                playButtonClick();
                break;
            case R.id.id_video_texture_view:
                mVideoPlayerControllerView.showOrHide();
                break;
        }
    }

    private void playButtonClick() {

        switch (mVideoPlayState) {
            case PLAY:
                pause();
                break;
            case PAUSE:
                play();
                break;
        }
    }

    /**
     * 开始播放指定url的视频
     *
     * @param videoUrl
     */
    public void play(String videoUrl) {

        setVideoPlayState(VideoPlayState.LOADING);
        createVideoSurface();
        loadVideo(videoUrl);
    }

    /**
     * 暂停播放
     */
    public void pause() {

        setVideoPlayState(VideoPlayState.PAUSE);
        mPlayer.pause();
    }

    /**
     * 开始播放
     */
    public void play() {

        setVideoPlayState(VideoPlayState.PLAY);
        mPlayer.start();
    }

    public void finish() {

        mPlayer.pause();
        mPlayer.seekTo(mPlayer.getDuration());
        setVideoPlayState(VideoPlayState.FINISH);
    }

    private void setVideoPlayState(VideoPlayState state) {
        mVideoPlayState = state;
        switch (state) {
            case STOP:
                hideLoadingBarIfNeed();
                mVideoPlayerControllerView.updatePlayButtonIcon();//更新播放按钮的图标
                break;
            case LOADING:
                showLoadingBarIfNeed();
                break;
            case PAUSE:
                mVideoPlayerControllerView.updatePlayButtonIcon();//更新播放按钮的图标
                break;
            case PLAY:
                showControllerViewIfNeed();
                updatePlayProgress();
                hideLoadingBarIfNeed();
                mVideoPlayerControllerView.updatePlayButtonIcon();//更新播放按钮的图标
                break;
            case FINISH:
                showControllerViewIfNeed();
                updatePlayProgress();
                mVideoPlayerControllerView.show();
                mVideoPlayerControllerView.updatePlayButtonIcon();//更新播放按钮的图标
                break;
        }
    }

    /**
     * 为播放器添加监听
     */
    private void addVideoPlayListener() {

        mPlayer.setOnPreparedListener(mOnPreparedListener);
        mPlayer.setOnCompletionListener(mOnCompletionListener);
        mPlayer.setOnErrorListener(mErrorListener);
        mPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);//缓冲监听
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setScreenOnWhilePlaying(true);
    }

    /**
     * 加载准备指定的视频
     *
     * @param videoUrl
     */
    private void loadVideo(String videoUrl) {

        try {
            mPlayer.setSurface(mSurface);
            mPlayer.setDataSource(videoUrl);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新播放进度
     */
    private void updatePlayProgress() {

        int duration = mPlayer.getDuration();
        int playTime = mPlayer.getCurrentPosition();
        mVideoPlayerControllerView.setVideoDuration(duration);
        mVideoPlayerControllerView.setVideoPlayTime(playTime);
        mVideoPlayerControllerView.setSecondaryProgress(mSecProgress);

        if(mVideoPlayState == VideoPlayState.PLAY) {

            postDelayed(new Runnable() {

                @Override
                public void run() {

                    updatePlayProgress();
                }
            }, 1000);
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {

        mSurface = new Surface(surfaceTexture);
        if(mPlayer != null) {
            mPlayer.setSurface(mSurface);
        }
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {

            play();
        }
    };

    private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (getWindowToken() != null) {
                String message = "播放失败";

                Log.e("mErrorListener", message);
            }
            return true;
        }
    };

    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

            mSecProgress = percent;
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {

            finish();
        }
    };

    private VideoPlayerControllerView.VideoControlListener mVideoControlListener =
            new VideoPlayerControllerView.VideoControlListener() {

        @Override
        public void onProgressChanged(int seekTime) {

            mPlayer.seekTo(seekTime);
            updatePlayProgress();
        }

        @Override
        public void fullScreen() {

        }

        @Override
        public void exitFullScreen() {

        }

        @Override
        public void onControllerShow() {

        }

        @Override
        public void onControllerHide() {

        }
    };

    public void onDestroy() {

        mVideoPlayerControllerView.onDestroy();
        setVideoPlayState(VideoPlayState.STOP);
        if(mPlayer != null) {
            mPlayer.release();
        }
        if(mSurface != null) {
            mSurface.release();
            mSurface = null;
        }
        mVideoContainer.removeView(mTextureView);
        mTextureView = null;
        ((ViewGroup)getParent()).removeView(this);
    }

    public VideoPlayState getVideoPlayState() {

        return mVideoPlayState;
    }

    /**---------------- 界面UI控制 ----------------------**/
    /**
     * 显示控制条
     */
    private void showControllerViewIfNeed() {

        if(mVideoPlayerControllerView.getVisibility() == GONE) {
            mVideoPlayerControllerView.setVisibility(VISIBLE);
        }
    }

    /**
     * 显示加载进度条
     */
    private void showLoadingBarIfNeed() {

        if(mProgressBar.getVisibility() == GONE) {
            mProgressBar.setVisibility(VISIBLE);
        }
    }

    /**
     * 隐藏加载加载进度条
     */
    private void hideLoadingBarIfNeed() {

        if(mProgressBar.getVisibility() == VISIBLE) {
            mProgressBar.setVisibility(GONE);
        }
    }


    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
