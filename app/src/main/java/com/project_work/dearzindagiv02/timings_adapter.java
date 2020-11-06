package com.project_work.dearzindagiv02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

public class timings_adapter extends RecyclerView.Adapter<timings_adapter.ViewHolder> {
    private ArrayList<timings> time;
    ItemSelected activity;
    interface ItemSelected
    {
        void onItemClicked(int index);
        void onDeleteClick(int index);
    }
    public timings_adapter(Context context, ArrayList<timings> list)
    {
        time=list;
        activity=(ItemSelected) context;
    }
       
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shows_time,out_name,out_numoftimes,out_expiry,content;
        ImageView out_del_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            out_name=itemView.findViewById(R.id.out_name);
            content=itemView.findViewById(R.id.content);
            out_numoftimes=itemView.findViewById(R.id.out_num0ftimes);
            out_expiry=itemView.findViewById(R.id.out_expiry);
            shows_time=itemView.findViewById(R.id.shows_time);
            out_del_btn=itemView.findViewById(R.id.out_del_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(time.indexOf((timings)v.getTag()));
                }
            });
            out_del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                   //------Code=1(Check HomeActivity)------
                    activity.onDeleteClick(time.indexOf((timings)v.getTag()));
                    }
            });
        }
    }

    @NonNull
    @Override
    public timings_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_list_layout,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull timings_adapter.ViewHolder holder, final int position){
        holder.itemView.setTag(time.get(position));
        holder.shows_time.setText(time.get(position).getTime());
        holder.content.setText(time.get(position).getContent());
        holder.out_name.setText(time.get(position).getName());
        holder.out_numoftimes.setText(time.get(position).getNumoftimes());
        holder.out_expiry.setText(time.get(position).getExpiry());
    }

    @Override
    public int getItemCount() {
        return time.size();
    }
}


