package com.work.fyc.MyWork;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import control.NetWord;

/**
 * Created by 方银城 on 2016/6/13.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText userPassword;
    private Button login_bt;
    private TextView forget;
    private TextView visit;
    private TextView register;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        if(!sharedPreferences.getAll().isEmpty()){
            Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(toMain);
            finish();
        }

        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //去除顶部状态栏

        init();//初始化控件对象

        login_bt.setOnClickListener(new Click());
        register.setOnClickListener(new Click());
        forget.setOnClickListener(new Click());
        visit.setOnClickListener(new Click());

        login_bt.setOnTouchListener(new Touch());
        register.setOnTouchListener(new Touch());
        forget.setOnTouchListener(new Touch());
        visit.setOnTouchListener(new Touch());

    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_bt:
                    if("".equals(username.getText().toString()) || "".equals(userPassword.getText().toString()))
                        Toast.makeText(getApplication(),getResources().getString(R.string.no_complete),Toast.LENGTH_SHORT).show();
                    else {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("name",username.getText().toString());
                                    jsonObject.put("password",userPassword.getText().toString());

                                    NetWord netWord = new NetWord("http://192.168.123.1:8080/MyForum/login",jsonObject,handler);
                                    jsonObject = netWord.doPost();

                                    if(jsonObject == null) {
                                    } else if(jsonObject.getString("result").equals("success")) {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("user");

                                        //保存登陆状态
                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("USER",Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("username",jsonObject1.get("username").toString());
                                        editor.putString("password",jsonObject1.get("password").toString());
                                        editor.putString("id",jsonObject1.get("id").toString());
                                        editor.putString("power",jsonObject1.get("power").toString());
                                        editor.commit();

                                        handler.sendEmptyMessage(1);
                                    } else if(jsonObject.getString("result").equals("user_error")) {
                                        handler.sendEmptyMessage(2);
                                    } else if(jsonObject.getString("result").equals("password_error")) {
                                        handler.sendEmptyMessage(3);
                                    } else {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        thread.start();
                    }
                    break;
                case R.id.register:
                    Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivityForResult(toReg,1);
                    break;
                case R.id.forget:
                    Toast.makeText(getApplication(),"敬请期待！！！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.visit:
                    Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                    toMain.putExtra("isVisit",true);
                    startActivity(toMain);
                    finish();
                    break;
            }
        }
    }

    private class Touch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (v.getId()) {
                case R.id.login_bt:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        login_bt.setBackgroundColor(Color.argb(80,19,86,255));
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        login_bt.setBackgroundResource(R.drawable.bt_bg);
                    break;
                case R.id.register:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        register.setTextColor(Color.BLUE);
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        register.setTextColor(Color.BLACK);
                    break;
                case R.id.forget:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        forget.setTextColor(Color.BLUE);
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        forget.setTextColor(Color.BLACK);
                    break;
                case R.id.visit:
                    if(event.getAction() == MotionEvent.ACTION_DOWN)
                        visit.setTextColor(Color.BLUE);
                    else if(event.getAction() == MotionEvent.ACTION_UP)
                        visit.setTextColor(Color.BLACK);
                    break;
            }
            return false;
        }
    }

    private void init() {
        username = (EditText) findViewById(R.id.username);
        userPassword = (EditText) findViewById(R.id.userPassword);
        login_bt = (Button) findViewById(R.id.login_bt);
        forget = (TextView) findViewById(R.id.forget);
        visit = (TextView) findViewById(R.id.visit);
        register = (TextView) findViewById(R.id.register);

        handler = new MyHandler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplication(),"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                    toMain.putExtra("isVisit",false);
                    startActivity(toMain);
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplication(),"用户不存在",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getApplication(),"密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
