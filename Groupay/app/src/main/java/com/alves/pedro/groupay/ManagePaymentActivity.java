package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alves.pedro.groupay.adapter.SplitAdapter;
import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.Invoice;
import com.alves.pedro.groupay.model.Split;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class ManagePaymentActivity extends AppCompatActivity {

    public static final String INVOICE_PARAM = "INVOICE_PARAM";

    public static final String USER_PARAM = "USER_PARAM";

    private ProgressBar mProgressBar;
    private RecyclerView mRvSplits;
    private Button mBtnPay;

    private Invoice mInvoice;

    private User mUser;

    private Split mSplitAtual;

    private PaymentHandler mHandler;

    private boolean mRunThread;
    private boolean mCanAskInvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_payment);

        setResult(RESULT_OK);

        if (!getIntent().hasExtra(INVOICE_PARAM))
            return;

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mHandler = new PaymentHandler();

        mInvoice = (Invoice) getIntent().getSerializableExtra(INVOICE_PARAM);
        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);

        mProgressBar = findViewById(R.id.pbLoading);
        mRvSplits = findViewById(R.id.rvSplits);
        mBtnPay = findViewById(R.id.btnPay);
        mBtnPay.setOnClickListener(v -> {
            Utils.showProgressBar(mProgressBar);
            mBtnPay.setEnabled(false);
            APIController.getInstance().payInvoiceCard(mInvoice, mUser, this, mHandler);
        });

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(mInvoice.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.showProgressBar(mProgressBar);
        mCanAskInvoice = true;
        mRunThread = true;
        askInvoiceData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRunThread = false;
    }

    @SuppressLint("HandlerLeak")
    class PaymentHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    if (msg.obj instanceof User) {
                        mSplitAtual.setUser((User) msg.obj);
                        if (mInvoice.getSplitList().indexOf(mSplitAtual) == mInvoice.getSplitList().size() - 1) {
                            loadData();
                            Utils.hideProgressBar(mProgressBar);
                            mCanAskInvoice = true;
                        } else {
                            mSplitAtual = mInvoice.getSplitList().get(mInvoice.getSplitList().indexOf(mSplitAtual) + 1);
                            APIController.getInstance().getUser(mSplitAtual.getUserId(), ManagePaymentActivity.this, mHandler);
                        }
                    } else if (msg.obj instanceof Invoice) {
                        mInvoice = (Invoice) msg.obj;
                        if (mInvoice.getSplitList() == null) {
                            Utils.hideProgressBar(mProgressBar);
                            return;
                        }
                        mSplitAtual = mInvoice.getSplitList().get(0);
                        APIController.getInstance().getUser(mSplitAtual.getUserId(), ManagePaymentActivity.this, mHandler);
                    }
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    Utils.hideProgressBar(mProgressBar);
                    break;
                default:
                    break;
            }
        }
    }

    private void loadData() {
        SplitAdapter splitAdapter = new SplitAdapter(mInvoice.getSplitList(), this);
        mRvSplits.setAdapter(splitAdapter);

        boolean showPayButton = true;
        for (Split split : mInvoice.getSplitList())
            showPayButton = showPayButton && split.isPaid();
        mBtnPay.setVisibility(showPayButton ? View.VISIBLE : View.GONE);
    }

    private void askInvoiceData() {
        //Deveria ser um Service :`(  ---- Deveria ser um FireBase :`(((((
        (new Thread() {
            @Override
            public void run() {
                super.run();
                while (mRunThread) {
                    if (mCanAskInvoice) {
                        mCanAskInvoice = false;
                        APIController.getInstance().getInvoice(mInvoice, ManagePaymentActivity.this, mHandler);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
