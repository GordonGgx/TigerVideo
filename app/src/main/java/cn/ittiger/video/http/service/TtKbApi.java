package cn.ittiger.video.http.service;

import cn.ittiger.video.bean.VideoData;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;
import java.util.Map;

/**
 *
 * http://video.toutiaokuaibao.com/app_video/getvideos
 *
 * 头条快报视频
 *
 * @author laohu
 * @site http://ittiger.cn
 */
public interface TtKbApi {

    @Headers({/*"Content-Type: application/x-www-form-urlencoded;charset=UTF-8",*/
              "Accept-Encoding: gzip",
              "User-Agent: okhttp/3.2.0"})
    @FormUrlEncoded
    @POST("app_video/getvideos")
    Call<List<VideoData>> getVideos(@FieldMap Map<String, String> fieldMap);
}
