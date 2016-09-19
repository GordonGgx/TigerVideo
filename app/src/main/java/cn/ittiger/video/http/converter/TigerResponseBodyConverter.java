package cn.ittiger.video.http.converter;

import cn.ittiger.video.http.DataType;
import cn.ittiger.video.http.parse.ResultParse;
import cn.ittiger.video.http.parse.ResultParseFactory;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.IOException;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
final class TigerResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private DataType mType;

    TigerResponseBodyConverter(DataType type) {

        mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {

            JSONObject jsonObj = new JSONObject(value.string());

            ResultParse parse = ResultParseFactory.create(mType);
            if(parse == null) {
                return null;
            }
            return (T) parse.parse(jsonObj);
        } catch(JSONException e) {
            Log.d("Converter", "converter error", e);
            return null;
        }
    }
}
