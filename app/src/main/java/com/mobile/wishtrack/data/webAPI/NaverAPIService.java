package com.mobile.wishtrack.data.webAPI;

import com.mobile.wishtrack.Constants;
import com.mobile.wishtrack.data.model.Naver.NaverResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NaverAPIService {

    @GET(Constants.NAVER_API_SHOP_SEARCH_ADDRESS)
    Call<NaverResponse> search(
        @Query("query") String query,
        @Query("display") int display,
        @Query("start") int start,
        @Query("sort") String sort,
        @Query("filter") String filter,
        @Query("exclude") String exclude
    );
}
