package com.example.selfphoneonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.selfphoneonline.R;
import com.example.selfphoneonline.retrofit.ApiBanHang;
import com.example.selfphoneonline.retrofit.RetrofitClient;
import com.example.selfphoneonline.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangky, txtresetpass;
    EditText email, pass;
    AppCompatButton btndangnhap;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        btndangnhap.setOnClickListener(view -> {
            String str_email = email.getText().toString().trim();
            String str_pass = pass.getText().toString().trim();
            if(TextUtils.isEmpty(str_email)){
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(str_pass)){
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            }else{
                //save cho lan login sau
                Paper.book().write("email", str_email);
                Paper.book().write("pass", str_pass);
                compositeDisposable.add(apiBanHang.dangNhap(str_email,str_pass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                           userModel -> {
                                if(userModel.isSuccess()){
                                    Utils.user_current = userModel.getResult().get(0);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                           },
                           throwable -> {
                               Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                        ));
            }
        });

        txtresetpass.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
            startActivity(intent);
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        txtdangky = findViewById(R.id.txtdangky);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btndangnhap = findViewById(R.id.btndangnhap);
        txtresetpass = findViewById(R.id.txtresetpass);
        Paper.init(this);
        //read paper
        if (Paper.book().read("email") != null && Paper.book().read("pass") != null){
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}