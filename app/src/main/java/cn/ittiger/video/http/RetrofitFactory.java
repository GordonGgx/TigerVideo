package cn.ittiger.video.http;

import cn.ittiger.video.http.converter.TigerConverterFactory;
import cn.ittiger.video.http.service.NetEasyApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
public class RetrofitFactory {

    private static final int TIME_OUT = 12;//超时时间
    private static final String NETEASY_BASE_URL = "http://c.m.163.com/";
    private static volatile NetEasyApi sNetEasyService;

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
}
