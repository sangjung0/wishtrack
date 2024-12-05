package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.WHError;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductSearchViewModel extends SearchViewModel {
    private final ProductSearchManager productSearchManager;
    private final ExecutorService networkExecutor;

    public ProductSearchViewModel(ExecutorService dbExecutor,ExecutorService networkExecutor, WishSearchManager wishSearchManager, ProductSearchManager productSearchManager) {
        super(dbExecutor, wishSearchManager);
        this.networkExecutor = networkExecutor;
        this.productSearchManager = productSearchManager;
    }

    @Override
    public void search(String query, Consumer<String> errorHandler) {
        this.networkExecutor.execute(() -> {
            try{
                final QueryStatement queryStatement = QueryStatement.newInstance(query, queryOptions);
                final List<Product> products = productSearchManager.searchProduct(queryStatement);
                productList.postValue(products);
            } catch (WHError e) {
                errorHandler.accept(e.getMessage());
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
