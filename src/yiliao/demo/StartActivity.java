package yiliao.demo;

import com.fs.yiliao.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartActivity extends Activity {
	private Paint paint = new Paint();//提取出onDraw中的
	private TextView hospitalInfo[] = new TextView[7];
	private BorderTextView test[] = new BorderTextView[9];
	private LinearLayout lout[] = new LinearLayout[9];
	private ImageButton exitBt;
	private ImageButton nextBt;
	private BroadcastReceiver mFinishReceiver;
	
	private LinearLayout layoutYes;
	private LinearLayout layoutNo;
	private SubYesNoButton BtExitYes;
	private SubYesNoButton BtExitNo;
	private LinearLayout layoutOut;
	private BorderView BorderOut;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HideStatusBar();转移到layout中设置android:theme="@android:style/Theme.NoTitleBar.Fullscreen
        setContentView(R.layout.startmain);
        hospitalInfo[0] = (TextView)this.findViewById(R.id.tvIryou);
        hospitalInfo[1] = (TextView)this.findViewById(R.id.tvIenn);
        hospitalInfo[2] = (TextView)this.findViewById(R.id.tvKensin);
        hospitalInfo[3] = (TextView)this.findViewById(R.id.tvZipcode);
        hospitalInfo[4] = (TextView)this.findViewById(R.id.tvAddress);
        hospitalInfo[5] = (TextView)this.findViewById(R.id.tvPhone);
        hospitalInfo[6] = (TextView)this.findViewById(R.id.tvFax);
        
        lout[0] = (LinearLayout)this.findViewById(R.id.lout1);
        lout[1] = (LinearLayout)this.findViewById(R.id.lout2);
        lout[2] = (LinearLayout)this.findViewById(R.id.lout3);
        lout[3] = (LinearLayout)this.findViewById(R.id.lout4);
        lout[4] = (LinearLayout)this.findViewById(R.id.lout5);
        lout[5] = (LinearLayout)this.findViewById(R.id.lout6);
        lout[6] = (LinearLayout)this.findViewById(R.id.lout7);
        lout[7] = (LinearLayout)this.findViewById(R.id.lout8);
        lout[8] = (LinearLayout)this.findViewById(R.id.lout9);
        for (int i = 0; i<9 ; i++){
        	test[i] = new BorderTextView(this,null);
        	test[i].setWidth(600);
            test[i].setHeight(150);
            lout[i].addView(test[i]);
        }
        // 初始化，创建写入要读取的数据文件
        MyToolKit.writeFileSdcard(this.getString(R.string.path_person), 
        						this.getString(R.string.str_person));
        MyToolKit.writeFileSdcard(this.getString(R.string.path_question)
        						+this.getString(R.string.file_tokutei), 
        						this.getString(R.string.str_tokutei));
        MyToolKit.writeFileSdcard(this.getString(R.string.path_question)
        						+this.getString(R.string.file_seikatu), 
        						this.getString(R.string.str_seikatu));
        
        // 读取文件
        String info = readFileSdcard(this.getString(R.string.path_hospital));
        String[] infoArray=info.split(",");
        for (int i = 0; i<7 ; i++){
        	hospitalInfo[i].setText(infoArray[i]);
        }
        // 关闭功能
        mFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                   if("finish".equals(intent.getAction())) {
                      finish();
               }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(mFinishReceiver, filter);
        

        
        exitBt = (ImageButton)this.findViewById(R.id.exitButton);
        nextBt = (ImageButton)this.findViewById(R.id.nextButton);
        
        exitBt.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// exit app
        				showDialog(StartActivity.this,"問診票終了しますか");
					}
        		}
        );
        
        nextBt.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				// next to search page
        				Intent intent = new Intent();
        				intent.setClass(StartActivity.this, SearchActivity.class);
        				startActivity(intent);
					}
        		}
        );
    }
    
    @Override
    public void onDestroy() {
    	unregisterReceiver(mFinishReceiver);
        super.onDestroy();
    }

    private void showDialog(Context context, String showInfo) {

   	 	AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dlg = builder.create();
        Window w=dlg.getWindow();
        WindowManager.LayoutParams lp =w.getAttributes();
        dlg.onWindowAttributesChanged(lp);
        lp.x=20;
        lp.y=0;

        dlg.show();
        dlg.getWindow().setContentView(R.layout.dialog);
        
        layoutYes = (LinearLayout) dlg.findViewById(R.id.yesButton);
        layoutNo = (LinearLayout) dlg.findViewById(R.id.noButton);
        layoutOut = (LinearLayout) dlg.findViewById(R.id.outBorder);
        
        BtExitYes = new SubYesNoButton(this,null);
        BtExitYes.setColorHigh(Color.rgb(205, 205, 132));
        BtExitYes.setWidth(600);
        BtExitYes.setHeight(250);
        BtExitYes.setBackgroundColor(Color.TRANSPARENT);
        layoutYes.addView(BtExitYes);

        BtExitNo = new SubYesNoButton(this,null);
        BtExitNo.setColorHigh(Color.rgb(205, 205, 132));
        BtExitNo.setWidth(600);
        BtExitNo.setHeight(250);
        BtExitNo.setBackgroundColor(Color.TRANSPARENT);
        layoutNo.addView(BtExitNo);
        
        BorderOut = new BorderView(this,null);
        BorderOut.setColorHigh(Color.rgb(154, 255, 154));
        BorderOut.setWidth(600);
        BorderOut.setHeight(250);
        BorderOut.setBackgroundColor(Color.TRANSPARENT);
        layoutOut.addView(BorderOut);

        TextView showInfoTv;
        showInfoTv = (TextView) dlg.findViewById(R.id.exitInfo);
        showInfoTv.setText(showInfo);
        
        BtExitYes.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
       				BtExitYes.setColorHigh(Color.rgb(162, 205, 144));
       				BtExitYes.invalidate();
        				dlg.cancel();
        				StartActivity.this.finish();
					}
        		}
        );
        
        BtExitNo.setOnClickListener(
         		new Button.OnClickListener(){
         			@Override
 					public void onClick(View v) {
         				BtExitNo.setColorHigh(Color.rgb(162, 205, 144));
         				BtExitNo.invalidate();
         				dlg.cancel();
 					}
         		}
        );
   }

    // 读取CSV文件
    public String readFileSdcard(String fileName){
		String res=""; 
		res=this.getString(R.string.str_hospital);
//        try{ 
//        	FileInputStream fin = new FileInputStream(fileName); 
//        	int length = fin.available();
//        	byte [] buffer = new byte[length];
//        	fin.read(buffer);
//        	res = EncodingUtils.getString(buffer, "Shift-JIS"); 
//        	fin.close();     
//        } 
//        catch(Exception e){ 
//        	e.printStackTrace(); 
//        } 
        return res; 
	}
}
