package control;

import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 方银城 on 2016/6/16.
 */
public class NetWord {

    private String u;
    private JSONObject jsonObject;
    private Handler handler;

    public NetWord(String u, JSONObject jsonObject, Handler handler) {
        this.u = u;
        this.jsonObject = jsonObject;
        this.handler = handler;
    }

    public JSONObject doPost() {
        try {
            URL url = new URL(u);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
            // http正文内，因此需要设为true, 默认情况下是false;
            urlConn.setDoOutput(true);

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            urlConn.setDoInput(true);

            // Post 请求不能使用缓存
            urlConn.setUseCaches(false);

            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            urlConn.setRequestProperty("Content-type", "application/x-java-serialized-object");

            // 设定请求的方法为"POST"，默认是GET
            urlConn.setRequestMethod("POST");

            //超时设置
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(3000);

            // 连接，上面对urlConn的所有配置必须要在connect之前完成，
            urlConn.connect();

            // 此处getOutputStream会隐含的进行connect (即：如同调用上面的connect()方法，
            // 所以在开发中不调用上述的connect()也可以)。
            OutputStream out = urlConn.getOutputStream();

            // 现在通过输出流对象构建对象输出流对象，以实现输出可序列化的对象。
            ObjectOutputStream oos = new ObjectOutputStream(out);

            // 向对象输出流写出数据，这些数据将存到内存缓冲区中
            oos.writeObject(jsonObject.toString());

            // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
            oos.flush();

            // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
            // 再调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
            oos.close();

            if(urlConn.getResponseCode()==200) {
                // 调用HttpURLConnection连接对象的getInputStream()函数,
                // 将内存缓冲区中封装好的完整的HTTP请求电文发送到服务端。
                InputStream inStr = urlConn.getInputStream(); // <===注意，实际发送请求的代码段就在这里
                BufferedReader br = new BufferedReader(new InputStreamReader(inStr));
                JSONObject jsonObject1 = new JSONObject(br.readLine());
                return jsonObject1;
            }else {
                handler.sendEmptyMessage(4);
                return null;
            }
        } catch (ProtocolException e) {
            handler.sendEmptyMessage(4);
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            handler.sendEmptyMessage(4);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            handler.sendEmptyMessage(4);
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            handler.sendEmptyMessage(4);
            e.printStackTrace();
            return null;
        }
    }
}
