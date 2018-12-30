package com.example.prithvi.logindemo;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newUsername,newEmail,newAge;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        initialize();

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Please wait!");
//        progressDialog.show();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //progressDialog.dismiss();
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                newUsername.setText(userProfile.getName());
                newAge.setText(userProfile.getAge());
                newEmail.setText(userProfile.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //progressDialog.dismiss();
                Toast.makeText(UpdateProfile.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = newUsername.getText().toString();
                String email = newEmail.getText().toString();
                String age = newAge.getText().toString();

                UserProfile userProfile = new UserProfile(age,email,name);

                databaseReference.setValue(userProfile);

                finish();
            }
        });

    }

    private void initialize(){
        newUsername = (EditText)findViewById(R.id.etNameUpdate);
        newEmail = (EditText)findViewById(R.id.etEmailUpdate);
        newAge = (EditText)findViewById(R.id.etAgeUpdate);
        save = (Button)findViewById(R.id.btnSave);
    }
}
