package com.work.fyc.MyWork;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import control.MainAdapter;
import control.NetWord;

/**
 * Created by 方银城 on 2016/6/14.
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar tool;
    private DrawerLayout drawerLayout;
    private TextView name;
    private HorizontalScrollView sv;
    private LinearLayout hl;
    private ActionBarDrawerToggle toggle;
    private SimpleAdapter simpleAdapter;
    private String[] s = new String[]{"我的帖子","我要发帖","个人中心","退出登陆"};
    private ArrayList<Map<String,Object>> arr;
    private ListView slip_list;
    private ListView main_list;
    private ArrayList arrayList,arrayList_sec,arrayList_t;
    private MainAdapter mainAdapter;
    private Menu menu;
    private MyHandler handler;
    public static int FLAG=3;//标记我的帖子页面还是动态页面
    public static String F="全部";//记录点击的版块
    private String sID;//记录板块版主id
    public static String[] spinner_arr;
    private Boolean b;//标记是否游客

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        b = intent.getBooleanExtra("isVisit",false);

        init();//初始化对象

        if(b) {
            s = new String[]{"我要登陆"};
        }

        tool.setTitle("");
        setSupportActionBar(tool);//设置toolbar
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //抽屉开关
        toggle = new ActionBarDrawerToggle(this,drawerLayout,tool,R.string.name,R.string.password) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
        drawerLayout.addDrawerListener(toggle);

        //设置侧滑菜单listview
        setListView();

        //获取帖子数据
        getCard("http://192.168.123.1:8080/MyForum/main","全部");

        slip_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(GravityCompat.START);
                if(s[position].equals("我的帖子")) {
                    FLAG = 2;
                    getCard("http://192.168.123.1:8080/MyForum/selfCard","全部");
                } else if(s[position].equals("回到动态")) {
                    FLAG = 3;
                    getCard("http://192.168.123.1:8080/MyForum/main","全部");
                } else if(s[position].equals("我要发帖")) {
                    Intent toSend = new Intent(MainActivity.this, SendActivity.class);
                    startActivityForResult(toSend,1);
                } else if(s[position].equals("个人中心")) {
                    Intent toSend = new Intent(MainActivity.this, PersonActivity.class);
                    startActivityForResult(toSend,2);
                } else if(s[position].equals("退出登陆") || s[position].equals("我要登陆")) {
                    //修改登录信息
                    SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
                    sharedPreferences.edit().clear().commit();

                    FLAG = 3;

                    Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(toLogin);
                    finish();
                }
            }
        });

    }

    private void getCard(final String path, final String s) {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = new JSONObject();
                try {
                    //获取登录用户
                    SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("USER",MODE_PRIVATE);
                    String nameId = sharedPreferences.getString("id","");
                    jsonObject.put("nameId",nameId);
                    jsonObject.put("section",s);

                    NetWord netWord = new NetWord(path, jsonObject, handler);
                    jsonObject = netWord.doPost();

                    if(jsonObject == null) {
                    } else {
                        if(s.equals("全部")) {
                            arrayList_sec.clear();
                            JSONArray ja = ((JSONArray) jsonObject.get("section"));
                            for(int i=0;i<ja.length();i++) {
                                arrayList_sec.add(ja.get(i));
                            }
                        }
                        JSONArray ja = ((JSONArray) jsonObject.get("card"));
                        for(int i=0;i<ja.length();i++) {
                            arrayList_t.add(ja.get(i));
                        }
                        F = s;
                        for (int i=0;i<arrayList_sec.size();i++) {
                            try {
                                if(F.equals(((JSONObject)arrayList_sec.get(i)).get("name").toString())) {
                                    sID = ((JSONObject) arrayList_sec.get(i)).get("host").toString();
                                    mainAdapter.setsID(sID);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(1);
                        handler.sendEmptyMessage(FLAG);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    FLAG = 3;
                    if(s.equals("全部"))
                        handler.sendEmptyMessage(6);
                    else
                        handler.sendEmptyMessage(5);
                }
            }
        });
        thread.start();
    }

    private class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(FLAG == 2)
                getCard("http://192.168.123.1:8080/MyForum/selfCard",((Button)v).getText().toString());
            else if(FLAG == 3)
                getCard("http://192.168.123.1:8080/MyForum/main",((Button)v).getText().toString());
        }
    }

    private void setListView() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        String n = sharedPreferences.getString("username","");
        name.setText(n);
        slip_list.setAdapter(simpleAdapter);
        main_list.setAdapter(mainAdapter);
        for(int i=0; i<s.length; i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("item",s[i]);
            arr.add(map);
        }
        simpleAdapter.notifyDataSetChanged();
    }

    private void init() {
        tool = (Toolbar) findViewById(R.id.tool);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        name = (TextView) findViewById(R.id.name);
        sv = (HorizontalScrollView) findViewById(R.id.sv);
        hl = (LinearLayout) findViewById(R.id.HL);

        handler = new MyHandler();

        slip_list = (ListView) findViewById(R.id.slip_list);
        arr = new ArrayList<Map<String,Object>>();
        simpleAdapter = new SimpleAdapter(this,arr,R.layout.main_listview,new String[]{"item"},new int[]{R.id.list_tv});

        main_list = (ListView) findViewById(R.id.main_list);
        arrayList = new ArrayList();
        mainAdapter = new MainAdapter(this,arrayList,R.layout.main_listview_m,new int[]{R.id.person,R.id.name,R.id.pic,R.id.title_c,R.id.content},handler);

        arrayList_sec = new ArrayList();
        arrayList_t = new ArrayList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                if (!b) {
                    item.setIcon(R.drawable.add2);
                    Intent toSend = new Intent(MainActivity.this, SendActivity.class);
                    startActivityForResult(toSend, 1);
                } else {
                    Toast.makeText(getApplication(),"请登录",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        menu.getItem(0).setIcon(R.drawable.add);
        FLAG = 3;
        getCard("http://192.168.123.1:8080/MyForum/main","全部");
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setSectionView() {
        hl.removeAllViews();
        Button button0 = new Button(this);
        button0.setText("全部");
        button0.setBackgroundColor(Color.GRAY);
        if(button0.getText().toString().equals(F))
            button0.setBackgroundColor(Color.BLACK);
        hl.addView(button0);
        button0.setOnClickListener(new Click());

        spinner_arr = new String[arrayList_sec.size()];
        for(int i=0;i<arrayList_sec.size();i++) {
            Button button = new Button(this);
            try {
                button.setText(((JSONObject)arrayList_sec.get(i)).get("name").toString());
                button.setBackgroundColor(Color.GRAY);
                spinner_arr[i] = ((JSONObject)arrayList_sec.get(i)).get("name").toString();
                if(button.getText().toString().equals(F))
                    button.setBackgroundColor(Color.BLACK);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            hl.addView(button);
            button.setOnClickListener(new Click());
        }
    }

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    arrayList.clear();
                    for (int i=0;i<arrayList_t.size();i++) {
                        arrayList.add(arrayList_t.get(i));
                    }
                    mainAdapter.notifyDataSetChanged();
                    arrayList_t.clear();
                    setSectionView();
                    break;
                case 2:
                    s[0] = "回到动态";
                    arr.clear();
                    setListView();
                    break;
                case 3:
                    if(!b)
                        s[0] = "我的帖子";
                    arr.clear();
                    setListView();
                    break;
                case 4:
                    Toast.makeText(getApplication(),"网络错误",Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(getApplication(),"没有该板块的帖子哦",Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(getApplication(),"您还没有发帖哦",Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(getApplication(),"删除成功",Toast.LENGTH_SHORT).show();
                    mainAdapter.notifyDataSetChanged();
                    if(FLAG == 2)
                        getCard("http://192.168.123.1:8080/MyForum/selfCard",F);
                    else if(FLAG == 3)
                        getCard("http://192.168.123.1:8080/MyForum/main",F);
                    break;
                case 8:
                    Toast.makeText(getApplication(),"删除失败",Toast.LENGTH_SHORT).show();
                    break;
                case 9:
                    Toast.makeText(getApplication(),"置顶成功",Toast.LENGTH_SHORT).show();
                    if(FLAG == 2)
                        getCard("http://192.168.123.1:8080/MyForum/selfCard",F);
                    else if(FLAG == 3)
                        getCard("http://192.168.123.1:8080/MyForum/main",F);
                    break;
                case 10:
                    Toast.makeText(getApplication(),"置顶失败",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
