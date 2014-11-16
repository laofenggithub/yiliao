package yiliao.demo;

import yiliao.demo.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConfirmActivity extends Activity {
	/** Called when the activity is first created. */
	private TextView tvDate;
	private TextView tvKanaName;
	private TextView tvKajiName;
	private TextView tvAge;
	private TextView tvSex;
	private TextView tvBirth;
	private TextView tvPslid;
	private TextView tvMano;
	private TextView tvMccrse;
	private TextView tvKind;
	private ImageButton ibtBack;
	private ImageButton ibtNext;
	private String sName;
	private String sPslid;
	private String sKindFileName;
	private BroadcastReceiver mSearchFinishReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// HideStatusBar();
		this.setContentView(R.layout.confirmlayout);
		tvDate = (TextView) this.findViewById(R.id.tvDate);
		tvKanaName = (TextView) this.findViewById(R.id.tvKanaName);
		tvKajiName = (TextView) this.findViewById(R.id.tvKajiName);
		tvAge = (TextView) this.findViewById(R.id.tvAge);
		tvSex = (TextView) this.findViewById(R.id.tvSex);
		tvBirth = (TextView) this.findViewById(R.id.tvBirth);
		tvPslid = (TextView) this.findViewById(R.id.tvPslid);
		tvMano = (TextView) this.findViewById(R.id.tvMano);
		tvMccrse = (TextView) this.findViewById(R.id.tvMccrse);
		tvKind = (TextView) this.findViewById(R.id.tvKind);
		ibtBack = (ImageButton) this.findViewById(R.id.ibtBack);
		ibtNext = (ImageButton) this.findViewById(R.id.ibtNext);

		Intent searchIntent = getIntent();
		tvDate.setText(searchIntent.getStringExtra("date"));
		tvKanaName.setText(searchIntent.getStringExtra("kananame"));
		tvKajiName.setText(searchIntent.getStringExtra("kajiname"));
		tvAge.setText(searchIntent.getStringExtra("age"));
		tvSex.setText(searchIntent.getStringExtra("sex"));
		tvBirth.setText(searchIntent.getStringExtra("birth"));
		tvPslid.setText(searchIntent.getStringExtra("pslid"));
		tvMano.setText(searchIntent.getStringExtra("mano"));

		if (searchIntent.getStringExtra("kindname").equals("生活機能評価")) {
			tvMccrse.setText(searchIntent.getStringExtra("mccrsename"));
		} else {
			tvMccrse.setText(searchIntent.getStringExtra("mccrse"));
		}

		tvKind.setText(searchIntent.getStringExtra("kindname"));

		sName = searchIntent.getStringExtra("kajiname");
		sPslid = searchIntent.getStringExtra("pslid");
		sKindFileName = searchIntent.getStringExtra("filename");

		ibtBack.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// exit app
				ConfirmActivity.this.finish();
			}
		});

		ibtNext.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// next to question page
				Intent intent = new Intent();
				intent.setClass(ConfirmActivity.this, QuestionActivity.class);
				intent.putExtra("kajiname", sName);
				intent.putExtra("pslid", sPslid);
				intent.putExtra("filename", sKindFileName);
				startActivity(intent);
			}
		});
		// 終了ブロードキャストfs
		mSearchFinishReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if ("SearchFinish".equals(intent.getAction())) {
					ConfirmActivity.this.finish();
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("SearchFinish");
		registerReceiver(mSearchFinishReceiver, filter);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(mSearchFinishReceiver);
		super.onDestroy();
	}
}
