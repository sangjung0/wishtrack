package com.mobile.wishtrack.domain.usecase;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.repository.DBManager;
import com.mobile.wishtrack.domain.repository.WebAPIManager;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchManagerImpl implements ProductSearchManager {
    private final WebAPIManager webAPIManager;
    private final DBManager dbManager;

    public ProductSearchManagerImpl(WebAPIManager webAPIManager, DBManager dbManager) {
        this.webAPIManager = webAPIManager;
        this.dbManager = dbManager;
    }

    @Override
    public List<Product> searchProduct(QueryStatement queryStatement) {
        final List<Product> products = webAPIManager.search(queryStatement);
        final List<Product> searchProducts = new ArrayList<>();

        // TODO 이 부분이 여기 있는게 맞는 느낌이지만, 로직상으로는 여기 있으면 별로다.
        for (Product product: products){
            final Integer id = dbManager.getIdByProductId(product.getProductId());
            if (id != null){
                product.setId(id);
                dbManager.updatePrice(product);
                product = dbManager.selectById(id);
            }
            searchProducts.add(product);
        }

        return searchProducts;
    }
}
