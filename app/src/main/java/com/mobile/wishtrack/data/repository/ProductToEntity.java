package com.mobile.wishtrack.data.repository;

import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.data.model.product.ProductEntity;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.sharedData.constant.NaverProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductToEntity {
    public static ProductEntity convert(Product product) {
        return new ProductEntity(
                product.getTitle(),
                product.getLink(),
                product.getImage(),
                product.getLprice(),
                product.getHprice(),
                product.getMallName(),
                product.getProductId(),
                product.getProductType(),
                product.getMaker(),
                product.getBrand(),
                product.getCategory1(),
                product.getCategory2(),
                product.getCategory3(),
                product.getCategory4()
        );
    }

    public static ProductWithPrices convertToProductWithPrice(Product product) {
        final List<Product.Price> prices = product.getPrices();
        final List<PriceEntity> priceEntities = new ArrayList<>();

        for (Product.Price price : prices) {
            priceEntities.add(new PriceEntity(price.getDate(), price.getLPrice(), price.getHPrice()));
        }

        return new ProductWithPrices(
                convert(product),
                priceEntities
        );
    }

    public static Product convert(ProductWithPrices productWithPrices) {
        final ProductEntity productEntity = productWithPrices.getProduct();
        final List<Product.Price> prices = new ArrayList<>();

        for (PriceEntity priceEntity : productWithPrices.getPrices()) {
            prices.add(new Product.Price(priceEntity.getDate(), priceEntity.getLPrice(), priceEntity.getHPrice()));
        }

        return Product.newInstance(
                productEntity.getId(),
                productEntity.getTitle(),
                productEntity.getLink(),
                productEntity.getImage(),
                productEntity.getMallName(),
                productEntity.getProductId(),
                productEntity.getProductType(),
                productEntity.getMaker(),
                productEntity.getBrand(),
                productEntity.getCategory1(),
                productEntity.getCategory2(),
                productEntity.getCategory3(),
                productEntity.getCategory4(),
                prices,
                true
        );
    }

    public static List<Product> convert(List<NaverProduct> naverProducts) {
        List<Product> products = new ArrayList<>();

        for (NaverProduct naverProduct : naverProducts) {
            final List<Product.Price> price = new ArrayList<>();
            final int lprice = naverProduct.getLprice();
            final int hprice = naverProduct.getHprice();

            price.add(new Product.Price(
                    Calendar.getInstance(),lprice, hprice == 0 ? lprice : hprice
            ));

            products.add(Product.newInstance(
                    naverProduct.getTitle(),
                    naverProduct.getLink(),
                    naverProduct.getImage(),
                    naverProduct.getMallName(),
                    naverProduct.getProductId(),
                    naverProduct.getProductType(),
                    naverProduct.getMaker(),
                    naverProduct.getBrand(),
                    naverProduct.getCategory1(),
                    naverProduct.getCategory2(),
                    naverProduct.getCategory3(),
                    naverProduct.getCategory4(),
                    price,
                    false
            ));
        }

        return products;
    }
}
