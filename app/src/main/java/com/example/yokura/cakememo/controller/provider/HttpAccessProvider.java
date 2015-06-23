package com.example.yokura.cakememo.controller.provider;

import com.example.yokura.cakememo.model.system.MyContract;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by YOKURA on 4/19/15.
 */
public class HttpAccessProvider {
    private Request request;
    private OkHttpClient client;

    public String getJsonString(String url) {
        String result = "";

        request = new Request.Builder()
            .url(url)
            .get()
            .build();

        // クライアントオブジェクトを作って
        client = new OkHttpClient();

        // リクエストして結果を受け取って
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返す
        return result;
    }
}
