package com.mobile.wishtrack.data.webAPI;

import com.mobile.wishtrack.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = Constants.NAVER_API_ADDRESS;
    private static Retrofit naverRetrofit;

    public static Retrofit getNaverClient() {
        if (naverRetrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("X-Naver-Client-Id", Constants.NAVER_CLIENT_ID)
                                .header("X-Naver-Client-Secret", Constants.NAVER_CLIENT_SECRET)
                                .build();
                        return chain.proceed(request);
                    })
                    .build();

            naverRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonProvider.getGson()))
                    .client(client)
                    .build();
        }
        return naverRetrofit;
    }

    public static NaverAPIService getNaverAPIService() {
        return RetrofitClient.getNaverClient().create(NaverAPIService.class);
    }
}
