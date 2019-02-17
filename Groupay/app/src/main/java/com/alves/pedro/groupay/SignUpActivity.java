package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.User;
import com.alves.pedro.groupay.utils.Utils;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtCPF;
    private EditText mEtPhone;
    private EditText mEtPassworld;
    private Button mBtnRegister;
    private ProgressBar mProgressBar;

    private UserRegisterHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEtName = findViewById(R.id.etName);
        mEtEmail = findViewById(R.id.etEmail);
        mEtCPF = findViewById(R.id.etCPF);
        mEtPhone = findViewById(R.id.etPhone);
        mEtPassworld = findViewById(R.id.etPassworld);
        mProgressBar = findViewById(R.id.pbLoading);

        mBtnRegister = findViewById(R.id.btnRegister);
        mBtnRegister.setOnClickListener((v) -> registerUser());

    }

    private void registerUser() {
        mBtnRegister.setEnabled(false);

        if (mHandler == null)
            mHandler = new UserRegisterHandler();

        boolean valid = false;
        String error = "";
        if (Utils.paramNotValid(mEtName))
            error = getString(R.string.msgErrorName);
        else if (Utils.paramNotValid(mEtEmail))
            error = getString(R.string.msgErrorEmail);
        else if (Utils.paramNotValid(mEtCPF))
            error = getString(R.string.msgErrorCPF);
        else if (Utils.paramNotValid(mEtPhone))
            error = getString(R.string.msgErrorTelefone);
        else if (Utils.paramNotValid(mEtPassworld))
            error = getString(R.string.msgErrorSenha);
        else
            valid = true;

        if (valid) {
            Utils.showProgressBar(mProgressBar);
            User user = new User(mEtName.getText().toString(),
                    mEtEmail.getText().toString(),
                    mEtCPF.getText().toString(),
                    mEtPhone.getText().toString(),
                    mEtPassworld.getText().toString());
            APIController.getInstance().registerUser(user, this, mHandler);
        } else {
            Utils.showErrorDialog(error, this);
            mBtnRegister.setEnabled(true);
        }
    }

    @SuppressLint("HandlerLeak")
    class UserRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Utils.hideProgressBar(mProgressBar);
            mBtnRegister.setEnabled(true);
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    User user = (User) msg.obj;
                    if (user == null)
                        return;
                    Intent registerCreditCardIntent = new Intent(SignUpActivity.this, RegisterCreditCardActivity.class);
                    registerCreditCardIntent.putExtra(RegisterCreditCardActivity.USER_PARAM, user);
                    startActivity(registerCreditCardIntent);
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    Utils.showErrorDialog(getString(R.string.msgErrorOnRegisterUser), SignUpActivity.this);
                    break;
                default:
                    break;
            }
        }
    }
}