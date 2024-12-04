package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductSearchViewModel extends SearchViewModel {
    private final ProductSearchManager productSearchManager;
    private final WishSearchManager wishSearchManager;

    private final ExecutorService networkExecutor, dbExecutor;


    public ProductSearchViewModel(ExecutorService dbExecutor,ExecutorService networkExecutor, WishSearchManager wishSearchManager, ProductSearchManager productSearchManager) {
        this.networkExecutor = networkExecutor;
        this.dbExecutor = dbExecutor;
        this.wishSearchManager = wishSearchManager;
        this.productSearchManager = productSearchManager;
    }

    @Override
    public void search(String query) {
        this.networkExecutor.execute(() -> {
            final QueryStatement queryStatement = QueryStatement.newInstance(query, queryOptions);
            final List<Product> products = productSearchManager.searchProduct(queryStatement);
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

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
