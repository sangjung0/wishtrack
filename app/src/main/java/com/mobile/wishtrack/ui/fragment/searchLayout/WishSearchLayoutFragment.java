package com.mobile.wishtrack.ui.fragment.searchLayout;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.fragment.searchList.SearchListFragment;
import com.mobile.wishtrack.ui.fragment.searchList.WishSearchListFragment;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;

public class WishSearchLayoutFragment extends SearchLayoutFragment{
    public WishSearchLayoutFragment(){};

    public static WishSearchLayoutFragment newInstance(){
        return new WishSearchLayoutFragment();
    }

    @Override
    SearchViewModel getSearchViewModel() {
        return new ViewModelProvider(requireActivity()).get(WishSearchViewModel.class);
    }

    @Override
    SearchListFragment getSearchListFragment() {
        return WishSearchListFragment.newInstance();
    }
}
