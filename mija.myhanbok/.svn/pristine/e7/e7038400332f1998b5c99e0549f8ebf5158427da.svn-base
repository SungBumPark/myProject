package myhanbok.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import mija.myhanbok.R;
import myhanbok.adapter.MenuAdapter;
import myhanbok.adapter.PersonData;
import myhanbok.model.TabMenu;
import myhanbok.server.cFTPClient;
import myhanbok.slide.ClickListenerForScrolling;
import myhanbok.slide.SizeCallbackForMenu;
import myhanbok.slide.SlideView;
import myhanbok.utilities.Config;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AdapterView.OnItemClickListener;

public class JoinActivity extends Activity implements OnClickListener,
		OnEditorActionListener {

	private SlideView scrollView;
	private MenuAdapter menuAdapter;
	private View menu;
	private View app;
	private ImageView joinName, joinSex, joinBirth, joinContact, joinEmail,
			joinSubmit;
	Bitmap profileBitmap;
	private String data;
	private TextView result;
	private EditText inputText;
	private RelativeLayout over, joinBackground;
	private int cyear, cmonth, cday;
	// private View pager;
	private final int TAKE_CAMERA = 1;// 카메라 리턴 코드값 설정
	private final int TAKE_GALLERY = 2;
	private final int PICK_FROM_CAMERA = 3;
	private boolean isImageSelected = false;
	private ImageView joinAdd;
	private ImageButton btnSlide, btnProfile;
	private ImageView addProfile, joinAddText, joinProfileImage;
	private static boolean menuOut = false;
	public static boolean canAppTouch = false;
	private ArrayList<TabMenu> tabMenu = new ArrayList<TabMenu>();
	private ClickListenerForScrolling scrollContext;
	private Uri mImageCaptureUri;
	final PersonData person = new PersonData();
	private TextView slide_name;
	private cFTPClient m_FtpClient;
	private phpDown m_task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();

		joinProfileImage = (ImageView) app
				.findViewById(R.id.join_profile_image);
		joinProfileImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// hideKeyboard();
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else
					alertDialog();
			}
		});

		joinBackground = (RelativeLayout) app
				.findViewById(R.id.join_background);
		joinBackground.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// hideKeyboard();
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				return false;
			}
		});

		addProfile = (ImageView) menu.findViewById(R.id.add_profile);
		addProfile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String state = prefs.getString("LOGIN_STATE", "");
				if (state.equals("FALSE")) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				} else if (state.equals("TRUE")) {
					AlertDialog.Builder builder = new AlertDialog.Builder(v
							.getContext());
					builder.setTitle("개인정보");

					builder.setMessage("정보를 입력시겠습니까?");
					builder.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											getApplicationContext(),
											JoinActivity.class);
									startActivity(intent);
									btnSlide.performClick();

								}
							});
					builder.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					builder.show();
				}
			}
		});

		joinAddText = (ImageView) app.findViewById(R.id.join_add_text);
		joinAdd = (ImageView) app.findViewById(R.id.join_add);
		joinAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 프로필 사진을
				// hideKeyboard();
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else
					alertDialog();
			}
		});

		joinName = (ImageView) app.findViewById(R.id.join_name);
		joinName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else {
					Context mContext = getApplicationContext();
					LayoutInflater inflater = (LayoutInflater) mContext
							.getSystemService(LAYOUT_INFLATER_SERVICE);
					final View layout = inflater.inflate(
							R.layout.custom_dialog,
							(ViewGroup) findViewById(R.id.dialog_layout));

					inputText = (EditText) layout.findViewById(R.id.input);
					final TextView inputName = (TextView) app
							.findViewById(R.id.join_name_text);
					final ImageView background = (ImageView) app
							.findViewById(R.id.join_name);

					AlertDialog.Builder aDialog = new AlertDialog.Builder(
							JoinActivity.this);
					aDialog.setTitle("이름 입력");
					aDialog.setView(layout);

					aDialog.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					aDialog.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									data = inputText.getText().toString();
									if (data.length() == 0) {
									} else {
										background
												.setBackgroundResource(R.drawable.join_blank);
										inputName.setText(data);
										person.setName(data);

									}
									// person.setName(data);
								}
							});
					AlertDialog ad = aDialog.create();
					ad.show();
					// joinName.setBackgroundResource(R.drawable.join_blank);
					// joinName.setVisibility(View.INVISIBLE);
					// String data = getData();
				}
			}
		});

		joinSex = (ImageView) app.findViewById(R.id.join_sex);
		joinSex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else {
					final CharSequence[] items = { "남자", "여자" };
					AlertDialog.Builder builder = new AlertDialog.Builder(v
							.getContext());
					final TextView inputSex = (TextView) app
							.findViewById(R.id.join_sex_text);
					final ImageView background = (ImageView) app
							.findViewById(R.id.join_sex);
					// 여기서 부터는 알림창의 속성 설정
					builder.setTitle("색상을 선택하세요") // 제목 설정
							.setItems(items,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int index) {
											switch (index) {
											case 0:
												background
														.setBackgroundResource(R.drawable.join_blank);
												inputSex.setText("남자");
												person.setSex(0);
												break;
											case 1:
												background
														.setBackgroundResource(R.drawable.join_blank);
												inputSex.setText("여자");
												person.setSex(1);
												break;
											}
										}
									});
					AlertDialog dialog = builder.create(); // 알림창 객체 생성
					dialog.show(); // 알림창 띄우기
				}
			}
		});

		final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub

				final TextView inputBirth = (TextView) app
						.findViewById(R.id.join_birth_text);
				final ImageView background = (ImageView) app
						.findViewById(R.id.join_birth);
				String msg = String.format("%d / %d / %d", year, monthOfYear,
						dayOfMonth);

				person.setYear(year);
				person.setMonth(monthOfYear);
				person.setDay(dayOfMonth);
				background.setBackgroundResource(R.drawable.join_blank);
				inputBirth.setText(msg);
			}

		};

		joinBirth = (ImageView) app.findViewById(R.id.join_birth);
		joinBirth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else {
					new DatePickerDialog(JoinActivity.this, listener, 1990, 0,
							1).show();
				}
			}

		});

		joinContact = (ImageView) app.findViewById(R.id.join_contact);
		joinContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else {
					Context mContext = getApplicationContext();
					LayoutInflater inflater = (LayoutInflater) mContext
							.getSystemService(LAYOUT_INFLATER_SERVICE);
					final View layout = inflater.inflate(
							R.layout.contact_custom_dialog,
							(ViewGroup) findViewById(R.id.dialog_layout));

					inputText = (EditText) layout
							.findViewById(R.id.contact_front);
					final EditText contactMid = (EditText) layout
							.findViewById(R.id.contact_mid);
					final EditText contactLast = (EditText) layout
							.findViewById(R.id.contact_last);
					final TextView inputName = (TextView) app
							.findViewById(R.id.join_contact_text);
					final ImageView background = (ImageView) app
							.findViewById(R.id.join_contact);

					AlertDialog.Builder aDialog = new AlertDialog.Builder(
							JoinActivity.this);
					aDialog.setTitle("메일주소 입력");
					aDialog.setView(layout);

					aDialog.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					aDialog.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									if (inputText.getText().length() == 0
											|| contactMid.getText().length() == 0
											|| contactLast.getText().length() == 0) {

									} else {
										background
												.setBackgroundResource(R.drawable.join_blank);
										data = inputText.getText().toString()
												+ contactMid.getText()
														.toString()
												+ contactLast.getText()
														.toString();
										person.setContact(data);
										data = inputText.getText().toString()
												+ "-"
												+ contactMid.getText()
														.toString()
												+ "-"
												+ contactLast.getText()
														.toString();
										inputName.setText(data);

									}
									// person.setName(data);
								}
							});
					AlertDialog ad = aDialog.create();
					ad.show();
				}
			}

		});

		joinEmail = (ImageView) app.findViewById(R.id.join_email);
		joinEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int menuWidth = menu.getMeasuredWidth();
				if (scrollContext.isMenuOut())
					scrollContext.closeMenu(menuWidth);
				else {
					Context mContext = getApplicationContext();
					LayoutInflater inflater = (LayoutInflater) mContext
							.getSystemService(LAYOUT_INFLATER_SERVICE);
					final View layout = inflater.inflate(
							R.layout.mail_custom_dialog,
							(ViewGroup) findViewById(R.id.dialog_layout));

					inputText = (EditText) layout.findViewById(R.id.input);
					final EditText inputEmail = (EditText) layout
							.findViewById(R.id.input_mail);
					final TextView inputName = (TextView) app
							.findViewById(R.id.join_email_text);
					final ImageView background = (ImageView) app
							.findViewById(R.id.join_email);

					AlertDialog.Builder aDialog = new AlertDialog.Builder(
							JoinActivity.this);
					aDialog.setTitle("메일주소 입력");
					aDialog.setView(layout);

					aDialog.setPositiveButton("취소",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									return;
								}
							});
					aDialog.setNegativeButton("확인",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									if (inputText.getText().length() == 0
											|| inputEmail.getText().length() == 0) {

									} else {

										background
												.setBackgroundResource(R.drawable.join_blank);
										data = inputText.getText().toString()
												+ "@"
												+ inputEmail.getText()
														.toString();
										person.setEmail(data);
										inputName.setText(data);

										// person.setName(data);
									}
								}
							});
					AlertDialog ad = aDialog.create();
					ad.show();
					// joinName.setBackgroundResource(R.drawable.join_blank);
					// joinName.setVisibility(View.INVISIBLE);
					// String data = getData();
				}
			}
		});

		joinSubmit = (ImageView) app.findViewById(R.id.join_submit);
		joinSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(v
						.getContext());
				builder.setTitle("회원가입");

				builder.setMessage("등록하시겠습니까?");
				builder.setNegativeButton("확인",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								Intent intent = new Intent(
										getApplicationContext(),
										MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);

							}
						});
				builder.setPositiveButton("취소",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								return;
							}
						});
				builder.show();
			}

		});

	}

	public void init() {
		LayoutInflater inflater = LayoutInflater.from(this);
		scrollView = (SlideView) inflater.inflate(
				R.layout.screen_scroll_with_list_menu, null);
		setContentView(scrollView);

		menu = inflater.inflate(R.layout.horz_scroll_menu, null);
		app = inflater.inflate(R.layout.join, null);

		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.actionbar);

		// pager = (View) findViewById(R.id.pager);
		ViewGroup tabBar = (ViewGroup) findViewById(R.id.tabBar);
		tabMenu = Config.createTab();

		over = (RelativeLayout) app.findViewById(R.id.over);
		slide_name = (TextView) menu.findViewById(R.id.slide_name);
		menuAdapter = new MenuAdapter(this, R.layout.link, tabMenu);
		ListView listView = (ListView) menu.findViewById(R.id.list);
		listView.setAdapter(menuAdapter);

		scrollContext = new ClickListenerForScrolling(getApplicationContext(),
				scrollView, menu, canAppTouch, menuOut, app, over);

		btnSlide = (ImageButton) tabBar.findViewById(R.id.BtnSlide);
		btnSlide.setOnClickListener(scrollContext);

		btnProfile = (ImageButton) tabBar.findViewById(R.id.BtnProfile);
		btnProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String state = prefs.getString("LOGIN_STATE", "");
				if (state.equals("FALSE")) {
					Intent intent = new Intent(getApplicationContext(),
							LoginActivity.class);
					startActivity(intent);
				} else if (state.equals("TRUE")) {
					Intent intent = new Intent(getApplication(),
							MyDressRoomActivity.class);
					startActivity(intent);
				}

			}
		});

		final View[] children = new View[] { menu, app };

		int scrollToViewIdx = 1;

		scrollView.initViews(children, scrollToViewIdx,
				new SizeCallbackForMenu(btnSlide));

		listView.setOnItemClickListener(new OnItemClickListener() {

			@SuppressLint("SetJavaScriptEnabled")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;

				switch (position) {
				case 0:
					intent = new Intent(getApplicationContext(),
							MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(getApplicationContext(),
							MainActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String page = "-1";
					intent.putExtra("page", page);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(getApplicationContext(),
							PreviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(getApplicationContext(),
							WebviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String web1 = "1";
					intent.putExtra("page", web1);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(getApplicationContext(),
							WebviewActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					final String web2 = "2";
					intent.putExtra("page", web2);
					startActivity(intent);
					break;
				}

			}
		});

		app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Context context = menu.getContext();

				int menuWidth = menu.getMeasuredWidth();
				// TODO Auto-generated method stub
				if (scrollContext.isMenuOut()) {
					Log.d("===closing slide==", "Scroll to left");
					Log.d("===clicked==", "clicked");
					over.setVisibility(View.GONE);
					int left = menuWidth;
					scrollView.smoothScrollTo(left, 0);
					System.out.println("@11 Hide Tab");
					menuOut = !menuOut;
				}

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		// SetImage setImage = new SetImage();
		if (resultCode == RESULT_OK) {
			// 카메라로 찍었을 때
			if (requestCode == TAKE_CAMERA) // 1
			{
				final Uri selectImageUri = data.getData();
				final String[] filePathColumn = { MediaStore.Images.Media.DATA };

				final Cursor imageCursor = this.getContentResolver().query(
						selectImageUri, filePathColumn, null, null, null);
				imageCursor.moveToFirst();

				final int columnIndex = imageCursor
						.getColumnIndex(filePathColumn[0]);
				final String imagePath = imageCursor.getString(columnIndex);
				imageCursor.close();

				int degree = GetExifOrientation(imagePath);
				Log.d("image", "degree : " + degree);
				profileBitmap = BitmapFactory.decodeFile(imagePath);

				profileBitmap = rotate(profileBitmap, degree);
				profileBitmap = Bitmap.createScaledBitmap(profileBitmap, 150,
						150, true);
				
				
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String id = prefs.getString("ID", "");
				m_FtpClient = new cFTPClient();
				Thread thread = new Thread(new ftpUploadThread(
						saveImage(profileBitmap), id));
				thread.start();
				m_task = new phpDown("/public_html/patternimg/" + id
						+ "/profile", id);
				m_task.execute("http://kookmin.sumcnd.com/imginsert.php");

				
				profileBitmap = getRoundedCornerBitmap(profileBitmap, 185);
				joinProfileImage.setImageBitmap(profileBitmap);
				joinProfileImage.setScaleType(ImageView.ScaleType.FIT_XY);
				joinProfileImage.setVisibility(View.VISIBLE);
				joinAdd.setVisibility(View.INVISIBLE);
				isImageSelected = true;
			}
			// 앨범에서 가져올 때
			else if (requestCode == TAKE_GALLERY) // 2
			{
				final Uri selectImageUri = data.getData();
				final String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Log.d("path", filePathColumn[0]);
				final Cursor imageCursor = this.getContentResolver().query(
						selectImageUri, filePathColumn, null, null, null);
				imageCursor.moveToFirst();

				final int columnIndex = imageCursor
						.getColumnIndex(filePathColumn[0]);
				final String imagePath = imageCursor.getString(columnIndex);
				imageCursor.close();

				int degree = GetExifOrientation(imagePath);
				Log.d("image", "degree : " + degree);
				Bitmap profileBitmap = BitmapFactory.decodeFile(imagePath);

				profileBitmap = rotate(profileBitmap, degree);
				profileBitmap = Bitmap.createScaledBitmap(profileBitmap, 150,
						150, true);
				
				
				SharedPreferences prefs = getSharedPreferences("mija.hanbok",
						MODE_PRIVATE);
				String id = prefs.getString("ID", "");
				m_FtpClient = new cFTPClient();
				Thread thread = new Thread(new ftpUploadThread(
						saveImage(profileBitmap), id));
				thread.start();
				m_task = new phpDown("/public_html/patternimg/" + id
						+ "/profile", id);
				m_task.execute("http://kookmin.sumcnd.com/imginsert.php");

				
				profileBitmap = getRoundedCornerBitmap(profileBitmap, 185);

				joinProfileImage.setImageBitmap(profileBitmap);
				joinProfileImage.setScaleType(ImageView.ScaleType.FIT_XY);
				joinProfileImage.setVisibility(View.VISIBLE);
				joinAdd.setVisibility(View.INVISIBLE);
				isImageSelected = true;

			} else {
				System.out.println("camera return error");
				return;
			}
		}
		if (isImageSelected == true)
			joinAddText.setVisibility(View.INVISIBLE);
	}

	private class phpDown extends AsyncTask<String, String, String> {
		String mpath, mbid;

		phpDown(String path, String id) {
			mpath = path;
			mbid = id;
		}

		@Override
		protected String doInBackground(String... urls) {
			StringBuilder jsonHtml = new StringBuilder();
			try {

				HttpPost request = new HttpPost(urls[0]);
				// 전달할 인자들
				Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
				nameValue.add(new BasicNameValuePair("path", mpath));
				nameValue.add(new BasicNameValuePair("mbid", mbid));
				nameValue.add(new BasicNameValuePair("mbidx", "2"));

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

		}

	}

	class ftpUploadThread extends Thread {
		String Path, ID;

		ftpUploadThread(String path, String id) {
			Path = path;
			ID = id;
		}

		public void run() {
			if (m_FtpClient.ftpConnect("sumcnd.com", "kookmin", "kookmin", 21)) {
				Log.d("TAG", "okokokok");
				m_FtpClient.ftpChangeDirectory("/public_html/patternimg");
				m_FtpClient.ftpMakeDirectory(ID);
				m_FtpClient.ftpChangeDirectory("/public_html/patternimg/" + ID);
				// m_FtpClient.ftpMakeDirectory("profile");
				// m_FtpClient.ftpChangeDirectory("/public_html/patternimg/" +
				// ID + "/profile");

				boolean a = m_FtpClient.ftpfileUpload(Path);
				// task = new
				// phpDown(m_FtpClient.ftpGetCurrentWorkingDirectory());
				// task.execute("http://kookmin.sumcnd.com/imginsert.php");

				m_FtpClient.ftpDisconnect();

			}
		}
	}

	public String saveImage(Bitmap bm) {

		String strFilename = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mija/" + "profile.jpg";
		try {
			FileOutputStream fos = new FileOutputStream(strFilename);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);

			try {
				fos.close();
				return strFilename;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strFilename;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	private void alertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
		builder.setTitle("프로필 사진 설정");
		if (isImageSelected) {
			builder.setPositiveButton("삭제하기",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							joinAdd.setVisibility(View.VISIBLE);
							joinProfileImage.setVisibility(View.INVISIBLE);
							joinAddText.setVisibility(View.VISIBLE);
							isImageSelected = !isImageSelected;
						}
					});
		}
		builder.setNeutralButton("사진첩에서 찾기",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(intent, TAKE_GALLERY);

					}
				});
		builder.setNegativeButton("새로운 사진 찍기",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Intent cameraIntent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);

						startActivityForResult(cameraIntent, TAKE_CAMERA);
					}
				});

		builder.show();
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	public Bitmap rotate(Bitmap bitmap, int degree) {

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Matrix matrix = new Matrix();

		matrix.postRotate(degree);

		bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return bitmap;

	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		// switch (v.getId()) {
		// case R.id.join_name_input:
		// if (actionId == EditorInfo.IME_ACTION_DONE)
		// Toast.makeText(getApplicationContext(), "완료 버튼", 500).show();
		//
		// break;
		// }
		return false;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	// public void hideKeyboard() {
	// InputMethodManager imm = (InputMethodManager)
	// getSystemService(Context.INPUT_METHOD_SERVICE);
	// imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
	// InputMethodManager.HIDE_NOT_ALWAYS);
	// }

	public synchronized static int GetExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}
		return degree;
	}

	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		SharedPreferences prefs = getSharedPreferences("mija.hanbok",
				MODE_PRIVATE);
		String state = prefs.getString("LOGIN_STATE", "");
		String name = prefs.getString("NAME", "") + " 님";
		if (state.equals("FALSE"))
			slide_name.setText("로그인을 해주세요");
		else if (state.equals("TRUE"))
			slide_name.setText(name);
	}

}
