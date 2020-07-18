package com.mahmoudjoe333.alakhwhmilk.view.logic;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.mahmoudjoe333.alakhwhmilk.Model.Item_Entity;
import com.mahmoudjoe333.alakhwhmilk.R;

import java.util.ArrayList;
import java.util.List;

public class Item_Adapter extends RecyclerView.Adapter<Item_Adapter.Holder> {
    private ArrayList<Item_Entity> mlist=new ArrayList<>();
    private Resources mresources;
    public void setList(List<Item_Entity> list) {
        this.mlist=new ArrayList<>(list);
        notifyDataSetChanged();
    }
    public void setRes(Resources res)
    {
        mresources=res;
    }
    public Item_Entity getItemAt(int position)
    {
        return mlist.get(position);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Item_Entity item=mlist.get(position);
        holder.mName.setText(item.getName());
        holder.mAddress.setText(item.getAddress());
        holder.mNumber.setText(item.getNumber());
        holder.mRegain.setText(mresources.getStringArray(R.array.main_regain)[item.getRegain()]);
        holder.checkBox.setChecked(item.isDone());

        holder.mQuantity.setText(String.valueOf(item.getQuantity()));
        holder.mTime.setText(mresources.getStringArray(R.array.time)[item.getTime()]);

            holder.mDays.setText(item.getDay());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView mName, mAddress, mNumber, mRegain,mQuantity,mDays,mTime;
        public CheckBox checkBox;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mName =itemView.findViewById(R.id.item_name);
            mAddress =itemView.findViewById(R.id.item_address);
            mNumber =itemView.findViewById(R.id.item_number);
            mRegain =itemView.findViewById(R.id.item_regain);

            mQuantity=itemView.findViewById(R.id.item_quantity);
            mDays=itemView.findViewById(R.id.item_days);
            mTime=itemView.findViewById(R.id.item_time);
            checkBox=itemView.findViewById(R.id.item_checkbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(mListener!=null&&position!=RecyclerView.NO_POSITION)
                    {
                        mListener.OnClick(mlist.get(position));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=getAdapterPosition();
                    if(mListener!=null&&position!=RecyclerView.NO_POSITION)
                    {
                        mListener.OnitemDelete(mlist.get(position));
                    }
                    return false;
                }
            });
        }
    }
    private OnItemClickListener mListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }
    public interface OnItemClickListener{
        void OnClick(Item_Entity item);

        void OnitemDelete(Item_Entity item);
    }
}
