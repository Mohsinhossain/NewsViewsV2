package com.mohsinmonad.newsviews.prod;

import android.content.Context;
import android.support.annotation.NonNull;
import com.mohsinmonad.newsviews.common.GetApiService;
import com.mohsinmonad.newsviews.source.remote.ApiClient;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class Injection {

    /*public static Repository provideRepository(@NonNull Context context) {
        checkNotNull(context);
        GetApiService apiService = ApiClient.getClient().create(GetApiService.class);
        return Repository.getInstance(RemoteDataSource.getInstance(apiService),
                LocalDataSource.getInstance(context.getContentResolver()));
    }*/
}
