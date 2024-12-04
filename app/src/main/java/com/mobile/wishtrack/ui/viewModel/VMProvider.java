package com.mobile.wishtrack.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.WHApplication;
import com.mobile.wishtrack.ui.ThreadGenerator;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

public class VMProvider implements ViewModelProvider.Factory {
    private final WHApplication application;

    public VMProvider(WHApplication application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        final ThreadGenerator threadGenerator = application.getThreadGenerator();
        final ProductSearchManager productSearchManager = application.getProductSearchManager();
        final WishSearchManager wishSearchManager = application.getWishSearchManager();

        if (modelClass.isAssignableFrom(ProductSearchViewModel.class)) {
            return (T) new ProductSearchViewModel(threadGenerator.getNetworkExecutor(), threadGenerator.getDbExecutor(), wishSearchManager, productSearchManager);
        }
        if (modelClass.isAssignableFrom(WishSearchViewModel.class)) {
            return (T) new WishSearchViewModel(threadGenerator.getDbExecutor(), wishSearchManager);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
