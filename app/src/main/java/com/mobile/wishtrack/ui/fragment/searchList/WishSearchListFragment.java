package com.mobile.wishtrack.ui.fragment.searchList;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.ui.viewModel.SearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;

public class WishSearchListFragment extends SearchListFragment {

    private WishSearchViewModel wishSearchViewModel;

    public WishSearchListFragment() {
    }

    public static WishSearchListFragment newInstance() {
        return new WishSearchListFragment();
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (wishSearchViewModel == null) wishSearchViewModel = new ViewModelProvider(requireActivity()).get(WishSearchViewModel.class);
        return wishSearchViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();

        wishSearchViewModel.search(
            () -> requireActivity().runOnUiThread(() ->recyclerView.smoothScrollToPosition(0)),
            (msg) -> requireActivity().runOnUiThread(() ->
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
            )
        );

    }
}
