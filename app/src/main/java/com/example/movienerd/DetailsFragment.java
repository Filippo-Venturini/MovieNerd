package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.movienerd.ActorRecyclerView.ActorCardAdapter;
import com.example.movienerd.ViewModels.ListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private static final String LOG = "DETAILS";
    private Film currentFilm;
    private User currentUser;
    private List<Actor> actorsList = new LinkedList<>();

    private ActorCardAdapter adapter;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private final static  String FILM_REQUEST_TAG = "FILM_REQUEST_TAG";

    private TextView titleTextView;
    private ImageView filmImageView;
    private ImageView previewImageView;
    private TextView voteTextView;
    private TextView yearTextView;
    private TextView durationTextView;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabWatchList;
    private FloatingActionButton fabWatched;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private boolean clicked = false;

    private List<String> currentWatchListIds;
    private List<String> currentWatchedIds;

    public DetailsFragment(Film film){
        this.currentFilm = film;
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

        if(activity != null){
            setHasOptionsMenu(true);
            requestQueue = Volley.newRequestQueue(activity);
            sendVolleyRequest(activity, view);
            Utilities.setUpToolbar((AppCompatActivity) activity, "FILM DETAILS");

            titleTextView = view.findViewById(R.id.title_textView);
            voteTextView = view.findViewById(R.id.vote_textView);
            filmImageView = view.findViewById(R.id.film_imageView);
            previewImageView = view.findViewById(R.id.preview_imageView);
            yearTextView = view.findViewById(R.id.textView_Year);
            durationTextView = view.findViewById(R.id.textView_Duration);
            fabAdd = view.findViewById(R.id.fab_add);
            fabWatchList = view.findViewById(R.id.fab_watchList);
            fabWatched = view.findViewById(R.id.fab_watched);

            rotateOpen = AnimationUtils.loadAnimation(this.getActivity(), R.anim.rotate_open_anim);
            rotateClose = AnimationUtils.loadAnimation(this.getActivity(), R.anim.rotate_close_anim);
            fromBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.from_bottom_anim);
            toBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.to_bottom_anim);


            ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);

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

            listViewModel.getWatchList().observe((LifecycleOwner) activity, new Observer<List<UserFilmCrossRef>>() {
                @Override
                public void onChanged(List<UserFilmCrossRef> usersFilms) {
                    currentWatchListIds = new LinkedList<>();
                    for(UserFilmCrossRef userFilm : usersFilms){
                        if(userFilm.getUser_id() == currentUser.getUser_id()){
                            currentWatchListIds.add(userFilm.getFilm_id());
                        }
                    }
                }
            });

            listViewModel.getWatchedFilms().observe((LifecycleOwner) activity, new Observer<List<UserFilmCrossRef>>() {
                @Override
                public void onChanged(List<UserFilmCrossRef> usersFilms) {
                    currentWatchedIds = new LinkedList<>();
                    for(UserFilmCrossRef userFilm : usersFilms){
                        if(userFilm.getUser_id() == currentUser.getUser_id()){
                            currentWatchedIds.add(userFilm.getFilm_id());
                        }
                    }
                }
            });

            listViewModel.getFilmSelected().observe(getViewLifecycleOwner(), new Observer<Film>() {
                @Override
                public void onChanged(Film film) {
                    Glide.with(activity)
                            .load(film.getUrlPosterImg())
                            .into(filmImageView);
                    titleTextView.setText(film.getTitle());
                    voteTextView.setText(film.getVote());
                    yearTextView.setText(film.getYear());
                }
            });

            MainActivity mainActivity = (MainActivity) this.getActivity();

            if(!mainActivity.isLogged()){
                fabAdd.setVisibility(View.INVISIBLE);
            }

            fabAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!clicked){
                        fabWatched.setVisibility(View.VISIBLE);
                        fabWatchList.setVisibility(View.VISIBLE);
                        fabWatched.startAnimation(fromBottom);
                        fabWatchList.startAnimation(fromBottom);
                        fabAdd.startAnimation(rotateOpen);
                        fabWatched.setClickable(true);
                        fabWatchList.setClickable(true);
                        clicked = true;
                    }else{
                        fabWatched.setVisibility(View.INVISIBLE);
                        fabWatchList.setVisibility(View.INVISIBLE);
                        fabWatched.startAnimation(toBottom);
                        fabWatchList.startAnimation(toBottom);
                        fabAdd.startAnimation(rotateClose);
                        fabWatched.setClickable(false);
                        fabWatchList.setClickable(false);
                        clicked = false;
                    }
                }
            });

            fabWatchList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Added to WatchList", Toast.LENGTH_SHORT).show();
                    if(!currentWatchedIds.contains(currentFilm.getFilm_id()) && !currentWatchListIds.contains(currentFilm.getFilm_id())){
                        listViewModel.addFilm(currentFilm);
                    }
                    UserFilmCrossRef currentRef = new UserFilmCrossRef(currentUser.getUser_id(),currentFilm.getFilm_id());
                    currentRef.setInWatchlist(true);
                    currentUser.toggleWatchListCounter(true);
                    listViewModel.updateUser(currentUser);

                    if(currentWatchedIds.contains(currentFilm.getFilm_id())){
                        listViewModel.updateUserFilms(currentRef);
                    }else{
                        listViewModel.addUserFilm(currentRef);
                    }
                    if(currentUser.getFilmInWatchlist() == 1){
                        listViewModel.addUserAchievement(new UserAchievementCrossRef(currentUser.getUser_id(),1));
                    }
                }
            });

            fabWatched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Added to Watched Movies", Toast.LENGTH_SHORT).show();
                    UserFilmCrossRef currentRef = new UserFilmCrossRef(currentUser.getUser_id(),currentFilm.getFilm_id());
                    if(currentWatchListIds.contains(currentFilm.getFilm_id())){
                        listViewModel.updateUserFilms(currentRef);
                    }else{
                        //listViewModel.addFilm(currentFilm);
                        listViewModel.addUserFilm(currentRef);
                    }
                    currentRef.setInWatchlist(false);
                    currentRef.setWatched(true);
                    listViewModel.addUserFilm(currentRef);
                    listViewModel.updateUser(currentUser);
                }
            });
        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    private void setRecyclerView(final Activity activity) {
        recyclerView = activity.findViewById(R.id.actors_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ActorCardAdapter(actorsList, activity);
        recyclerView.setAdapter(adapter);
    }

    private void sendVolleyRequest(Activity activity, View view){
        String url = "https://imdb-api.com/en/API/Title/k_4ej6zo7h/"+this.currentFilm.getFilm_id()+"/Images";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String previewUrl = (String) response.getJSONObject("images").getJSONArray("items").getJSONObject(0).get("image");

                    Glide.with(activity)
                            .load(previewUrl)
                            .into(previewImageView);

                    durationTextView.setText((String) response.get("runtimeMins")+" mins");

                    JSONArray actors = response.getJSONArray("actorList");
                    TextView director = view.findViewById(R.id.director_TextView);
                    TextView description = view.findViewById(R.id.descriptionTextView);
                    description.setText((String)response.get("plot"));
                    director.setText((String)response.getJSONArray("directorList").getJSONObject(0).get("name"));
                    for(int i=0; i < actors.length(); i++){
                        actorsList.add(new Actor((String) actors.getJSONObject(i).get("name"),(String) actors.getJSONObject(i).get("asCharacter"),
                                (String) actors.getJSONObject(i).get("image")));
                    }

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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(requestQueue != null){
            requestQueue.cancelAll(FILM_REQUEST_TAG);
        }
    }
}
