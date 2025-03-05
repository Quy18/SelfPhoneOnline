package com.example.selfphoneonline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.selfphoneonline.Interface.ImageClickListenner;
import com.example.selfphoneonline.R;
import com.example.selfphoneonline.model.EventBus.TinhTongEvent;
import com.example.selfphoneonline.model.GioHang;
import com.example.selfphoneonline.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong() + " ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format(gioHang.getGiasp()));
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia) + "Đ");
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                Utils.mangmuahang.add(gioHang);
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }else{
                for(int i = 0; i < Utils.mangmuahang.size(); i++){
                    if(Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                        Utils.mangmuahang.remove(i);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            }
        });
        holder.setListenner((view, position1, giatri) -> {
            if(giatri == 1){
                if(gioHangList.get(position).getSoluong() > 1){
                    int soluongmoi = gioHangList.get(position).getSoluong() - 1;
                    gioHangList.get(position).setSoluong(soluongmoi);

                    holder.item_giohang_soluong.setText(gioHangList.get(position).getSoluong() + " ");
                    long gia1 = gioHangList.get(position).getSoluong() * gioHangList.get(position).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia1) + "Đ");
                    // Sử dụng thư viện để đẩy sử kiện click cong-tru lên GioHangActivity. vì tổng total nằm ở bên GioHangActivity
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }else if(gioHangList.get(position).getSoluong() == 1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo.");
                    builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không ?");
                    builder.setPositiveButton("Có", (dialogInterface, i) -> {
                        if(!Utils.mangmuahang.isEmpty()){
                            for(int j = 0; j < Utils.mangmuahang.size(); j++){
                                if(Utils.mangmuahang.get(j).getIdsp() == Utils.manggiohang.get(position).getIdsp()){
                                    Utils.mangmuahang.remove(j);
                                }
                            }
                        }
                        Utils.manggiohang.remove(position);
                        notifyDataSetChanged();
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    });
                    builder.setNegativeButton("Hủy", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });
                    builder.show();
                }
            }else if(giatri == 2){
                if (gioHangList.get(position).getSoluong() < 10){
                    int soluongmoi = gioHangList.get(position).getSoluong() + 1;
                    gioHangList.get(position).setSoluong(soluongmoi);
                }
                holder.item_giohang_soluong.setText(gioHangList.get(position).getSoluong() + " ");
                long gia1 = gioHangList.get(position).getSoluong() * gioHangList.get(position).getGiasp();
                holder.item_giohang_giasp2.setText(decimalFormat.format(gia1) + "Đ");
                // Sử dụng thư viện để đẩy sử kiện click cong-tru lên GioHangActivity. vì tổng total nằm ở bên GioHangActivity
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }

        });
    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        ImageView imgtru, imgcong;
        ImageClickListenner listenner;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);

            //even click cong, tru
            imgtru.setOnClickListener(this);
            imgcong.setOnClickListener(this);
        }

        private void setListenner(ImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                listenner.onImageClick(view, getAdapterPosition(), 1);
            }else if(view == imgcong){
                listenner.onImageClick(view, getAdapterPosition(), 2);
            }
        }
    }
}
