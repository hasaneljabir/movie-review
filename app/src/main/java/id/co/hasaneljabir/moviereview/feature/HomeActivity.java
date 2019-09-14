package id.co.hasaneljabir.moviereview.feature;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import id.co.hasaneljabir.moviereview.R;
import id.co.hasaneljabir.moviereview.entity.movie.MovieFavoriteDb;
import id.co.hasaneljabir.moviereview.entity.tvShow.TvShowFavoriteDb;
import id.co.hasaneljabir.moviereview.feature.movie.MovieFavActivity;
import id.co.hasaneljabir.moviereview.feature.movie.MovieListFragment;
import id.co.hasaneljabir.moviereview.feature.tvShow.TvShowFavActivity;
import id.co.hasaneljabir.moviereview.feature.tvShow.TvShowListFragment;

public class HomeActivity extends AppCompatActivity {
    public static MovieFavoriteDb movieFavoriteDb;
    public static TvShowFavoriteDb tvShowFavoriteDb;

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    fragment = new MovieListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_tv_show:
                    fragment = new TvShowListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle(R.string.home);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }

        movieFavoriteDb = Room.databaseBuilder(getApplicationContext(),
                MovieFavoriteDb.class, "movie_fav").allowMainThreadQueries().build();

        tvShowFavoriteDb = Room.databaseBuilder(getApplicationContext(),
                TvShowFavoriteDb.class, "tv_show_fav").allowMainThreadQueries().build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_language_setting:
                Intent changeLanguage = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changeLanguage);
                break;
            case R.id.action_fav_movie:
                Intent movieFav = new Intent(this, MovieFavActivity.class);
                startActivity(movieFav);
                break;
            case R.id.action_fav_tv_show:
                Intent tvShowFav = new Intent(this, TvShowFavActivity.class);
                startActivity(tvShowFav);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
