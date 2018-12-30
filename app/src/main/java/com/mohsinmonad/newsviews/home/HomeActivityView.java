package com.mohsinmonad.newsviews.home;


import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.BasePresenter;
import com.mohsinmonad.newsviews.BaseView;

import java.util.List;

public interface HomeActivityView {

    interface View extends BaseView {

        void showSources(@NonNull List<Source> sources);

        void setRefreshing(boolean refreshing);

        boolean isNetworkAvailable();

        boolean isActive();

        void showLoadingSourcesError();

        void showNoSourcesData();
    }

    interface Presenter extends BasePresenter {

        void loadSources();
    }

}
