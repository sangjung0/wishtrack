package com.mobile.wishtrack.data.repository;

import com.mobile.wishtrack.data.model.price.PriceEntity;
import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.data.model.product.ProductEntity;
import com.mobile.wishtrack.domain.model.Product;

import java.util.ArrayList;
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
                product.getLPrice(),
                product.getHPrice(),
                product.getMallName(),
                product.getProductId(),
                product.getNaverProductType(),
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
                productEntity.getNaverProductType(),
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
}
