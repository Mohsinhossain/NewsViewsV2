package com.mohsinmonad.newsviews.home;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import com.mohsinmonad.newsviews.source.RepositoryDataSource;
import com.mohsinmonad.newsviews.source.remote.IRemoteDataSource;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ALL")
public class HomeActivityViewPresenter implements HomeActivityView.Presenter {

    private HomeActivityView.View view;
    private RepositoryDataSource repository;

    public HomeActivityViewPresenter(@NonNull RepositoryDataSource repository,
                            @NonNull HomeActivityView.View view) {
        this.repository = checkNotNull(repository, "repository cannot be null");
        this.view = checkNotNull(view, "View cannot be null!");
    }

    @Override
    public void loadSources() {
        loadSourcesFromRepository(view.isNetworkAvailable());
    }

    private void loadSourcesFromRepository(boolean isNetworkAvailable ) {
        view.setRefreshing(true);
        repository.getSources(new IRemoteDataSource.LoadDataCallback<Source>() {
            @Override
            public void onDataLoaded(List<Source> list) {
                if (list.isEmpty()) {
                    view.showNoSourcesData();
                }
                view.showSources(list);
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
