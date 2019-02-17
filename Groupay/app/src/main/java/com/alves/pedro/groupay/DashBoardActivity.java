package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
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
import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.SharedPreferencesUtils;
import com.alves.pedro.groupay.utils.Utils;

public class DashBoardActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";
    public static final String CREDIT_CARD_PARAM = "CREDIT_CARD_PARAM";

    private TextView mTvUserName;
    private TextView mTvBalanceValue;
    private RecyclerView mRvGroups;
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

        mRvGroups = findViewById(R.id.rvGroups);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvGroups.setLayoutManager(layout);

        mRunThread = true;
        askUserData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        GroupAdapter groupAdapter = new GroupAdapter(mUser.getGroupList(), this);
        mRvGroups.setAdapter(groupAdapter);
        groupAdapter.setClickListener((view, position) -> {

        });
    }

}
