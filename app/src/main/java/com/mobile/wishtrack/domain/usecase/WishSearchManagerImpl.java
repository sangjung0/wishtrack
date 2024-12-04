package com.mobile.wishtrack.domain.usecase;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.repository.DBManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;

public class WishSearchManagerImpl implements WishSearchManager {
    private final DBManager dbManager;

    public WishSearchManagerImpl(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void setWish(Product product) {
        dbManager.insert(product);
    }

    @Override
    public void removeWish(Product product) {
        dbManager.delete(product);
    }

    @Override
    public List<Product> getWishList() {
        return dbManager.selectAll();
    }
}
