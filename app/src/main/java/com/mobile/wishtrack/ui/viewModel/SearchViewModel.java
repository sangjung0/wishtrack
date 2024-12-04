package com.mobile.wishtrack.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryOptions;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.List;
import java.util.concurrent.ExecutorService;

import lombok.Getter;
import lombok.Setter;

public abstract class SearchViewModel extends ViewModel {

    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visible = new MutableLiveData<>();
    @Getter
    protected final MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    @Setter
    @Getter
    protected QueryOptions queryOptions;

    public void setProduct(Product product) {
        this.product.postValue(product);
    }
    public void setVisible(boolean visible) {
        this.visible.postValue(visible);
    }
    public LiveData<Product> getProduct() {return product;}
    public LiveData<Boolean> getVisible() {return visible;}

    public void setWish(Product product) {
        getDBExecutor().execute(() -> getWishSearchManager().setWish(product));
    }

    public void removeWish(Product product) {
        getDBExecutor().execute(() -> getWishSearchManager().removeWish(product));
    }

    /* abstract */
    public abstract void search(String query);
    protected abstract ExecutorService getDBExecutor();
    protected abstract WishSearchManager getWishSearchManager();
}
