package com.mohsinmonad.newsviews.source;

import java.util.List;



public interface IDataSource {

    interface  LoadDataCallback<T> {

        void onDataLoaded(List<T> list);

        void onDataNotAvailable();
    }
}
