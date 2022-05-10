package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movienerd.ActorRecyclerView.ActorCardAdapter;
import com.example.movienerd.FilmRecyclerView.FilmCardAdapter;
import com.example.movienerd.ViewModels.ListViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private static final String LOG = "DETAILS";
    private Film film;
    private List<Actor> actorsList = new LinkedList<>();

    private ActorCardAdapter adapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private final static  String FILM_REQUEST_TAG = "FILM_REQUEST_TAG";

    private TextView titleTextView;
    private ImageView filmImageView;
    private ImageView previewImageView;

    public DetailsFragment(Film film){
        this.film = film;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Activity activity = getActivity();

        String previewUrl = "https://imdb-api.com/images/original/MV5BMTM0NjUxMDk5MF5BMl5BanBnXkFtZTcwNDMxNDY3Mw@@._V1_Ratio1.5000_AL_.jpg";

        if(activity != null){
            requestQueue = Volley.newRequestQueue(activity);
            sendVolleyRequest(activity, view);
            Utilities.setUpToolbar((AppCompatActivity) activity, "FILM DETAILS");

            titleTextView = view.findViewById(R.id.title_textView);
            filmImageView = view.findViewById(R.id.film_imageView);
            previewImageView = view.findViewById(R.id.preview_imageView);

            ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getFilmSelected().observe(getViewLifecycleOwner(), new Observer<Film>() {
                @Override
                public void onChanged(Film film) {
                    Glide.with(activity)
                            .load(film.getUrlPosterImg())
                            .into(filmImageView);
                    Glide.with(activity)
                            .load(previewUrl)
                            .into(previewImageView);
                    titleTextView.setText(film.getTitle());
                }
            });
        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.actors_recyclerView);

       /* recyclerView.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), 3));
        recyclerView.setHasFixedSize(true);*/
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ActorCardAdapter(actorsList, activity);
        recyclerView.setAdapter(adapter);
    }

    private void sendVolleyRequest(Activity activity, View view){
        String url = "https://imdb-api.com/en/API/Title/k_4ej6zo7h/"+this.film.getId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray actors = response.getJSONArray("actorList");
                    TextView director = view.findViewById(R.id.director_TextView);
                    director.setText((String)response.getJSONArray("directorList").getJSONObject(0).get("name"));
                    for(int i=0; i < actors.length(); i++){
                        actorsList.add(new Actor((String) actors.getJSONObject(i).get("name"),(String) actors.getJSONObject(i).get("asCharacter"),
                                (String) actors.getJSONObject(i).get("image")));
                    }
                    System.out.println(actorsList);
                    setRecyclerView(activity);

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

        jsonObjectRequest.setTag(FILM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(requestQueue != null){
            requestQueue.cancelAll(FILM_REQUEST_TAG);
        }
    }
}
