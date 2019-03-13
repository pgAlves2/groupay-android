package com.alves.pedro.groupay.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alves.pedro.groupay.R;
import com.alves.pedro.groupay.model.Split;

import java.util.List;

public class SplitAdapter extends RecyclerView.Adapter<SplitAdapter.SplitViewHolder> {

    private List<Split> mSplitList;

    private Context context;

    private ItemClickListener clickListener;

    public SplitAdapter(List<Split> splits, Context context) {
        this.mSplitList = splits;
        this.context = context;
    }

    @NonNull
    @Override
    public SplitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.groupay_item_cell_vertical_with_check, parent, false);
        return new SplitViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SplitViewHolder holder, int position) {
        Split split = mSplitList.get(position);
        holder.userName.setText(split.getUser().getName());
        holder.userValue.setText("R$ " + String.valueOf(split.getValue()));
        holder.paid.setChecked(split.isPaid());
    }

    @Override
    public int getItemCount() {
        return mSplitList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class SplitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView userName;
        final TextView userValue;
        final CheckBox paid;

        SplitViewHolder(View view){
            super(view);
            userName = view.findViewById(R.id.tvItemName);
            userValue = view.findViewById(R.id.tvItemValue);
            paid = view.findViewById(R.id.cbItemSelected);
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
