package yiliao.demo;

import java.util.ArrayList;
import java.util.Calendar;

import yiliao.demo.tool.MyToolKit;
import yiliao.demo.tool.SubYesNoButton;

import yiliao.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ModifyActivity extends Activity {
	/** Called when the activity is first created. */
	private LinearLayout outLayout;
	private SubYesNoButton btChange[];
	private ImageButton btSave;
	private String pathSave;
	private String personname;
	private String pslid;
	private String filename;
	private TextView tvName;
	private ArrayList<String> listQuestion;
	private ArrayList<String> listAnswer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// HideStatusBar();
		setContentView(R.layout.modify);
		outLayout = (LinearLayout) this.findViewById(R.id.outlayout);
		tvName = (TextView) this.findViewById(R.id.personname);
		btSave = (ImageButton) this.findViewById(R.id.ibtSave);
		pathSave = this.getString(R.string.path_result);

		Intent modifyIntent = getIntent();
		pslid = modifyIntent.getStringExtra("pslid");
		filename = modifyIntent.getStringExtra("filename");
		personname = modifyIntent.getStringExtra("personname");
		listQuestion = modifyIntent.getStringArrayListExtra("listQ");
		listAnswer = modifyIntent.getStringArrayListExtra("listA");

		tvName.setText(personname);
		TextView tvStartLineEmpty = new TextView(this);
		tvStartLineEmpty.setHeight(20);
		outLayout.addView(tvStartLineEmpty);

		btChange = new SubYesNoButton[100];

		for (int index = 0; index < listQuestion.size(); index++) {
			String tempQ = listQuestion.get(index);
			String tempA = listAnswer.get(index);

			String[] oneQuestion = tempQ.split(",");
			String[] oneAnswer = tempA.split(",");

			String tempSubQuestion;
			String tempAnswer;

			btChange[index] = new SubYesNoButton(this, null);
			InitQuestion(index + 1, oneQuestion[1], btChange[index]);
			if (oneQuestion[0].equals("1")) {
				tempAnswer = oneAnswer[1];
				InitAnswer("", tempAnswer);
			} else if (oneQuestion[0].equals("2")) {
				for (int i = 0; i < Integer.parseInt(oneAnswer[1]); i++) {
					tempSubQuestion = oneQuestion[i + 3];
					tempAnswer = oneAnswer[i + 2];
					InitAnswer(tempSubQuestion, tempAnswer);
				}
			} else if (oneQuestion[0].equals("3")) {
				tempAnswer = oneAnswer[1];
				InitAnswer(tempAnswer, "");
			}

			TextView tvMidLineEmpty = new TextView(this);
			tvMidLineEmpty.setHeight(20);
			outLayout.addView(tvMidLineEmpty);
		}

		btSave.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
						"yyyyMMdd", java.util.Locale.getDefault());
				String nowData = format
						.format(Calendar.getInstance().getTime());

				String writeInfo = "";
				pathSave += pslid;
				pathSave += "_";
				pathSave += filename;
				pathSave += "_";
				pathSave += nowData;
				pathSave += ".csv";

				for (int i = 0; i < listAnswer.size(); i++) {
					String tempSave;
					String[] strKind2 = listAnswer.get(i).toString().trim()
							.split(",");
					if (strKind2[0].equals("2")) {
						tempSave = strKind2[0];
						int count = Integer.parseInt(strKind2[1]);
						for (int j = 0; j < count; j++) {
							tempSave += ",";
							tempSave += strKind2[j + 2];
						}
						writeInfo += tempSave;
					} else {
						writeInfo += listAnswer.get(i).toString().trim();
					}

					writeInfo += getString(R.string.str_split);
				}
				writeInfo = writeInfo.substring(0, writeInfo.length() - 1);
				MyToolKit.writeFileSdcard(pathSave, writeInfo);

				Intent intent = new Intent();
				intent.setClass(ModifyActivity.this, SearchActivity.class);
				startActivity(intent);
				ModifyActivity.this.finish();
				getApplicationContext().sendBroadcast(
						new Intent("SearchFinish"));
			}
		});

		// for(int btIndex=0; btIndex<listQuestion.size(); btIndex++){
		// btChange[btIndex].setOnClickListener(
		// new Button.OnClickListener(){
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent();
		// intent.setClass(ModifyActivity.this, ModifyActivity.class);
		// String temp = ""+btIndex;
		// intent.putExtra("number", temp);
		// intent.putStringArrayListExtra("listQ", listQuestion);
		// intent.putStringArrayListExtra("listA", listAnswer);
		// startActivity(intent);
		// }
		// }
		// );
		// }
		if (listQuestion.size() > 0) {
			btChange[0].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(0);
				}
			});
		}

		if (listQuestion.size() > 1) {
			btChange[1].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(1);
				}
			});
		}

		if (listQuestion.size() > 2) {
			btChange[2].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(2);
				}
			});
		}

		if (listQuestion.size() > 3) {
			btChange[3].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(3);
				}
			});
		}

		if (listQuestion.size() > 4) {
			btChange[4].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(4);
				}
			});
		}

		if (listQuestion.size() > 5) {
			btChange[5].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(5);
				}
			});
		}

		if (listQuestion.size() > 6) {
			btChange[6].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(6);
				}
			});
		}

		if (listQuestion.size() > 7) {
			btChange[7].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(7);
				}
			});
		}

		if (listQuestion.size() > 8) {
			btChange[8].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(8);
				}
			});
		}

		if (listQuestion.size() > 9) {
			btChange[9].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(9);
				}
			});
		}

		if (listQuestion.size() > 10) {
			btChange[10].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(10);
				}
			});
		}

		if (listQuestion.size() > 11) {
			btChange[11].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(11);
				}
			});
		}

		if (listQuestion.size() > 12) {
			btChange[12].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(12);
				}
			});
		}

		if (listQuestion.size() > 13) {
			btChange[13].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(13);
				}
			});
		}

		if (listQuestion.size() > 14) {
			btChange[14].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(14);
				}
			});
		}

		if (listQuestion.size() > 15) {
			btChange[15].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(15);
				}
			});
		}

		if (listQuestion.size() > 16) {
			btChange[16].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(16);
				}
			});
		}

		if (listQuestion.size() > 17) {
			btChange[17].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(17);
				}
			});
		}

		if (listQuestion.size() > 18) {
			btChange[18].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(18);
				}
			});
		}

		if (listQuestion.size() > 19) {
			btChange[19].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(19);
				}
			});
		}

		if (listQuestion.size() > 20) {
			btChange[20].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(20);
				}
			});
		}

		if (listQuestion.size() > 21) {
			btChange[21].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(21);
				}
			});
		}

		if (listQuestion.size() > 22) {
			btChange[22].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(22);
				}
			});
		}

		if (listQuestion.size() > 23) {
			btChange[23].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(23);
				}
			});
		}

		if (listQuestion.size() > 24) {
			btChange[24].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(24);
				}
			});
		}

		if (listQuestion.size() > 25) {
			btChange[25].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(25);
				}
			});
		}

		if (listQuestion.size() > 26) {
			btChange[26].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(26);
				}
			});
		}

		if (listQuestion.size() > 27) {
			btChange[27].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(27);
				}
			});
		}

		if (listQuestion.size() > 28) {
			btChange[28].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(28);
				}
			});
		}

		if (listQuestion.size() > 29) {
			btChange[29].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(29);
				}
			});
		}

		if (listQuestion.size() > 30) {
			btChange[30].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(30);
				}
			});
		}

		if (listQuestion.size() > 31) {
			btChange[31].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(31);
				}
			});
		}

		if (listQuestion.size() > 32) {
			btChange[32].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(32);
				}
			});
		}

		if (listQuestion.size() > 33) {
			btChange[33].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(33);
				}
			});
		}

		if (listQuestion.size() > 34) {
			btChange[34].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(34);
				}
			});
		}

		if (listQuestion.size() > 35) {
			btChange[35].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(35);
				}
			});
		}

		if (listQuestion.size() > 36) {
			btChange[36].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(36);
				}
			});
		}

		if (listQuestion.size() > 37) {
			btChange[37].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(37);
				}
			});
		}

		if (listQuestion.size() > 38) {
			btChange[38].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(38);
				}
			});
		}

		if (listQuestion.size() > 39) {
			btChange[39].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(39);
				}
			});
		}

		if (listQuestion.size() > 40) {
			btChange[40].setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					clickChangeButton(40);
				}
			});
		}

	}

	private void clickChangeButton(int number) {
		Intent intent = new Intent();
		intent.setClass(ModifyActivity.this, ModifyOneActivity.class);
		intent.putExtra("pslid", pslid);
		intent.putExtra("filename", filename);
		intent.putExtra("personname", personname);
		intent.putExtra("number", number);
		intent.putStringArrayListExtra("listQ", listQuestion);
		intent.putStringArrayListExtra("listA", listAnswer);
		startActivity(intent);
		ModifyActivity.this.finish();
	}

	private void InitQuestion(int number, String question, SubYesNoButton button) {
		LinearLayout linearLayout1;
		TextView tvForeEmpty = new TextView(this);
		tvForeEmpty.setWidth(20);
		TextView tvNumber = new TextView(this);
		tvNumber.setText("" + number);
		tvNumber.setTextAppearance(this, R.style.styleConfirm);
		tvNumber.setWidth(30);
		TextView tvQuestion = new TextView(this);
		tvQuestion.setText(question);
		tvQuestion.setTextAppearance(this, R.style.styleConfirm);
		tvQuestion.setWidth(450);

		RelativeLayout rButton = new RelativeLayout(this);

		LinearLayout.LayoutParams lBtParams = new LinearLayout.LayoutParams(70,
				45);
		LinearLayout chButton = new LinearLayout(this);
		chButton.setLayoutParams(lBtParams);
		rButton.addView(chButton, lBtParams);

		TextView tvChange = new TextView(this);
		tvChange.setText("変更");
		tvChange.setTextAppearance(this, R.style.styleButton);
		RelativeLayout.LayoutParams rBtParams = new RelativeLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		rBtParams.setMargins(15, 10, 0, 0);
		rButton.addView(tvChange, rBtParams);

		button.setColorHigh(Color.rgb(220, 230, 242));
		button.setWidth(600);
		button.setHeight(250);
		button.setBackgroundColor(Color.TRANSPARENT);
		chButton.addView(button);

		LinearLayout.LayoutParams q1Params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout1 = new LinearLayout(this);
		linearLayout1.setLayoutParams(q1Params);
		linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

		linearLayout1.addView(tvForeEmpty);
		linearLayout1.addView(tvNumber);
		linearLayout1.addView(tvQuestion);
		linearLayout1.addView(rButton);

		outLayout.addView(linearLayout1);
	}

	private void InitAnswer(String subQuestion, String answer) {

		TextView tvForeEmpty = new TextView(this);
		tvForeEmpty.setWidth(80);
		TextView tvSubQuestion = new TextView(this);
		tvSubQuestion.setText(subQuestion);
		tvSubQuestion.setTextAppearance(this, R.style.styleConfirm);
		tvSubQuestion.setWidth(420);

		TextView tvAnswer = new TextView(this);
		tvAnswer.setText(answer);
		tvAnswer.setTextAppearance(this, R.style.styleConfirm);
		tvAnswer.setWidth(70);

		LinearLayout.LayoutParams q1Params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		LinearLayout linearLayout2;
		linearLayout2 = new LinearLayout(this);
		linearLayout2.setLayoutParams(q1Params);
		linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

		linearLayout2.addView(tvForeEmpty);
		linearLayout2.addView(tvSubQuestion);
		linearLayout2.addView(tvAnswer);
		outLayout.addView(linearLayout2);
	}
}
