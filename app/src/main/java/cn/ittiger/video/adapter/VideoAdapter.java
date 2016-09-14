package cn.ittiger.video.adapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ittiger.video.R;
import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.ui.recycler.HeaderAndFooterAdapter;
import cn.ittiger.video.ui.recycler.ViewHolder;
import cn.ittiger.video.player.VideoPlayState;
import cn.ittiger.video.player.VideoPlayerHelper;
import cn.ittiger.video.util.DisplayManager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    class VideoViewHolder extends ViewHolder {
        @BindView(R.id.iv_video_item_image)
        ImageView mVideoImage;
        @BindView(R.id.iv_video_item_play_btn)
        ImageView mPlayImage;
        int mPosition;

        public VideoViewHolder(View itemView) {

            super(itemView);
            //以宽高比16:9的比例设置播放器的尺寸
            int width = DisplayManager.screenWidthPixel(mContext);
            int height = (int) (width * 1.0f / 16 * 9 + 0.5f);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.height = height;
            params.width = width;
            itemView.setLayoutParams(params);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.iv_video_item_image, R.id.iv_video_item_play_btn})
        public void onClick(View v) {

            VideoPlayerHelper.getInstance().play((ViewGroup) itemView, getItem(mPosition).getVideoUrl(), mPosition);
        }

        public void setPosition(int position) {

            mPosition = position;
        }
    }
}
