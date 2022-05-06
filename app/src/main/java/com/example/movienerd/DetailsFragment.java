package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class DetailsFragment extends Fragment {

    private static final String LOG = "DETAILS";
    private Film film;

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
            Utilities.setUpToolbar((AppCompatActivity) activity, "FILM DETAILS");
            Glide.with(activity)
                    .load(film.getUrlPosterImg())
                    .into((ImageView) view.findViewById(R.id.film_imageView));
            Glide.with(activity)
                    .load(previewUrl)
                    .into((ImageView) view.findViewById(R.id.preview_imageView));

        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.app_bar_search).setVisible(false);
    }
}
