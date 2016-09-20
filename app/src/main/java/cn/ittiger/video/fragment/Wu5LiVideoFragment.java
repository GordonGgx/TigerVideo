package cn.ittiger.video.fragment;

import cn.ittiger.video.R;
import cn.ittiger.video.http.DataType;
import cn.ittiger.video.factory.RetrofitFactory;
import cn.ittiger.video.util.DataKeeper;
import retrofit2.Call;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class Wu5LiVideoFragment extends VideoFragment {
    private static final String REFRESH = "DOWN";
    private static final String LOAD_MORE = "UP";

    @Override
    public Call<String> getHttpCall(int curPage) {

        String slipType = curPage == 1 ? REFRESH : LOAD_MORE;
        String cursor = DataKeeper.getWu5LiCursor();
        return RetrofitFactory.getWu5LiVideoService().getVideos(cursor, slipType);
    }

    @Override
    public DataType getType() {

        return DataType.WU5LI;
    }

    @Override
    public int getName() {

        return R.string.wu5li_video;
    }
}
