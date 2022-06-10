package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movienerd.FilmRecyclerView.FilmCardAdapter;
import com.example.movienerd.FilmRecyclerView.OnItemClickListener;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.ViewModels.ListViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {

    private static final String LOG = "Home-Fragment";

    private FilmCardAdapter adapter;
    private FilmCardAdapter popularAdapter;
    private RecyclerView recyclerView;
    private RecyclerView popularRecyclerView;
    private MainActivity mainActivity;

    private  RequestQueue requestQueue;
    private final static  String TOP_FILM_REQUEST_TAG = "TOP_FILM_REQUEST";
    private final static  String POPULAR_FILM_REQUEST_TAG = "POPULAR_FILM_REQUEST";

    private ListViewModel listViewModel;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);
        mainActivity = (MainActivity) getActivity();
        if(mainActivity != null){
            Utilities.setUpToolbar((AppCompatActivity) mainActivity, "HOME");
            //activity.homeAPIRequestDone = true; //DA TOGLIERE!!!!
            if(!mainActivity.homeAPIRequestDone || !mainActivity.dataReceived){
                requestQueue = Volley.newRequestQueue(mainActivity);
                this.sendVolleyRequest(mainActivity);
                this.sendPopularVolleyRequest(mainActivity);
                mainActivity.homeAPIRequestDone = true;
            }else{
                setRecyclerView(mainActivity);
                setPopularRecyclerView(mainActivity);
                setHomeViewModel(mainActivity);
            }

            view.findViewById(R.id.login_textView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.isInHome = false;
                    Utilities.insertFragment((AppCompatActivity) mainActivity, new RegisterFragment(), RegisterFragment.class.getSimpleName());
                }
            });
        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if(requestQueue != null){
            requestQueue.cancelAll(TOP_FILM_REQUEST_TAG);
        }
    }

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.home_recycler_view);

        final OnItemClickListener listener = this;
        adapter = new FilmCardAdapter(listener, activity);
        recyclerView.setAdapter(adapter);
    }

    private void setPopularRecyclerView(final Activity activity){
        popularRecyclerView = activity.findViewById(R.id.popular_recyclerView);

        final OnItemClickListener listener = this;
        popularAdapter = new FilmCardAdapter(listener, activity);
        popularRecyclerView.setAdapter(popularAdapter);
    }

    private void setHomeViewModel(Activity activity){
        listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);

        listViewModel.getAllUsers().observe((LifecycleOwner) activity, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                for(User user : users){
                    if(user.getIsLogged()){
                        currentUser = user;
                    }
                }
            }
        });

        listViewModel.getHomeFilms().observe((LifecycleOwner) activity, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                adapter.setData(films);
            }
        });

        listViewModel.getPopularFilms().observe((LifecycleOwner) activity, new Observer<List<Film>>() {
            @Override
            public void onChanged(List<Film> films) {
                popularAdapter.setData(films);
            }
        });
    }

    private void sendVolleyRequest(Activity activity){
        String url = "https://imdb-api.com/en/API/Top250Movies/k_4tiqdg1l";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setRecyclerView(activity);
                    setHomeViewModel(activity);

                    JSONArray films = response.getJSONArray("items"); //Prende l'array di film
                    listViewModel.clearHomeList();

                    for(int i=0; i < 20; i++){
                        listViewModel.addHomeFilm(new Film((String) films.getJSONObject(i).get("id"),
                                (String) films.getJSONObject(i).get("title"),(String) films.getJSONObject(i).get("image"),
                                (String) films.getJSONObject(i).get("year"), (String) films.getJSONObject(i).get("imDbRating")));
                    }

                    mainActivity.dataReceived = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HOME-FRAGMENT", error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        jsonObjectRequest.setTag(TOP_FILM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void sendPopularVolleyRequest(Activity activity){
        String url = "https://imdb-api.com/en/API/MostPopularMovies/k_4tiqdg1l";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setPopularRecyclerView(activity);
                    setHomeViewModel(activity);

                    JSONArray films = response.getJSONArray("items"); //Prende l'array di film
                    listViewModel.clearPopularFilms();

                    for(int i=0; i < 20; i++){
                        listViewModel.addPopularFilm(new Film((String) films.getJSONObject(i).get("id"),
                                (String) films.getJSONObject(i).get("title"),(String) films.getJSONObject(i).get("image"),
                                (String) films.getJSONObject(i).get("year"), (String) films.getJSONObject(i).get("imDbRating")));
                    }

                    mainActivity.dataReceived = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HOME-FRAGMENT", error.toString());
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        jsonObjectRequest.setTag(POPULAR_FILM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(Film film) {
        Activity activity = getActivity();
        if(activity != null){
            Utilities.insertFragment((AppCompatActivity) activity, new DetailsFragment(film), DetailsFragment.class.getSimpleName());
            listViewModel.setFilmSelected(film);
        }
    }
}
