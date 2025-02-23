package com.example.selfphoneonline.retrofit;



import com.example.selfphoneonline.model.LoaiSpModel;
import com.example.selfphoneonline.model.SanPhamMoiModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getcategory.php")
    Observable<LoaiSpModel> getLoaiSp();

    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSanPhamMoi();

    @POST("getSpTheoDanhMuc.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getSanPhamTheoDanhMuc(
            @Field("page") int page,
            @Field("loai") int loai
    );
}
