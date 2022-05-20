package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienerd.FilmRecyclerView.GridItemDecorator;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.FilmRecyclerView.SimpleCardAdapter;
import com.example.movienerd.ViewModels.ListViewModel;

import java.util.List;

public class WatchedFilmsFragment extends Fragment implements OnItemListener{
    private static final String LOG = "WATCHED_FILMS";
    private SimpleCardAdapter adapter;
    private ListViewModel listViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.watched_films, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            setHasOptionsMenu(true);

            Utilities.setUpToolbar((AppCompatActivity) activity, "WATCHED MOVIES");

            setRecyclerView(activity);

            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getWatchedFilms().observe((LifecycleOwner) activity, new Observer<List<Film>>() {
                @Override
                public void onChanged(List<Film> films) {
                    adapter.setData(films);
                }
            });

        }else{
            Log.e(LOG, "Activity is null");
        }
    }
    private void setRecyclerView(final Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.watchedFilms_recyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridItemDecorator(3,10,false));
        final OnItemListener listener = this;
        adapter = new SimpleCardAdapter(listener, activity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Activity activity = getActivity();
        if(activity != null){
            Utilities.insertFragment((AppCompatActivity) activity, new DetailsFragment(adapter.getFilmSelected(position)), DetailsFragment.class.getSimpleName());
            listViewModel.setFilmSelected(adapter.getFilmSelected(position));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}