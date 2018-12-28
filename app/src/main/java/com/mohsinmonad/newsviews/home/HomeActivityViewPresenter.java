package com.mohsinmonad.newsviews.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;
import com.mohsinmonad.newsviews.common.App;
import com.mohsinmonad.newsviews.common.GetApiService;
import com.mohsinmonad.newsviews.source.IDataSource;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ALL")
public class HomeActivityViewPresenter {

    private HomeActivityView homeActivityContract;

    private IDataSource.LoadDataCallback<Source> callback;

    HomeActivityViewPresenter(HomeActivityView homeActivityContract) {
        this.homeActivityContract = homeActivityContract;
    }

    void getSourcesAll() {
        GetApiService service = App.instance.getRetrofitInstance().create(GetApiService.class);
        Call<SourceResponse> articleResponseCall = service.getSource("en");
        articleResponseCall.enqueue(new Callback<SourceResponse>() {
            @Override
            public void onResponse(Call<SourceResponse> call, @NonNull Response<SourceResponse> response) {
                if ("ok".equals(Objects.requireNonNull(response.body()).getStatus())) {
                    if (!response.body().getSources().isEmpty()) {
                        callback.onDataLoaded(response.body().getSources());
                    } else {
                        Log.e("No data", "Oops, something went wrong!");
                    }
                } else {
                    Log.e("error", "Oops, something went wrong!");
                }
            }

            @Override
            public void onFailure(Call<SourceResponse> call, Throwable t) {
                Log.e("error1", "Error:" + t.getMessage());
            }
        });

    }
}
