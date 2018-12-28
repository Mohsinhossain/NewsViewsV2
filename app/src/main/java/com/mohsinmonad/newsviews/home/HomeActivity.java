package com.mohsinmonad.newsviews.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.drawerevent.NavMenu;
import com.mohsinmonad.newsviews.news.NewsActivity;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeActivityView {

    ImageView mToggleMenu;
    NavMenu navgLayout;
    private HomeActivityViewPresenter presenter;
    private HomeRecyclerviewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    String conCode;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navgLayout = (NavMenu) this.getLayoutInflater().inflate(R.layout.activity_home, null);
        this.setContentView(navgLayout);

        mToggleMenu = findViewById(R.id.menu_icon);
        mToggleMenu.setOnClickListener(this::toggleMenu);

        initToolbar();
        initRecycler();
        initSwipeToRefresh();
        initPresenter();
        initMenuImage();

    }

    public void toggleMenu(View v) {
        this.navgLayout.toggleMenu();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //presenter.getSourcesAll();
    }

    private void initPresenter() {

        presenter = new HomeActivityViewPresenter(this);
        presenter.getSourcesAll();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
        }
    }

    private void initMenuImage() {
        ImageView imageView = findViewById(R.id.menu_icon);
        TextView textView = findViewById(R.id.header_tiltle);
    }

    @Override
    public void showSources(@NonNull List<Source> sources) {
        if (adapter != null) {
            adapter.setSourceList(sources);
        }

    }

    @Override
    public void setRefreshing(boolean refreshing) {

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
        Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        adapter = new HomeRecyclerviewAdapter(source -> showNewsActivity(source.getId()), new ArrayList<Source>());
        recyclerView.setAdapter(adapter);
    }

    private void showNewsActivity(String sourceId) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra(NewsActivity.EXTRA_SOURCE_ID, sourceId);
        startActivity(intent);
    }

    private void initSwipeToRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getSourcesAll());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void setSources(List<Source> sourcesList) {
        initRecycler();
    }
}
