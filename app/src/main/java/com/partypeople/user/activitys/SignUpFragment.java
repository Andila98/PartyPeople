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

public class SignUpFragment extends Fragment implements View.OnClickListener {
    
    private Button button_register;
    private EditText first_name;
    private EditText last_name;
    private EditText username;
    private EditText email;
    private EditText password;
    private TextView textviewSignin;

    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,container,false);


        progressDialog = new ProgressDialog(getContext());

        button_register = view.findViewById(R.id.button_register);
        first_name = view.findViewById(R.id.first_name);
        last_name = view. findViewById(R.id.last_name);
        username = view. findViewById(R.id.username);
        email = view. findViewById(R.id.email);
        password = view.findViewById(R.id.Password);
        textviewSignin = view.findViewById(R.id.textviewSignin);

        button_register.setOnClickListener(this);
        textviewSignin.setOnClickListener(this);
        return view;
    }


    private void registerUser(){
        String firstname = first_name.getText().toString().trim();
        String lastname = last_name.getText().toString().trim();
        String Username = username.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if (TextUtils.isEmpty(firstname)){
            //firstname is empty
            Toast.makeText(getActivity(),"please enter your firstname", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }
        if (TextUtils.isEmpty(lastname)){
            //lastname is empty
            Toast.makeText(getActivity(),"please enter your lastname", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }
        if (TextUtils.isEmpty(Username)){
            //username is empty
            Toast.makeText(getActivity(),"please enter your username", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        if (TextUtils.isEmpty(Email)){
            //email is empty
            Toast.makeText(getActivity(),"please enter your email", Toast.LENGTH_SHORT).show();
            //stop the function from executing further
            return;
        }

        if (TextUtils.isEmpty(Password)){
            //password is empty
            Toast.makeText(getActivity(), "please enter password", Toast.LENGTH_SHORT).show();
            //stop the function from further execution
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }

                        // ...
                    }
                });

        //if validation is ok
        //we will show a progress bar

        progressDialog.setMessage("please wait...");
        progressDialog.show();
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            Toast.makeText(getContext(),"Please try again", Toast.LENGTH_SHORT).show();
        }else {

        }
    }
    @Override
    public void onClick(View view){
        if(view == button_register){
            registerUser();
        }

        if (view ==textviewSignin){
            //open the login activity here
            //startActivity(new Intent(getContext(),SignInFragment));

        }

    }
}
