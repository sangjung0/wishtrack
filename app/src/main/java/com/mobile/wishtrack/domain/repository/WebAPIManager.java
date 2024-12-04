package com.mobile.wishtrack.domain.repository;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryStatement;

import java.util.List;

public interface WebAPIManager {
    List<Product> search(QueryStatement queryStatement);
}
