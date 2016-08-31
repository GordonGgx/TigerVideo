package com.tumblr.video;

import com.tumblr.video.ui.video.VideoPlayerHelper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

/**
 * @author: laohu on 2016/8/31
 * @site: http://ittiger.cn
 */
public class FullScreenPlayVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_play_video);
        VideoPlayerHelper.getInstance(this).startFullScreenPlay((ViewGroup) findViewById(R.id.root));
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        VideoPlayerHelper.getInstance(this).stop();
    }
}
