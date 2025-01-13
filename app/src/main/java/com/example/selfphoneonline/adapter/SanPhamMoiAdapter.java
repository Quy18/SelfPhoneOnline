package com.example.selfphoneonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.selfphoneonline.R;
import com.example.selfphoneonline.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> arrSanPhamMoi;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> arrSanPhamMoi) {
        this.context = context;
        this.arrSanPhamMoi = arrSanPhamMoi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spmoi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = arrSanPhamMoi.get(position);
        holder.txtTenSP.setText(sanPhamMoi.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText("Giá: " + decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp())) + "đ");
        Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imgSP);
    }

    @Override
    public int getItemCount() {
        return arrSanPhamMoi.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSP, txtGiaSP;
        ImageView imgSP;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.itemsp_tensp);
            txtGiaSP = itemView.findViewById(R.id.itemsp_giasp);
            imgSP = itemView.findViewById(R.id.itemsp_image);
        }
    }
}
