package com.partypeople.user.activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.partypeople.user.R;
import com.partypeople.user.ui.activity.MainActivity;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private Button button_login;
    private EditText login_username;
    private EditText login_password;
    private TextView login_passwordreset;
    private TextView textveiwsignup;

    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        progressDialog = new ProgressDialog(getActivity());

        button_login = view.findViewById(R.id.button_login);
        login_username = view.findViewById(R.id.login_username);
        login_password = view.findViewById(R.id.login_password);
        login_passwordreset = view.findViewById(R.id.login_passwordreset);
        textveiwsignup = view.findViewById(R.id.textviewsignup);



        button_login.setOnClickListener(this);
        login_password.setOnClickListener(this);
        login_passwordreset.setOnClickListener(this);
        return view;
    }

    private void loginuser() {
        String email = login_username.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(getActivity(), "please enter your email", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(getActivity(), "please enter password", Toast.LENGTH_SHORT).show();
            //stop the function from further execution
            return;
        }


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }else
                    Toast.makeText(getContext(), "please check your Password",Toast.LENGTH_SHORT).show();
            }
        });
        //if validation is ok
        //we will show a progress bar

        progressDialog.setMessage("please wait...");
        progressDialog.show();
    }


    @Override
    public void onClick(View view) {
        if (view == button_login) {
            loginuser();
        }
        if (view == textveiwsignup) {
            startActivity(new Intent(getContext(), SignUpFragment.class));
        }
    }
}
