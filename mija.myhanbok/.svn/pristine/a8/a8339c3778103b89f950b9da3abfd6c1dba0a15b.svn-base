package myhanbok.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Vector;

import mija.myhanbok.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class DB{

	Bitmap bmImg;
	// back task;
	phpDown task;
	String id, pwd;

	public DB(String id, String pwd) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.pwd = pwd;
		task = new phpDown();
		task.execute("http://kookmin.sumcnd.com/app_login.php");
	}



	private class back extends AsyncTask<String, Integer, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub
			try {
				URL myFileUrl = new URL(urls[0]);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();
				// String json = DownloadHtml("http://서버주소/appdata.php");
				InputStream is = conn.getInputStream();

				bmImg = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bmImg;
		}

		protected void onPostExecute(Bitmap img) {
			// imView.setImageBitmap(bmImg);
		}
	}

	private class phpDown extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {

				HttpPost request = new HttpPost(urls[0]);
				// 전달할 인자들
				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
				nameValue.add(new BasicNameValuePair("id", "admin"));
				nameValue.add(new BasicNameValuePair("pwd", "kookmin"));

				// 웹 접속 - utf-8 방식으로
				HttpEntity enty = new UrlEncodedFormEntity(nameValue,
						HTTP.UTF_8);
				request.setEntity(enty);

				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(request);
				// 웹 서버에서 값받기
				HttpEntity entityResponse = res.getEntity();
				InputStream im = entityResponse.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(im, HTTP.UTF_8));

				String total = "";
				String tmp = "";
				// 버퍼에있는거 전부 더해주기
				// readLine -> 파일내용을 줄 단위로 읽기
				while ((tmp = reader.readLine()) != null) {
					if (tmp != null) {
						total += tmp;
					}
				}
				im.close();
				// 결과창뿌려주기 - ui 변경시 에러
				// result.setText(total);
				return total;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Log.d("msg", jsonHtml.toString());
			return jsonHtml.toString();
		}

		protected void onPostExecute(String str) {
			super.onPostExecute(str);
			// txtView.setText(str);

			String result = null;
			try {
				JSONObject root = new JSONObject(str);
				// JSONArray ja = root.getJSONArray("results");
				// for (int i = 0; i < ja.length(); i++) {
				// JSONObject jo = ja.getJSONObject(i);
				result = root.getString("result");

				// listItem.add(new ListItem(imgurl, txt1, txt2));
				// }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// txtView.setText("result : " + result);

		}

	}

}
