package com.example.selfphoneonline;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        ActionBar();
        ActionViewFlipper();
    }

    private void ActionViewFlipper() {
        List<String> arrQuangCao = new ArrayList<>();
        arrQuangCao.add("https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbVVHVlQ3NVRPMkVKS1VFcFp6WTRRV1RhZ1lVZ3xBQ3Jtc0ttd0l2NXJsUWVCR3dOakgwdlRwSE5SdGhoQlB5V2sxclJuUHpGUjV2UV9iVUhnMHFiOFNJTjk3UHVTYVpHOUIxbGZEcUhndFUyRldtNGdMbHRUTXEzQWVIeEF3XzZIeDZtd1lPb1ByME1LMTlOWHVzQQ&q=http%3A%2F%2Fmauweb.monamedia.net%2Fthegioididong%2Fwp-content%2Fuploads%2F2017%2F12%2Fbanner-Le-hoi-phu-kien-800-300.png&v=QAqXA_bpD5o");
        arrQuangCao.add("https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqa3FSRHAxSjVlVk1XdzZXYUlBNnhqN191VG40UXxBQ3Jtc0tsd2VUTXJpQThMdjhycDBJektjMWNyWVdfSnFUNXhJUHBVbHBNNEY1MlNvcnFBb1Bka3VVMjZST0ZPa3J0UkhZTkh2OG9PV3JYR005UWFtc1lIS01XRWdLYmkwakZQRko3ZmtPalJQRHY3VnNnWHlVcw&q=http%3A%2F%2Fmauweb.monamedia.net%2Fthegioididong%2Fwp-content%2Fuploads%2F2017%2F12%2Fbanner-HC-Tra-Gop-800-300.png&v=QAqXA_bpD5o");
        arrQuangCao.add("https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqa210NEN6LTNaN0U4bk5MdnU4b01RdlhTQ2UtQXxBQ3Jtc0tuX2s5M0FoVHpZWEFueWdYbk5WZG9XQ090SkJ6WWVyaUhPQnh4N0hqcFVRWjRORDVwWnFqVGp2d1l4T0ttWVVyY2FSQ3A5VDktZ0h6cFdienJRTzI0MDBWbnE3SjdCUV9mME5JUzFvLXMwSU9KUG9Kbw&q=http%3A%2F%2Fmauweb.monamedia.net%2Fthegioididong%2Fwp-content%2Fuploads%2F2017%2F12%2Fbanner-big-ky-nguyen-800-300.jpg&v=QAqXA_bpD5o");
        for (int i = 0; i < arrQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(arrQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    public void anhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerLayout);
    }
}