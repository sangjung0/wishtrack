package com.mobile.wishtrack.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryOptions;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.sharedData.util.Function;
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
    //TODO 임시 변수
    protected final MutableLiveData<Boolean> searchEvent = new MutableLiveData<>();

    @Setter
    @Getter
    protected QueryOptions queryOptions;
    @Setter
    @Getter
    protected String query;
    protected boolean isEnd;

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
    public LiveData<Boolean> getSearchEvent() {return searchEvent;}
    public void doneSearchEvent() {searchEvent.postValue(false);}


    public void setWish(Product product, Consumer<Integer> callback) {
        dbExecutor.execute(() -> {
            int id = wishSearchManager.setWish(product);
            callback.accept(id);
        });
    }

    public void removeWish(int id) {
        dbExecutor.execute(() -> wishSearchManager.removeWish(id));
    }

    /* abstract */
    public abstract void search(Function callback, Consumer<String> errorHandler);
    public abstract void searchMore(Function callback, Consumer<String> errorHandler);
}
