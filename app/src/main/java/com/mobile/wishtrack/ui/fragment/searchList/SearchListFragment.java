package com.mobile.wishtrack.ui.fragment.searchList;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.ui.adapter.SearchListAdapter;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;

public abstract class SearchListFragment extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /* view setting */
        final View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        final Context context = getContext();

        /* viewmodel setting */
        final SearchViewModel searchViewModel = getViewModel();

        SearchListAdapter adapter = new SearchListAdapter(context);

        recyclerView = view.findViewById(R.id.recycleView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        searchViewModel.getProductList().observe(getViewLifecycleOwner(), adapter::submitList);

        adapter.setOnProductClickListener(new SearchListAdapter.OnClickListener(){
            @Override
            public void onCartClick(Product product) {
                if (product.isWish()) searchViewModel.removeWish(product);
                else searchViewModel.setWish(product);
            }

            @Override
            public void onProductClick(Product product) {
                searchViewModel.setProduct(product);
                searchViewModel.setVisible(true);
            }
        });

        return view;
    }

    /* abstract */
    protected abstract SearchViewModel getViewModel();
}