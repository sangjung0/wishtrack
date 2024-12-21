package com.mobile.wishtrack.ui.fragment.searchLayout;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.fragment.searchList.ProductSearchListFragment;
import com.mobile.wishtrack.ui.fragment.searchList.SearchListFragment;
import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;

public class ProductSearchLayoutFragment extends SearchLayoutFragment {
    public ProductSearchLayoutFragment(){};

    public static ProductSearchLayoutFragment newInstance(){
        return new ProductSearchLayoutFragment();
    }

    @Override
    SearchViewModel getSearchViewModel() {
        return new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);
    }

    @Override
    SearchListFragment getSearchListFragment() {
        return ProductSearchListFragment.newInstance();
    }
}
