package com.mobile.wishtrack.domain.repository;

import com.mobile.wishtrack.domain.model.Product;

import java.util.List;

public interface DBManager {
    void insert(Product product);
    void delete(Product product);
    List<Product> selectAll();
}
