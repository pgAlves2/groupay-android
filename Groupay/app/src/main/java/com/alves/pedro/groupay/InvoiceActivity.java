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
    private static final int REQUEST_MANAGMENT = 2;

    private TextView mTvInvoiceName;
    private TextView mTvInvoiceDateValue;
    private TextView mTvStatusValue;
    private TextView mTvValueValue;
    private ProgressBar mProgressBar;
    private Button mBtnDivideInvoice;
    private Button mBtnPayInvoice;
    private Button mBtnManagePayments;

    private User mUser;

    private Invoice mInvoice;

    private InvoiceRegisterHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        mHandler = new InvoiceRegisterHandler();

        if (!getIntent().hasExtra(INVOICE_PARAM))
            return;

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mInvoice = (Invoice) getIntent().getSerializableExtra(INVOICE_PARAM);
        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);

        mTvInvoiceName = findViewById(R.id.tvInvoiceName);
        mTvInvoiceDateValue = findViewById(R.id.tvInvoiceDateValue);
        mTvStatusValue = findViewById(R.id.tvStatusValue);
        mTvValueValue = findViewById(R.id.tvValueValue);

        mProgressBar = findViewById(R.id.pbLoading);
        mBtnDivideInvoice = findViewById(R.id.btnDivideInvoice);
        mBtnDivideInvoice.setOnClickListener(v -> {
            Intent groupListIntent = new Intent(this, GroupListActivity.class);
            groupListIntent.putExtra(GroupListActivity.USER_PARAM, mUser);
            startActivityForResult(groupListIntent, REQUEST_GROUP);
        });

        mBtnManagePayments = findViewById(R.id.btnManagePayment);
        mBtnManagePayments.setOnClickListener(v -> {
            Intent managePaymentIntent = new Intent(this, ManagePaymentActivity.class);
            managePaymentIntent.putExtra(ManagePaymentActivity.USER_PARAM, mUser);
            managePaymentIntent.putExtra(ManagePaymentActivity.INVOICE_PARAM, mInvoice);
            startActivityForResult(managePaymentIntent, REQUEST_MANAGMENT);
        });

        mBtnPayInvoice = findViewById(R.id.btnPayInvoice);
        mBtnPayInvoice.setOnClickListener(v -> {
            Utils.showProgressBar(mProgressBar);
            mBtnPayInvoice.setEnabled(false);
            APIController.getInstance().payInvoiceCard(mInvoice, mUser, this, mHandler);
        });

        loadData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == REQUEST_GROUP) {
            if (data.hasExtra(GroupListActivity.GROUP_OUT_PARAM)) {
                Utils.showProgressBar(mProgressBar);

                Group group = (Group) data.getSerializableExtra(GroupListActivity.GROUP_OUT_PARAM);
                mInvoice.setGroupID(group.getId());
                APIController.getInstance().updateInvoice(mInvoice, this, mHandler);
            }
        } else if (requestCode == REQUEST_MANAGMENT) {
            Utils.showProgressBar(mProgressBar);
            APIController.getInstance().getInvoice(mInvoice, InvoiceActivity.this, mHandler);
        }
    }

    @SuppressLint("HandlerLeak")
    class InvoiceRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            mBtnPayInvoice.setEnabled(true);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    if (msg.obj == null) {
                        Utils.showProgressBar(mProgressBar);
                        APIController.getInstance().getInvoice(mInvoice, InvoiceActivity.this, mHandler);
                    } else {
                        mInvoice = (Invoice) msg.obj;
                        loadData();
                    }
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadData() {
        mTvInvoiceName.setText(mInvoice.getName());
        mTvInvoiceDateValue.setText(Utils.getReadableDate(mInvoice.getDueDate()));
        mTvStatusValue.setText(mInvoice.isPaid() ? getString(R.string.paid) : getString(R.string.notPaid));
        mTvValueValue.setText("R$ " + String.valueOf(mInvoice.getValue()));
        if (mInvoice.getGroupID() == null) {
            if (!mInvoice.isPaid()) {
                mBtnDivideInvoice.setVisibility(View.VISIBLE);
                mBtnPayInvoice.setVisibility(View.VISIBLE);
            } else {
                mBtnDivideInvoice.setVisibility(View.GONE);
                mBtnPayInvoice.setVisibility(View.GONE);
            }
            mBtnManagePayments.setVisibility(View.GONE);
        } else {
            mBtnDivideInvoice.setVisibility(View.GONE);
            mBtnPayInvoice.setVisibility(View.GONE);
            mBtnManagePayments.setVisibility(View.VISIBLE);
        }
    }
}
