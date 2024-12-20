package com.mobile.wishtrack.data.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mobile.wishtrack.WHError;
import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.database.WTDatabase;
import com.mobile.wishtrack.data.model.Naver.NaverResponse;
import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.data.model.product.ProductEntity;
import com.mobile.wishtrack.data.webAPI.NaverAPIService;
import com.mobile.wishtrack.data.webAPI.RetrofitClient;
import com.mobile.wishtrack.sharedData.constant.NaverParameterSort;
import com.mobile.wishtrack.sharedData.constant.NaverProduct;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class PriceUpdateWorker extends Worker {
    private final ProductDao productDao;
    private final PriceDao priceDao;
    private final NaverAPIService naverAPIService;


    public PriceUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        WTDatabase wtDatabase = WTDatabase.getInstance(context);
        productDao = wtDatabase.productDao();
        priceDao = wtDatabase.priceDao();
        naverAPIService = RetrofitClient.getNaverAPIService();
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            updateProductPrices();
        } catch (Exception e){
            return Result.failure();
        }
        return Result.success();
    }

    private void updateProductPrices() {

        List<ProductWithPrices> productWithPrices = productDao.getAllProductsWithPrices();

        for (ProductWithPrices productWithPrice : productWithPrices) {
            ProductEntity product = productWithPrice.getProduct();
            PriceEntity price = productWithPrice.getPrices().get(0);

            String cleanTitle = product.getTitle().replaceAll("<[^>]*>", "");
            for (int start = 1; start < 50; start += 10){
                List<NaverProduct> result = search(cleanTitle, start, start + 10);
                boolean flag = false;
                for (NaverProduct naverProduct : result) {
                   if (naverProduct.getProductId() == product.getProductId()) {
                       flag = true;
                       int lprice = naverProduct.getLprice();
                       int hprice = naverProduct.getHprice();
                       hprice = Math.max(lprice, hprice);
                       if (lprice != price.getLprice() || hprice != price.getHprice()) {
                           priceDao.insert(new PriceEntity(product.getId(), Calendar.getInstance(), lprice, hprice));
                       }
                       break;
                   }
                }
                if (flag) break;
            }
        }
    }

    public List<NaverProduct> search(String title, int start, int display){

        Call<NaverResponse> call = naverAPIService.search(
                title,
                display,
                start,
                NaverParameterSort.SIM.getDescription(),
                "", ""
        );

        try{
            Response<NaverResponse> response = call.execute();
            if (response.isSuccessful()) {
                if (response.body() == null) return Collections.emptyList();
                NaverResponse naverResponse = response.body();
                return naverResponse.getItems();
            }
        } catch (IOException ioe) {
            Log.d("WHError", Objects.requireNonNull(ioe.getMessage()));
            throw new WHError(WHError.ErrorMessage.NAVER_SEARCH_ERROR);
        }

        throw new WHError(WHError.ErrorMessage.NAVER_SEARCH_RESPONSE_ERROR);
    }
}
