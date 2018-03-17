package com.example.tamer.trade;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends Activity {

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private Spinner gender;
    private EditText phonenumber;
    private EditText city;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstname = (EditText)findViewById(R.id.editTextFirstName);
        lastname = (EditText)findViewById(R.id.editLastName);
        email = (EditText)findViewById(R.id.editTextEmail);
        password = (EditText)findViewById(R.id.editTextPassword);
        gender = (Spinner) findViewById(R.id.spinnerG);
        phonenumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        city = (EditText) findViewById(R.id.editTextCity);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Up");

    }
    //TODO toast not working
    public void Onclick (View view)
    {
        if(firstname.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter firstname",Toast.LENGTH_SHORT);
            firstname.requestFocus();
            return;
        }
        if(lastname.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter lastname",Toast.LENGTH_SHORT);
            lastname.requestFocus();
            return;
        }
        if(email.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter Email",Toast.LENGTH_SHORT);
            email.requestFocus();
            return;
        }
        if(password.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter password",Toast.LENGTH_SHORT);
            password.requestFocus();
            return;
        }
        if(gender.getSelectedItem().toString().isEmpty()){
            Toast.makeText(signUp.this,"select gender",Toast.LENGTH_SHORT);
            gender.requestFocus();
            return;
        }
        if(phonenumber.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter phone number",Toast.LENGTH_SHORT);
            phonenumber.requestFocus();
            return;
        }
        if(city.getText().toString().isEmpty()){
            Toast.makeText(signUp.this,"Enter city",Toast.LENGTH_SHORT);
            city.requestFocus();
            return;
        }
        String emails = email.getText().toString();
        String passs = password.getText().toString();
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(emails,passs)
                .addOnCompleteListener(signUp.this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users users = new Users(firebaseAuth.getCurrentUser().getUid(),firstname.getText().toString(),lastname.getText().toString(),gender.getSelectedItem().toString(),email.getText().toString(),phonenumber.getText().toString(),city.getText().toString());
                            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(users);
                            Toast.makeText(signUp.this, "Registered Successfully ",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signUp.this,LogIn.class));
                            progressDialog.dismiss();
                        }
                        else {
                            Toast.makeText(signUp.this, "Regestration Failed",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }
}
