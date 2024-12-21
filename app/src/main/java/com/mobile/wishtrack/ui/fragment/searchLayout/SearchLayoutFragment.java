package com.mobile.wishtrack.ui.fragment.searchLayout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.mobile.wishtrack.R;
import com.mobile.wishtrack.ui.dialog.CategoryDialog;
import com.mobile.wishtrack.ui.fragment.searchList.SearchListFragment;
import com.mobile.wishtrack.ui.viewModel.SearchViewModel;

/*
 * Fragment는 생성자를 쓰지 않는게 좋다
 * */
public abstract class SearchLayoutFragment extends Fragment {
    private SearchView searchView;
    private ImageView btnCategory;
    private CategoryDialog categoryDialog;
    private SearchViewModel searchViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_layout, container, false);
        searchViewModel = getSearchViewModel();

        /* view setting */
        searchView = view.findViewById(R.id.searchView);
        btnCategory = view.findViewById(R.id.btnCategory);
        categoryDialog = new CategoryDialog();

        /* event setting */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("DEBUG", "onQueryTextSubmit: " + s);
                searchViewModel.setQuery(s);
                searchViewModel.search(
                    ()->{},
                    (msg)->requireActivity().runOnUiThread(()->
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
                    )
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        btnCategory.setOnClickListener(v -> {
            categoryDialog.show(getChildFragmentManager(), "categoryDialog");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction().replace(R.id.container, getSearchListFragment()).commit();
    }


    /* abstract */
    abstract SearchViewModel getSearchViewModel();
    abstract SearchListFragment getSearchListFragment();
}