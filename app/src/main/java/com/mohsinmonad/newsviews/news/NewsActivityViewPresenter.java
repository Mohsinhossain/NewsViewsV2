package com.mohsinmonad.newsviews.news;


import android.util.Log;

import com.mohsinmonad.newsviews.BuildConfig;
import com.mohsinmonad.newsviews.common.App;
import com.mohsinmonad.newsviews.common.GetApiService;
import com.mohsinmonad.newsviews.source.IDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mohsinmonad.newsviews.common.App.API_KEY;


public class NewsActivityViewPresenter {

    private NewsActivityViewPresenter newsActivityPresenter;
    private NewsActivityView newsActivityView;
    IDataSource.LoadDataCallback<Article> callback;

    NewsActivityViewPresenter(NewsActivityView newsActivityView) {
        this.newsActivityView = newsActivityView;
    }


    public void getArticles(String source) {
        GetApiService service = App.instance.getRetrofitInstance().create(GetApiService.class);
        Call<ArticleResponse> articleResponseCall = service.getArticle(API_KEY, source, "top");
        articleResponseCall.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if (response.body() != null) {
                    if ("ok".equals(response.body().getStatus())) {
                        if (!response.body().getArticles().isEmpty()) {
                            callback.onDataLoaded(response.body().getArticles());
                        } else {
                            callback.onDataNotAvailable();
                            Log.e("Error", "Oops, something went wrong!");
                        }
                    } else {
                        callback.onDataNotAvailable();
                        Log.e("No data", "Oops, something went wrong!");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                Log.e("error", "Error:" + t.getMessage());
                callback.onDataNotAvailable();
            }
        });

    }
}
