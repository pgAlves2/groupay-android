package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.CreditCard;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class RegisterCreditCardActivity extends AppCompatActivity {

    public static final String USER_PARAM = "USER_PARAM";

    private EditText mEtCardNumber;
    private EditText mEtCardName;
    private Spinner mSpinnerMonth;
    private Spinner mSpinnerYear;
    private EditText mEtCVV;
    private Button mBtnRegister;
    private ProgressBar mProgressBar;

    private User mUser;

    private UserRegisterCreditCardHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_credit_card);

        if (!getIntent().hasExtra(USER_PARAM))
            return;

        mEtCardNumber = findViewById(R.id.etCardNumber);
        mEtCardName = findViewById(R.id.etCardName);
        mSpinnerMonth = findViewById(R.id.spinnerMonth);
        mSpinnerYear = findViewById(R.id.spinnerYear);
        mEtCVV = findViewById(R.id.etCardCVV);
        mProgressBar = findViewById(R.id.pbLoading);

        mEtCardNumber.setText("4929256328394091");

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener((v) -> registerCreditCard());

        mUser = (User) getIntent().getSerializableExtra(USER_PARAM);
        TextView tvUserName = findViewById(R.id.tvUserName);
        tvUserName.setText(mUser.getName());
    }

    @SuppressLint("DefaultLocale")
    private void registerCreditCard() {
        mBtnRegister.setEnabled(false);

        if (mHandler == null)
            mHandler = new UserRegisterCreditCardHandler();

        boolean valid = false;
        String error = "";
        if (Utils.paramNotValid(mEtCardNumber))
            error = getString(R.string.msgErrorNumberCard);
        else if (Utils.paramNotValid(mEtCardName))
            error = getString(R.string.msgErrorNameCard);
        else if (Utils.paramNotValid(mEtCVV))
            error = getString(R.string.msgErrorCVVCard);
        else
            valid = true;

        if (valid) {
            Utils.showProgressBar(mProgressBar);
            CreditCard creditCard = new CreditCard(mEtCardName.getText().toString(),
                    mEtCardNumber.getText().toString(),
                    String.format("%02d", Integer.valueOf((String) mSpinnerMonth.getSelectedItem())),
                    (String) mSpinnerYear.getSelectedItem(),
                    mEtCVV.getText().toString());
            APIController.getInstance().registerCreditCard(creditCard, mUser, this, mHandler);
        } else {
            Utils.showErrorDialog(error, this);
            mBtnRegister.setEnabled(true);
        }
    }

    @SuppressLint("HandlerLeak")
    class UserRegisterCreditCardHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    break;
                default:
                    break;
            }
        }
    }
}
