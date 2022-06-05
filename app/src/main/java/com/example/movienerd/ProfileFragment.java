package com.example.movienerd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import com.android.volley.toolbox.Volley;
import com.example.movienerd.FilmRecyclerView.AchievementCardAdapter;
import com.example.movienerd.FilmRecyclerView.OnItemListener;
import com.example.movienerd.FilmRecyclerView.SearchedFilmAdapter;
import com.example.movienerd.ViewModels.ListViewModel;

import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment {
    private static final String LOG = "PROFILE";
    private AchievementCardAdapter adapter;
    private ListViewModel listViewModel;
    private List<Achievement> allAchievements;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, "PROFILE");
            setRecyclerView(activity);

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

            listViewModel.getAllAchievements().observe((LifecycleOwner) activity, new Observer<List<Achievement>>() {
                @Override
                public void onChanged(List<Achievement> achievements) {
                    allAchievements = achievements;
                }
            });

            listViewModel.getUsersWithAchievement().observe((LifecycleOwner) activity, new Observer<List<UserAchievementCrossRef>>() {
                @Override
                public void onChanged(List<UserAchievementCrossRef> achievementsId) {
                    List<Achievement> toAdd = new LinkedList<>();
                    for(UserAchievementCrossRef userWithAchievements : achievementsId){
                        if(currentUser.getUser_id() == userWithAchievements.getUser_id()){
                            for(Achievement achievement : allAchievements){
                                if(achievement.getAchievement_id() == userWithAchievements.getAchievement_id()){
                                    toAdd.add(achievement);
                                }
                            }
                        }
                    }
                    adapter.setData(toAdd);
                }
            });

        }else{
            Log.e(LOG, "Activity is null");
        }
    }

    private void setRecyclerView(Activity activity){
        RecyclerView recyclerView = activity.findViewById(R.id.achievement_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new AchievementCardAdapter(activity);
        recyclerView.setAdapter(adapter);
    }
}
