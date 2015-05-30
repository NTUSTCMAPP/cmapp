package com.example.jsonarray1;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();

	private EditText input1, input2, input3;
	private Button btn1;
	private TextView tv1, tv2, tv3, tv4, tv5;
	private static String Url = "http://cmapp.nado.tw/JsonArray.php";
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	//private  JSONObject = null;

	JSONObject jsonobject = new JSONObject();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input1 = (EditText) findViewById(R.id.editText1);
		input2 = (EditText) findViewById(R.id.editText2);
		input3 = (EditText) findViewById(R.id.editText3);
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
			new EnterBeaconID().execute();

		}
	};

	/**
	 * Background Async Task to Create
	 * */
	class EnterBeaconID extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Entering BeaconID..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Entering BeaconID
		 * */
		@Override
		protected String doInBackground(String... args) {

			String MyData1 = input1.getText().toString();
			String MyData2 = input2.getText().toString();
			String MyData3 = input3.getText().toString();

			JSONObject j1 = new JSONObject();
			ArrayList<NameValuePair> jsonarray = new ArrayList<NameValuePair>();
			try {
				j1.put("1", MyData1);
				j1.put("2", MyData2);
				j1.put("3", MyData3);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonarray.add(new BasicNameValuePair("BeaconID", j1.toString()));// 重要！！
			try {
				// Note that create product url accepts POST method

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(Url);
				httpPost.setEntity(new UrlEncodedFormEntity(jsonarray,HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost); // 中斷！！！！！！！！！！！！！！！！
				HttpEntity httpEntity = httpResponse.getEntity();

				try {
					String json = EntityUtils.toString(httpEntity);
					 jsonobject = new JSONObject(json);

					

				} catch (JSONException ex) {

				}

			} catch (Exception e) {
				e.printStackTrace();
				Log.d("mylog", "error");
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
			tv4.setText(jsonobject.toString());
			tv5.setText("AAA");
			
			
			try {
				JSONArray jsonarray = jsonobject.getJSONArray("item");
				JSONObject itemjsonobject = new JSONObject(); 
				itemjsonobject=jsonarray.getJSONObject(0);
				//String title =itemjsonobject.getString("1");
				String echoString1 = itemjsonobject.getString("1");
				tv1.setText(echoString1);
				String echoString2 = itemjsonobject.getString("2");
				tv2.setText(echoString2);
				String echoString3 = itemjsonobject.getString("3");
				tv3.setText(echoString3);
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
			
//			try {
//				String echoString1 = jsonobject.getString("1");
//				tv1.setText(echoString1);
//				String echoString2 = jsonobject.getString("2");
//				tv2.setText(echoString2);
//				String echoString3 = jsonobject.getString("3");
//				tv3.setText(echoString3);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
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
