package cn.ittiger.video.http.parse;

import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.bean.VideoTabData;
import cn.ittiger.video.http.DataType;
import cn.ittiger.video.util.DataKeeper;
import cn.ittiger.video.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class Wu5LiResultParse implements ResultParse {
    private static final String KEY_VIDEO_LIST = "data";
    private static final String KEY_ID = "newsId";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VIDEO_URL = "srcLink";
    private static final String KEY_CURSOR = "createTime";

    private static final String KEY_IMAGE_ITEM = "newsImageInfoList";
    private static final String KEY_IMAGE = "src";

    private static final String KEY_DURATION_ITEM = "paraMap";
    private static final String KEY_DURATION = "duration";

    @Override
    public List<VideoData> parse(JSONObject json) throws JSONException {

        List<VideoData> list = new ArrayList<>();

        JSONArray videos = json.getJSONArray(KEY_VIDEO_LIST);
        JSONObject item;
        for(int i = 0; i < videos.length(); i++) {
            item = videos.getJSONObject(i);
            VideoData videoData = new VideoData();
            videoData.setDataType(DataType.WU5LI.value());
            videoData.setId(item.optString(KEY_ID));
            videoData.setTitle(item.optString(KEY_TITLE));
            videoData.setVideoUrl(item.optString(KEY_VIDEO_URL));

            JSONObject imageItem = item.getJSONArray(KEY_IMAGE_ITEM).getJSONObject(0);
            videoData.setImageUrl(imageItem.optString(KEY_IMAGE));

            JSONObject durationItem = item.getJSONObject(KEY_DURATION_ITEM);
            long duration = durationItem.optLong(KEY_DURATION);
            videoData.setDuration(Utils.formatTimeLength(duration / 1000));
            list.add(videoData);
            if(i == videos.length() - 1) {
                DataKeeper.saveWu5LiCursor(item.optString(KEY_CURSOR));
            }
        }
        return list;
    }

    @Override
    public List<VideoTabData> parseTab(JSONObject json) throws JSONException {

        return null;
    }
}
