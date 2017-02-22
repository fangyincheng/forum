package control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.work.fyc.MyWork.Circle;
import com.work.fyc.MyWork.LookActivity;
import com.work.fyc.MyWork.MainActivity;
import com.work.fyc.MyWork.R;
import com.work.fyc.MyWork.SendActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 方银城 on 2016/6/15.
 */
public class MainAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private ArrayList arrayList;
    private int layoutID;
    private int[] itemIDs;
    private Handler handler;
    private String sID;

    public MainAdapter(Context context, ArrayList arrayList, int layoutID, int[] itemIDs, Handler handler) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;
        this.layoutID = layoutID;
        this.itemIDs = itemIDs;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(layoutID, null);

        if(arrayList.size() == 0)
            return convertView;

        Circle c = (Circle) convertView.findViewById(R.id.person);
        c.setImageResource(R.drawable.bg);
        try {
            TextView textView = (TextView) convertView.findViewById(R.id.name);
            textView.setText(((JSONObject)arrayList.get(position)).get("name").toString());

            ImageView imageView = (ImageView) convertView.findViewById(R.id.pic);
            imageView.setImageResource(R.drawable.section);

            TextView textView1 = (TextView) convertView.findViewById(R.id.content);
            textView1.setText(((JSONObject)arrayList.get(position)).get("contents").toString());

            TextView textView2 = (TextView) convertView.findViewById(R.id.title_c);
            textView2.setText(((JSONObject)arrayList.get(position)).get("title").toString());

            TextView textView3 = (TextView) convertView.findViewById(R.id.date);
            Date date = new Date(Long.parseLong(((JSONObject)arrayList.get(position)).get("date").toString()));
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            textView3.setText(format1.format(date));

            TextView textView4 = (TextView) convertView.findViewById(R.id.look);
//            TextView textView5 = (TextView) convertView.findViewById(R.id.reply);
            TextView textView6 = (TextView) convertView.findViewById(R.id.del);
            TextView textView7 = (TextView) convertView.findViewById(R.id.top);

            SharedPreferences sharedPreferences = context.getSharedPreferences("USER",Context.MODE_PRIVATE);
            if(sharedPreferences.getString("power","").equals("1") || MainActivity.FLAG == 2 || sharedPreferences.getString("id","").equals(sID))
                textView6.setVisibility(View.VISIBLE);
            else
                textView6.setVisibility(View.GONE);

            if(sharedPreferences.getString("power","").equals("1"))
                textView7.setVisibility(View.VISIBLE);
            else
                textView7.setVisibility(View.GONE);

//            textView4.setOnTouchListener(new Touch());
//            textView5.setOnTouchListener(new Touch());
//            textView6.setOnTouchListener(new Touch());
//            textView7.setOnTouchListener(new Touch());

            textView4.setOnClickListener(new Click(position));
//            textView5.setOnClickListener(new Click(position));
            textView6.setOnClickListener(new Click(position));
            textView7.setOnClickListener(new Click(position));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class Click implements View.OnClickListener {

        private int position;

        Click(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.look:
                    ((TextView)v).setTextColor(Color.BLUE);
                    Intent toLook = new Intent(context, LookActivity.class);
                    try {
                        toLook.putExtra("replyId",((JSONObject)arrayList.get(position)).get("id").toString());
                        toLook.putExtra("sectionId",((JSONObject)arrayList.get(position)).get("sectionId").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ((Activity)context).startActivityForResult(toLook,1);
                    break;
                case R.id.del:
                    ((TextView)v).setTextColor(Color.BLUE);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("id",((JSONObject)arrayList.get(position)).get("id").toString());
                                NetWord netWord = new NetWord("http://192.168.123.1:8080/MyForum/deleteCard",jsonObject,handler);
                               jsonObject = netWord.doPost();
                                if (jsonObject.getString("result").equals("success")) {
                                    arrayList.remove(position);
                                    handler.sendEmptyMessage(7);
                                } else if(jsonObject.getString("result").equals("error")) {
                                    handler.sendEmptyMessage(8);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();

                    break;
                case R.id.top:
                    ((TextView)v).setTextColor(Color.BLUE);
                    Thread thread1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("id",((JSONObject)arrayList.get(position)).get("id").toString());
                                NetWord netWord = new NetWord("http://192.168.123.1:8080/MyForum/keepTop",jsonObject,handler);
                                jsonObject = netWord.doPost();
                                if (jsonObject.getString("result").equals("success")) {
                                    handler.sendEmptyMessage(9);
                                } else if(jsonObject.getString("result").equals("error")) {
                                    handler.sendEmptyMessage(10);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread1.start();
                    break;
            }
        }
    }

//    private class Touch implements View.OnTouchListener {
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            if(event.getAction() == MotionEvent.ACTION_DOWN)
//                ((TextView)v).setTextColor(Color.BLUE);
//            else if(event.getAction() == MotionEvent.ACTION_UP)
//                ((TextView)v).setTextColor(Color.BLACK);
//            return false;
//        }
//    }

    public void setsID(String sID) {
        this.sID = sID;
    }

}
