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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.about.AboutActivity;
import com.mohsinmonad.newsviews.drawerevent.NavMenu;
import com.mohsinmonad.newsviews.news.NewsActivity;
import com.mohsinmonad.newsviews.prod.Injection;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeActivityView.View {

    private HomeActivityView.Presenter presenter;
    private HomeRecyclerviewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    ImageView mToggleMenu;
    NavMenu navgLayout;
    ArrayList<Source> sources = new ArrayList<>();
    String conCode;
    LinearLayout linearLayout1, linearLayout2, linearLayout3;

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
        initLinearItem();

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
        presenter.loadSources();
    }

    private void initPresenter() {
        presenter = new HomeActivityViewPresenter(
                Injection.provideRepository(getApplicationContext()), this);
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
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(refreshing);
            }
        });
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
        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoSourcesData() {
        setRefreshing(false);
        Toast.makeText(getApplicationContext(), "no data", Toast.LENGTH_SHORT).show();
    }

    private void initLinearItem() {
        linearLayout1 = findViewById(R.id.linear_home);
        linearLayout2 = findViewById(R.id.linear_about);
        linearLayout3 = findViewById(R.id.linear_exit);

        linearLayout1.setOnClickListener(v -> {
            toggleMenu(v);
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        });
        linearLayout2.setOnClickListener(v -> {
            toggleMenu(v);
            startActivity(new Intent(getApplicationContext(), AboutActivity.class));
        });
        linearLayout3.setOnClickListener(v -> {
            //startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        });

    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());
        }
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
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadSources());
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }
}
