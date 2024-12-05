package com.mobile.wishtrack.data.repository;

import android.util.Log;

import com.mobile.wishtrack.Constants;
import com.mobile.wishtrack.WHError;
import com.mobile.wishtrack.data.model.Naver.NaverResponse;
import com.mobile.wishtrack.data.webAPI.NaverAPIService;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryOptions;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.repository.WebAPIManager;
import com.mobile.wishtrack.sharedData.constant.NaverParameterKind;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class WebAPIManagerImpl implements WebAPIManager {

    private final NaverAPIService naverAPIService;

    public WebAPIManagerImpl(NaverAPIService naverAPIService) {
        this.naverAPIService = naverAPIService;
    }

    @Override
    public List<Product> search(QueryStatement queryStatement){
        QueryOptions queryOptions = queryStatement.getOptions();
        List<NaverParameterKind> excludeList = queryOptions.getExclude();
        StringBuilder exclude = new StringBuilder();

        for (int i = 0; i < excludeList.size(); i++) {
            exclude.append(excludeList.get(i).getDescription());
            if (i < excludeList.size() - 1) {
                exclude.append(":");
            }
        }

        Call<NaverResponse> call = naverAPIService.search(
                queryStatement.getQuery(),
                queryOptions.getDisplay(),
                queryOptions.getStart(),
                queryOptions.getSort().getDescription(),
                queryOptions.getFilter(),
                String.join(":", exclude)
        );

        try{
            Response<NaverResponse> response = call.execute();
            if (response.isSuccessful()) {
                if (response.body() == null) return Collections.emptyList();
                NaverResponse naverResponse = response.body();
                return ProductToEntity.convert(naverResponse.getItems());
            }
        } catch (IOException ioe) {
            Log.d("WHError", Objects.requireNonNull(ioe.getMessage()));
            throw new WHError(WHError.ErrorMessage.NAVER_SEARCH_ERROR);
        }

        throw new WHError(WHError.ErrorMessage.NAVER_SEARCH_RESPONSE_ERROR);
    }
}
