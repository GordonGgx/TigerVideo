package cn.ittiger.video.http.service;

import cn.ittiger.video.bean.VideoData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public interface NetEasyApi {

    @GET("recommend/getChanListNews?channel=T1457068979049&size=10")
    Call<List<VideoData>> getVideos(@Query("offset") int offset);
}
