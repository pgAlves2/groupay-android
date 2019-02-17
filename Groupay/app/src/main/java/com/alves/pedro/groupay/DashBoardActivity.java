package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alves.pedro.groupay.adapter.GroupAdapter;
import com.alves.pedro.groupay.adapter.InvoiceAdapter;
import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.Invoice;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.SharedPreferencesUtils;
import com.alves.pedro.groupay.utils.Utils;

public class DashBoardActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";
    public static final String CREDIT_CARD_PARAM = "CREDIT_CARD_PARAM";

    private TextView mTvUserName;
    private TextView mTvBalanceValue;
    private RecyclerView mRvGroups;
    private RecyclerView mRvMyInvoices;
    private RecyclerView mRvLinkedInvoices;
    private ProgressBar mProgressBar;

    private User mUser;
    private CreditCard mCreditCard;
    private UserRegisterHandler mHandler;

    private boolean mRunThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mHandler = new UserRegisterHandler();

        if (getIntent().hasExtra(USER_PARAM)) {
            mUser = (User) getIntent().getSerializableExtra(USER_PARAM);
            if (getIntent().hasExtra(CREDIT_CARD_PARAM))
                mCreditCard = (CreditCard) getIntent().getSerializableExtra(CREDIT_CARD_PARAM);
        } else {
            SharedPreferences sharedPreferences = SharedPreferencesUtils.getSharedPreferences(this);
            mUser = SharedPreferencesUtils.getUserPreference(sharedPreferences);
            mCreditCard = SharedPreferencesUtils.getCreditCardPreference(sharedPreferences);
        }

        if (mUser == null || mCreditCard == null)
            return;


        mTvUserName = findViewById(R.id.tvUserName);
        mTvBalanceValue = findViewById(R.id.tvBalanceValue);
        mProgressBar = findViewById(R.id.pbLoading);

        RecyclerView.LayoutManager layoutRvGroups = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvGroups = findViewById(R.id.rvGroups);
        mRvGroups.setLayoutManager(layoutRvGroups);

        RecyclerView.LayoutManager layoutRvMyInvoices = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvMyInvoices = findViewById(R.id.rvMyInvoices);
        mRvMyInvoices.setLayoutManager(layoutRvMyInvoices);

        RecyclerView.LayoutManager layoutRvLinkedInvoices = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvLinkedInvoices = findViewById(R.id.rvLinkedInvoices);
        mRvLinkedInvoices.setLayoutManager(layoutRvLinkedInvoices);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRunThread = true;
        askUserData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRunThread = false;
    }

    @SuppressLint("HandlerLeak")
    class UserRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    mUser = (User) msg.obj;
                    mUser.setCreditCard(mCreditCard);
                    loadUserData();
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    break;
                default:
                    break;
            }
        }
    }

    private void askUserData() {
        //Deveria ser um Service :`(  ---- Deveria ser um FireBase :`(((((
        (new Thread() {
            @Override
            public void run() {
                super.run();
                while (mRunThread) {
                    APIController.getInstance().getFullUser(mUser, DashBoardActivity.this, mHandler);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void loadUserData() {
        mTvUserName.setText(mUser.getName());
        mTvBalanceValue.setText(mUser.getBalance());

        GroupAdapter groupAdapter = new GroupAdapter(mUser.getGroupList(), this, true);
        groupAdapter.setClickListener((view, position) -> {

        });
        mRvGroups.setAdapter(groupAdapter);

        InvoiceAdapter myInvocesAdapter = new InvoiceAdapter(mUser.getMyInvoiceList(), this);
        myInvocesAdapter.setClickListener((view, position) -> {
            Invoice invoice = mUser.getMyInvoiceList().get(position);
            Intent invoiceIntent = new Intent(this, InvoiceActivity.class);
            invoiceIntent.putExtra(InvoiceActivity.INVOICE_PARAM, invoice);
            invoiceIntent.putExtra(InvoiceActivity.USER_PARAM, mUser);
            startActivity(invoiceIntent);
        });
        mRvMyInvoices.setAdapter(myInvocesAdapter);

        InvoiceAdapter linkedInvocesAdapter = new InvoiceAdapter(mUser.getLinkedInvoiceList(), this);
        linkedInvocesAdapter.setClickListener((view, position) -> {
            Invoice invoice = mUser.getLinkedInvoiceList().get(position);
            Intent invoiceIntent = new Intent(this, InvoiceActivity.class);
            invoiceIntent.putExtra(InvoiceActivity.INVOICE_PARAM, invoice);
            invoiceIntent.putExtra(InvoiceActivity.USER_PARAM, mUser);
            startActivity(invoiceIntent);
        });
        mRvLinkedInvoices.setAdapter(linkedInvocesAdapter);
    }

}
