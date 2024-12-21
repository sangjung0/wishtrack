package com.mobile.wishtrack.domain.repository;

import com.mobile.wishtrack.domain.model.Product;

import java.util.List;

public interface DBManager {
    Product selectById(int id);
    void updatePrice(Product product);
    Integer getIdByProductId(long productId);
    int insert(Product product);
    void delete(int id);
    List<Product> selectAll();
}
