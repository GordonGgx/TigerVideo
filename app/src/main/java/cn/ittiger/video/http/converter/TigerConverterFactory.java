package cn.ittiger.video.http.converter;

import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.http.DataType;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

public class TigerConverterFactory extends Converter.Factory {
    private DataType mType;

    public TigerConverterFactory(DataType type) {

        mType = type;
    }

    public static TigerConverterFactory create(DataType type) {
        return new TigerConverterFactory(type);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Converter<ResponseBody, ?> converter = null;
        switch (mType) {
            case NET_EASY:
                converter = new NetEasyResponseBodyConverter<List<VideoData>>();
                break;
        }
        return converter;
    }
}
