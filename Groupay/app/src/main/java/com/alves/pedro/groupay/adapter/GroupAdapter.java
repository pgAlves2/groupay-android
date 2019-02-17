package com.alves.pedro.groupay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alves.pedro.groupay.R;
import com.alves.pedro.groupay.model.Group;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private List<Group> mGroupList;

    private Context context;

    private ItemClickListener clickListener;

    public GroupAdapter(List<Group> groups, Context context) {
        this.mGroupList = groups;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_cell, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = mGroupList.get(position);
        holder.groupName.setText(group.getName());
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView groupName;

        GroupViewHolder(View view) {
            super(view);
            groupName = view.findViewById(R.id.tvGroupName);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onItemClick(view, getAdapterPosition());
        }

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }


}
