package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movienerd.RecyclerView.CardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String LOG = "Home-Fragment";

    private CardAdapter adapter;
    private RecyclerView recyclerView;

    private  RequestQueue requestQueue;
    private final static  String TOP_FILM_REQUEST_TAG = "TOP_FILM_REQUEST";
    private List<String> topFilmImagesURLs = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance){
        super.onViewCreated(view, savedInstance);
        final Activity activity = getActivity();
        if(activity != null){
            requestQueue = Volley.newRequestQueue(activity);
            this.sendVolleyRequest(activity);

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
        recyclerView = activity.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(activity.getApplicationContext(), 2));
        recyclerView.setHasFixedSize(true);
        List<String> list = new ArrayList<>();
        list.addAll(topFilmImagesURLs);

        adapter = new CardAdapter(list, activity);
        recyclerView.setAdapter(adapter);
    }

    private void sendVolleyRequest(Activity activity){
        String url = "https://imdb-api.com/en/API/Top250Movies/k_4ej6zo7h";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray films = response.getJSONArray("items"); //Prende l'array di film

                    for(int i=0; i < 20; i++){
                        topFilmImagesURLs.add((String) films.getJSONObject(i).get("image"));
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

        jsonObjectRequest.setTag(TOP_FILM_REQUEST_TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
