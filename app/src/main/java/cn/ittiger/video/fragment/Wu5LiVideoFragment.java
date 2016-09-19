package cn.ittiger.video.fragment;

import cn.ittiger.video.R;
import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.http.RetrofitFactory;
import cn.ittiger.video.util.CallbackHandler;
import cn.ittiger.video.util.DataKeeper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;

import java.util.List;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class Wu5LiVideoFragment extends VideoFragment {
    private static final String REFRESH = "DOWN";
    private static final String LOAD_MORE = "UP";

    @Override
    public void queryVideoData(int curPage, final CallbackHandler<List<VideoData>> callback) {

        String slipType = curPage == 1 ? REFRESH : LOAD_MORE;
        String cursor = DataKeeper.getWu5LiCursor();
        Call<List<VideoData>> call = RetrofitFactory.getWu5LiVideoService().getVideos(cursor, slipType);
        call.enqueue(new Callback<List<VideoData>>() {

            @Override
            public void onResponse(Call<List<VideoData>> call, final Response<List<VideoData>> response) {

                if(response.isSuccessful()) {
                    queryVideoHandler(response.body(), callback);
                } else {
                    onFailure(call, new NullPointerException("not query videos"));
                }
            }

            @Override
            public void onFailure(Call<List<VideoData>> call, Throwable t) {

                queryVideoHandler(null, callback);
            }
        });
    }

    private void queryVideoHandler(final List<VideoData> videos, final CallbackHandler<List<VideoData>> callback) {

        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(callback != null) {
                    callback.callback(videos);
                }
            }
        });
    }

    @Override
    public int getName() {

        return R.string.wu5li_video;
    }
}
