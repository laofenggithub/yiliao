package yiliao.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import yiliao.demo.tool.QuestionTextView;
import yiliao.demo.tool.SubYesNoButton;
import yiliao.demo.tool.YesNoButton;

import yiliao.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ModifyOneActivity extends Activity {
	private TextView tvPersonName;
	private QuestionTextView outView;
	private QuestionTextView outViewHigh;
	private TextView inText;
	private TextView inTextHigh;
	private TextView yesText;
	private TextView noText;
	private TextView pageNo;
	private LinearLayout layoutQuestion;
	private LinearLayout layoutQuestionHigh;
	private ImageButton ibtNext;
	private LinearLayout yesLayout;
	private LinearLayout noLayout;
	private YesNoButton btYes;
	private YesNoButton btNo;
	private Map<String, Object> mapTemp;
	private int currentCount;
	private int questionCounts;
	private SubYesNoButton btSubYes[] = new SubYesNoButton[4];
	private SubYesNoButton btSubNo[] = new SubYesNoButton[4];
	private LinearLayout subyesLayout[] = new LinearLayout[4];
	private LinearLayout subnoLayout[] = new LinearLayout[4];
	private TextView subQuestion[] = new TextView[4];
	private TextView subBtNo[] = new TextView[4];
	private TextView subBtYes[] = new TextView[4];
	private TextView itemAnswerText[] = new TextView[5];
	private LinearLayout itemLay[] = new LinearLayout[5];
	private SubYesNoButton itemAnswer[] = new SubYesNoButton[5];

	private Map<String, Object> mapQuestion;
	private Map<String, Object> mapAnswer;
	private String strQuestion;
	private String strAnswer;

	private ArrayList<String> listQuestion;
	private ArrayList<String> listAnswer;
	private String personname;
	private String pslid;
	private String filename;

	private final static String STR_YES = "はい";
	private final static String STR_NO = "いいえ";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// HideStatusBar();
		setContentView(R.layout.modifyone);

		tvPersonName = (TextView) this.findViewById(R.id.personname);
		Intent confirmIntent = getIntent();

		pslid = confirmIntent.getStringExtra("pslid").toString().trim();
		filename = confirmIntent.getStringExtra("filename").toString().trim();
		personname = confirmIntent.getStringExtra("personname").toString()
				.trim();
		listQuestion = confirmIntent.getStringArrayListExtra("listQ");
		listAnswer = confirmIntent.getStringArrayListExtra("listA");
		currentCount = confirmIntent.getIntExtra("number", -1) + 1;

		strQuestion = listQuestion.get(currentCount - 1);
		strAnswer = listAnswer.get(currentCount - 1);
		questionCounts = listQuestion.size();

		tvPersonName.setText(personname);
		pageNo = (TextView) this.findViewById(R.id.pageNo);
		ibtNext = (ImageButton) this.findViewById(R.id.ibtNext);

		yesText = (TextView) this.findViewById(R.id.btyestext);
		noText = (TextView) this.findViewById(R.id.btnotext);
		yesLayout = (LinearLayout) this.findViewById(R.id.yeslayout);
		noLayout = (LinearLayout) this.findViewById(R.id.nolayout);
		layoutQuestionHigh = (LinearLayout) this
				.findViewById(R.id.questionHigh);
		inTextHigh = (TextView) this.findViewById(R.id.questionTextHigh);
		layoutQuestion = (LinearLayout) this.findViewById(R.id.question);
		inText = (TextView) this.findViewById(R.id.questionText);

		subQuestion[0] = (TextView) this.findViewById(R.id.subquestion1);
		subQuestion[1] = (TextView) this.findViewById(R.id.subquestion2);
		subQuestion[2] = (TextView) this.findViewById(R.id.subquestion3);
		subQuestion[3] = (TextView) this.findViewById(R.id.subquestion4);
		subBtNo[0] = (TextView) this.findViewById(R.id.subBtNo1);
		subBtNo[1] = (TextView) this.findViewById(R.id.subBtNo2);
		subBtNo[2] = (TextView) this.findViewById(R.id.subBtNo3);
		subBtNo[3] = (TextView) this.findViewById(R.id.subBtNo4);
		subBtYes[0] = (TextView) this.findViewById(R.id.subBtYes1);
		subBtYes[1] = (TextView) this.findViewById(R.id.subBtYes2);
		subBtYes[2] = (TextView) this.findViewById(R.id.subBtYes3);
		subBtYes[3] = (TextView) this.findViewById(R.id.subBtYes4);

		subyesLayout[0] = (LinearLayout) this.findViewById(R.id.subYesLay1);
		subnoLayout[0] = (LinearLayout) this.findViewById(R.id.subNoLay1);
		subyesLayout[1] = (LinearLayout) this.findViewById(R.id.subYesLay2);
		subnoLayout[1] = (LinearLayout) this.findViewById(R.id.subNoLay2);
		subyesLayout[2] = (LinearLayout) this.findViewById(R.id.subYesLay3);
		subnoLayout[2] = (LinearLayout) this.findViewById(R.id.subNoLay3);
		subyesLayout[3] = (LinearLayout) this.findViewById(R.id.subYesLay4);
		subnoLayout[3] = (LinearLayout) this.findViewById(R.id.subNoLay4);

		// init button「はい」、「いいえ」
		btYes = new YesNoButton(this, null);
		btYes.setColorHigh(Color.rgb(220, 230, 242));
		btYes.setWidth(600);
		btYes.setHeight(250);
		btYes.setBackgroundColor(Color.TRANSPARENT);
		yesLayout.addView(btYes);

		btNo = new YesNoButton(this, null);
		btNo.setColorHigh(Color.rgb(220, 230, 242));
		btNo.setWidth(600);
		btNo.setHeight(250);
		btNo.setBackgroundColor(Color.TRANSPARENT);
		noLayout.addView(btNo);

		for (int i = 0; i < 4; i++) {
			btSubYes[i] = new SubYesNoButton(this, null);
			btSubYes[i].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[i].setWidth(600);
			btSubYes[i].setHeight(250);
			btSubYes[i].setBackgroundColor(Color.TRANSPARENT);
			subyesLayout[i].addView(btSubYes[i]);

			btSubNo[i] = new SubYesNoButton(this, null);
			btSubNo[i].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[i].setWidth(600);
			btSubNo[i].setHeight(250);
			btSubNo[i].setBackgroundColor(Color.TRANSPARENT);
			subnoLayout[i].addView(btSubNo[i]);
		}

		outView = new QuestionTextView(this, null);
		outView.setWidth(600);
		outView.setHeight(250);

		outViewHigh = new QuestionTextView(this, null);
		outViewHigh.setWidth(600);
		outViewHigh.setHeight(250);

		layoutQuestionHigh.addView(outViewHigh);
		layoutQuestion.addView(outView);

		itemAnswerText[0] = (TextView) this.findViewById(R.id.itemAnswerText1);
		itemLay[0] = (LinearLayout) this.findViewById(R.id.itemLay1);
		itemAnswerText[1] = (TextView) this.findViewById(R.id.itemAnswerText2);
		itemLay[1] = (LinearLayout) this.findViewById(R.id.itemLay2);
		itemAnswerText[2] = (TextView) this.findViewById(R.id.itemAnswerText3);
		itemLay[2] = (LinearLayout) this.findViewById(R.id.itemLay3);
		itemAnswerText[3] = (TextView) this.findViewById(R.id.itemAnswerText4);
		itemLay[3] = (LinearLayout) this.findViewById(R.id.itemLay4);
		itemAnswerText[4] = (TextView) this.findViewById(R.id.itemAnswerText5);
		itemLay[4] = (LinearLayout) this.findViewById(R.id.itemLay5);

		for (int i = 0; i < 5; i++) {
			itemAnswer[i] = new SubYesNoButton(this, null);
			itemAnswer[i].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[i].setWidth(600);
			itemAnswer[i].setHeight(250);
			itemAnswer[i].setBackgroundColor(Color.TRANSPARENT);
			itemLay[i].addView(itemAnswer[i]);
		}

		// load question file
		// init Question Answer questionCounts
		initQuestion(strQuestion, strAnswer);

		// init page number
		changPageNo();

		// init question
		showQuestion();

		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		itemAnswer[0].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				clickItemAnswer(0);
				saveAnswer("1", 0);
			}
		});
		itemAnswer[1].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				clickItemAnswer(1);
				saveAnswer("2", 0);
			}
		});
		itemAnswer[2].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				clickItemAnswer(2);
				saveAnswer("3", 0);
			}
		});
		itemAnswer[3].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				clickItemAnswer(3);
				saveAnswer("4", 0);
			}
		});
		itemAnswer[4].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				clickItemAnswer(4);
				saveAnswer("5", 0);
			}
		});
		// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		btSubYes[0].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubYes[0].setColorHigh(Color.rgb(162, 205, 144));
				btSubYes[0].invalidate();
				btSubNo[0].setColorHigh(Color.rgb(220, 230, 242));
				btSubNo[0].invalidate();
				saveAnswer(STR_YES, 1);
			}
		});
		btSubNo[0].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubNo[0].setColorHigh(Color.rgb(162, 205, 144));
				btSubNo[0].invalidate();
				btSubYes[0].setColorHigh(Color.rgb(220, 230, 242));
				btSubYes[0].invalidate();
				saveAnswer(STR_NO, 1);
			}
		});
		btSubYes[1].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubYes[1].setColorHigh(Color.rgb(162, 205, 144));
				btSubYes[1].invalidate();
				btSubNo[1].setColorHigh(Color.rgb(220, 230, 242));
				btSubNo[1].invalidate();
				saveAnswer(STR_YES, 2);
			}
		});
		btSubNo[1].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubNo[1].setColorHigh(Color.rgb(162, 205, 144));
				btSubNo[1].invalidate();
				btSubYes[1].setColorHigh(Color.rgb(220, 230, 242));
				btSubYes[1].invalidate();
				saveAnswer(STR_NO, 2);
			}
		});
		btSubYes[2].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubYes[2].setColorHigh(Color.rgb(162, 205, 144));
				btSubYes[2].invalidate();
				btSubNo[2].setColorHigh(Color.rgb(220, 230, 242));
				btSubNo[2].invalidate();
				saveAnswer(STR_YES, 3);
			}
		});
		btSubNo[2].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubNo[2].setColorHigh(Color.rgb(162, 205, 144));
				btSubNo[2].invalidate();
				btSubYes[2].setColorHigh(Color.rgb(220, 230, 242));
				btSubYes[2].invalidate();
				saveAnswer(STR_NO, 3);
			}
		});
		btSubYes[3].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubYes[3].setColorHigh(Color.rgb(162, 205, 144));
				btSubYes[3].invalidate();
				btSubNo[3].setColorHigh(Color.rgb(220, 230, 242));
				btSubNo[3].invalidate();
				saveAnswer(STR_YES, 4);
			}
		});
		btSubNo[3].setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btSubNo[3].setColorHigh(Color.rgb(162, 205, 144));
				btSubNo[3].invalidate();
				btSubYes[3].setColorHigh(Color.rgb(220, 230, 242));
				btSubYes[3].invalidate();
				saveAnswer(STR_NO, 4);
			}
		});

		btYes.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btYes.setColorHigh(Color.rgb(162, 205, 144));
				btYes.invalidate();
				btNo.setColorHigh(Color.rgb(220, 230, 242));
				btNo.invalidate();
				saveAnswer(STR_YES, 0);
			}
		});

		btNo.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// set button high
				btNo.setColorHigh(Color.rgb(162, 205, 144));
				btNo.invalidate();
				btYes.setColorHigh(Color.rgb(220, 230, 242));
				btYes.invalidate();
				saveAnswer(STR_NO, 0);
			}
		});

		ibtNext.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// back to answer list
				String tempAnswer;
				String kind = mapQuestion.get("kindQuestion").toString().trim();
				tempAnswer = kind;
				if (kind.equals("1")) {
					tempAnswer += ",";
					tempAnswer += mapAnswer.get("answer").toString().trim();
				} else if (kind.equals("2")) {
					String qCount = mapQuestion.get("subCounts").toString()
							.trim();
					tempAnswer += ",";
					tempAnswer += qCount;
					for (int index = 1; index <= Integer.parseInt(qCount); index++) {
						tempAnswer += ",";
						tempAnswer += mapAnswer.get("subAnswer" + index)
								.toString().trim();
					}
				} else if (kind.equals("3")) {
					tempAnswer += ",";
					tempAnswer += mapAnswer.get("answer").toString().trim();
				}

				Intent intent = new Intent();
				intent.setClass(ModifyOneActivity.this, ModifyActivity.class);
				intent.putExtra("pslid", pslid);
				intent.putExtra("filename", filename);
				intent.putExtra("personname", personname);
				intent.putStringArrayListExtra("listQ", listQuestion);
				listAnswer.set(currentCount - 1, tempAnswer);
				intent.putStringArrayListExtra("listA", listAnswer);
				startActivity(intent);
				ModifyOneActivity.this.finish();
			}
		});
	}

	public void initQuestion(String strQuestion, String strAnswer) {
		String[] infoArrayQ = strQuestion.split(",");
		String[] infoArrayA = strAnswer.split(",");
		mapQuestion = new HashMap<String, Object>();
		mapAnswer = new HashMap<String, Object>();

		mapQuestion.put("kindQuestion", infoArrayQ[0]);
		mapAnswer.put("kindAnswer", infoArrayA[0]);
		mapQuestion.put("question", infoArrayQ[1]);
		if (infoArrayQ[0].equals("1")) {
			mapAnswer.put("answer", infoArrayA[1]);
		} else if (infoArrayQ[0].equals("2")) {
			mapQuestion.put("subCounts", infoArrayQ[2]);
			mapAnswer.put("answerCounts", infoArrayA[1]);
			for (int j = 3; j < infoArrayQ.length; j++) {
				int temp = j - 2;
				String tempKeyQ = "subQuestion" + temp;
				String tempKeyA = "subAnswer" + temp;
				mapQuestion.put(tempKeyQ, infoArrayQ[j]);
				mapAnswer.put(tempKeyA, infoArrayA[j - 1]);
			}
		} else if (infoArrayQ[0].equals("3")) {
			mapQuestion.put("subCounts", infoArrayQ[2]);
			mapAnswer.put("answer", infoArrayA[1]);
			for (int j = 3; j < infoArrayQ.length; j++) {
				int temp = j - 2;
				String tempKeyQ = "subQuestion" + temp;
				mapQuestion.put(tempKeyQ, infoArrayQ[j]);
			}
		}
	}

	public void changPageNo() {
		String pageNoText;
		pageNoText = "" + questionCounts;
		pageNoText = "/" + pageNoText;
		pageNoText = currentCount + pageNoText;
		pageNo.setText(pageNoText);
	}

	public void showQuestion() {
		Map<String, Object> mapSaveAnswer = mapAnswer;
		mapTemp = mapQuestion;
		String kind = mapTemp.get("kindQuestion").toString().trim();
		if (kind.equals("1")) {
			clearClickHigh(1);
			hiddAllButton();
			showYesNoButton();
			String tempShow = mapTemp.get("question").toString().trim();
			if (tempShow.length() > 76) {
				inTextHigh.setText(tempShow);
				showInTextHigh();
			} else {
				inText.setText(tempShow);
				showInText();
			}
			String tempAnswer = mapSaveAnswer.get("answer").toString().trim();
			if (tempAnswer.trim().equals(STR_YES)) {
				btYes.setColorHigh(Color.rgb(162, 205, 144));
				btYes.invalidate();
			} else if (tempAnswer.trim().equals(STR_NO)) {
				btNo.setColorHigh(Color.rgb(162, 205, 144));
				btNo.invalidate();
			}
		} else if (kind.equals("2")) {
			clearClickHigh(2);
			hiddAllButton();
			String tempShow = mapTemp.get("question").toString().trim();
			inText.setText(tempShow);
			showInText();

			String qCount = mapTemp.get("subCounts").toString().trim();
			for (int index = 1; index <= Integer.parseInt(qCount); index++) {
				String tempQuestion = mapTemp.get("subQuestion" + index)
						.toString().trim();
				subQuestion[index - 1].setText(tempQuestion);
				showSubBtYesNo(index - 1);
				String tempAnswer = mapSaveAnswer.get("subAnswer" + index)
						.toString().trim();
				if (tempAnswer.trim().equals(STR_YES)) {
					btSubYes[index - 1].setColorHigh(Color.rgb(162, 205, 144));
					btSubYes[index - 1].invalidate();
				} else if (tempAnswer.trim().equals(STR_NO)) {
					btSubNo[index - 1].setColorHigh(Color.rgb(162, 205, 144));
					btSubNo[index - 1].invalidate();
				}
			}
		} else if (kind.equals("3")) {
			clearClickHigh(3);
			hiddAllButton();
			String tempShow = mapTemp.get("question").toString().trim();
			if (tempShow.length() > 76) {
				inTextHigh.setText(tempShow);
				showInTextHigh();
			} else {
				inText.setText(tempShow);
				showInText();
			}
			String tempAnswer = mapSaveAnswer.get("answer").toString().trim();
			String qCount = mapTemp.get("subCounts").toString().trim();
			for (int index = 1; index <= Integer.parseInt(qCount); index++) {
				String tempQuestion = mapTemp.get("subQuestion" + index)
						.toString().trim();
				itemAnswerText[index - 1].setText(tempQuestion);
				showItemAnswer(index - 1);
				if (tempAnswer.trim().equals(tempQuestion)) {
					clickItemAnswer(index - 1);
				}
			}
		}
	}

	public void saveAnswer(String answer, int ianswer) {
		Map<String, Object> mapSaveAnswer = mapAnswer;
		String kind = mapSaveAnswer.get("kindAnswer").toString().trim();
		if (kind.equals("1")) {
			mapSaveAnswer.put("answer", answer);
		} else if (kind.equals("2")) {
			mapSaveAnswer.put("subAnswer" + ianswer, answer);
		} else if (kind.equals("3")) {
			mapSaveAnswer.put("answer",
					mapQuestion.get("subQuestion" + Integer.parseInt(answer))
							.toString().trim());
		}
	}

	public void hiddYesNoButton() {
		btYes.setVisibility(View.GONE);
		btYes.invalidate();
		btNo.setVisibility(View.GONE);
		btNo.invalidate();
		yesText.setVisibility(View.GONE);
		yesText.invalidate();
		noText.setVisibility(View.GONE);
		noText.invalidate();
	}

	public void showYesNoButton() {
		btYes.setVisibility(View.VISIBLE);
		btYes.invalidate();
		btNo.setVisibility(View.VISIBLE);
		btNo.invalidate();
		yesText.setVisibility(View.VISIBLE);
		yesText.invalidate();
		noText.setVisibility(View.VISIBLE);
		noText.invalidate();
	}

	public void hiddSubBtYesNo(int i) {
		subQuestion[i].setVisibility(View.GONE);
		subQuestion[i].invalidate();
		subBtNo[i].setVisibility(View.GONE);
		subBtNo[i].invalidate();
		subBtYes[i].setVisibility(View.GONE);
		subBtYes[i].invalidate();
		btSubYes[i].setVisibility(View.GONE);
		btSubYes[i].invalidate();
		btSubNo[i].setVisibility(View.GONE);
		btSubNo[i].invalidate();
	}

	public void showSubBtYesNo(int i) {
		subQuestion[i].setVisibility(View.VISIBLE);
		subQuestion[i].invalidate();
		subBtNo[i].setVisibility(View.VISIBLE);
		subBtNo[i].invalidate();
		subBtYes[i].setVisibility(View.VISIBLE);
		subBtYes[i].invalidate();
		btSubYes[i].setVisibility(View.VISIBLE);
		btSubYes[i].invalidate();
		btSubNo[i].setVisibility(View.VISIBLE);
		btSubNo[i].invalidate();
	}

	public void showInText() {
		outViewHigh.setVisibility(View.GONE);
		outView.setVisibility(View.VISIBLE);
		inTextHigh.setVisibility(View.GONE);
		inText.setVisibility(View.VISIBLE);

		outViewHigh.invalidate();
		outView.invalidate();
		inTextHigh.invalidate();
		inText.invalidate();
	}

	public void showInTextHigh() {
		outViewHigh.setVisibility(View.VISIBLE);
		outView.setVisibility(View.GONE);
		inTextHigh.setVisibility(View.VISIBLE);
		inText.setVisibility(View.GONE);

		outViewHigh.invalidate();
		outView.invalidate();
		inTextHigh.invalidate();
		inText.invalidate();
	}

	public void hiddItemAnswer(int i) {
		itemAnswerText[i].setVisibility(View.GONE);
		itemAnswerText[i].invalidate();
		itemAnswer[i].setVisibility(View.GONE);
		itemAnswer[i].invalidate();
	}

	public void showItemAnswer(int i) {
		itemAnswerText[i].setVisibility(View.VISIBLE);
		itemAnswerText[i].invalidate();
		itemAnswer[i].setVisibility(View.VISIBLE);
		itemAnswer[i].invalidate();
	}

	public void hiddAllButton() {
		hiddYesNoButton();
		hiddSubBtYesNo(0);
		hiddSubBtYesNo(1);
		hiddSubBtYesNo(2);
		hiddSubBtYesNo(3);
		hiddItemAnswer(0);
		hiddItemAnswer(1);
		hiddItemAnswer(2);
		hiddItemAnswer(3);
		hiddItemAnswer(4);
	}

	public void clickItemAnswer(int i) {
		if (i == 0) {
			itemAnswer[0].setColorHigh(Color.rgb(162, 205, 144));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[4].invalidate();
		} else if (i == 1) {
			itemAnswer[0].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(162, 205, 144));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[4].invalidate();
		} else if (i == 2) {
			itemAnswer[0].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(162, 205, 144));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[4].invalidate();
		} else if (i == 3) {
			itemAnswer[0].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(162, 205, 144));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[4].invalidate();
		} else if (i == 4) {
			itemAnswer[0].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(162, 205, 144));
			itemAnswer[4].invalidate();
		}
	}

	public void clearClickHigh(int i) {
		if (i == 1) {
			btYes.setColorHigh(Color.rgb(220, 230, 242));
			btYes.invalidate();
			btNo.setColorHigh(Color.rgb(220, 230, 242));
			btNo.invalidate();
		} else if (i == 2) {
			btSubNo[0].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[0].invalidate();
			btSubYes[0].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[0].invalidate();
			btSubNo[1].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[1].invalidate();
			btSubYes[1].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[1].invalidate();
			btSubNo[2].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[2].invalidate();
			btSubYes[2].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[2].invalidate();
			btSubNo[3].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[3].invalidate();
			btSubYes[3].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[3].invalidate();
		} else if (i == 3) {
			itemAnswer[0].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[0].invalidate();
			itemAnswer[1].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[1].invalidate();
			itemAnswer[2].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[2].invalidate();
			itemAnswer[3].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[3].invalidate();
			itemAnswer[4].setColorHigh(Color.rgb(220, 230, 242));
			itemAnswer[4].invalidate();
		}
	}
}
