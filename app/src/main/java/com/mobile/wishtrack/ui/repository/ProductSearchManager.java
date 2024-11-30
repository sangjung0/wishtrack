package com.mobile.wishtrack.ui.repository;

import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.model.Product;

import java.util.List;

public interface ProductSearchManager {
    List<Product> searchProduct(QueryStatement queryStatement);
}
