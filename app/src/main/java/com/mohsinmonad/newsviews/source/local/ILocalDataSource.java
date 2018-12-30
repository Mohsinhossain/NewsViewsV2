package com.mohsinmonad.newsviews.source.local;

import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.home.Source;
import com.mohsinmonad.newsviews.news.Article;
import com.mohsinmonad.newsviews.source.IDataSource;

import java.util.List;


public interface ILocalDataSource extends IDataSource {

    void getSource(@NonNull String sourceId,
                   @NonNull LoadDataCallback<Source> callback);

    void getAllSources(@NonNull LoadDataCallback<Source> callback);

    void refreshSource(@NonNull Source source);

    void saveSource(@NonNull Source source);

    void saveSources(@NonNull List<Source> source);

    void deleteAllSources();

    void deleteSource(@NonNull String sourceId);

    void getArticles(@NonNull String sourceId,
                     @NonNull LoadDataCallback<Article> callback);

    void getAllArticles();

    void refreshArticle(@NonNull String sourceId, @NonNull Article article);

    void saveArticles(@NonNull String sourceId, @NonNull Article article);

    void saveArticles(@NonNull String sourceId, @NonNull List<Article> articles);

    void deleteAllArticles();

    void deleteArticles(@NonNull String sourceId);
}
