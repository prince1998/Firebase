package com.upin.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
public String name,email,password;
public FirebaseAuth authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authenticator=FirebaseAuth.getInstance();

        login();
    }

    public void login()
    {
        final EditText name=findViewById(R.id.nameInput);
        final EditText email=findViewById(R.id.emailInput);
        final EditText password=findViewById(R.id.passInput);


        Button b=findViewById(R.id.loginButton);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(name.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                }

                else if (email.getText().toString().isEmpty()) {
                    email.setError("Field can't be empty");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Please enter a valid email address");
                }

                else if(password.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter a password", Toast.LENGTH_SHORT).show();
                }
                else if (!PASSWORD_PATTERN.matcher(password.getText().toString()).matches()) {
                    password.setError("Password too weak");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_LONG).show();
                    authenticator.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString());

                    Intent launchApp=getPackageManager().getLaunchIntentForPackage("medifact.upin.medifact");
                    if(launchApp!=null)
                    {
                        startActivity(launchApp);
                    }


                }
            }
        });
    }
}