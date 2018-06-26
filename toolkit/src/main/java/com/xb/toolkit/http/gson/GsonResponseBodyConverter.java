package com.xb.toolkit.http.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import okhttp3.ResponseBody;
import retrofit2.Converter;
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//        Reader reader = value.charStream();
        try {
            String strValue = value.string();
            JsonReader jsonReader = gson.newJsonReader(new StringReader(strValue));
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }



}



