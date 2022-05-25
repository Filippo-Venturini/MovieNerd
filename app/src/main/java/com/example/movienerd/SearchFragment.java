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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movienerd.FilmRecyclerView.FilmCardAdapter;
import com.example.movienerd.FilmRecyclerView.GridItemDecorator;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.FilmRecyclerView.SearchedFilmAdapter;
import com.example.movienerd.FilmRecyclerView.SimpleCardAdapter;
import com.example.movienerd.ViewModels.ListViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchFragment extends Fragment  implements OnItemListener {
    private static final String LOG = "SEARCHED_FILM";
    private SearchedFilmAdapter adapter;
    private ListViewModel listViewModel;
    private String searchExpression;

    private RequestQueue requestQueue;
    private final static  String SEARCH_FILM_REQUEST_TAG = "SEARCH_FILM_REQUEST";

    public SearchFragment(String searchExpression){
        this.searchExpression = searchExpression;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            requestQueue = Volley.newRequestQueue(activity);
            this.sendVolleyRequest(activity);
            setRecyclerView(activity);
        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    private void setRecyclerView(Activity activity){
        RecyclerView recyclerView = activity.findViewById(R.id.search_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        final OnItemListener listener = this;
        adapter = new SearchedFilmAdapter(listener, activity);
        recyclerView.setAdapter(adapter);
    }

    private void sendVolleyRequest(Activity activity){
        String url = "https://imdb-api.com/en/API/SearchMovie/k_4ej6zo7h/"+this.searchExpression;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setRecyclerView(activity);
                    listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
                    listViewModel.getSearchedFilms().observe((LifecycleOwner) activity, new Observer<List<Film>>() {
                        @Override
                        public void onChanged(List<Film> films) {
                            adapter.setData(films);
                        }
                    });

                    listViewModel.clearSearchedList();

                    JSONArray searchedFilms = response.getJSONArray("results");
                    for(int i=0; i<searchedFilms.length(); i++){
                        String year = (String) searchedFilms.getJSONObject(i).get("description");
                        if(year.length() >= 6){
                            year = year.substring(1,5);
                        }
                        listViewModel.addSearchedFilm(new Film((String) searchedFilms.getJSONObject(i).get("id"),
                                (String) searchedFilms.getJSONObject(i).get("title"),(String) searchedFilms.getJSONObject(i).get("image"),
                                year, "8.9"));
                    }

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

        jsonObjectRequest.setTag(SEARCH_FILM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if(activity != null){
            Utilities.insertFragment((AppCompatActivity) activity, new DetailsFragment(adapter.getFilmSelected(position)), DetailsFragment.class.getSimpleName());
            listViewModel.setFilmSelected(adapter.getFilmSelected(position));
        }
    }
}
