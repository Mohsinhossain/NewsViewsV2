package com.mohsinmonad.newsviews.news;

import android.support.annotation.NonNull;

import java.util.List;

public interface NewsActivityView {

        void setArticles(List<Article> sources);
        void showArticles(@NonNull List<Article> sources);

        void setRefreshing(boolean refreshing);

        boolean isNetworkAvailable();

        boolean isActive();

        void showLoadingSourcesError();

        void showNoSourcesData();


}
