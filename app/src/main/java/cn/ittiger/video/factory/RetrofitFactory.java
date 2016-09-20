package cn.ittiger.video.factory;

import cn.ittiger.video.http.service.IFengApi;
import cn.ittiger.video.http.service.Wu5LiApi;
import cn.ittiger.video.http.service.NetEasyApi;
import cn.ittiger.video.http.service.TtKbApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class RetrofitFactory {

    private static final int TIME_OUT = 12;//超时时间
    private static final String NETEASY_BASE_URL = "http://c.m.163.com/";
    private static final String TTKB_BASE_URL = "http://video.toutiaokuaibao.com/";
    private static final String WU5LI_BASE_URL = "http://api.5wuli.com/";
    private static final String IFENG_BASE_URL = "http://vcis.ifeng.com/";
    private static volatile NetEasyApi sNetEasyService;
    private static volatile Wu5LiApi sWu5LiService;
    private static volatile TtKbApi sTtKbService;
    private static volatile IFengApi sIFengService;

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
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NETEASY_BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(NetEasyApi.class);
    }

    public static Wu5LiApi getWu5LiVideoService() {

        if(sWu5LiService == null) {
            synchronized (RetrofitFactory.class) {
                if(sWu5LiService == null) {
                    sWu5LiService = createWu5LiService();
                }
            }
        }
        return sWu5LiService;
    }

    private static Wu5LiApi createWu5LiService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WU5LI_BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(Wu5LiApi.class);
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
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TTKB_BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(TtKbApi.class);
    }

    public static IFengApi getIFengVideoService() {

        if(sIFengService == null) {
            synchronized (RetrofitFactory.class) {
                if(sIFengService == null) {
                    sIFengService = createIFengService();
                }
            }
        }
        return sIFengService;
    }

    private static IFengApi createIFengService() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IFENG_BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(IFengApi.class);
    }
}
