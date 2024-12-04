package com.mobile.wishtrack.ui.repository;

import com.mobile.wishtrack.domain.model.Product;

import java.util.List;

public interface WishSearchManager {
    void setWish(Product product);
    void removeWish(Product product);
    List<Product> getWishList();
}
