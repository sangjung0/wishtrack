package com.mobile.wishtrack.domain.usecase;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.repository.WebAPIManager;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;

import java.util.List;

public class ProductSearchManagerImpl implements ProductSearchManager {
    private final WebAPIManager webAPIManager;

    public ProductSearchManagerImpl(WebAPIManager webAPIManager) {
        this.webAPIManager = webAPIManager;
    }

    @Override
    public List<Product> searchProduct(QueryStatement queryStatement) {
        return webAPIManager.search(queryStatement);
    }
}
