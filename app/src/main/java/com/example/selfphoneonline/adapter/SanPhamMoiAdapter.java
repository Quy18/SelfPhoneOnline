package com.example.selfphoneonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.selfphoneonline.Interface.ItemClickListener;
import com.example.selfphoneonline.R;
import com.example.selfphoneonline.activity.ChiTietActivity;
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
        holder.setItemClickListener((view, pos, isLongClick) -> {
            if (!isLongClick){
                Intent intent = new Intent(context, ChiTietActivity.class);
                intent.putExtra("chitiet",sanPhamMoi);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrSanPhamMoi.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenSP, txtGiaSP;
        ImageView imgSP;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.itemsp_tensp);
            txtGiaSP = itemView.findViewById(R.id.itemsp_giasp);
            imgSP = itemView.findViewById(R.id.itemsp_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
