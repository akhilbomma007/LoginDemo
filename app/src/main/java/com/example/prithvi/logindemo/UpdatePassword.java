package com.example.prithvi.logindemo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private EditText pass1,pass2;
    private Button update;
    private FirebaseUser firebaseUser;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        initialize();

        progressDialog = new ProgressDialog(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p1 = pass1.getText().toString();
                String p2 = pass2.getText().toString();

                progressDialog.setMessage("Please wait!");
                progressDialog.show();

                if (!(p1.equals(p2))){
                    progressDialog.dismiss();
                    Toast.makeText(UpdatePassword.this, "both do not match please re-enter password", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseUser.updatePassword(p1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(UpdatePassword.this,"Password updated",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(UpdatePassword.this,"Password update failed",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initialize(){
        pass1 = (EditText)findViewById(R.id.etNewPassword);
        pass2 = (EditText)findViewById(R.id.etAgain);
        update = (Button)findViewById(R.id.btnUpdate);
    }
}
