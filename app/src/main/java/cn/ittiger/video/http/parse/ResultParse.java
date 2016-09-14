package cn.ittiger.video.http.parse;

import cn.ittiger.video.bean.VideoData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public interface ResultParse {

    List<VideoData> parse(JSONObject json) throws JSONException;
}
