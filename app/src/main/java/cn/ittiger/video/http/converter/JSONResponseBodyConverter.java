package cn.ittiger.video.http.converter;

import cn.ittiger.video.http.DataType;
import cn.ittiger.video.http.parse.ResultParse;
import cn.ittiger.video.http.parse.ResultParseFactory;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import java.io.IOException;

/**
 * @author laohu
 * @site http://ittiger.cn
 */
final class JSONResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private DataType mType;

    JSONResponseBodyConverter(DataType type) {

        mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {

            JsonReader jsonReader = new Gson().newJsonReader(value.charStream());
            jsonReader.setLenient(true);
            jsonReader.beginObject();
            while(jsonReader.hasNext()) {
                String name = jsonReader.nextName();
                String string = jsonReader.nextString();
                Log.d("Tag", string);
            }
            jsonReader.endObject();
            ResultParse parse = ResultParseFactory.create(mType);
            if(parse == null) {
                return null;
            }
            return null;
        } catch(Exception e) {
            Log.d("Converter", "converter error", e);
            return null;
        }
    }
}
