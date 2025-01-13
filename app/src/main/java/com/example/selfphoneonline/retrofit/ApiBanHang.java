package com.example.selfphoneonline.retrofit;



import com.example.selfphoneonline.model.LoaiSpModel;
import com.example.selfphoneonline.model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getcategory.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSanPhamMoi();
}
