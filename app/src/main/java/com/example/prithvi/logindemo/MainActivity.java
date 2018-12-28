package com.example.prithvi.logindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText userPassword;
    private Button login;
    private TextView signup,forgotPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intialize();

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,PasswordActivity.class));
            }
        });
    }

    private  void intialize(){
        userName = (EditText)findViewById(R.id.name);
        userPassword = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.btnLogin);
        signup = (TextView)findViewById(R.id.tvSignup);
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
    }

    private void validate(){
        String user_name = userName.getText().toString();
        String user_password = userPassword.getText().toString();


        if(user_name.isEmpty() && user_password.isEmpty())
            Toast.makeText(this,"enter Username and Password",Toast.LENGTH_SHORT).show();
        else if(user_name.isEmpty())
            Toast.makeText(this,"enter Username",Toast.LENGTH_SHORT).show();
        else if(user_password.isEmpty())
            Toast.makeText(this,"enter Password",Toast.LENGTH_SHORT).show();
        else {
            progressDialog.setMessage("jara aapuko aithadi!");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(user_name, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        checkEmailVerification();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();  //the 'F' in FireBaseAuth may be small check it
        boolean emailflag = firebaseUser.isEmailVerified();

        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,SecondActivity.class));

//        if(emailflag){
//            finish();
//            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(MainActivity.this,SecondActivity.class));
//        }else{
//            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
//            firebaseAuth.signOut();
//        }
    }
}
