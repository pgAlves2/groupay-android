package com.alves.pedro.groupay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alves.pedro.groupay.R;
import com.alves.pedro.groupay.model.Invoice;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    private List<Invoice> mInvoiceList;

    private Context context;

    private ItemClickListener clickListener;

    public InvoiceAdapter(List<Invoice> invoices, Context context) {
        this.mInvoiceList = invoices;
        this.context = context;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.groupay_item_cell, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        Invoice invoice = mInvoiceList.get(position);
        holder.invoiceName.setText(invoice.getName());
    }

    @Override
    public int getItemCount() {
        return mInvoiceList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView invoiceName;

        InvoiceViewHolder(View view) {
            super(view);
            invoiceName = view.findViewById(R.id.tvItemName);
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
