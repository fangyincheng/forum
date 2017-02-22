package com.work.fyc.MyWork;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import control.LookAdapter;
import control.NetWord;

/**
 * Created by 方银城 on 2016/6/18.
 */
public class LookActivity extends AppCompatActivity {

    private Toolbar tool;
    private ListView reply_listView;
    private EditText reply_c;
    private Button reply_bt;
    private MyHandler handler;
    private String replyId;
    private LookAdapter lookAdapter;
    private ArrayList arr_reply, arrayList_t;
    private JSONObject jb;
    private RelativeLayout rl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);

        init();

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

        Intent fromMain = getIntent();
        replyId = fromMain.getStringExtra("replyId");
        final String sectionId = fromMain.getStringExtra("sectionId");

        SharedPreferences sp = getSharedPreferences("USER",MODE_PRIVATE);
        final String name = sp.getString("username","");

        reply_listView.setAdapter(lookAdapter);
        getCard();//网络获取数据

        reply_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(reply_c.getText().toString()))
                    Toast.makeText(getApplication(),"没有内容",Toast.LENGTH_SHORT).show();
                else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("name", name);
                                jsonObject.put("replyId", replyId);
                                jsonObject.put("sectionId", sectionId);
                                jsonObject.put("reply", reply_c.getText().toString());

                                NetWord net = new NetWord("http://192.168.123.1:8080/MyForum/replyCard", jsonObject, handler);
                                jsonObject = net.doPost();

                                if (jsonObject == null) {

                                } else if (jsonObject.getString("result").equals("success"))
                                    handler.sendEmptyMessage(1);
                                else if (jsonObject.getString("result").equals("error"))
                                    handler.sendEmptyMessage(2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            }
        });
    }

    private void getCard() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = new JSONObject();
                try {
                    json.put("replyId",replyId);
                    NetWord net = new NetWord("http://192.168.123.1:8080/MyForum/lookReply",json,handler);
                    json = net.doPost();
                    if (json == null) {
                    } else {
                        jb = json.getJSONObject("c");
                        lookAdapter.setJb(jb);
                        JSONArray ja = json.getJSONArray("reply_arr");

                        for(int i=0;i<ja.length();i++) {
                            arrayList_t.add(ja.get(i));
                        }
                        handler.sendEmptyMessage(3);
                    }
                } catch (JSONException e) {
                    handler.sendEmptyMessage(5);
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void init() {
        tool = (Toolbar) findViewById(R.id.tool);
        reply_listView = (ListView) findViewById(R.id.reply_listView);
        reply_c = (EditText) findViewById(R.id.reply_c);
        reply_bt = (Button) findViewById(R.id.reply_bt);

        handler = new MyHandler();

        arr_reply = new ArrayList();
        arrayList_t = new ArrayList();
        lookAdapter = new LookAdapter(LookActivity.this,arr_reply,R.layout.look_listview);

        rl = (RelativeLayout) findViewById(R.id.rl);
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplication(),"回复成功",Toast.LENGTH_SHORT).show();
                    getCard();
                    reply_c.setText("");
                    break;
                case 2:
                    Toast.makeText(getApplication(),"回复失败",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    arr_reply.clear();
                    for (int i = 0; i<arrayList_t.size(); i++) {
                        arr_reply.add(arrayList_t.get(i));
                    }
                    lookAdapter.notifyDataSetChanged();
                    arrayList_t.clear();
                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    rl.setVisibility(View.VISIBLE);

                    try {
                        TextView textView = (TextView) findViewById(R.id.name);
                        textView.setText(jb.get("name").toString());

                        ImageView imageView = (ImageView) findViewById(R.id.pic);
                        imageView.setImageResource(R.drawable.section);

                        TextView textView1 = (TextView) findViewById(R.id.content);
                        textView1.setText(jb.get("contents").toString());

                        TextView textView2 = (TextView) findViewById(R.id.title_c);
                        textView2.setText(jb.get("title").toString());

                        TextView textView3 = (TextView) findViewById(R.id.date);
                        Date date = new Date(Long.parseLong(jb.get("date").toString()));
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                        textView3.setText(format1.format(date));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplication(),"该帖子没有回复",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
