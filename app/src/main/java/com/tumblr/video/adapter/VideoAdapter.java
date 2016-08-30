package com.tumblr.video.adapter;

import com.tumblr.video.bean.VideoData;
import com.tumblr.video.ui.recycler.HeaderAndFooterAdapter;
import com.tumblr.video.ui.recycler.ViewHolder;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
public class VideoAdapter extends HeaderAndFooterAdapter<VideoData> {

    public VideoAdapter(List<VideoData> list) {

        super(list);
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position, VideoData item) {

    }

    public class VideoViewHolder extends ViewHolder {

        public VideoViewHolder(View itemView) {

            super(itemView);
        }
    }
}
