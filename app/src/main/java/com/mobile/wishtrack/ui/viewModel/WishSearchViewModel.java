package com.mobile.wishtrack.ui.viewModel;

import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.util.concurrent.ExecutorService;

public class WishSearchViewModel extends SearchViewModel {
    private final ExecutorService dbExecutor;
    private final WishSearchManager wishSearchManager;

    public WishSearchViewModel(ExecutorService dbExecutor, WishSearchManager wishSearchManager) {
        this.dbExecutor = dbExecutor;
        this.wishSearchManager = wishSearchManager;
    }

    @Override
    public void search(String query) {

    }
}
