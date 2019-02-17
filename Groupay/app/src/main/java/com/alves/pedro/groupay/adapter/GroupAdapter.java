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

    private boolean mHorizontal;

    public GroupAdapter(List<Group> groups, Context context, boolean horizontal) {
        this.mGroupList = groups;
        this.context = context;
        this.mHorizontal = horizontal;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(mHorizontal ? R.layout.groupay_item_cell : R.layout.groupay_item_cell_vertical, parent, false);
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

    public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView groupName;

        GroupViewHolder(View view) {
            super(view);
            groupName = view.findViewById(R.id.tvItemName);
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
