package com.mobile.wishtrack.data.repository;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.repository.WebAPIManager;

import java.util.Collections;
import java.util.List;

public class WebAPIManagerImpl implements WebAPIManager {

    @Override
    public List<Product> search(QueryStatement queryStatement) {
        return Collections.emptyList();
    }
}
