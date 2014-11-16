package yiliao.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yiliao.demo.tool.MyToolKit;
import yiliao.demo.tool.SubYesNoButton;
import yiliao.demo.tool.YesNoButton;

import yiliao.demo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuestionActivity extends Activity {
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
	private ImageButton ibtBack;
	private ImageButton ibtNext;
	private LinearLayout yesLayout;
	private LinearLayout noLayout;
	private YesNoButton btYes;
	private YesNoButton btNo;
	private List<Map<String, Object>> ltQuestion;
	private List<Map<String, Object>> ltAnswer;
	private Map<String, Object> mapTemp;
	private int currentCount = 1;
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
	private int clickFlg = 0;
	private String sPslid;
	private String sKindFileName;
	private String sPersonName;
	
	private LinearLayout layoutOut;
	private BorderView Border;
	
	private final static String STR_YES = "はい";
	private final static String STR_NO = "いいえ";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HideStatusBar();
        setContentView(R.layout.question);
        tvPersonName = (TextView) this.findViewById(R.id.personname);
    	Intent confirmIntent = getIntent();

    	sPersonName = confirmIntent.getStringExtra("kajiname").toString().trim();
    	sPslid = confirmIntent.getStringExtra("pslid").toString().trim();
    	sKindFileName = confirmIntent.getStringExtra("filename").toString().trim();
    	tvPersonName.setText( sPersonName );
    	
        pageNo = (TextView) this.findViewById(R.id.pageNo);
        ibtBack = (ImageButton)this.findViewById(R.id.ibtBack);
        ibtNext = (ImageButton)this.findViewById(R.id.ibtNext);

        yesText = (TextView) this.findViewById(R.id.btyestext);
        noText = (TextView) this.findViewById(R.id.btnotext);
        yesLayout = (LinearLayout)this.findViewById(R.id.yeslayout);
        noLayout = (LinearLayout)this.findViewById(R.id.nolayout);
    	layoutQuestionHigh = (LinearLayout)this.findViewById(R.id.questionHigh);
    	inTextHigh = (TextView)this.findViewById(R.id.questionTextHigh);
    	layoutQuestion = (LinearLayout)this.findViewById(R.id.question);
    	inText = (TextView)this.findViewById(R.id.questionText);

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

		subyesLayout[0] = (LinearLayout)this.findViewById(R.id.subYesLay1);
		subnoLayout[0] = (LinearLayout)this.findViewById(R.id.subNoLay1);
		subyesLayout[1] = (LinearLayout)this.findViewById(R.id.subYesLay2);
		subnoLayout[1] = (LinearLayout)this.findViewById(R.id.subNoLay2);
		subyesLayout[2] = (LinearLayout)this.findViewById(R.id.subYesLay3);
		subnoLayout[2]= (LinearLayout)this.findViewById(R.id.subNoLay3);
		subyesLayout[3] = (LinearLayout)this.findViewById(R.id.subYesLay4);
		subnoLayout[3] = (LinearLayout)this.findViewById(R.id.subNoLay4);

		// init button「はい」、「いいえ」
        btYes = new YesNoButton(this,null);
        btYes.setColorHigh(Color.rgb(220, 230, 242));
        btYes.setWidth(600);
        btYes.setHeight(250);
        btYes.setBackgroundColor(Color.TRANSPARENT);
        yesLayout.addView(btYes);

        btNo = new YesNoButton(this,null);
        btNo.setColorHigh(Color.rgb(220, 230, 242));
        btNo.setWidth(600);
        btNo.setHeight(250);
        btNo.setBackgroundColor(Color.TRANSPARENT);
        noLayout.addView(btNo);

		for(int i=0; i<4; i++){
			btSubYes[i] = new SubYesNoButton(this,null);
			btSubYes[i].setColorHigh(Color.rgb(220, 230, 242));
			btSubYes[i].setWidth(600);
			btSubYes[i].setHeight(250);
			btSubYes[i].setBackgroundColor(Color.TRANSPARENT);
			subyesLayout[i].addView(btSubYes[i]);
			
			btSubNo[i] = new SubYesNoButton(this,null);
			btSubNo[i].setColorHigh(Color.rgb(220, 230, 242));
			btSubNo[i].setWidth(600);
			btSubNo[i].setHeight(250);
			btSubNo[i].setBackgroundColor(Color.TRANSPARENT);
			subnoLayout[i].addView(btSubNo[i]);
		}

        outView = new QuestionTextView(this,null);
        outView.setWidth(600);
        outView.setHeight(250);

        outViewHigh = new QuestionTextView(this,null);
        outViewHigh.setWidth(600);
        outViewHigh.setHeight(250);

        layoutQuestionHigh.addView(outViewHigh);
        layoutQuestion.addView(outView);

        itemAnswerText[0] = (TextView)this.findViewById(R.id.itemAnswerText1);
        itemLay[0] = (LinearLayout)this.findViewById(R.id.itemLay1);
        itemAnswerText[1] = (TextView)this.findViewById(R.id.itemAnswerText2);
        itemLay[1] = (LinearLayout)this.findViewById(R.id.itemLay2);
        itemAnswerText[2] = (TextView)this.findViewById(R.id.itemAnswerText3);
        itemLay[2] = (LinearLayout)this.findViewById(R.id.itemLay3);
        itemAnswerText[3] = (TextView)this.findViewById(R.id.itemAnswerText4);
        itemLay[3] = (LinearLayout)this.findViewById(R.id.itemLay4);
        itemAnswerText[4] = (TextView)this.findViewById(R.id.itemAnswerText5);
        itemLay[4] = (LinearLayout)this.findViewById(R.id.itemLay5);
        
        for(int i=0; i<5; i++){
        	itemAnswer[i] = new SubYesNoButton(this,null);
        	itemAnswer[i].setColorHigh(Color.rgb(220, 230, 242));
        	itemAnswer[i].setWidth(600);
        	itemAnswer[i].setHeight(250);
        	itemAnswer[i].setBackgroundColor(Color.TRANSPARENT);
        	itemLay[i].addView(itemAnswer[i]);
        }
		
        // load question file
        // init ltQuestion ltAnswer questionCounts
        initQAList(sKindFileName);

        // init page number
        changPageNo();

        // init question
		showQuestion();

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		itemAnswer[0].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				clickItemAnswer(0);
        				saveAnswer("1",0);
					}
        		}
        );
		itemAnswer[1].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				clickItemAnswer(1);
        				saveAnswer("2",0);
					}
        		}
        );
		itemAnswer[2].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				clickItemAnswer(2);
        				saveAnswer("3",0);      				
					}
        		}
        );
		itemAnswer[3].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				clickItemAnswer(3);
        				saveAnswer("4",0);      				
					}
        		}
        );
		itemAnswer[4].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				clickItemAnswer(4);
        				saveAnswer("5",0);
					}
        		}
        );
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        btSubYes[0].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubYes[0].setColorHigh(Color.rgb(162, 205, 144));
        				btSubYes[0].invalidate();
        				btSubNo[0].setColorHigh(Color.rgb(220, 230, 242));
        				btSubNo[0].invalidate();
        				saveAnswer(STR_YES,1);
					}
        		}
        );
        btSubNo[0].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubNo[0].setColorHigh(Color.rgb(162, 205, 144));
        				btSubNo[0].invalidate();
        				btSubYes[0].setColorHigh(Color.rgb(220, 230, 242));
        				btSubYes[0].invalidate();
        				saveAnswer(STR_NO,1);
					}
        		}
        );
        btSubYes[1].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubYes[1].setColorHigh(Color.rgb(162, 205, 144));
        				btSubYes[1].invalidate();
        				btSubNo[1].setColorHigh(Color.rgb(220, 230, 242));
        				btSubNo[1].invalidate();
        				saveAnswer(STR_YES,2);
					}
        		}
        );
        btSubNo[1].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubNo[1].setColorHigh(Color.rgb(162, 205, 144));
        				btSubNo[1].invalidate();
        				btSubYes[1].setColorHigh(Color.rgb(220, 230, 242));
        				btSubYes[1].invalidate();
        				saveAnswer(STR_NO,2);
					}
        		}
        ); 
        btSubYes[2].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubYes[2].setColorHigh(Color.rgb(162, 205, 144));
        				btSubYes[2].invalidate();
        				btSubNo[2].setColorHigh(Color.rgb(220, 230, 242));
        				btSubNo[2].invalidate();
        				saveAnswer(STR_YES,3);
					}
        		}
        );   
        btSubNo[2].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubNo[2].setColorHigh(Color.rgb(162, 205, 144));
        				btSubNo[2].invalidate();
        				btSubYes[2].setColorHigh(Color.rgb(220, 230, 242));
        				btSubYes[2].invalidate();
        				saveAnswer(STR_NO,3);
					}
        		}
        );
        btSubYes[3].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubYes[3].setColorHigh(Color.rgb(162, 205, 144));
        				btSubYes[3].invalidate();
        				btSubNo[3].setColorHigh(Color.rgb(220, 230, 242));
        				btSubNo[3].invalidate();
        				saveAnswer(STR_YES,4);
					}
        		}
        );   
        btSubNo[3].setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btSubNo[3].setColorHigh(Color.rgb(162, 205, 144));
        				btSubNo[3].invalidate();
        				btSubYes[3].setColorHigh(Color.rgb(220, 230, 242));
        				btSubYes[3].invalidate();
        				saveAnswer(STR_NO,4);
					}
        		}
        );

        btYes.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
        				btYes.setColorHigh(Color.rgb(162, 205, 144));
        				btYes.invalidate();
        				btNo.setColorHigh(Color.rgb(220, 230, 242));
        				btNo.invalidate();
        				saveAnswer(STR_YES,0);
					}
        		}
        );
        
        btNo.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// set button high
           				btNo.setColorHigh(Color.rgb(162, 205, 144));
        				btNo.invalidate();
        				btYes.setColorHigh(Color.rgb(220, 230, 242));
        				btYes.invalidate();
        				saveAnswer(STR_NO,0);
					}
        		}
        );

        //////////////////////////////////////////////////////////////////////
        ibtBack.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// back to question
        				if( currentCount == 1 ){
        					QuestionActivity.this.finish();
        				}else{
        					currentCount--;
        					changPageNo();
        					showQuestion();
        				}
					}
        		}
        );
        
        ibtNext.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// next to question
        				if( currentCount >= questionCounts ){
//        					showToast("問題がありません。");
        					showModify();
        				}else{
        					clickAllYesNoButton();
        					if( clickFlg == 1 ){
            					currentCount++;
            					changPageNo();
            					showQuestion();
            					clickFlg = 0;
        					}else{
            					showToast("この質問を答えしてください");
        					}
        				}
					}
        		}
        );
    }

    private void showModify(){
    	final ArrayList<String> ltsQuestion = new ArrayList<String>();
    	final ArrayList<String> ltsAnswer = new ArrayList<String>();
    	
    	for(int i=0; i<ltQuestion.size(); i++){
    		Map<String, Object> tempMapQuestion = new HashMap<String, Object>();
    		Map<String, Object> tempMapAnswer = new HashMap<String, Object>();
    		tempMapQuestion = ltQuestion.get(i);
    		tempMapAnswer = ltAnswer.get(i);
    		
    		String tempQuestion;
    		String tempAnswer;
    		String kind;
    		kind = tempMapQuestion.get("kindQuestion").toString().trim();
    		tempQuestion = kind;
    		tempAnswer = tempMapAnswer.get("kindAnswer").toString().trim();
    		
            if(kind.equals("1")){
            	tempQuestion += ",";
            	tempQuestion += tempMapQuestion.get("question").toString().trim();
            	tempAnswer += ",";
            	tempAnswer += tempMapAnswer.get("answer").toString().trim();
            }else if(kind.equals("2")){
            	int counts;
            	counts = Integer.parseInt(tempMapQuestion.get("subCounts").toString().trim());
            	tempQuestion += ",";
            	tempQuestion += tempMapQuestion.get("question").toString().trim();
            	tempQuestion += ",";
            	tempQuestion += tempMapQuestion.get("subCounts").toString().trim();
            	tempAnswer += ",";
            	tempAnswer += tempMapAnswer.get("answerCounts").toString().trim();
            	for(int j = 1; j <= counts; j++){
            		String tempKeyQ = "subQuestion"+j;
            		String tempKeyA = "subAnswer"+j;
                	tempQuestion += ",";
                	tempQuestion += tempMapQuestion.get(tempKeyQ).toString().trim();
                	tempAnswer += ",";
                	tempAnswer += tempMapAnswer.get(tempKeyA).toString().trim();
            	}
            }else if(kind.equals("3")){
            	int counts;
            	counts = Integer.parseInt(tempMapQuestion.get("subCounts").toString().trim());
            	tempQuestion += ",";
            	tempQuestion += tempMapQuestion.get("question").toString().trim();
            	tempQuestion += ",";
            	tempQuestion += tempMapQuestion.get("subCounts").toString().trim();
            	tempAnswer += ",";
            	tempAnswer += tempMapAnswer.get("answer").toString().trim();
            	for(int j = 1; j <= counts; j++){
            		String tempKeyQ = "subQuestion"+j;
            		tempQuestion += ",";
            		tempQuestion += tempMapQuestion.get(tempKeyQ).toString().trim();
            	}
            }
            ltsQuestion.add(tempQuestion);
            ltsAnswer.add(tempAnswer);
    	}
    	
    	
		Intent intent = new Intent();
		intent.setClass(QuestionActivity.this, ModifyActivity.class);
		intent.putExtra("pslid",sPslid);
		intent.putExtra("filename",sKindFileName);
		intent.putExtra("personname", sPersonName);
		intent.putStringArrayListExtra("listQ", ltsQuestion);
		intent.putStringArrayListExtra("listA", ltsAnswer);
		startActivity(intent);
		QuestionActivity.this.finish();
    }
    public void initQAList(String filename){
        ltQuestion = new ArrayList<Map<String, Object>>();
        ltAnswer = new ArrayList<Map<String, Object>>();
        
        String path = this.getString(R.string.path_question)+filename+".csv";
        String info = MyToolKit.readFileSdcard(path);
        String[] infoArray=info.split(this.getString(R.string.str_split));
        questionCounts = infoArray.length;
        for (int i = 0; i < infoArray.length; i++) {
        	Map<String, Object> mapQuestion = new HashMap<String, Object>();
        	Map<String, Object> mapAnswer = new HashMap<String, Object>();
            String oneInfo = infoArray[i];
            String[] oneInfoArray=oneInfo.split(",");
            mapQuestion.put("kindQuestion", oneInfoArray[0]);
            mapAnswer.put("kindAnswer", oneInfoArray[0]);
            if(oneInfoArray[0].equals("1")){
            	mapQuestion.put("question", oneInfoArray[1]);
            	mapAnswer.put("answer", "不明");
            }else if(oneInfoArray[0].equals("2")){
            	mapQuestion.put("question", oneInfoArray[1]);
            	mapQuestion.put("subCounts", oneInfoArray.length-2);
            	mapAnswer.put("answerCounts", oneInfoArray.length-2);
            	for(int j = 2; j < oneInfoArray.length; j++){
            		int temp = j-1;
            		String tempKeyQ = "subQuestion"+temp;
            		String tempKeyA = "subAnswer"+temp;
            		mapQuestion.put(tempKeyQ, oneInfoArray[j]);
            		mapAnswer.put(tempKeyA, "不明");
            	}
            }else if(oneInfoArray[0].equals("3")){
            	mapQuestion.put("question", oneInfoArray[1]);
            	mapAnswer.put("answer", "not click");
            	mapQuestion.put("subCounts", oneInfoArray.length-2);
            	for(int j = 2; j < oneInfoArray.length; j++){
            		int temp = j-1;
            		String tempKeyQ = "subQuestion"+temp;
            		mapQuestion.put(tempKeyQ, oneInfoArray[j]);
            	}
            }
            ltQuestion.add(mapQuestion);
            ltAnswer.add(mapAnswer);
        }
    }
    
    public void changPageNo(){
        String pageNoText;
        pageNoText = ""+questionCounts;
        pageNoText = "/"+pageNoText;
        pageNoText = currentCount + pageNoText;
        pageNo.setText(pageNoText);
    }
    
    public void showQuestion(){
    	Map<String, Object> mapSaveAnswer = ltAnswer.get(currentCount-1);
        mapTemp = ltQuestion.get(currentCount-1);
        String kind = mapTemp.get("kindQuestion").toString().trim();
        if( kind.equals("1") ){
        	clearClickHigh(1);
        	hiddAllButton();
        	showYesNoButton();
            String tempShow = mapTemp.get("question").toString().trim();
            if( tempShow.length() > 76 ){
                inTextHigh.setText(tempShow);
                showInTextHigh();
            }else{
                inText.setText(tempShow);
                showInText();
            }
            String tempAnswer = mapSaveAnswer.get("answer").toString().trim();
            if( tempAnswer == STR_YES ){
				btYes.setColorHigh(Color.rgb(162, 205, 144));
				btYes.invalidate();
            }else if( tempAnswer == STR_NO ){
				btNo.setColorHigh(Color.rgb(162, 205, 144));
				btNo.invalidate();
            }
        }else if( kind.equals("2") ){
        	clearClickHigh(2);
        	hiddAllButton();
            String tempShow = mapTemp.get("question").toString().trim();
            inText.setText(tempShow);
            showInText();
            
            String qCount = mapTemp.get("subCounts").toString().trim();
            for(int index=1; index <= Integer.parseInt(qCount); index++ ){
            	String tempQuestion = mapTemp.get("subQuestion"+index).toString().trim();
            	subQuestion[index-1].setText(tempQuestion);
            	showSubBtYesNo(index-1);
                String tempAnswer = mapSaveAnswer.get("subAnswer"+index).toString().trim();
                if( tempAnswer == STR_YES ){
                	btSubYes[index-1].setColorHigh(Color.rgb(162, 205, 144));
                	btSubYes[index-1].invalidate();
                }else if( tempAnswer == STR_NO ){
                	btSubNo[index-1].setColorHigh(Color.rgb(162, 205, 144));
                	btSubNo[index-1].invalidate();
                }
            }
        }else if( kind.equals("3") ){
        	clearClickHigh(3);
        	hiddAllButton();
            String tempShow = mapTemp.get("question").toString().trim();
            if( tempShow.length() > 76 ){
                inTextHigh.setText(tempShow);
                showInTextHigh();
            }else{
                inText.setText(tempShow);
                showInText();
            }
            String tempAnswer = mapSaveAnswer.get("answer").toString().trim();
            String qCount = mapTemp.get("subCounts").toString().trim();
            for(int index=1; index <= Integer.parseInt(qCount); index++ ){
            	String tempQuestion = mapTemp.get("subQuestion"+index).toString().trim();
            	itemAnswerText[index-1].setText(tempQuestion);
            	showItemAnswer(index-1);
            	if( tempQuestion == tempAnswer ){
            		clickItemAnswer(index-1);
            	}
            }
        }
    }
    
    public void saveAnswer(String answer, int ianswer){
    	Map<String, Object> mapSaveAnswer = ltAnswer.get(currentCount-1);
        String kind = mapSaveAnswer.get("kindAnswer").toString().trim();
        if( kind.equals("1") ){
        	mapSaveAnswer.put("answer", answer);
        }else if( kind.equals("2") ){
           	mapSaveAnswer.put("subAnswer"+ianswer, answer);
        }else if( kind.equals("3") ){
        	Map<String, Object> mapQuestion;
        	mapQuestion = ltQuestion.get(currentCount-1);
        	mapSaveAnswer.put("answer", mapQuestion.get("subQuestion"+Integer.parseInt(answer)).toString().trim());
        }
    }
    
    private void clickAllYesNoButton(){
    	Map<String, Object> mapAnswer = ltAnswer.get(currentCount-1);
        String kind = mapAnswer.get("kindAnswer").toString().trim();
        if( kind.equals("1") ){
            String tempAnswer = mapAnswer.get("answer").toString().trim();
            if( tempAnswer == STR_YES || tempAnswer == STR_NO){
            	clickFlg = 1;
            }
        }else if( kind.equals("2") ){
        	clickFlg = 1;
            String qCount = mapAnswer.get("answerCounts").toString().trim();
            for(int index=1; index <= Integer.parseInt(qCount); index++ ){
            	String tempAnswer = mapAnswer.get("subAnswer"+index).toString().trim();
                if( !(tempAnswer == STR_YES || tempAnswer == STR_NO) ){
                	clickFlg = 0;
                }
            }
        }else if( kind.equals("3") ){
            String tempAnswer = mapAnswer.get("answer").toString().trim();
            if( !tempAnswer.equals("not click") ){
            	clickFlg = 1;
            }
        }
    }
    
    private void showToast(String showInfo){

    	  LayoutInflater inflater = getLayoutInflater();
          View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastRoot));
          
          layoutOut = (LinearLayout) layout.findViewById(R.id.Border);
          
          Border = new BorderView(this,null);
          Border.setColorHigh(Color.rgb(154, 255, 154));
          Border.setWidth(600);
          Border.setHeight(250);
          Border.setBackgroundColor(Color.TRANSPARENT);
          layoutOut.addView(Border);
          
          TextView toastTV = (TextView) layout.findViewById(R.id.toastText);
          toastTV.setText(showInfo);

          Toast toast = new Toast(getApplicationContext());
          toast.setGravity(Gravity.CENTER, 0, 200);
          toast.setDuration(Toast.LENGTH_SHORT);
          toast.setView(layout);
          toast.show();
      }
    
    public void hiddYesNoButton(){
    	btYes.setVisibility(View.GONE);
    	btYes.invalidate();
    	btNo.setVisibility(View.GONE);
    	btNo.invalidate();
    	yesText.setVisibility(View.GONE);
    	yesText.invalidate();
    	noText.setVisibility(View.GONE);
    	noText.invalidate();
    }
    
    public void showYesNoButton(){
    	btYes.setVisibility(View.VISIBLE);
    	btYes.invalidate();
    	btNo.setVisibility(View.VISIBLE);
    	btNo.invalidate();
    	yesText.setVisibility(View.VISIBLE);
    	yesText.invalidate();
    	noText.setVisibility(View.VISIBLE);
    	noText.invalidate();
    }
    
    public void hiddSubBtYesNo(int i){
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
    public void showSubBtYesNo(int i){
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
    public void showInText(){
        outViewHigh.setVisibility(View.GONE);
    	outView.setVisibility(View.VISIBLE);
        inTextHigh.setVisibility(View.GONE);
        inText.setVisibility(View.VISIBLE);
        
        outViewHigh.invalidate();
        outView.invalidate();
        inTextHigh.invalidate();
        inText.invalidate();
    }
    public void showInTextHigh(){
    	outViewHigh.setVisibility(View.VISIBLE);
    	outView.setVisibility(View.GONE);
        inTextHigh.setVisibility(View.VISIBLE);
        inText.setVisibility(View.GONE);

        outViewHigh.invalidate();
        outView.invalidate();
        inTextHigh.invalidate();
        inText.invalidate();
    }
    public void hiddItemAnswer(int i){
    	itemAnswerText[i].setVisibility(View.GONE);
    	itemAnswerText[i].invalidate();
    	itemAnswer[i].setVisibility(View.GONE);
    	itemAnswer[i].invalidate();
    }
    public void showItemAnswer(int i){
    	itemAnswerText[i].setVisibility(View.VISIBLE);
    	itemAnswerText[i].invalidate();
    	itemAnswer[i].setVisibility(View.VISIBLE);
    	itemAnswer[i].invalidate();
    }
    public void hiddAllButton(){
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
    public void clickItemAnswer(int i){
    	if( i==0 ){
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
    	}else if( i==1 ){
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
    	}else if( i==2 ){
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
    	}else if( i==3 ){
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
    	}else if( i==4 ){
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
    public void clearClickHigh(int i){
    	if( i==1 ){
			btYes.setColorHigh(Color.rgb(220, 230, 242));
			btYes.invalidate();
			btNo.setColorHigh(Color.rgb(220, 230, 242));
			btNo.invalidate();
    	}else if( i==2 ){
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
    	}else if( i==3 ){
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
    

    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class QuestionTextView extends TextView
    {
        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            Paint paint = new Paint();

            paint.setColor(Color.rgb(66, 120, 185));
            paint.setStrokeWidth(8);
            canvas.drawCircle(40, 40, 40, paint);
            canvas.drawCircle(this.getWidth()-40, 40, 40, paint);

            canvas.drawCircle(40, this.getHeight()-70, 40, paint);
            canvas.drawCircle(this.getWidth()-40, this.getHeight()-70, 40, paint);

            paint.setColor(Color.rgb(220, 230, 242));
            // left top
            canvas.drawCircle(40, 40, 36, paint);
            canvas.drawRect(40, 0, 80, 80, paint);
            canvas.drawRect(0, 40, 80, 80, paint);
            // right top
            canvas.drawCircle(this.getWidth()-40, 40, 36, paint);
            canvas.drawRect(this.getWidth()-80, 0, this.getWidth()-40, 80, paint);
            canvas.drawRect( this.getWidth()-80, 40, this.getWidth(), 80, paint);
            // left bottom
            canvas.drawCircle(40, this.getHeight()-70, 36, paint);
            canvas.drawRect(0, this.getHeight()-110, 80, this.getHeight()-70, paint);
            canvas.drawRect(40, this.getHeight()-70, 80, this.getHeight()-30, paint);
            // right bottom
            canvas.drawCircle(this.getWidth()-40, this.getHeight()-70, 36, paint);
            canvas.drawRect(this.getWidth()-80, this.getHeight()-110, this.getWidth(), this.getHeight()-70, paint);
            canvas.drawRect(this.getWidth()-80, this.getHeight()-110, this.getWidth()-40, this.getHeight()-30, paint);
            // draw top left right line
            paint.setColor(Color.rgb(66, 120, 185));
            canvas.drawLine(40, 0, this.getWidth()-40, 0, paint);
            canvas.drawLine(0, 40, 0, this.getHeight()-70, paint);
            canvas.drawLine(this.getWidth(), 40, this.getWidth(), this.getHeight()-70, paint);
            // draw out triangle
            Path path = new Path();
            path.moveTo(92,this.getHeight()-33);
            path.lineTo(149,this.getHeight());
            path.lineTo(206,this.getHeight()-33);
            // draw down line
            paint.setStrokeWidth(4);
            canvas.drawLine(40, this.getHeight()-31, 100, this.getHeight()-31, paint);
            canvas.drawLine(200, this.getHeight()-31, this.getWidth()-40, this.getHeight()-31, paint);
            canvas.drawPath(path, paint);
            // draw in triangle
            paint.setColor(Color.rgb(220, 230, 242));
            Path path1 = new Path();
            path1.moveTo(100,this.getHeight()-33);
            path1.lineTo(149,this.getHeight()-5);
            path1.lineTo(198,this.getHeight()-33);
            canvas.drawPath(path1, paint);
            // draw in Rect
            canvas.drawRect(80, 4, this.getWidth()-80, this.getHeight()-33, paint);
            canvas.drawRect(4, 80, this.getWidth()-4, this.getHeight()-110, paint);

        }
        public QuestionTextView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }
    }
    public class BorderView extends TextView
    {
        //no_high:Color.rgb(220, 230, 242)
        //high:Color.rgb(162, 205, 144)
    	int colorBack;

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            Paint paint = new Paint();
            int radius = 30;
            int colorPaint = Color.rgb(66, 120, 185);

            paint.setColor(colorPaint);
            paint.setStrokeWidth(8);
            canvas.drawCircle(radius, radius, radius, paint);
            canvas.drawCircle(this.getWidth()-radius, radius, radius, paint);

            canvas.drawCircle(radius, this.getHeight()-radius, radius, paint);
            canvas.drawCircle(this.getWidth()-radius, this.getHeight()-radius, radius, paint);

            paint.setColor(colorBack);
            // left top
            canvas.drawCircle(radius, radius, radius-4, paint);
            canvas.drawRect(radius, 0, radius*2, radius*2, paint);
            canvas.drawRect(0, radius, radius*2, radius*2, paint);
            // right top
            canvas.drawCircle(this.getWidth()-radius, radius, radius-4, paint);
            canvas.drawRect(this.getWidth()-radius*2, 0, this.getWidth()-radius, radius*2, paint);
            canvas.drawRect( this.getWidth()-radius*2, radius, this.getWidth(), radius*2, paint);
            // left bottom
            canvas.drawCircle(radius, this.getHeight()-radius, radius-4, paint);
            canvas.drawRect(0, this.getHeight()-radius*2, radius*2, this.getHeight()-radius, paint);
            canvas.drawRect(radius, this.getHeight()-radius, radius*2, this.getHeight(), paint);
            // right bottom
            canvas.drawCircle(this.getWidth()-radius, this.getHeight()-radius, radius-4, paint);
            canvas.drawRect(this.getWidth()-radius*2, this.getHeight()-radius*2, this.getWidth(), this.getHeight()-radius, paint);
            canvas.drawRect(this.getWidth()-radius*2, this.getHeight()-radius*2, this.getWidth()-radius, this.getHeight(), paint);
            // draw top left right line
            paint.setColor(colorPaint);
            canvas.drawLine(radius, 0, this.getWidth()-radius, 0, paint);
            canvas.drawLine(0, radius, 0, this.getHeight()-radius, paint);
            canvas.drawLine(this.getWidth(), radius, this.getWidth(), this.getHeight()-radius, paint);
            // draw down line
            canvas.drawLine(radius, this.getHeight(), this.getWidth()-radius, this.getHeight(), paint);
             // draw in Rect
            paint.setColor(colorBack);
            canvas.drawRect(radius*2, 4, this.getWidth()-radius*2, this.getHeight()-4, paint);
            canvas.drawRect(4, radius*2, this.getWidth()-4, this.getHeight()-radius*2, paint);
        }
        public BorderView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }
        void setColorHigh(int color){
        	colorBack = color;
        }
    }
}