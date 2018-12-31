package com.example.prithvi.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name,email,password,age;
    private Button register;
    private TextView registered;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageView profilePic;
    String username,useremail,userpassword,userage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setValues();

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //upload data to data base
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();

                    progressDialog.setMessage("please wait!");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                //sendEmailVerification();
                                sendUserData();
                                firebaseAuth.signOut();
                                Toast.makeText(RegistrationActivity.this,"Successfully Registered,Upload complete!",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
            }
        });
    }

    private void setValues(){
        name = (EditText)findViewById(R.id.etName);
        email = (EditText)findViewById(R.id.etEmail);
        age = (EditText)findViewById(R.id.etAge);
        password = (EditText)findViewById(R.id.etPassword);
        register = (Button)findViewById(R.id.btnRegister);
        registered = (TextView)findViewById(R.id.tvRegistered);
        profilePic = (ImageView)findViewById(R.id.ivImage);
    }

    private boolean validate(){
        boolean result=false;
        username = name.getText().toString();
        useremail = email.getText().toString();
        userpassword = password.getText().toString();
        userage = age.getText().toString();

        if(username.isEmpty() || useremail.isEmpty() || userpassword.isEmpty() || userage.isEmpty()){
            Toast.makeText(this,"enter details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification(){
        FirebaseUser firebaseUser  = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //sendUserData();
                        Toast.makeText(RegistrationActivity.this,"Successfully Registered Verification mail sent!",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(RegistrationActivity.this,"Verification mail has'nt been sent!",Toast.LENGTH_SHORT).show();  //else condition is invoked when Firebase server is down it is not our fault
                    }
                }
            });
        }
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        UserProfile userProfile = new UserProfile(userage,useremail,username);
        myRef.setValue(userProfile);
    }
}