package cn.ittiger.video.http.converter;

import cn.ittiger.video.http.parse.NetEasyResultParse;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
final class NetEasyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    NetEasyResponseBodyConverter() {

    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {

            JSONObject jsonObj = new JSONObject(value.string());

            return (T) new NetEasyResultParse().parse(jsonObj);
        } catch(JSONException e) {
            return null;
        }
    }
}
