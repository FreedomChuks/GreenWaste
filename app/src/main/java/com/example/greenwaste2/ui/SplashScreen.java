package com.example.greenwaste2.ui;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.ImageView;


import com.example.greenwaste2.R;
import com.example.greenwaste2.ui.auth.Login;

import java.util.Timer;
import java.util.TimerTask;



public class SplashScreen extends AppCompatActivity {
    public ImageView imgview;
    Timer timer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);



        // takes 4seconds to the next activity
        imgview = findViewById(R.id.default_image);


        timer = new Timer();
        timer.schedule(new TimerTask() { // second sehudule with long delay
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

//


}
