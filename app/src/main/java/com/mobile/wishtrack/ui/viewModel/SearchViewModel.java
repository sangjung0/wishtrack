package com.mobile.wishtrack.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.domain.model.QueryOptions;

import java.util.List;

public abstract class SearchViewModel extends ViewModel {

    private final MutableLiveData<Product> product = new MutableLiveData<>();
    private final MutableLiveData<Boolean> visible = new MutableLiveData<>();
    protected final MutableLiveData<List<Product>> productList = new MutableLiveData<>();

    protected QueryOptions queryOptions;

    public QueryOptions getQueryOptions(){
        return queryOptions;
    }
    public void setQueryOptions(QueryOptions queryOptions){
        this.queryOptions = queryOptions;
    }
    public void setProduct(Product product) {
        this.product.postValue(product);
    }
    public void setVisible(boolean visible) {
        this.visible.postValue(visible);
    }
    public LiveData<Product> getProduct() {return product;}
    public LiveData<Boolean> getVisible() {return visible;}
    public MutableLiveData<List<Product>> getProductList() {
        return productList;
    }

    /* abstract */
    public abstract void search(String query);
}
