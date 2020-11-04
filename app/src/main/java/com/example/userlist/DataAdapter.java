package com.example.userlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{
    Context context;
    List<DataBean> list;

    public DataAdapter(Context context, List<DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getAvatar()).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        holder.email_nametv.setText(list.get(position).getEmail());
        holder.first_nametv.setText(list.get(position).getFirst_name());
        holder.last_nametv.setText(list.get(position).getLast_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView email_nametv,last_nametv,first_nametv;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            email_nametv = itemView.findViewById(R.id.email_nametv);
            last_nametv = itemView.findViewById(R.id.last_nametv);
            first_nametv = itemView.findViewById(R.id.first_nametv);
            imageView = itemView.findViewById(R.id.profile_image);
        }
    }
}
