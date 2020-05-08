package com.example.greenwaste2.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.greenwaste2.ui.MainActivity;
import com.example.greenwaste2.R;
import com.example.greenwaste2.databinding.LoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    LoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.login);
        auth = FirebaseAuth.getInstance();
        Validate();
        initalize();
    }

    private void initalize() {
        //Location Permission
        int Permission_All = 1;
        String[] Permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.MEDIA_CONTENT_CONTROL, Manifest.permission.CAMERA};
        if (!hasPermission(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });


    }

    private void Validate() {
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    binding.email.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    binding.password.setError("Password Required");
                    return;
                }
                if (password.length() < 6) {
                    binding.password.setError("Minimum of 6 Character is required");
                    return;
                }
                startLoader(0);
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startLoader(1);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            startLoader(1);
                            Toast.makeText(Login.this, "Invalid Details" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    //permission
    public static boolean hasPermission(Context context, String... permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null){
            for (String permission: permissions){
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    //0 shows loading state ,1 shows default state
    public void startLoader(int status){
        switch (status){
            case 0:
                binding.login.setText("Loading");
                binding.login.setClickable(false);
                binding.login.setAlpha(0.6F);
                binding.progressbar.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.login.setText("login");
                binding.login.setAlpha(1.0F);
                binding.login.setClickable(true);
                binding.progressbar.setVisibility(View.GONE);
                break;
        }

    }

}
