package com.mohsinmonad.newsviews.prod;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.common.base.Preconditions;
import com.mohsinmonad.newsviews.common.GetApiService;
import com.mohsinmonad.newsviews.source.Repository;
import com.mohsinmonad.newsviews.source.local.LocalDataSource;
import com.mohsinmonad.newsviews.source.remote.ApiClient;
import com.mohsinmonad.newsviews.source.remote.ApiService;
import com.mohsinmonad.newsviews.source.remote.RemoteDataSource;

public class Injection {

    public static Repository provideRepository(@NonNull Context context) {
        Preconditions.checkNotNull(context);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        return Repository.getInstance(RemoteDataSource.getInstance(apiService),
                LocalDataSource.getInstance(context.getContentResolver()));
    }
}
