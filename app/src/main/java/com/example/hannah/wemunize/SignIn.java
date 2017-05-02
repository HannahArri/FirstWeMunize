package com.example.hannah.wemunize;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class SignIn extends AppCompatActivity {
    EditText username;
    EditText password;
    ProgressBar bar;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser user = ParseUser.getCurrentUser();
        if(user != null) {
            String role = user.getString("userType");
            if( role.equalsIgnoreCase("rp")){
                startActivity(new Intent(SignIn.this, RpPage.class));
                finish();
            }
            else {
                startActivity(new Intent(SignIn.this, OptionsPage.class));
                finish();
            }
        }
        else {


            setContentView(R.layout.activity_sign_in);
            username = (EditText) findViewById(R.id.signinUsername);
            password = (EditText) findViewById(R.id.signinPassword);
            login = (Button) findViewById(R.id.signinButton);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Login();
                }
            });
            username.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        Login();
                    }
                    return false;
                }
            });
            password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if(actionId == EditorInfo.IME_ACTION_DONE){
                        Login();
                    }
                    return false;
                }
            });
            bar = (ProgressBar) findViewById(R.id.loginProgressBar);
            bar.setVisibility(View.INVISIBLE);
        }
    }
    public void Login(){
        bar.setVisibility(View.VISIBLE);
        if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(SignIn.this, "Please Fill in All Fields", Toast.LENGTH_SHORT).show();
        }else{
            final String Username = username.getText().toString().trim();
            String Password = password.getText().toString().trim();
            ParseUser.logInInBackground(Username, Password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){

                        Toast.makeText(SignIn.this, "Signed in", Toast.LENGTH_LONG).show();
                        bar.setVisibility(View.INVISIBLE);
                        String role = user.getString("userType");
                        if( role.equalsIgnoreCase("rp")){
                            Toast.makeText(SignIn.this, "Signed in as rp", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignIn.this, RpPage.class));
                            finish();
                        }
                        else {
                            startActivity(new Intent(SignIn.this, OptionsPage.class));
                            finish();
                        }

                    }
                    else{
                        String error = e.getMessage().substring(0, 1).toUpperCase() + e.getMessage().substring(1);
                        new AlertDialog.Builder(SignIn.this)
                                .setTitle("Login Failed")
                                .setCancelable(true)
                                .setMessage(error)
                                .show();
                        bar.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }

    }

}
