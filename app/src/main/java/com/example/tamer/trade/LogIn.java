package com.example.tamer.trade;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends Activity {

    EditText email;
    EditText password;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = (EditText)findViewById(R.id.editTextEmail);
        password=(EditText)findViewById(R.id.editTextPassword);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging In");

    }
    public void LoginClick(View view)
    {

        if(email.getText().toString().isEmpty()){
            Toast.makeText(LogIn.this,"Enter Email",Toast.LENGTH_SHORT);
            email.requestFocus();
            return;
        }
        if(password.getText().toString().isEmpty()){
            Toast.makeText(LogIn.this,"Enter password",Toast.LENGTH_SHORT);
            password.requestFocus();
            return;
        }

        String emails = email.getText().toString();
        String passs = password.getText().toString();
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emails,passs)
                .addOnCompleteListener(LogIn.this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn.this, "Login successfully ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LogIn.this,FeedActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(LogIn.this, "Login failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void CreateUser(View view){
        startActivity(new Intent(LogIn.this,signUp.class));
    }
}
