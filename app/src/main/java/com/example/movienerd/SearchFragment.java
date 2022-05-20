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

import com.example.movienerd.FilmRecyclerView.FilmCardAdapter;
import com.example.movienerd.FilmRecyclerView.GridItemDecorator;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.FilmRecyclerView.SearchedFilmAdapter;
import com.example.movienerd.FilmRecyclerView.SimpleCardAdapter;
import com.example.movienerd.ViewModels.ListViewModel;

import java.util.List;

public class SearchFragment extends Fragment  implements OnItemListener {
    private static final String LOG = "WATCHED_FILMS";
    private SearchedFilmAdapter adapter;
    private ListViewModel listViewModel;

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

    @Override
    public void onItemClick(int position) {

    }
}