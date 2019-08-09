package com.example.myfungame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<UserInfo> userRank;

    public RecycleAdapter(ArrayList<UserInfo>userRank) {
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
        UserInfo object = userRank.get(position);

        ((Item)holder).pointView.setText(Integer.toString(object.getPoint()));
        ((Item)holder).nameView.setText(object.getName());
        ((Item)holder).gameView.setText(object.getGame());



    }

    @Override
    public int getItemCount() {
        return userRank.size();
    }
    public class Item extends  RecyclerView.ViewHolder {
        TextView nameView;
        TextView pointView;
        TextView gameView;

        public Item(View itemView) {
            super(itemView);
            pointView= (TextView)itemView.findViewById(R.id.pointView);
            nameView = (TextView)itemView.findViewById(R.id.nameView);
            gameView = (TextView)itemView.findViewById(R.id.gameView);


        }

    }
}
