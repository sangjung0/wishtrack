package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductSearchViewModel extends SearchViewModel {
    private final ProductSearchManager productSearchManager;

    private final ExecutorService networkExecutor;


    public ProductSearchViewModel(ExecutorService networkExecutor, ProductSearchManager productSearchManager) {
        this.networkExecutor = networkExecutor;
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
    protected void onCleared() {
        super.onCleared();
    }
}
