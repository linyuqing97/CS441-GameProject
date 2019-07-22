package com.example.myfungame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<userInfo> userRank;

    public RecycleAdapter(ArrayList<userInfo>userRank) {
        this.userRank = userRank;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_board_format,parent,false);
        Item itemHolder = new Item(row);
        return itemHolder;
}

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        userInfo object = userRank.get(position);

        ((Item)holder).pointView.setText(Integer.toString(object.point));
        ((Item)holder).nameView.setText(object.name);


    }

    @Override
    public int getItemCount() {
        return userRank.size();
    }
    public class Item extends  RecyclerView.ViewHolder {
        TextView nameView;
        TextView pointView;

        public Item(View itemView) {
            super(itemView);
            pointView= (TextView)itemView.findViewById(R.id.pointView);
            nameView = (TextView)itemView.findViewById(R.id.nameView);

        }

    }
}
