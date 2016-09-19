package cn.ittiger.video.http.converter;

import cn.ittiger.video.bean.VideoData;
import cn.ittiger.video.http.DataType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

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

        return new TigerResponseBodyConverter<List<VideoData>>(mType);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        Gson gson = new Gson();
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new TigerRequestBodyConverter<>(gson, adapter);
    }
}
