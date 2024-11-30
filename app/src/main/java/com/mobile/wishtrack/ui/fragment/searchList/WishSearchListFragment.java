package com.mobile.wishtrack.ui.fragment.searchList;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.viewModel.SearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;

public class WishSearchListFragment extends SearchListFragment {

    public WishSearchListFragment(){}

    public static WishSearchListFragment newInstance() {
        return new WishSearchListFragment();
    }

    @Override
    protected SearchViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(WishSearchViewModel.class);
    }
}
