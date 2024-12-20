package com.mobile.wishtrack.ui.repository;

import com.mobile.wishtrack.domain.model.Product;

import java.util.List;

public interface WishSearchManager {
    int setWish(Product product);
    void removeWish(int id);
    List<Product> getWishList();
}
