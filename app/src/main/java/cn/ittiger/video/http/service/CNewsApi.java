package cn.ittiger.video.http.service;

import cn.ittiger.video.bean.VideoData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

/**
 * http://r.cnews.qq.com/getSubNewsChlidInterest?last_id=20160910V03GWE00&forward=1&bottom_id=20160910V03GWE00&apptype=android&hw=HUAWEI_PLK-AL10&appversion=2.4.0&appver=21_areading_2.4.0&top_id=20140819V005HY00&page=1&direction=0&chlid=kb_video_news
 *
 * 天天快报视频
 *
 * @author laohu
 * @site http://ittiger.cn
 */
public interface CNewsApi {

    @GET("getSubNewsChlidInterest?last_id=20160910V03GWE00&bottom_id=20160910V03GWE00&apptype=android&hw=HUAWEI_PLK-AL10&appversion=2.4.0&appver=21_areading_2.4.0&top_id=20140819V005HY00&chlid=kb_video_news")
    Call<List<VideoData>> getVideos(@Query("page") int page, @Query("direction") int direction, @Query("forward") int forward);
}
