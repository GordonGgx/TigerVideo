package cn.ittiger.video.http;

import cn.ittiger.video.http.converter.TigerConverterFactory;
import cn.ittiger.video.http.service.CNewsApi;
import cn.ittiger.video.http.service.NetEasyApi;
import cn.ittiger.video.http.service.TtKbApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

/*
http://c.m.163.com/recommend/getChanListNews?channel=T1457068979049&size=20&offset=60

http://lf.snssdk.com/api/news/feed/v43/?category=video&refer=20&count=20&tt_from=tab&device_platform=android&language=zh

http://r.cnews.qq.com/getSubNewsChlidInterest?last_id=20160910V03GWE00&forward=1&bottom_id=20160910V03GWE00&apptype=android&hw=HUAWEI_PLK-AL10&appversion=2.4.0&appver=21_areading_2.4.0&top_id=20140819V005HY00&page=1&direction=0&chlid=kb_video_news

http://r.cnews.qq.com/getSubNewsChlidInterest?last_id=20160901V00Q1700&forward=1&bottom_id=20160910V03GWE00&apptype=android&hw=HUAWEI_PLK-AL10&appversion=2.4.0&appver=21_areading_2.4.0&top_id=20160913V02C9800&page=2&direction=1&chlid=kb_video_news

*/

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class RetrofitFactory {

    private static final int TIME_OUT = 12;//超时时间
    private static final String NETEASY_BASE_URL = "http://c.m.163.com/";
    private static final String TTKB_BASE_URL = "http://video.toutiaokuaibao.com/";
    private static final String CNEWS_BASE_URL = "http://r.cnews.qq.com/";
    private static volatile NetEasyApi sNetEasyService;
    private static volatile CNewsApi sCNewsService;
    private static volatile TtKbApi sTtKbService;

    public static NetEasyApi getNetEasyVideoService() {

        if(sNetEasyService == null) {
            synchronized (RetrofitFactory.class) {
                if(sNetEasyService == null) {
                    sNetEasyService = createNetEasyService();
                }
            }
        }
        return sNetEasyService;
    }

    private static NetEasyApi createNetEasyService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NETEASY_BASE_URL)
                .client(client)
                .addConverterFactory(TigerConverterFactory.create(DataType.NET_EASY))
                .build();
        return retrofit.create(NetEasyApi.class);
    }

    public static CNewsApi getCNewsVideoService() {

        if(sCNewsService == null) {
            synchronized (RetrofitFactory.class) {
                if(sCNewsService == null) {
                    sCNewsService = createCNewsService();
                }
            }
        }
        return sCNewsService;
    }

    private static CNewsApi createCNewsService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CNEWS_BASE_URL)
                .client(client)
                .addConverterFactory(TigerConverterFactory.create(DataType.CNEWS))
                .build();
        return retrofit.create(CNewsApi.class);
    }

    public static TtKbApi getTtKbVideoService() {

        if(sTtKbService == null) {
            synchronized (RetrofitFactory.class) {
                if(sTtKbService == null) {
                    sTtKbService = createTtKbService();
                }
            }
        }
        return sTtKbService;
    }

    private static TtKbApi createTtKbService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TTKB_BASE_URL)
                .client(client)
                .addConverterFactory(TigerConverterFactory.create(DataType.TTKB))
                .build();
        return retrofit.create(TtKbApi.class);
    }
}
