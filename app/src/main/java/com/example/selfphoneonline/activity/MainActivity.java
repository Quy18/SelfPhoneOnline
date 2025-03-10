package com.example.selfphoneonline.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.selfphoneonline.R;
import com.example.selfphoneonline.adapter.LoaiSpAdapter;
import com.example.selfphoneonline.adapter.SanPhamMoiAdapter;
import com.example.selfphoneonline.model.LoaiSp;
import com.example.selfphoneonline.model.SanPhamMoi;
import com.example.selfphoneonline.model.User;
import com.example.selfphoneonline.retrofit.ApiBanHang;
import com.example.selfphoneonline.retrofit.RetrofitClient;
import com.example.selfphoneonline.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> arrLoaiSp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    List<SanPhamMoi> arrSpMoi;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imgsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        Paper.init(this);
        if(Paper.book().read("user") != null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        AnhXa();
        ActionBar();

        if (isConnected(this)){
            ActionViewFlipper();
            // Lấy dữ liệu từ API để hiển thị category
            getCategory();
            // Hiển thị sản phẩm mới
            getSanPhamMoi();
            // Xử lý sự kiện click item category
            getEvenClick();
        }else {
            Toast.makeText(getApplicationContext(), "Không có internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEvenClick() {
        listViewManHinhChinh.setOnItemClickListener((adapterView, view, i, l) -> {
            switch (i){
                case 0:
                    Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(trangchu);
                    break;
                case 1:
                    Intent dienthoai = new Intent(getApplicationContext(), DienThoaiActivity.class);
                    dienthoai.putExtra("loai",1);
                    startActivity(dienthoai);
                    break;
                case 2:
                    // Sử dụng lại DienThoaiActivity vì giống nhau
                    Intent laptop = new Intent(getApplicationContext(), DienThoaiActivity.class);
                    laptop.putExtra("loai",2);
                    startActivity(laptop);
                    break;
                case 5:
                    Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
                    startActivity(donhang);
                    break;
                case 6:
                    // Xoa key user
                    Paper.book().delete("user");
                    Intent dangnhap = new Intent(getApplicationContext(), DangNhapActivity.class);
                    startActivity(dangnhap);
                    break;
                default:
            }
        });
    }

    private void getSanPhamMoi() {
        compositeDisposable.add(apiBanHang.getSanPhamMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                arrSpMoi = sanPhamMoiModel.getResult();
                                sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(), arrSpMoi);
                                recyclerViewManHinhChinh.setAdapter(sanPhamMoiAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được với server!" + throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void getCategory() {
        compositeDisposable.add(apiBanHang.getLoaiSp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        loaiSpModel -> {
                            if (loaiSpModel.isSuccess()) {
                                arrLoaiSp = loaiSpModel.getResult();
                                arrLoaiSp.add(new LoaiSp("Đăng xuất",""));
                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), arrLoaiSp);
                                listViewManHinhChinh.setAdapter(loaiSpAdapter);
                            }
                        }
                )
        );
    }

    private void ActionViewFlipper() {
        List<String> arrQuangCao = new ArrayList<>();
        arrQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        arrQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png");
        arrQuangCao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg");

        for (int i = 0; i < arrQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext())
                    .load(arrQuangCao.get(i))
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    private void AnhXa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewflipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        navigationView = findViewById(R.id.navigationview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerLayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        imgsearch = findViewById(R.id.imgsearch);

        // Khởi tạo danh sách sản phẩm
        arrLoaiSp = new ArrayList<>();
        arrSpMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for(int i = 0; i < Utils.manggiohang.size(); i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }

        frameLayout.setOnClickListener(view -> {
           Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
           startActivity(giohang);
        });

        imgsearch.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totalItem = 0;
        for(int i = 0; i < Utils.manggiohang.size(); i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    private boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}