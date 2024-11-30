package com.mobile.wishtrack.ui.fragment.searchList;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;

public class ProductSearchListFragment extends SearchListFragment {

    public ProductSearchListFragment(){}

    public static ProductSearchListFragment newInstance() {
        return new ProductSearchListFragment();
    }


    @Override
    protected SearchViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);
    }
}
