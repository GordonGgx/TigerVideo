package cn.ittiger.video.fragment;

import cn.ittiger.video.R;
import cn.ittiger.video.bean.TtKb;
import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.http.RetrofitFactory;
import cn.ittiger.video.util.CallbackHandler;
import cn.ittiger.video.util.DBManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class TtKbVideoFragment extends VideoFragment {
    private int mRefreshPageNum = 0;//刷新页

    @Override
    public void queryVideoData(int curPage, final CallbackHandler<List<VideoData>> callback) {

        String startKey = "";
        String newKey = "";

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("categoryId", "799999");
        if(curPage == 1) {//刷新
            mRefreshPageNum += -1;
            fieldMap.put("count", "10");
            fieldMap.put("pgnum", String.valueOf(mRefreshPageNum));
            TtKb ttKb = DBManager.getInstance().getSQLiteDB().queryOne(TtKb.class, "isRefresh=?", new String[]{"1"});
            if(ttKb != null) {
                newKey = ttKb.getNewkey();
                startKey = ttKb.getEndkey();
            }
        } else {
            fieldMap.put("count", "20");
            fieldMap.put("pgnum", String.valueOf(curPage));
            TtKb ttKb = DBManager.getInstance().getSQLiteDB().queryOne(TtKb.class, "isRefresh=?", new String[]{"0"});
            if(ttKb != null) {
                startKey = ttKb.getEndkey();
            }
        }
        fieldMap.put("startkey", startKey);
        fieldMap.put("newkey", newKey);
        fieldMap.put("param", "TouTiaoKuaiBao%09TTKBAndroid%09860582034297685%09huawei160918%09TTKB%091.4.0%09Android5.0.2%09null%09null%09PLK-AL10");

        Call<List<VideoData>> call = RetrofitFactory.getTtKbVideoService().getVideos(fieldMap);
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

        return R.string.ttkb_video;
    }
}
