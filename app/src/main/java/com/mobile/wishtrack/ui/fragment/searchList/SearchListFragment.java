package com.mobile.wishtrack.ui.fragment.searchList;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobile.wishtrack.R;
import com.mobile.wishtrack.domain.model.Product;
import com.mobile.wishtrack.sharedData.util.Consumer;
import com.mobile.wishtrack.ui.adapter.SearchListAdapter;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;
import com.mobile.wishtrack.ui.viewModel.WishSearchViewModel;

public abstract class SearchListFragment extends Fragment {

    protected RecyclerView recyclerView;

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
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //TODO 임시 조치
                if (searchViewModel instanceof WishSearchViewModel) return;

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int cntItem = adapter.getItemCount() - 1;
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == cntItem) {
                    final int lastItem = cntItem;
                    searchViewModel.searchMore(
                        ()-> requireActivity().runOnUiThread(()->
                            recyclerView.scrollToPosition(lastItem)
                        ),
                        (msg)-> requireActivity().runOnUiThread(()->
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
                        )
                    );
                }
            }
        });

        searchViewModel.getProductList().observe(getViewLifecycleOwner(), adapter::submitList);
        searchViewModel.getSearchEvent().observe(getViewLifecycleOwner(), (b)->{
            if (b) {
                recyclerView.scrollToPosition(0);
                searchViewModel.doneSearchEvent();
            }
        });

        adapter.setOnProductClickListener(new SearchListAdapter.OnClickListener(){
            @Override
            public void onProductClick(Product product) {
                searchViewModel.setProduct(product);
                searchViewModel.setVisible(true);
            }

            @Override
            public void onDelete(int id) {
                searchViewModel.removeWish(id);
            }

            @Override
            public void onInsert(Product product, Consumer<Integer> callback) {
                searchViewModel.setWish(product, callback);
            }
        });

        return view;
    }

    /* abstract */
    protected abstract SearchViewModel getViewModel();
}