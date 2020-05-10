package com.example.greenwaste2.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.greenwaste2.R;
import com.example.greenwaste2.model.RegisterList;
import com.example.greenwaste2.databinding.RegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseAuth auth;
    RegisterBinding binding;
    RegisterList reglist;
    DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.register);
        auth = FirebaseAuth.getInstance(); // Firebase authentication
        dataref = FirebaseDatabase.getInstance().getReference().child("RegisterList");
        init();
    }

    private void validation() {
        String firstname=binding.firstname.getText().toString().trim();
        String lastname=binding.lastname.getText().toString().trim();
        String phonenumber=binding.Phonenumber.getText().toString().trim();
        String email=binding.emial.getText().toString().trim();
        String password=binding.password.getText().toString().trim();
        String gender=binding.male.getText().toString();

        if (auth.getCurrentUser()!=null){
            finish();
        }
        if (TextUtils.isEmpty(firstname)){
            binding.firstname.setError("Firstname cant't be empty");
            return;
        }
        if (TextUtils.isEmpty(lastname)){
            binding.lastname.setError("Lastname cant't be empty");
            return;
        }
        if (TextUtils.isEmpty(phonenumber)){
            binding.Phonenumber.setError("Phone cant't be empty");
            return;
        }
        if (TextUtils.isEmpty(email)){
            binding.emial.setError("Email cant't be empty");
            return;
        }
        if (TextUtils.isEmpty(password)){
            binding.password.setError("Password cant't be empty");
            return;
        }
        if (TextUtils.isEmpty(gender)){
            Toast.makeText(this,"Select Gender",Toast.LENGTH_LONG).show();
            return;
        }
        int id=binding.gender.getCheckedRadioButtonId();
        switch (id){
            case  R.id.male:
                gender="male";
                Toast.makeText(this,gender,Toast.LENGTH_LONG).show();
                break;
            case R.id.female:
                gender="female";
                Toast.makeText(this,gender,Toast.LENGTH_LONG).show();
                break;
        }
        reglist= new RegisterList(firstname,lastname,email,password,phonenumber,gender);

        startLoader(0);
        auth.createUserWithEmailAndPassword(binding.emial.getText().toString(),binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    dataref.push().setValue(reglist);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }else {
                    startLoader(1);
                    Toast.makeText(Register.this, "Registration Failed"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //0 shows loading state ,1 shows default state
    public void startLoader(int status){
        switch (status){
            case 0:
                binding.signup.setText("Loading");
                binding.signup.setClickable(false);
                binding.signup.setAlpha(0.6F);
                binding.progressbar.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.signup.setText("Signup");
                binding.signup.setAlpha(1.0F);
                binding.signup.setClickable(true);
                binding.progressbar.setVisibility(View.GONE);
                break;
        }

    }

    private void init(){
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
    }

}


