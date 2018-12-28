package com.mohsinmonad.newsviews.source;

import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.home.Source;
import com.mohsinmonad.newsviews.news.Article;

import java.util.List;


public interface RepositoryDataSource {

    void getArticles(String source,
                     IDataSource.LoadDataCallback<Article> callback,
                     boolean isNetworkAvailable);

    void saveArticles(@NonNull List<Article> articles);

    void deleteAllArticles();

    void getSources(@NonNull IDataSource.LoadDataCallback<Source> callback,
                    boolean isNetworkAvailable);

    void saveSources(@NonNull List<Source> sources);

    void deleteAllSources();

}
