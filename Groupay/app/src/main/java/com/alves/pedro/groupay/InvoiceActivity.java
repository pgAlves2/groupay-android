package com.alves.pedro.groupay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alves.pedro.groupay.model.Invoice;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class InvoiceActivity extends AppCompatActivity {

    public static final String INVOICE_PARAM = "INVOICE_PARAM";

    public static final String USER_PARAM = "USER_PARAM";

    private User mUser;

    private Invoice mInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        if (!getIntent().hasExtra(INVOICE_PARAM))
            return;

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mInvoice = (Invoice) getIntent().getSerializableExtra(INVOICE_PARAM);
        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);

        TextView tvInvoiceName = findViewById(R.id.tvInvoiceName);
        tvInvoiceName.setText(mInvoice.getName());

        TextView tvInvoiceDateValue = findViewById(R.id.tvInvoiceDateValue);
        tvInvoiceDateValue.setText(Utils.getReadableDate(mInvoice.getDueDate()));

        TextView tvStatusValue = findViewById(R.id.tvStatusValue);
        tvStatusValue.setText(mInvoice.isPaid() ? getString(R.string.paid) : getString(R.string.notPaid));

        if (mInvoice.getGroupID() == null) {
            Button btnDivideInvoice = findViewById(R.id.btnDivideInvoice);
            btnDivideInvoice.setOnClickListener(v -> {
                Intent groupListIntent = new Intent(this, GroupListActivity.class);
                groupListIntent.putExtra(GroupListActivity.USER_PARAM, mUser);
                startActivity(groupListIntent);
            });
            btnDivideInvoice.setVisibility(View.VISIBLE);
        }
    }
}
