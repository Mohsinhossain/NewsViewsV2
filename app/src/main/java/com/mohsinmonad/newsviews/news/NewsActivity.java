package com.mohsinmonad.newsviews.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.Toast;
import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.prod.Injection;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsActivityView.View {

    public static final String EXTRA_SOURCE_ID = "com.mohsinmonad.newsviews.EXTRA_SOURCE_ID";
    private NewsActivityViewPresenter presenter;
    private NewsRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String sourceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent()!=null){
            sourceId = getIntent().getStringExtra(EXTRA_SOURCE_ID);
        }
        setContentView(R.layout.activity_news);
        //initEditText();
        initRecycler();
        initSwipeToRefresh();
        initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadArticles(sourceId);
    }

    // Create the presenter
    private void initPresenter() {
        presenter = new NewsActivityViewPresenter(
                Injection.provideRepository(getApplicationContext()),this);
    }

    @Override
    public void showArticles(@NonNull List<Article> articles) {
        if (adapter != null) {
            adapter.addArticles(articles);
        }
    }

    @Override
    public void setRefreshing(final boolean refreshing) {
        if (swipeRefreshLayout == null) {
            return;
        }
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(refreshing));
    }

    @Override
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void showLoadingSourcesError() {
        setRefreshing(false);
        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoSourcesData() {
        setRefreshing(false);
        Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
    }

    /*private void initEditText() {
        EditText editText = findViewById(R.id.area_title);

    }
*/
    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        adapter = new NewsRecyclerViewAdapter(article ->
                showNewsInWebBrowser(article.getUrl()), new ArrayList<Article>());
        recyclerView.setAdapter(adapter);
    }

    private void showNewsInWebBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


    private void initSwipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadArticles(sourceId));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
