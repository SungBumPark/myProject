package myhanbok.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private View progressDialog;
	private String id, pwd;
	ImageView loginOk;
	ImageView loginCancel;

	EditText input_id, input_pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated constructor stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_popup);

		progressDialog = findViewById(R.id.progressDialog);
		hideProgressDialog();
		loginOk = (ImageView) findViewById(R.id.login_ok);
		loginOk.setOnClickListener(this);
		loginCancel = (ImageView) findViewById(R.id.login_cancel);
		loginCancel.setOnClickListener(this);

		input_id = (EditText) findViewById(R.id.login_id_input);
		input_pwd = (EditText) findViewById(R.id.login_password_input);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == loginOk) {
			id = input_id.getText().toString();
			pwd = input_pwd.getText().toString();
			showProgressDialog();
			phpDown task;
			task = new phpDown(this, id, pwd);
			task.execute("http://kookmin.sumcnd.com/app_login.php");
		} else if (v == loginCancel) {
			finish();
		}
	}

	private class phpDown extends AsyncTask<String, String, String> {

		String id, pwd;

		public phpDown(LoginActivity popup, String id, String pwd) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.pwd = pwd;
		}

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {

				HttpPost request = new HttpPost(urls[0]);
				// 전달할 인자들
				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
				nameValue.add(new BasicNameValuePair("id", id));
				nameValue.add(new BasicNameValuePair("pwd", pwd));

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

				Log.d("msg", "hi");
				return total;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			Log.d("msg", jsonHtml.toString());
			return jsonHtml.toString();
		}
		

		protected void onPostExecute(String str) {
			super.onPostExecute(str);
			try {
				JSONObject root = new JSONObject(str);

				String result = null;
				String id = null;
				String name = null;
				String mb_1 = null;
				String mb_2 = null;
				String mb_profile_img = null;
				result = root.getString("result");
				if (result.equals("true")) {
					name = root.getString("name");
					mb_1 = root.getString("mb_1");
					mb_2 = root.getString("mb_2");
					mb_profile_img = root.getString("mb_profile_img");
					id = root.getString("mb_id");
				}
//				Toast.makeText(getApplicationContext(), "name : " + name
//						+ " mb_1 : " + mb_1 + " mb_2 : " + mb_2
//						+ " mb_profile_img : " + mb_profile_img, 1000);
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();

				if (result.equals("true")) {
					Toast.makeText(getApplicationContext(), name + "님 로그인 되었습니다.", 1000)
							.show();
					editor.putString("LOGIN_STATE", "TRUE");
					editor.putString("ID", id);
					editor.putString("NAME", name);
					editor.putString("MB_1", mb_1);
					editor.putString("MB_2", mb_2);
					editor.putString("MB_PROFILE_IMG", mb_profile_img);
					editor.commit();
					finish();

				} else if (result.equals("false")) {
					Toast.makeText(getApplicationContext(),
							"Id 혹은 비밀번호를 확인하고\n다시 로그인 해주세요", 1000).show();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(0, 0);
				}
				hideProgressDialog();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	public void showProgressDialog() {
		progressDialog.setVisibility(View.VISIBLE);
	}

	public void hideProgressDialog() {
		progressDialog.setVisibility(View.GONE);
	}

	

}
