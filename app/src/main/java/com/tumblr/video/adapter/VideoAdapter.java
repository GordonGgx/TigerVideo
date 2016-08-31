package com.tumblr.video.adapter;

import com.tumblr.video.R;
import com.tumblr.video.bean.VideoData;
import com.tumblr.video.ui.recycler.HeaderAndFooterAdapter;
import com.tumblr.video.ui.recycler.ViewHolder;
import com.tumblr.video.ui.video.VideoPlayState;
import com.tumblr.video.ui.video.VideoPlayerHelper;
import com.tumblr.video.ui.video.VideoPlayerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @author: laohu on 2016/8/24
 * @site: http://ittiger.cn
 */
public class VideoAdapter extends HeaderAndFooterAdapter<VideoData> {

    private Context mContext;
    private int mPlayingPostion = -1;//当前正在播放的视频索引

    public VideoAdapter(Context context, List<VideoData> list) {

        super(list);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.video_list_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(ViewHolder holder, int position, VideoData item) {

        VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
        int value = position % 8;
        switch (value) {
            case 0:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_8);
                break;
            case 1:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_1);
                break;
            case 2:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_2);
                break;
            case 3:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_3);
                break;
            case 4:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_4);
                break;
            case 5:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_5);
                break;
            case 6:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_6);
                break;
            case 7:
                videoViewHolder.mVideoImage.setImageResource(R.drawable.beautiful_7);
                break;
        }
        videoViewHolder.itemView.setTag(R.id.tag_video_list_item, VideoPlayState.STOP);
        videoViewHolder.setPosition(position);
    }

    class VideoViewHolder extends ViewHolder implements View.OnClickListener {
        ImageView mVideoImage;
        ImageView mPlayImage;
        int mPosition;

        public VideoViewHolder(View itemView) {

            super(itemView);
            mVideoImage = (ImageView) itemView.findViewById(R.id.iv_video_item_image);
            mPlayImage = (ImageView) itemView.findViewById(R.id.iv_video_item_play_btn);
            mVideoImage.setOnClickListener(this);
            mPlayImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            VideoPlayerHelper.getInstance(mContext).play((ViewGroup) itemView, getItem(mPosition).getUrl());
            mPlayingPostion = mPosition;
        }

        public void setPosition(int position) {

            mPosition = position;
        }
    }

    public int getPlayingPostion() {

        return mPlayingPostion;
    }

    public void setPlayingPostion(int playingPostion) {

        mPlayingPostion = playingPostion;
    }
}
