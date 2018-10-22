package com.example.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.terminal.R;

import java.util.List;

/**
 * Created by Administrator on 2018/9/13.
 */

public class DoorAdapter extends RecyclerView.Adapter<DoorAdapter.ViewHolder> {
   private List<DoorBean> mList;
    public DoorAdapter(List<DoorBean> list) {
        mList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.door_detail_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.content.setText(mList.get(position).getDoorAction());
         holder.time.setText(mList.get(position).getActionTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
