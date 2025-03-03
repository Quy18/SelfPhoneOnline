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
import com.example.selfphoneonline.model.ItemSp;

import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {
    Context context;
    List<ItemSp> listchitiet;

    public ChiTietAdapter(Context context, List<ItemSp> listchitiet) {
        this.context = context;
        this.listchitiet = listchitiet;
    }

    public ChiTietAdapter(List<ItemSp> listchitiet) {
        this.listchitiet = listchitiet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemSp itemSp = listchitiet.get(position);
        holder.txtten.setText(itemSp.getTensp() + "");
        holder.txtsoluong.setText("Số lượng: " + itemSp.getSoluong());
        Glide.with(context).load(itemSp.getHinhanh()).into(holder.imgchitiet);
    }

    @Override
    public int getItemCount() {
        return listchitiet.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgchitiet;
        TextView txtten, txtsoluong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgchitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
