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
public class NetEasyResultParse implements ResultParse {
    private static final String KEY_VIDEO = "视频";

    @Override
    public List<VideoData> parse(JSONObject json) throws JSONException {

        List<VideoData> list = new ArrayList<>();

        JSONArray videos = json.getJSONArray(KEY_VIDEO);
        JSONObject item;
        for(int i = 0; i < videos.length(); i++) {
            item = videos.getJSONObject(i);
            VideoData videoData = new VideoData();
            videoData.setId(item.optString("topicSid"));
            videoData.setImageUrl(item.optString("topicImg"));
            videoData.setTitle(item.optString("title"));
            videoData.setVideoUrl(item.optString("mp4_url"));
            long playDuration = item.optLong("playCount");
            videoData.setDuration(Utils.formatTimeLength(playDuration));
            list.add(videoData);
        }
        return list;
    }
}
