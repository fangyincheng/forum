package control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.work.fyc.MyWork.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 方银城 on 2016/6/19.
 */
public class LookAdapter extends BaseAdapter {

    private JSONObject jb;
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList arr_reply;
    private int layoutID;
    private int t;

    public LookAdapter(Context context, ArrayList arr_reply,int layoutID) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.arr_reply = arr_reply;
        this.layoutID = layoutID;
    }

    @Override
    public int getCount() {
        return arr_reply.size();
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

//        if(position == arr_reply.size()) {
//            if (t == 0) {
//                try {
//                    TextView textView = (TextView) convertView.findViewById(R.id.name);
//                    textView.setText(jb.get("name").toString());
//
//                    ImageView imageView = (ImageView) convertView.findViewById(R.id.pic);
//                    imageView.setImageResource(R.drawable.section);
//
//                    TextView textView1 = (TextView) convertView.findViewById(R.id.content);
//                    textView1.setText(jb.get("contents").toString());
//
//                    TextView textView2 = (TextView) convertView.findViewById(R.id.title_c);
//                    textView2.setText(jb.get("title").toString());
//
//                    TextView textView3 = (TextView) convertView.findViewById(R.id.date);
//                    Date date = new Date(Long.parseLong(jb.get("date").toString()));
//                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//                    textView3.setText(format1.format(date));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                convertView.findViewById(R.id.LL_4).setVisibility(View.GONE);
//            }
//        } else {

            if (position == 0) {
                t = 1;
                try {
                    TextView textView = (TextView) convertView.findViewById(R.id.name);
                    textView.setText(jb.get("name").toString());

                    ImageView imageView = (ImageView) convertView.findViewById(R.id.pic);
                    imageView.setImageResource(R.drawable.section);

                    TextView textView1 = (TextView) convertView.findViewById(R.id.content);
                    textView1.setText(jb.get("contents").toString());

                    TextView textView2 = (TextView) convertView.findViewById(R.id.title_c);
                    textView2.setText(jb.get("title").toString());

                    TextView textView3 = (TextView) convertView.findViewById(R.id.date);
                    Date date = new Date(Long.parseLong(jb.get("date").toString()));
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    textView3.setText(format1.format(date));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                convertView.findViewById(R.id.rl).setVisibility(View.GONE);
            }
            try {
                ((TextView) convertView.findViewById(R.id.reply_t)).setText(((JSONObject) arr_reply.get(position)).get("name").toString() + ":");
                Date date = new Date(Long.parseLong(((JSONObject) arr_reply.get(position)).get("date").toString()));
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                ((TextView) convertView.findViewById(R.id.reply_d)).setText(format1.format(date));
                ((TextView) convertView.findViewById(R.id.reply_c)).setText(((JSONObject) arr_reply.get(position)).get("contents").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
//        }
        return convertView;
    }
    public void setJb(JSONObject jb) {
        this.jb  = jb;
    }
}
