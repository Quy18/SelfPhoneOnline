package com.example.selfphoneonline.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfphoneonline.R;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Paper.init(this);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3500);
                } catch (Exception e) {

                }finally {
                    if(Paper.book().read("user") == null){
                        Intent intent = new Intent(SplashActivity.this, DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent home = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}