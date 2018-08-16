package com.xb.toolkit.http.gson;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.xb.toolkit.Toolkit;
import com.xb.toolkit.utils.LogUtils;


import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, String cacheUrl) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String strValue = value.string();
            LogUtils.e("返回的Json数据:" + strValue);
            JsonReader jsonReader = gson.newJsonReader(new StringReader(strValue));
            return adapter.read(jsonReader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            value.close();
        }
        return null;
    }


}



