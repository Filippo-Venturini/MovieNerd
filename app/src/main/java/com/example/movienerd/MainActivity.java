package com.example.movienerd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movienerd.ViewModels.ListViewModel;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public boolean homeAPIRequestDone = false;
    private ListViewModel listViewModel;
    private User currentUser;
    public boolean isInHome = true;
    public List<Film> allFilms;
    private boolean firstGuest = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        Activity activity = this;

        listViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(ListViewModel.class);

        listViewModel.getAllUsers().observe((LifecycleOwner) this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                TextView txtLoginLogout = findViewById(R.id.loginLogout_textView);
                TextView txtUsername = findViewById(R.id.username_TextView);
                boolean logged = false;
                for(User user : users){
                    if(user.getIsLogged() && txtUsername != null){
                        txtUsername.setText(user.getUsername());
                        currentUser = user;
                        logged = true;
                    }
                }
                if(logged){
                    Glide.with(activity)
                            .load(getImage("man"))
                            .into((ImageView) findViewById(R.id.user_profileImageView));
                    txtLoginLogout.setText("Logout");
                }else{
                    if (!firstGuest) {
                        Glide.with(activity)
                                .load(getImage("user"))
                                .into((ImageView) findViewById(R.id.user_profileImageView));
                    }else{
                        firstGuest = false;
                    }

                    txtLoginLogout.setText("Login");
                }
            }
        });

        listViewModel.getAllFilms().observe((LifecycleOwner) this, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                allFilms = films;
            }
        });

        listViewModel.getAllAchievements().observe((LifecycleOwner) this, new Observer<List<Achievement>>() {
            @Override
            public void onChanged(List<Achievement> achievements) {
                if(achievements.size() == 0){
                    listViewModel.addAchievement(new Achievement("watch","A Good Start", "Add your first film to your watchlist"));
                    listViewModel.addAchievement(new Achievement("imdb_logo","Beginner", "Watch your first movie"));
                    listViewModel.addAchievement(new Achievement("imdb_logo","Let me take a look", "Look at the details of a movie"));
                    listViewModel.addAchievement(new Achievement("imdb_logo","Advanced", "Watch 3 movies"));
                }
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        findViewById(R.id.loginLogout_CardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtLoginLogout = findViewById(R.id.loginLogout_textView);
                if(txtLoginLogout.getText().equals("Login")){
                    drawer.closeDrawer(GravityCompat.START);
                    Utilities.insertFragment((AppCompatActivity) activity, new LoginFragment(), LoginFragment.class.getSimpleName());
                }else{
                    currentUser.setIsLogged(false);
                    listViewModel.updateUser(currentUser);
                    Toast.makeText(activity, "Logged out", Toast.LENGTH_SHORT).show();
                    TextView txtUsername = findViewById(R.id.username_TextView);
                    txtUsername.setText("Guest");
                    drawer.closeDrawer(GravityCompat.START);
                    if(!isInHome){
                        isInHome = true;
                        Utilities.insertFragment((AppCompatActivity) activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                    }
                }
            }
        });
        Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
    }

    private int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
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
                if(!isLogged()){
                    Toast.makeText(this, "Login is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                Utilities.insertFragment(this, new ProfileFragment(), ProfileFragment.class.getSimpleName());
                isInHome = false;
                break;
            case R.id.nav_home:
                if(isInHome){
                    break;
                }
                Utilities.insertFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
                isInHome = true;
                break;
            case R.id.nav_watchList:
                if(!isLogged()){
                    Toast.makeText(this, "Login is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                Utilities.insertFragment(this, new WatchListFragment(), WatchListFragment.class.getSimpleName());
                isInHome = false;
                break;
            case R.id.nav_watchedMovies:
                if(!isLogged()){
                    Toast.makeText(this, "Login is required", Toast.LENGTH_SHORT).show();
                    break;
                }
                Utilities.insertFragment(this, new WatchedFilmsFragment(), WatchedFilmsFragment.class.getSimpleName());
                isInHome = false;
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

    public boolean isLogged(){
        TextView txtLoginLogout = findViewById(R.id.loginLogout_textView);
        return txtLoginLogout.getText().equals("Logout");
    }
}