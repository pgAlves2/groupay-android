package com.alves.pedro.groupay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.alves.pedro.groupay.model.Invoice;

public class InvoiceActivity extends AppCompatActivity {

    public static final String INVOICE_PARAM = "INVOICE_PARAM";

    private Invoice mInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        if (!getIntent().hasExtra(INVOICE_PARAM))
            return;

        mInvoice = (Invoice) getIntent().getSerializableExtra(INVOICE_PARAM);

        TextView tvInvoiceName = findViewById(R.id.tvGroupName);
        tvInvoiceName.setText(mInvoice.getName());
    }
}
