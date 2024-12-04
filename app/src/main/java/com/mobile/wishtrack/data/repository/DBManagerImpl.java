package com.mobile.wishtrack.data.repository;

import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.repository.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DBManagerImpl implements DBManager {
    private final PriceDao priceDao;
    private final ProductDao productDao;

    public DBManagerImpl(PriceDao priceDao, ProductDao productDao) {
        this.priceDao = priceDao;
        this.productDao = productDao;
    }

    @Override
    public void insert(Product product) {
        final ProductWithPrices productWithPrices = ProductToEntity.convertToProductWithPrice(product);
        productDao.insert(productWithPrices.getProduct());
        for(PriceEntity priceEntity : productWithPrices.getPrices()) {
            priceDao.insert(priceEntity);
        }
    }

    @Override
    public void delete(Product product) {
        productDao.delete(ProductToEntity.convert(product));
    }

    @Override
    public List<Product> selectAll() {
        final List<ProductWithPrices> productWithPrices = productDao.getAllProductsWithPrices();
        final List<Product> products = new ArrayList<>();

        for (ProductWithPrices productWithPrice : productWithPrices) {
            products.add(ProductToEntity.convert(productWithPrice));
        }

        return products;
    }
}
