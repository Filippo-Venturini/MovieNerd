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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.movienerd.ViewModels.ListViewModel;

import java.util.List;

public class LoginFragment extends Fragment{
    private static final String LOG = "LOGIN";
    private ListViewModel listViewModel;
    private EditText usernameEdit;
    private EditText passwordEdit;

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
            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);

            System.out.println(listViewModel.getUser(2));

            this.usernameEdit = view.findViewById(R.id.username_editText);
            this.passwordEdit = view.findViewById(R.id.password_editText);
            view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listViewModel.addUser(new User(usernameEdit.getText().toString(), passwordEdit.getText().toString()));
                }
            });
        }
    }
}
