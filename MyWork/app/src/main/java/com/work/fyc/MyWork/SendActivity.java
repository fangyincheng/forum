package com.work.fyc.MyWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import control.NetWord;

/**
 * Created by 方银城 on 2016/6/15.
 */
public class SendActivity extends AppCompatActivity{

    private Toolbar tool;
    private Spinner spinner;
    private EditText title;
    private EditText content;
    private ArrayAdapter arrayAdapter;
    private MyHandler handler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        init();//初始化对象

        tool.setTitle("");
        setSupportActionBar(tool);//设置toolbar
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置显示返回按钮
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent();
                setResult(RESULT_OK,toMain);
                finish();
            }
        });
    }

    private void init() {
        tool = (Toolbar) findViewById(R.id.tool);
        spinner = (Spinner) findViewById(R.id.spinner);
        title = (EditText) findViewById(R.id.title_s);
        content = (EditText) findViewById(R.id.content);

        arrayAdapter = new ArrayAdapter(this,R.layout.spinner,MainActivity.spinner_arr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        handler = new MyHandler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                if("".equals(title.getText().toString()) || "".equals(content.getText().toString()))
                    Toast.makeText(getApplication(),getResources().getString(R.string.no_complete),Toast.LENGTH_SHORT).show();
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                SharedPreferences sharedPreferences = SendActivity.this.getSharedPreferences("USER",MODE_PRIVATE);
                                String string = sharedPreferences.getString("id","");
                                jsonObject.put("id",string);
                                jsonObject.put("section",spinner.getSelectedItem().toString());
                                jsonObject.put("title",title.getText().toString());
                                jsonObject.put("content",content.getText().toString());
                                System.out.print(jsonObject.toString());

                                NetWord netWord = new NetWord("http://192.168.123.1:8080/MyForum/sendCard", jsonObject, handler);
                                jsonObject = netWord.doPost();

                                if(jsonObject == null) {

                                } else if(jsonObject.getString("result").equals("success"))
                                    handler.sendEmptyMessage(1);
                                else
                                    handler.sendEmptyMessage(2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }

                break;
        }
        return true;
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplication(),"发贴成功",Toast.LENGTH_SHORT).show();
                    Intent toMain = new Intent();
                    setResult(RESULT_OK,toMain);
                    finish();
                    break;
                case 2:
                    Toast.makeText(getApplication(),"发贴失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
