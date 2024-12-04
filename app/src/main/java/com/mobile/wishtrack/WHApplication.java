package com.mobile.wishtrack;

import android.app.Application;

import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.database.WTDatabase;
import com.mobile.wishtrack.ui.ThreadGenerator;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import lombok.Getter;

@Getter
public class WHApplication extends Application {

    protected ThreadGenerator threadGenerator;
    protected ProductSearchManager productSearchManager;
    protected WishSearchManager wishSearchManager;
    protected WTDatabase wtDatabase;
    protected ProductDao productDao;
    protected PriceDao priceDao;

    @Override
    public void onCreate() {
        super.onCreate();

        this.wtDatabase = WTDatabase.getInstance(this);
        this.productDao = wtDatabase.productDao();
        this.priceDao = wtDatabase.priceDao();
        this.threadGenerator = ThreadGenerator.newInstance();

    }
}
