package com.work.fyc.MyWork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * Created by 方银城 on 2016/6/15.
 */
public class PersonActivity extends AppCompatActivity {

    private Toolbar tool;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        init();//初始化对象

        tool.setTitle("");
        setSupportActionBar(tool);//设置toolbar

        Toast.makeText(getApplication(),"敬请期待！！！",Toast.LENGTH_SHORT).show();

    }

    private void init() {
        tool = (Toolbar) findViewById(R.id.tool);
    }
}
