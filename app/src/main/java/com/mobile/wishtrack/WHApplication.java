package com.mobile.wishtrack;

import android.app.Application;

import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.database.WTDatabase;
import com.mobile.wishtrack.data.repository.DBManagerImpl;
import com.mobile.wishtrack.data.repository.WebAPIManagerImpl;
import com.mobile.wishtrack.data.webAPI.NaverAPIService;
import com.mobile.wishtrack.data.webAPI.RetrofitClient;
import com.mobile.wishtrack.domain.repository.DBManager;
import com.mobile.wishtrack.domain.repository.WebAPIManager;
import com.mobile.wishtrack.domain.usecase.ProductSearchManagerImpl;
import com.mobile.wishtrack.domain.usecase.WishSearchManagerImpl;
import com.mobile.wishtrack.ui.ThreadGenerator;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import lombok.Getter;

@Getter
public class WHApplication extends Application {

    /* public */
    protected ThreadGenerator threadGenerator;

    /* data */
    protected WTDatabase wtDatabase;
    protected ProductDao productDao;
    protected PriceDao priceDao;
    protected WebAPIManager webAPIManager;
    protected DBManager dbManager;
    protected NaverAPIService naverAPIService;

    protected ProductSearchManager productSearchManager;
    protected WishSearchManager wishSearchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        /* public */
        this.threadGenerator = ThreadGenerator.newInstance();

        /* data */
        this.wtDatabase = WTDatabase.getInstance(this);
        this.productDao = wtDatabase.productDao();
        this.priceDao = wtDatabase.priceDao();
        this.naverAPIService = RetrofitClient.getNaverAPIService();
        this.webAPIManager = new WebAPIManagerImpl(naverAPIService);
        this.dbManager = new DBManagerImpl(productDao, priceDao);

        this.productSearchManager = new ProductSearchManagerImpl(webAPIManager, dbManager);
        this.wishSearchManager = new WishSearchManagerImpl(dbManager);
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        this.threadGenerator.close();
    }
}
