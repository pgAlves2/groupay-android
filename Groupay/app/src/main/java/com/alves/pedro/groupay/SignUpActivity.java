package com.alves.pedro.groupay;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alves.pedro.groupay.data.APIController;
import com.alves.pedro.groupay.model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEtName;
    private EditText mEtEmail;
    private EditText mEtCPF;
    private EditText mEtPhone;
    private EditText mEtPassworld;
    private Button mBtnRegister;
    private ProgressBar mProgressBar;

    private AlertDialog.Builder mBuilder;

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
        if (paramNotValid(mEtName))
            error = getString(R.string.msgErrorName);
        else if (paramNotValid(mEtEmail))
            error = getString(R.string.msgErrorEmail);
        else if (paramNotValid(mEtCPF))
            error = getString(R.string.msgErrorCPF);
        else if (paramNotValid(mEtPhone))
            error = getString(R.string.msgErrorTelefone);
        else if (paramNotValid(mEtPassworld))
            error = getString(R.string.msgErrorSenha);
        else
            valid = true;

        if (valid) {
            showProgressBar();
            User user = new User(mEtName.getText().toString(),
                    mEtEmail.getText().toString(),
                    mEtCPF.getText().toString(),
                    mEtPhone.getText().toString(),
                    mEtPassworld.getText().toString());
            APIController.getInstance().registerUser(user, this, mHandler);
        } else {
            showErrorDialog(error);
            mBtnRegister.setEnabled(true);
        }
    }

    private boolean paramNotValid(EditText editText) {
        return (editText.getText().toString().trim().length() <= 0);
    }

    private void showErrorDialog(String value) {
        if (mBuilder == null)
            mBuilder = new AlertDialog.Builder(this);
        mBuilder.setMessage(value)
                .setPositiveButton(R.string.msgOk, (dialog, id) -> dialog.dismiss())
                .create()
                .show();
    }

    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("HandlerLeak")
    class UserRegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideProgressBar();
            switch (msg.what) {
                case APIController.REQUEST_RESULT_OK:
                    User user = (User) msg.obj;
                    Toast.makeText(SignUpActivity.this, user.getId(), Toast.LENGTH_SHORT).show();
                    break;
                case APIController.REQUEST_RESULT_ERROR:
                    showErrorDialog(getString(R.string.msgErrorOnRegisterUser));
                    break;
                default:
                    break;
            }
        }
    }
}