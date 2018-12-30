package com.mohsinmonad.newsviews.news;


import android.support.annotation.NonNull;

import com.mohsinmonad.newsviews.source.RepositoryDataSource;
import com.mohsinmonad.newsviews.source.remote.IRemoteDataSource;
import java.util.List;


import static com.google.common.base.Preconditions.checkNotNull;

public class NewsActivityViewPresenter implements NewsActivityView.Presenter {

    private NewsActivityView.View view;
    private RepositoryDataSource repository;

    public NewsActivityViewPresenter(@NonNull RepositoryDataSource repository,
                         @NonNull NewsActivityView.View view) {
        this.repository = checkNotNull(repository, "repository cannot be null");
        this.view = checkNotNull(view, "View cannot be null!");
    }

    @Override
    public void loadArticles(String sourceId) {
        loadNewsFromRepository(sourceId, view.isNetworkAvailable());
    }

    private void loadNewsFromRepository(String source, boolean isNetworkAvailable ) {
        view.setRefreshing(true);
        repository.getArticles(source, new IRemoteDataSource.LoadDataCallback<Article>() {
            @Override
            public void onDataLoaded(List<Article> list) {
                if (list.isEmpty()) {
                    view.showNoSourcesData();
                }
                view.showArticles(list);
                view.setRefreshing(false);
            }

            @Override
            public void onDataNotAvailable() {
                if (!view.isActive()) {
                    return;
                }
                view.showLoadingSourcesError();
            }
        }, isNetworkAvailable);

    }
}
