package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienerd.ViewModels.ListViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public boolean homeAPIRequestDone = false;
    private ListViewModel listViewModel;
    private User currentUser;
    private boolean isInHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(ListViewModel.class);

        listViewModel.getAllUsers().observe((LifecycleOwner) this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for(User user : users){
                    if(user.getIsLogged()){
                        TextView txtUsername = findViewById(R.id.username_TextView);
                        txtUsername.setText(user.getUsername());
                        currentUser = user;
                    }
                }
            }
        });

        listViewModel.getAllAchievements().observe((LifecycleOwner) this, new Observer<List<Achievement>>() {
            @Override
            public void onChanged(List<Achievement> achievements) {
                if(achievements.size() == 0){
                    listViewModel.addAchievement(new Achievement("imdb_logo","Achievement_1", "Primo film in watchList"));
                    listViewModel.addAchievement(new Achievement("imdb_logo","Achievement_2", "achievement di prova"));
                }
            }
        });

        Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_bar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        menuItem.setVisible(true);
        SearchView searchView = (SearchView) menuItem.getActionView();

        Activity activity = this;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Utilities.insertFragment((AppCompatActivity) activity, new SearchFragment(query), SearchFragment.class.getSimpleName());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_profile:
                Utilities.insertFragment(this, new ProfileFragment(), ProfileFragment.class.getSimpleName());
                isInHome = false;
                break;
            case R.id.nav_home:
                Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
                isInHome = true;
                break;
            case R.id.nav_watchList:
                Utilities.insertFragment(this, new WatchListFragment(), WatchListFragment.class.getSimpleName());
                isInHome = false;
                break;
            case R.id.nav_watchedMovies:
                Utilities.insertFragment(this, new WatchedFilmsFragment(), WatchedFilmsFragment.class.getSimpleName());
                isInHome = false;
                break;
            case R.id.nav_logout:
                TextView txtUsername = findViewById(R.id.username_TextView);
                if(currentUser == null || txtUsername.getText().equals("User")){
                    break;
                }
                currentUser.setIsLogged(false);
                listViewModel.updateUser(currentUser);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                txtUsername.setText("User");
                if(!isInHome){
                    isInHome = true;
                    Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
                }
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}