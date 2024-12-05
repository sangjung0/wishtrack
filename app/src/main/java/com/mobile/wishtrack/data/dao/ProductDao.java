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
    void insert(ProductEntity productEntity);

    @Update
    void update(ProductEntity product);

    @Delete
    void delete(ProductEntity product);

    @Query("SELECT * FROM Product ORDER BY wishDate DESC")
    List<ProductEntity> getAllProducts();

    @Query("SELECT * FROM Product WHERE id = :id")
    ProductEntity getProductById(int id);

    @Query(
            "SELECT p.*, " +
                    "  '[' || GROUP_CONCAT(" +
                    "      '{\"date\":' || pr.date || ',\"lPrice\":' || pr.lPrice || ',\"hPrice\":' || pr.hPrice || '}'" +
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
                    "      '{\"date\":' || pr.date || ',\"lPrice\":' || pr.lPrice || ',\"hPrice\":' || pr.hPrice || '}'" +
                    "  ) || ']' AS prices " +
                    "FROM Product p " +
                    "LEFT JOIN Price pr ON p.id = pr.pid " +
                    "GROUP BY p.id " +
                    "ORDER BY p.wishDate DESC"
    )
    List<ProductWithPrices> getAllProductsWithPrices();

}
