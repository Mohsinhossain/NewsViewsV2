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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.home.HomeActivity;
import com.mohsinmonad.newsviews.network.NetworkUtils;
import com.mohsinmonad.newsviews.prod.Injection;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsActivityView.View {

    public static final String EXTRA_SOURCE_ID = "com.mohsinmonad.newsviews.EXTRA_SOURCE_ID";
    private NewsActivityViewPresenter presenter;
    private NewsRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String sourceId;
    private String criteria;
    EditText search_News;
    RelativeLayout searchLayout;
    ImageView backBtn, backBtn2;
    TextView titleTv;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<>();
    LinearLayout noArticleLayout, loadingLayout;
    private ProgressBar searchProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            sourceId = getIntent().getStringExtra(EXTRA_SOURCE_ID);
        }
        setContentView(R.layout.activity_news);
        searchProgressBar = findViewById(R.id.progressBar);
        search_News = findViewById(R.id.search_user);

        criteria = getIntent().getExtras().getString("CRITERIA");

        //initSearchCastItem();
        initRecycler();
        initSwipeToRefresh();
        initPresenter();
        //searAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadArticles(sourceId);
    }

    // Create the presenter
    private void initPresenter() {
        presenter = new NewsActivityViewPresenter(
                Injection.provideRepository(getApplicationContext()), this);
    }

    @Override
    public void showArticles(@NonNull List<Article> articles) {
        if (adapter != null) {
            adapter.addArticles(articles);
            searchProgressBar.setVisibility(View.GONE);
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

    void initSearchCastItem() {
        //searchText = findViewById(R.id.searchET);
        searchLayout = findViewById(R.id.searchLayout);
        backBtn = findViewById(R.id.backBtn);
        backBtn2 = findViewById(R.id.backBtn2);
        titleTv = findViewById(R.id.titleTV);
        noArticleLayout = findViewById(R.id.noArticleLayout);
        noArticleLayout = findViewById(R.id.loadingLayout);

    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);
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
