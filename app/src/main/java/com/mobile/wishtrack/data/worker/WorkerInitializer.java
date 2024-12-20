package com.mobile.wishtrack.data.worker;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class WorkerInitializer {
    public static void schedulePriceUpdate(Context context) {
        PeriodicWorkRequest priceUpdateWork = new PeriodicWorkRequest
                .Builder(PriceUpdateWorker.class, 12, TimeUnit.HOURS)
                .setConstraints(new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build())
                .build();

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                        "PriceUpdateWork", // 고유 작업 이름
                        ExistingPeriodicWorkPolicy.KEEP,
                        priceUpdateWork
                );
    }
}
