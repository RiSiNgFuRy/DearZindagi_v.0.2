package com.project_work.dearzindagiv02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class timings_adapter extends RecyclerView.Adapter<timings_adapter.ViewHolder> {
    private ArrayList<timings> time;
    ItemSelected activity;
    interface ItemSelected
    {
        void onItemClicked(int index);
    }
    public timings_adapter(Context context, ArrayList<timings> list)
    {
        time=list;
        activity=(ItemSelected) context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView shows_time,shows_list;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shows_list=itemView.findViewById(R.id.shows_list);
            shows_time=itemView.findViewById(R.id.shows_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //activity.onItemClicked(time.indexOf(timings)v.getTag());
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
    public void onBindViewHolder(@NonNull timings_adapter.ViewHolder holder, int position){
        holder.itemView.setTag(time.get(position));
        holder.shows_time.setText(time.get(position).getTime());
        holder.shows_list.setText(time.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return time.size();
    }
}


