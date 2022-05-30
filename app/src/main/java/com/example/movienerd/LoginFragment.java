package com.example.movienerd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar(activity, "LOGIN");
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
                        System.out.println("LOGGATO");
                    }else{
                        System.out.println("NO LOGGATO");
                    }
                }
            });

            view.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listViewModel.addUser(new User(usernameEdit.getText().toString(), passwordEdit.getText().toString()));
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
}
