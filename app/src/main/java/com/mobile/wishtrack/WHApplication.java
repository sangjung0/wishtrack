package com.mobile.wishtrack;

import android.app.Application;

import com.mobile.wishtrack.ui.ThreadGenerator;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

public class WHApplication extends Application {

    protected ThreadGenerator threadGenerator;
    protected ProductSearchManager productSearchManager;
    protected WishSearchManager wishSearchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        this.threadGenerator = ThreadGenerator.newInstance();
    }

    public ThreadGenerator getThreadGenerator() {
        return threadGenerator;
    }

    public ProductSearchManager getProductSearchManager() {
        return productSearchManager;
    }

    public WishSearchManager getWishSearchManager() {
        return wishSearchManager;
    }
}
