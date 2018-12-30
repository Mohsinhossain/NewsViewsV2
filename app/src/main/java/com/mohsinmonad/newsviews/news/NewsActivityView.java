package com.mohsinmonad.newsviews.news;

import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.BasePresenter;
import com.mohsinmonad.newsviews.BaseView;

import java.util.List;

public interface NewsActivityView {

        interface View extends BaseView {

                void showArticles(@NonNull List<Article> sources);

                void setRefreshing(boolean refreshing);

                boolean isNetworkAvailable();

                boolean isActive();

                void showLoadingSourcesError();

                void showNoSourcesData();
        }

        interface Presenter extends BasePresenter {

                void loadArticles(String sourceId);
        }
}
