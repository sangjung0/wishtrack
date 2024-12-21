package com.mobile.wishtrack.ui.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mobile.wishtrack.WHApplication;
import com.mobile.wishtrack.ui.ThreadGenerator;
import com.mobile.wishtrack.ui.repository.ProductSearchManager;
import com.mobile.wishtrack.ui.repository.WishSearchManager;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;

public class VMProvider extends ViewModelProvider.NewInstanceFactory {
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

        try{
            if (modelClass.isAssignableFrom(ProductSearchViewModel.class)) {
                return modelClass
                        .getConstructor(ExecutorService.class, ExecutorService.class, WishSearchManager.class, ProductSearchManager.class)
                        .newInstance(
                                threadGenerator.getNetworkExecutor(),
                                threadGenerator.getDbExecutor(),
                                wishSearchManager,
                                productSearchManager
                        );
            }
            if (modelClass.isAssignableFrom(WishSearchViewModel.class)) {
                return modelClass
                        .getConstructor(ExecutorService.class, WishSearchManager.class)
                        .newInstance(threadGenerator.getDbExecutor(), wishSearchManager);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException e) {
            Log.d("VMProvider", "create: " + e.getMessage());
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
