package com.mobile.wishtrack.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryOptions;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

import lombok.Getter;
import lombok.Setter;

public abstract class SearchViewModel extends ViewModel {
    protected final ExecutorService dbExecutor;
    protected final WishSearchManager wishSearchManager;

    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visible = new MutableLiveData<>();
    protected final MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    @Setter
    @Getter
    protected QueryOptions queryOptions;

    protected SearchViewModel(ExecutorService dbExecutor, WishSearchManager wishSearchManager){
        this.dbExecutor = dbExecutor;
        this.wishSearchManager = wishSearchManager;
        this.queryOptions = QueryOptions.newInstance();
    }

    public void setProduct(Product product) {
        this.product.postValue(product);
    }
    public void setVisible(boolean visible) {
        this.visible.postValue(visible);
    }
    public LiveData<Product> getProduct() {return product;}
    public LiveData<Boolean> getVisible() {return visible;}
    public LiveData<List<Product>> getProductList() {return productList;}


    public void setWish(Product product) {
        dbExecutor.execute(() -> wishSearchManager.setWish(product));
    }

    public void removeWish(Product product) {
        dbExecutor.execute(() -> wishSearchManager.removeWish(product));
    }

    /* abstract */
    public abstract void search(String query, Consumer<String> errorHandler);
}
