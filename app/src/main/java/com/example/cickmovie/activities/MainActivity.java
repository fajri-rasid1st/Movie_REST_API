package com.example.cickmovie.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cickmovie.R;
import com.example.cickmovie.fragments.LibraryFragment;
import com.example.cickmovie.fragments.MovieFragment;
import com.example.cickmovie.fragments.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static String TOOLBAR_TITLE = "";
    private Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbMain = findViewById(R.id.tb_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);

        setSupportActionBar(tbMain);
        tbMain.setTitleTextAppearance(this, R.style.PoppinsBoldTextAppearance);

        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.menu_item_movie, MovieFragment.newInstance());
        fragmentMap.put(R.id.menu_item_tv_show, TvShowFragment.newInstance());
        fragmentMap.put(R.id.menu_item_library, LibraryFragment.newInstance());

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_item_movie);

        Objects.requireNonNull(getSupportActionBar())
                .setTitle(Objects.requireNonNull(MovieFragment.newInstance()
                        .getArguments())
                        .getString(TOOLBAR_TITLE));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = fragmentMap.get(item.getItemId());

        assert fragment != null;
        assert fragment.getArguments() != null;
        String tbTitle = fragment.getArguments().getString(TOOLBAR_TITLE);

        Objects.requireNonNull(getSupportActionBar()).setTitle(tbTitle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            Drawable drawable = menu.getItem(i).getIcon();

            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources()
                        .getColor(R.color.textOnPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }
}