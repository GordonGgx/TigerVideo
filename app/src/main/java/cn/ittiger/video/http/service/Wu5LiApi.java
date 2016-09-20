package cn.ittiger.video.http.service;

import cn.ittiger.video.bean.VideoData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.List;

/**
 * http://api.5wuli.com/v1/video/list?pageSize=10&cursor=1474280470258&slipType=DOWN
 *
 * 唔哩视频
 *
 * @author laohu
 * @site http://ittiger.cn
 */
public interface Wu5LiApi {

    @Headers({"Content-Type: application/json;charset=UTF-8",
              "Accept-Encoding: gzip","Connection: Keep-Alive",
              "User-Agent: Dalvik/2.1.0 (Linux; U; Android 5.0.2; PLK-AL10 Build/HONORPLK-AL10) Wuli/2.4.0 (agent:s;channel:huawei;credential:e1MyMDEwMDAxMDF9LXsjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIzg2MDU4MjAzNDI5NzY4NX0teyMjIyMjIyMxNDc0MjgxNTc2OTk2LTY5MjUxMDY5Mzd9QDE0NzM0NzQ5NzM1NDk=;deviceId:860582034297685;osTypeId:01;detailInfo:android;simTypeId:02;netTypeId:01;deviceTypeId:01;osVersion:5.0.2;idfa:null)"})
    @GET("v1/video/list?pageSize=10")
    Call<List<VideoData>> getVideos(@Query("cursor") String cursor, @Query("slipType") String slipType);
}
