package com.example.test2;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private EditText ed1,ed2,ed3;
	private Button btn1;
	private TextView tv1, tv2, tv3, tv4, tv5;
	private Thread HttpThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ed1 = (EditText) findViewById(R.id.editText1);
		ed2 = (EditText) findViewById(R.id.editText2);
		ed3 = (EditText) findViewById(R.id.editText3);
		btn1 = (Button) findViewById(R.id.button1);
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);
		btn1.setOnClickListener(btn1Listener);
	}

	private Button.OnClickListener btn1Listener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// 產生新的HttpThread物件
			HttpThread myThread = new HttpThread();
			// 設定變數值
			myThread.MyData1 = ed1.getText().toString();
			myThread.MyData2 = ed2.getText().toString();
			myThread.MyData3 = ed3.getText().toString();
			// 開始執行緒
			myThread.start();
		}
	};

	// 必須利用Handler來改變主執行緒的UI值
	private Handler MyHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// msg.getData會傳回Bundle，Bundle類別可以由getString(<KEY_NAME>)取出指定KEY_NAME的值
			
			tv5.setText(msg.getData().getString("response"));
			String data = tv5.getText().toString();
			
			//tv4.setText(data);
			String[] dataString = data.split(" ");
			tv1.setText(dataString[0]);
			tv2.setText(dataString[1]);
			tv3.setText(dataString[2]);
			
		}
	};

	// 宣告一個新的類別並擴充Thread
	class HttpThread extends Thread {

		// 宣告變數並指定預設值
		public String MyData1 = "NoData";
		public String MyData2 = "NoData";
		public String MyData3 = "NoData";
		public String Url = "http://www.tui-na.com.tw/MtoM.php";

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();

			// 宣告一個新的Bundle物件，Bundle可以在多個執行緒之間傳遞訊息
			Bundle myBundle = new Bundle();

			try {
				HttpClient client = new DefaultHttpClient();
				URI website = new URI(this.Url);

				// 指定POST模式
				HttpPost request = new HttpPost();

				// POST傳值必須將key、值加入List<NameValuePair>
				List<NameValuePair> parmas = new ArrayList<NameValuePair>();

				// 逐一增加POST所需的Key、值
				parmas.add(new BasicNameValuePair("MyData1", this.MyData1));
				parmas.add(new BasicNameValuePair("MyData2", this.MyData2));
				parmas.add(new BasicNameValuePair("MyData3", this.MyData3));
				//parmas.add(new BasicNameValuePair("MyMessage", this.MyMessage));

				// 宣告UrlEncodedFormEntity來編碼POST，指定使用UTF-8
				UrlEncodedFormEntity env = new UrlEncodedFormEntity(parmas,
						HTTP.UTF_8);
				request.setURI(website);

				// 設定POST的List
				request.setEntity(env);

				HttpResponse response = client.execute(request);
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					myBundle.putString("response",EntityUtils.toString(resEntity));
				} else {
					myBundle.putString("response", "Nothing");
				}

				Message msg = new Message();
				msg.setData(myBundle);
				MyHandler.sendMessage(msg);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
