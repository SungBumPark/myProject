package com.example.travelmaker.main;

import com.example.travelmaker.tour.gpsinfomain.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 팝업 출력하는 클래스
 */
public class PopupFragment extends DialogFragment {

	private int request; // 팝업 타입

	public static final int REQUEST_GPS_WAIT = 0; // GPS 수신 대기
	public static final int REQUEST_GPS_UNABLE = 1; // GPS 사용 불가능
	public static final int REQUEST_INTERNET_UNABLE = 2;

	/**
	 * 팝업 창을 출력하는 인터페이스
	 */
	public interface OnPopupShowListener {
		/**
		 * 팝업을 출력한다.
		 * 
		 * @param request
		 *            - 보여질 팝업의 종류를 구분하는 변수
		 */
		public void onPopupShow(int request);
	}

	public static PopupFragment newInstance(int request) {
		PopupFragment f = new PopupFragment();

		Bundle args = new Bundle();
		args.putInt("request", request);
		f.setArguments(args);

		return f;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		request = getArguments().getInt("request");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View layout = View.inflate(getActivity(), R.layout.custom_popup, null);
		TextView title = (TextView) layout.findViewById(R.id.popup_title);
		TextView content = (TextView) layout.findViewById(R.id.popup_content);

		title.setText("알림");

		layout.findViewById(R.id.popup_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dismiss();
					}
				});

		builder.setView(layout);

		switch (request) {
		// GPS 사용 불가능
		case REQUEST_GPS_UNABLE:
			content.setText("GPS를 사용할수 없습니다.\nGPS 상태를 확인하세요.");
			break;
		// GPS 수신 대기
		case REQUEST_GPS_WAIT:
			content.setText("GPS 데이터를 수신중입니다.\n다시 시도해 주세요.");
			break;
		case REQUEST_INTERNET_UNABLE:
			content.setText("인터넷을 사용할 수 없습니다.\n인터넷 연결 후 사용해 주세요.");
			break;

		}

		return builder.create();
	}
}
