package com.mohsinmonad.newsviews.source.remote;

import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.home.Source;
import com.mohsinmonad.newsviews.news.Article;
import com.mohsinmonad.newsviews.source.IDataSource;


public interface IRemoteDataSource extends IDataSource {

    void getSourcesAll(@NonNull LoadDataCallback<Source> callback);

    void getArticles(String source, @NonNull LoadDataCallback<Article> callback);
}
