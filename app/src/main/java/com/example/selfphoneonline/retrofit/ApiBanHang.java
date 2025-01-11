package com.example.selfphoneonline.retrofit;



import com.example.selfphoneonline.module.LoaiSpModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
}
