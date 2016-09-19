package cn.ittiger.video.http.parse;

import cn.ittiger.video.bean.VideoData;
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
public class CNewsResultParse implements ResultParse {
    private static final String KEY_VIDEO_LIST = "newslist";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";

    private static final String KEY_VIDEO_CHANNEL = "video_channel";
    private static final String KEY_VIDEO = "video";
    private static final String KEY_IMAGE = "img";
    private static final String KEY_VIDEO_URL = "playurl";
    private static final String KEY_DURATION = "duration";

    @Override
    public List<VideoData> parse(JSONObject json) throws JSONException {

        List<VideoData> list = new ArrayList<>();

        JSONArray videos = json.getJSONArray(KEY_VIDEO_LIST);
        JSONObject item;
        for(int i = 0; i < videos.length(); i++) {
            item = videos.getJSONObject(i);
            VideoData videoData = new VideoData();
            videoData.setId(item.optString(KEY_ID));
            videoData.setTitle(item.optString(KEY_TITLE));

            item = item.getJSONObject(KEY_VIDEO_CHANNEL);
            item = item.getJSONObject(KEY_VIDEO);

            videoData.setImageUrl(item.optString(KEY_IMAGE));
            videoData.setVideoUrl(item.optString(KEY_VIDEO_URL));
            String duration = item.optString(KEY_DURATION);
            videoData.setDuration(duration);
            list.add(videoData);
        }
        return list;
    }
}
