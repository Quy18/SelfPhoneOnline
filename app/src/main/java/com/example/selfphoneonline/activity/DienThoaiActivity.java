package com.example.selfphoneonline.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfphoneonline.R;
import com.example.selfphoneonline.adapter.DienThoaiAdapter;
import com.example.selfphoneonline.model.SanPhamMoi;
import com.example.selfphoneonline.retrofit.ApiBanHang;
import com.example.selfphoneonline.retrofit.RetrofitClient;
import com.example.selfphoneonline.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int loai;
    DienThoaiAdapter adapterDt;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEvenLoad();
    }

    private void addEvenLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false){
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == sanPhamMoiList.size() - 1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(() -> {
            sanPhamMoiList.add(null);
            adapterDt.notifyItemInserted(sanPhamMoiList.size() - 1);
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
                sanPhamMoiList.remove(sanPhamMoiList.size() - 1);
                adapterDt.notifyItemRemoved(sanPhamMoiList.size());
                page = page + 1;
                getData(page);
                adapterDt.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiBanHang.getSanPhamTheoDanhMuc(page, loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                if (adapterDt == null) {
                                    sanPhamMoiList = sanPhamMoiModel.getResult();
                                    adapterDt = new DienThoaiAdapter(getApplicationContext(), sanPhamMoiList);
                                    recyclerView.setAdapter(adapterDt);
                                }else{
                                    int vitri = sanPhamMoiList.size() - 1;
                                    int soluongadd = sanPhamMoiModel.getResult().size();
                                    for (int i = 0; i < soluongadd; i++){
                                        sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                    }
                                    adapterDt.notifyItemRangeInserted(vitri,soluongadd);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Hết dữ liệu.",Toast.LENGTH_SHORT).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được với server!",Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleview_dt);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}