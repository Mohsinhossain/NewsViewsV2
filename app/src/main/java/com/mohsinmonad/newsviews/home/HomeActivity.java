package com.mohsinmonad.newsviews.home;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.mohsinmonad.newsviews.R;
import com.mohsinmonad.newsviews.drawerevent.NavMenu;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    ImageView mToggleMenu;
    NavMenu navgLayout;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navgLayout = (NavMenu) this.getLayoutInflater().inflate( R.layout.activity_home, null );
        this.setContentView( navgLayout );

        mToggleMenu = findViewById(R.id.menu_icon);
        mToggleMenu.setOnClickListener(this::toggleMenu);
    }

    public void toggleMenu(View v) {
        this.navgLayout.toggleMenu();
    }
}
