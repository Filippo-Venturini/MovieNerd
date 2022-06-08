package com.example.movienerd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import com.example.movienerd.ViewModels.ListViewModel;

import java.util.List;

public class RegisterFragment extends Fragment {
    private static final String LOG = "REGISTER";
    private ListViewModel listViewModel;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText confirmPasswEdit;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MainActivity activity = (MainActivity) getActivity();
        if(activity != null){
            Utilities.setUpToolbar(activity, "SIGN UP");
            activity.currentFragment = "register";
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);

            this.usernameEdit = view.findViewById(R.id.registerUser_editText);
            this.passwordEdit = view.findViewById(R.id.registerPassword_editText);
            this.confirmPasswEdit = view.findViewById(R.id.confirmPassword_editText);

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

            view.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String passw = passwordEdit.getText().toString();
                    String passwConf = confirmPasswEdit.getText().toString();
                    if(passw.equals(passwConf) && !passw.equals("")){
                        if(currentUser != null){
                            currentUser.setIsLogged(false);
                            listViewModel.updateUser(currentUser);
                        }
                        User user = new User(usernameEdit.getText().toString(), passw);
                        user.setIsLogged(true);
                        listViewModel.addUser(user);
                        Toast.makeText(activity, "Registered", Toast.LENGTH_SHORT).show();
                        TextView txtLogin = activity.findViewById(R.id.loginLogout_textView);
                        TextView txtUsername = activity.findViewById(R.id.username_TextView);
                        txtLogin.setText("Logout");
                        txtUsername.setText(user.getUsername());
                        Utilities.insertFragment(activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                    }else{
                        Toast.makeText(activity, "The passwords must be equals and not empty.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
