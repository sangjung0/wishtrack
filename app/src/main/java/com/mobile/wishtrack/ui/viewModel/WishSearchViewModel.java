package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class WishSearchViewModel extends SearchViewModel {
    private final ExecutorService dbExecutor;
    private final WishSearchManager wishSearchManager;

    public WishSearchViewModel(ExecutorService dbExecutor, WishSearchManager wishSearchManager) {
        this.dbExecutor = dbExecutor;
        this.wishSearchManager = wishSearchManager;
    }

    @Override
    public void search(String query) {
        this.dbExecutor.execute(() -> {
            final List<Product> products = wishSearchManager.getWishList();
            productList.postValue(products);
        });
    }

    @Override
    protected ExecutorService getDBExecutor() {
        return dbExecutor;
    }

    @Override
    protected WishSearchManager getWishSearchManager() {
        return wishSearchManager;
    }
}
