package com.mobile.wishtrack.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mobile.wishtrack.data.model.price.ProductWithPrices;
import com.mobile.wishtrack.data.model.product.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    long insert(ProductEntity productEntity);

    @Update
    void update(ProductEntity product);

    @Delete
    void delete(ProductEntity product);

    @Query("DELETE FROM product WHERE id = :id")
    void delete(int id);

    @Query("SELECT id FROM Product WHERE ProductId = :productId LIMIT 1")
    Integer getIdByProductId(long productId);

    @Query("SELECT * FROM Product ORDER BY wishDate DESC")
    List<ProductEntity> getAllProducts();

    @Query("SELECT * FROM Product WHERE id = :id")
    ProductEntity getProductById(int id);

    @Query(
            "SELECT p.*, " +
                    "  '[' || GROUP_CONCAT(" +
                    "      '{\"pid\":' || pr.pid || ',\"date\":' || pr.date || ',\"lprice\":' || pr.lprice || ',\"hprice\":' || pr.hprice || '}'" +
                    "  ) || ']' AS prices " +
                    "FROM Product p " +
                    "LEFT JOIN Price pr ON p.id = pr.pid " +
                    "WHERE p.id = :id " +
                    "GROUP BY p.id " +
                    "ORDER BY p.wishDate DESC"
    )
    ProductWithPrices getProductWithPricesById(int id);

    @Query(
            "SELECT p.*, " +
                    "  '[' || GROUP_CONCAT(" +
                    "      '{\"pid\":' || pr.pid || ',\"date\":' || pr.date || ',\"lprice\":' || pr.lprice || ',\"hprice\":' || pr.hprice || '}'" +
                    "  ) || ']' AS prices " +
                    "FROM Product p " +
                    "LEFT JOIN Price pr ON p.id = pr.pid " +
                    "WHERE p.productId = :productId " +
                    "GROUP BY p.id " +
                    "ORDER BY p.wishDate DESC"
    )
    ProductWithPrices getProductWithPricesByProductId(long productId);

    @Query(
            "SELECT p.*, " +
                    "  '[' || GROUP_CONCAT(" +
                    "      '{\"pid\":' || pr.pid || ',\"date\":' || pr.date || ',\"lprice\":' || pr.lprice || ',\"hprice\":' || pr.hprice || '}'" +
                    "  ) || ']' AS prices " +
                    "FROM Product p " +
                    "LEFT JOIN Price pr ON p.id = pr.pid " +
                    "GROUP BY p.id " +
                    "ORDER BY p.wishDate DESC"
    )
    List<ProductWithPrices> getAllProductsWithPrices();

}
