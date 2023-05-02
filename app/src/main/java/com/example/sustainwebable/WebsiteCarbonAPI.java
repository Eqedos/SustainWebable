package com.example.sustainwebable;

import java.util.concurrent.TimeUnit;

import okhttp3.*;

import okhttp3.*;

public class WebsiteCarbonAPI {
    private final String BASE_URL = "https://api.websitecarbon.com/";

    public void getCarbonData(String url, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build();


        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addPathSegment("site");
        urlBuilder.addQueryParameter("url", url);

        String finalUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(finalUrl)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}


