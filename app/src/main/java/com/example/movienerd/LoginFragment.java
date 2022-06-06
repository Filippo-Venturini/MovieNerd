package com.example.movienerd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.bumptech.glide.util.Util;
import com.example.movienerd.ViewModels.ListViewModel;

import java.util.List;

public class LoginFragment extends Fragment{
    private static final String LOG = "LOGIN";
    private ListViewModel listViewModel;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private List<User> allUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar(activity, "LOGIN");
            activity.currentFragment = "login";
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);

            listViewModel.getAllUsers().observe((LifecycleOwner) activity, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
                    allUsers = users;
                }
            });

            this.usernameEdit = view.findViewById(R.id.username_editText);
            this.passwordEdit = view.findViewById(R.id.password_editText);
            view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkLogin(usernameEdit.getText().toString(), passwordEdit.getText().toString())){
                        Toast.makeText(activity, "Logged", Toast.LENGTH_SHORT).show();
                        Utilities.insertFragment(activity,new HomeFragment(),HomeFragment.class.getSimpleName());
                    }else{
                        Toast.makeText(activity, "Wrong username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            view.findViewById(R.id.register_textView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertFragment(activity, new RegisterFragment(), RegisterFragment.class.getSimpleName());
                }
            });
        }
    }

    private boolean checkLogin(String username, String password){
        if(allUsers.isEmpty()){
            return false;
        }
        Boolean isIn = false;
        for (User user : this.allUsers){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                isIn = true;
                user.setIsLogged(true);
                listViewModel.updateUser(user);
                break;
            }
        }
        return isIn;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
