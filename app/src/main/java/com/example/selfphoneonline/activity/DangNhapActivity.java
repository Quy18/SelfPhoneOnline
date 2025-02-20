package com.example.selfphoneonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfphoneonline.R;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControl();
    }

    private void initControl() {
        txtdangky.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DangKyActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        txtdangky = findViewById(R.id.txtdangky);

    }
}