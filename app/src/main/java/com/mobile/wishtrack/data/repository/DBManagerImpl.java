package com.mobile.wishtrack.data.repository;

import com.mobile.wishtrack.data.dao.PriceDao;
import com.mobile.wishtrack.data.dao.ProductDao;
import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.repository.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBManagerImpl implements DBManager {
    private final PriceDao priceDao;
    private final ProductDao productDao;

    public DBManagerImpl(ProductDao productDao, PriceDao priceDao) {
        this.productDao = productDao;
        this.priceDao = priceDao;
    }

    @Override
    public Integer getIdByProductId(long productId) {
        return productDao.getIdByProductId(productId);
    }

    @Override
    public void updatePrice(Product product) {
        final ProductWithPrices productWithPrices = ProductToEntity.convertToProductWithPrice(product);
        PriceEntity lastPriceEntity = priceDao.getLatestPriceOfProduct(product.getId());
        final List<PriceEntity> newPrices = new ArrayList<>();

        for (PriceEntity priceEntity : productWithPrices.getPrices()) {
            if (priceEntity.getDate().before(lastPriceEntity.getDate())) return;
            newPrices.add(priceEntity);
        }

        for (PriceEntity priceEntity : newPrices){
            if (lastPriceEntity.getHprice() == priceEntity.getHprice() && lastPriceEntity.getLprice() == priceEntity.getLprice()) continue;
            priceEntity.setPid(product.getId());
            priceDao.insert(priceEntity);
            lastPriceEntity = priceEntity;
        }
    }

    @Override
    public int insert(Product product) {
        final ProductWithPrices productWithPrices = ProductToEntity.convertToProductWithPrice(product);
        final int productId = (int) productDao.insert(productWithPrices.getProduct());
        for(PriceEntity priceEntity : productWithPrices.getPrices()) {
            priceEntity.setPid(productId);
            priceDao.insert(priceEntity);
        }

        return productId;
    }

    @Override
    public void delete(int id) {
        productDao.delete(id);
    }

    @Override
    public List<Product> selectAll() {
        final List<ProductWithPrices> productWithPrices = productDao.getAllProductsWithPrices();
        final List<Product> products = new ArrayList<>();

        for (ProductWithPrices productWithPrice : productWithPrices) {
            //TODO 현재 sql의 한계로 데이터를  한번에 불러올 때, 정렬된 상태로 불러오는 것이 불가능함. SQL 장점을 살리기 위해 이렇게 만들었는데.. 쓸모 없어짐
            final List<PriceEntity> prices = productWithPrice.getPrices();
            Collections.reverse(prices);

            products.add(ProductToEntity.convert(productWithPrice));
        }

        return products;
    }

    @Override
    public Product selectById(int id) {
        //TODO sql 정렬 문제 그대로
        final ProductWithPrices productWithPrices = productDao.getProductWithPricesById(id);
        final List<PriceEntity> prices = productWithPrices.getPrices();
        Collections.reverse(prices);

        return ProductToEntity.convert(productWithPrices);
    }

    @Deprecated
    public Product selectByProductId(long productId) {
        //TODO sql 정렬 문제 그대로
        final ProductWithPrices productWithPrices = productDao.getProductWithPricesByProductId(productId);
        final List<PriceEntity> prices = productWithPrices.getPrices();
        Collections.reverse(prices);

        return ProductToEntity.convert(productWithPrices);
    }
}
