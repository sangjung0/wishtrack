package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.WHError;
import com.mobile.wishtrack.domain.model.QueryStatement;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.sharedData.util.Function;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProductSearchViewModel extends SearchViewModel {
    private final ProductSearchManager productSearchManager;
    private final ExecutorService networkExecutor;

    public ProductSearchViewModel(ExecutorService dbExecutor,ExecutorService networkExecutor, WishSearchManager wishSearchManager, ProductSearchManager productSearchManager) {
        super(dbExecutor, wishSearchManager);
        this.isEnd = true;
        this.networkExecutor = networkExecutor;
        this.productSearchManager = productSearchManager;
    }

    private List<Product> search() {
        return productSearchManager.searchProduct(QueryStatement.newInstance(query, queryOptions));
    }

    @Override
    public void search(Function callback, Consumer<String> errorHandler) {
        if (query == null || query.isEmpty()) return;
        queryOptions.setStart(1);
        this.networkExecutor.execute(() -> {
            try{
                final List<Product> products = search();
                if (products != null){
                    isEnd = false;
                    productList.postValue(products);
                }else{
                    errorHandler.accept("검색 결과가 없습니다.");
                    isEnd = true;
                }
            }catch (WHError e){
                errorHandler.accept(e.getMessage());
            }
            callback.call();
        });
    }

    @Override
    public void searchMore(Function callback, Consumer<String> errorHandler) {
        if (isEnd) return;


        queryOptions.setStart(queryOptions.getStart() + queryOptions.getDisplay());
        this.networkExecutor.execute(() -> {
            try{
                isEnd = true;

                final List<Product> products = search();
                final List<Product> currentList = productList.getValue();
                if (products != null){
                    isEnd = false;
                    List<Product> updatedList = new ArrayList<>(currentList);
                    updatedList.addAll(products);
                    productList.postValue(updatedList);
                }else{
                    errorHandler.accept("검색 결과가 없습니다.");
                    isEnd = true;
                }
            }catch (WHError e){
                errorHandler.accept(e.getMessage());
            }
            callback.call();
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
