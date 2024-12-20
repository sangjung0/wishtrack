package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.WHError;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.sharedData.util.Function;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class WishSearchViewModel extends SearchViewModel {

    public WishSearchViewModel(ExecutorService dbExecutor, WishSearchManager wishSearchManager) {
        super(dbExecutor, wishSearchManager);
        search(()->{}, (s)->{});
    }

    //TODO 검색 기능 비활성화 함. 일단.
    @Override
    public void search(Function callback, Consumer<String> errorHandler) {
        this.dbExecutor.execute(() -> {
            try {
                final List<Product> products = wishSearchManager.getWishList();
                productList.postValue(products);
            } catch (WHError e) {
                errorHandler.accept(e.getMessage());
            }
            callback.call();
        });
    }

    @Override
    public void searchMore(Function callback, Consumer<String> errorHandler) {
        search(callback, errorHandler);
    }
}
