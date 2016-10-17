package com.yeahgenius.hy.gloryboom.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.yeahgenius.hy.gloryboom.R;
import com.yeahgenius.hy.gloryboom.application.MyApplication;
import com.yeahgenius.hy.gloryboom.constant.Constant;
import com.yeahgenius.hy.gloryboom.util.NetUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity
{

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.htv_login_activity_logo) TextView logo;
    @BindView(R.id.shimmer_view_container) ShimmerFrameLayout container;
    @BindView(R.id.et_register_email) EditText etRegisterEmail;
    @BindView(R.id.et_register_password) EditText etRegisterPassword;
    @BindView(R.id.et_register_re_entry) EditText etRegisterReEntry;
    @BindView(R.id.btn_register_singup) CircularProgressButton btnRegisterSingup;
    private Handler handler;
    private Pattern pattern = Pattern.compile(Constant.EMAIL_PATTERN);
    private Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initToolbar();
        initLogo();
        handler = new Handler();
    }

    private void initLogo()
    {
        logo.setTypeface(MyApplication.loadFont(this));
        container.setDuration(5000);
        container.startShimmerAnimation();
    }

    private void initToolbar()
    {
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        container.stopShimmerAnimation();
    }

    @OnClick(R.id.btn_register_singup)
    public void regist(View v)
    {
        hideKeyboard();
        //1) 判空
        if (isEmpty(etRegisterEmail, etRegisterPassword, etRegisterReEntry)) {return;}

        //2) 两次输入的密码是否一致
        if (!etRegisterPassword.getText().toString().equals(etRegisterReEntry.getText().toString()))
        {
            Snackbar.make(v, "两次输入的密码不一致，请重新输入", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            etRegisterReEntry.setText("");
            return;
        }

        //3) 判网
        if (!NetUtil.isNetworkAvailable(this))
        {
            Snackbar.make(v, "当前网络不给力", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            return;
        }

        //4）判断邮箱格式
        if (!isValidateEmail(etRegisterEmail.getText().toString()))
        {
            Snackbar.make(v, "邮箱格式不正确，请重新输入", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            return;
        }

        //5）判断密码长度
        if (!isValidatePassword(etRegisterPassword.getText().toString()))
        {
            Snackbar.make(v, "密码长度不足6位，请重新输入", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            return;
        }
    }

    private boolean isEmpty(EditText... editTexts){

        for (EditText et:editTexts)
        {
            if (TextUtils.isEmpty(et.getText().toString()))
            {
                et.setError("请输入完整");
                return true;
            }
        }
        return false;
    }

    private boolean isValidateEmail(String email)
    {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidatePassword(String password)
    {
        return password.length() > 5;
    }

    private void hideKeyboard()
    {
        View view = getCurrentFocus();
        if (view != null)
        {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
