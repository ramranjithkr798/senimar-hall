package com.example.ramra.seminarhall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapater_layout extends RecyclerView.Adapter<Adapater_layout.RecyclerViewHolder> {

    ArrayList name_scr,Image_src;
    Context context;
    OnClickHall onClickHall;
     public Adapater_layout(ArrayList name_scr,ArrayList Image_src,Context context,OnClickHall onClickHall){
     this.name_scr=name_scr;
     this.Image_src=Image_src;
     this.context=context;
     this.onClickHall=onClickHall;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_for_list_seminar_hall,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view,onClickHall);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.tx_name.setText(name_scr.get(i).toString());
        Picasso.with(context).load(Image_src.get(i).toString()).fit().centerCrop().into(recyclerViewHolder.Img_src);
    }

    @Override
    public int getItemCount() {
        return name_scr.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tx_name;
        ImageView Img_src;
        OnClickHall onClickHall;
        public RecyclerViewHolder(View view,OnClickHall onClickHall)
        {
            super(view);
            tx_name=view.findViewById(R.id.textView);
            Img_src=view.findViewById(R.id.ImageView);
            this.onClickHall=onClickHall;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
         onClickHall.OnClick(getAdapterPosition());
        }
    }
    public interface OnClickHall{
        void OnClick(int position);
    }
}
