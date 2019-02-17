package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.Group;
import com.alves.pedro.groupay.model.Invoice;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class InvoiceActivity extends AppCompatActivity {

    public static final String INVOICE_PARAM = "INVOICE_PARAM";

    public static final String USER_PARAM = "USER_PARAM";

    private static final int REQUEST_GROUP = 1;

    private ProgressBar mProgressBar;
    private Button mBtnDivideInvoice;

    private User mUser;

    private Invoice mInvoice;

    private InvoiceRegisterHandler mHandler;

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

        mProgressBar = findViewById(R.id.pbLoading);

        mBtnDivideInvoice = findViewById(R.id.btnDivideInvoice);

        if (mInvoice.getGroupID() == null) {
            mBtnDivideInvoice.setOnClickListener(v -> {
                Intent groupListIntent = new Intent(this, GroupListActivity.class);
                groupListIntent.putExtra(GroupListActivity.USER_PARAM, mUser);
                startActivityForResult(groupListIntent, REQUEST_GROUP);
            });
            mBtnDivideInvoice.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;
        if (requestCode == REQUEST_GROUP && resultCode == RESULT_OK) {
            if (data.hasExtra(GroupListActivity.GROUP_OUT_PARAM)) {
                if (mHandler == null)
                    mHandler = new InvoiceRegisterHandler();

                Utils.showProgressBar(mProgressBar);

                Group group = (Group) data.getSerializableExtra(GroupListActivity.GROUP_OUT_PARAM);
                mInvoice.setGroupID(group.getId());
                APIController.getInstance().updateInvoice(mInvoice, this, mHandler);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    class InvoiceRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    mBtnDivideInvoice.setVisibility(View.INVISIBLE);
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    break;
                default:
                    break;
            }
        }
    }
}
