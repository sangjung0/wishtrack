package com.mobile.wishtrack.ui.fragment.searchList;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.viewModel.ProductSearchViewModel;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;

public class ProductSearchListFragment extends SearchListFragment {

    private ProductSearchViewModel productSearchViewModel;

    public ProductSearchListFragment(){}

    public static ProductSearchListFragment newInstance() {
        return new ProductSearchListFragment();
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (productSearchViewModel == null) productSearchViewModel = new ViewModelProvider(requireActivity()).get(ProductSearchViewModel.class);
        return productSearchViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();

        productSearchViewModel.search(
            () -> requireActivity().runOnUiThread(() ->recyclerView.smoothScrollToPosition(0)),
            (msg) -> requireActivity().runOnUiThread(() ->
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
            )
        );
    }
}
