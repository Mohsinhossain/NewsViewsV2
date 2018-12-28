package com.mohsinmonad.newsviews.home;


import java.util.List;

public interface HomeActivityView {

    void setSources(List<Source> sourcesList);
    void  setRefreshing (boolean refreshing);
    void showSources (List<Source> sources);
    boolean isNetworkAvailable();
    boolean isActive();
    void  showLoadingSourcesError();
    void showNoSourcesData();

}
