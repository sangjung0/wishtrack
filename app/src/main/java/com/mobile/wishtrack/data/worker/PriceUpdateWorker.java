package com.mobile.wishtrack.data.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mobile.wishtrack.R;
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
import com.mobile.wishtrack.ui.Notification;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class PriceUpdateWorker extends Worker {
    private final ProductDao productDao;
    private final PriceDao priceDao;
    private final NaverAPIService naverAPIService;
    private final Context context;


    public PriceUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        WTDatabase wtDatabase = WTDatabase.getInstance(context);
        this.context = context;
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
        Notification.GroupMessage groupMessages = new Notification.GroupMessage(R.drawable.baseline_shopping_cart_24, "가격 변동 탐지!");

        for (ProductWithPrices productWithPrice : productWithPrices) {
            List<PriceEntity> prices = productWithPrice.getPrices();
            PriceEntity price = prices.get(prices.size()-1);
            NaverProduct naverProduct = searchAndUpdate(productWithPrice.getProduct(), price);
            if (naverProduct != null) {
                final int lpriceGap = naverProduct.getLprice() - price.getLprice();
                final float priceChangeRate = (float) lpriceGap /price.getLprice();
                final String cleanTitle = productWithPrice.getProduct().getTitle().replaceAll("<[^>]*>", "");
                final int icon;
                final String title;
                final String text;

                if (priceChangeRate > 0.1){
                    icon = R.drawable.baseline_keyboard_double_arrow_up_24;
                    title = "\""+ cleanTitle + "\" 상품의 가격이 엄청 올랐어요!";
                    text = lpriceGap + "원 급상승!";
                }
                else if (priceChangeRate > 0) {
                    icon = R.drawable.baseline_keyboard_arrow_up_24;
                    title = "\""+cleanTitle + "\" 상품의 가격이 올랐어요!";
                    text = lpriceGap + "원 상승!";
                }
                else if (priceChangeRate < -0.1) {
                    icon = R.drawable.baseline_keyboard_double_arrow_down_24;
                    title = "\""+cleanTitle + "\" 상품의 가격이 엄청 내려갔어요!";
                    text = lpriceGap + "원 급하락!";
                }
                else {
                    icon = R.drawable.baseline_keyboard_arrow_down_24;
                    title = "\""+cleanTitle + "\" 상품의 가격이 내려갔어요!";
                    text = lpriceGap + "원 하락!";
                }

                groupMessages.addContent(icon, title, text);
            }
        }

        Notification.sendGroupedNotifications(context, groupMessages);
    }

    private NaverProduct searchAndUpdate(ProductEntity product, PriceEntity price){
        String cleanTitle = product.getTitle().replaceAll("<[^>]*>", "");

        for (int start = 1; start < 50; start += 10){
            List<NaverProduct> result = search(cleanTitle, start, start + 10);
            for (NaverProduct naverProduct : result) {
                if (naverProduct.getProductId() == product.getProductId()) {
                    int lprice = naverProduct.getLprice();
                    int hprice = naverProduct.getHprice();
                    hprice = Math.max(lprice, hprice);

                    //TODO 테스트 코드
//                    lprice = lprice + (int)((Math.random() -0.5)*lprice/2);
//                    hprice = hprice + (int)((Math.random() -0.5)*lprice/2);
//                    if (lprice < 1000) lprice = 1000;
//                    if (hprice < lprice) hprice = lprice;
//
                    if (lprice != price.getLprice() || hprice != price.getHprice()) {
                        //TODO 테스트 코드
//                        Calendar calendar = Calendar.getInstance();
//                        Random random = new Random();
//                        int randomDays = random.nextInt(56) + 7;
//                        calendar.add(Calendar.DAY_OF_YEAR, -randomDays);
//                        priceDao.insert(new PriceEntity(product.getId(), calendar, lprice, hprice));

                        priceDao.insert(new PriceEntity(product.getId(), Calendar.getInstance(), lprice, hprice));

                        naverProduct.setHprice(hprice);
                        naverProduct.setLprice(lprice);
                        return naverProduct;
                    }
                    return null;
                }
            }
        }
        return null;
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
