package yiliao.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	private Paint paint = new Paint();//提取出onDraw中的
	private ListView lvPerson = null;
	private ListView lvKind = null;
	private ImageButton btiSearch;
	private ImageButton btiExit;
	private EditText edText;
	private List<Map<String, Object>> ltPerson;
	private List<Map<String, Object>> ltClick;
	private List<Map<String, Object>> ltKind;
	private List<Map<String, Object>> ltTemp;
	private Map<String, Object> mapClick;
	private PersonAdapter clickAdapter;
	
	private LinearLayout layoutYes;
	private LinearLayout layoutNo;
	private SubYesNoButton BtExitYes;
	private SubYesNoButton BtExitNo;
	private LinearLayout layoutOut;
	private BorderView BorderOut;
	private BorderView Border;
	
	private BroadcastReceiver mSearchFinishReceiver;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        HideStatusBar();
        setContentView(R.layout.searchlayout);

        lvPerson = (ListView) this.findViewById(R.id.lt1);
        lvKind = (ListView) this.findViewById(R.id.lt2);
        btiSearch = (ImageButton) this.findViewById(R.id.IBT1);
        btiExit = (ImageButton) this.findViewById(R.id.bt_exit);
        edText = (EditText) this.findViewById(R.id.ed_find);
        ltPerson = getPersonData();

    	PersonAdapter initAdapter = new PersonAdapter(ltPerson);
        lvPerson.setAdapter(initAdapter);
        clickAdapter = initAdapter;
        ltClick = ltPerson;
        lvPerson.setBackgroundColor(Color.rgb(166, 166, 166));
        lvKind.setBackgroundColor(Color.rgb(166, 166, 166));

        // 受診者listview click
        lvPerson.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				clickAdapter.setSelectItem(position);
				clickAdapter.notifyDataSetInvalidated();
				mapClick = ltClick.get(position);
				ltKind = getKindData( mapClick, getKindFileNameList());
				SimpleAdapter kindAdapter = new SimpleAdapter(SearchActivity.this,ltKind,R.layout.kindlist,
		                new String[]{"kind","img","status"},
		                new int[]{R.id.kind,R.id.img,R.id.status});
		        lvKind.setAdapter(kindAdapter);
			}
		});

        // 問診票種類listview click
        lvKind.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Map<String, Object> kind = ltKind.get(position);

				if( kind.get("status").toString().trim().equals("未記入") ){
					// turn to 確認ページ
					Intent intent = new Intent();
					intent.setClass(SearchActivity.this, ConfirmActivity.class);
					intent.putExtra("date", mapClick.get("date").toString().trim());
					intent.putExtra("kananame", mapClick.get("kananame").toString().trim());
					intent.putExtra("kajiname", mapClick.get("kajiname").toString().trim());
					intent.putExtra("age", mapClick.get("age").toString().trim());
					intent.putExtra("sex", mapClick.get("sex").toString().trim());
					intent.putExtra("birth", mapClick.get("birth").toString().trim());
					intent.putExtra("pslid", mapClick.get("pslid").toString().trim());
					intent.putExtra("mano", mapClick.get("mano").toString().trim());
					intent.putExtra("mccrse", mapClick.get("mccrse").toString().trim());
					intent.putExtra("mccrsename", mapClick.get("mccrsename").toString().trim());
					intent.putExtra("filename", kind.get("filename").toString().trim());
					intent.putExtra("kindname", kind.get("kind").toString().trim());
					startActivity(intent);
				}else{
					String temp = mapClick.get("kajiname").toString().trim();
					temp += "「";
					temp += kind.get("kind").toString().trim();
					temp += "」:";
					temp += kind.get("status").toString().trim();
					showToast(temp);
				}
			}
		});

        // 受診者検索 click
        btiSearch.setOnClickListener(
        		new Button.OnClickListener(){
        			@Override
					public void onClick(View v) {
        				PersonAdapter tempAdapter = null;
        				if( !edText.getText().toString().trim().equals("") ){
        					// search user return list
        					ltTemp = searchData(ltPerson,edText.getText().toString().trim());
        					if(ltTemp != null){
        						tempAdapter = new PersonAdapter(ltTemp);
        						lvPerson.setAdapter(tempAdapter);
	        					lvPerson.invalidate();
	        					clickAdapter = tempAdapter;
	        					ltClick = ltTemp;
        					}else{
        						List<Map<String, Object>> nodatalist = new ArrayList<Map<String, Object>>();
             					tempAdapter = new PersonAdapter(nodatalist);
            					lvPerson.setAdapter(tempAdapter);
            					lvPerson.invalidate();
            					clickAdapter = tempAdapter;
            					ltClick = nodatalist;
        					}
         				}else{
         					tempAdapter = new PersonAdapter(ltPerson);
        					lvPerson.setAdapter(tempAdapter);
        					lvPerson.invalidate();
        					clickAdapter = tempAdapter;
        					ltClick = ltPerson;
        				}
        				// 種類リストをクリア
        				List<Map<String, Object>> nodatalist = new ArrayList<Map<String, Object>>();
    					SimpleAdapter kindAdapter = new SimpleAdapter(SearchActivity.this,nodatalist,R.layout.kindlist,
    			                new String[]{"kind","img","status"},
    			                new int[]{R.id.kind,R.id.img,R.id.status});
    			        lvKind.setAdapter(kindAdapter);
					}
        		});

        btiExit.setOnClickListener(
        		new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						// 終了
						showDialog(SearchActivity.this, "問診票終了しますか"); 
					}
				});
        
        // 終了ブロードキャストfs
        mSearchFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                   if("SearchFinish".equals(intent.getAction())) {
                	   SearchActivity.this.finish();
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

    // 受診者リスト設定
    private List<Map<String, Object>> getPersonData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        String info = MyToolKit.readFileSdcard(this.getString(R.string.path_person));
        String[] infoArray=info.split(this.getString(R.string.str_split));

        for (int i = 0; i < infoArray.length; i++) {
            map = new HashMap<String, Object>();
            String oneInfo = infoArray[i];
            String[] oneInfoArray=oneInfo.split(",");
            map.put("date", oneInfoArray[0]);
            map.put("kananame", oneInfoArray[1]);
            map.put("kajiname", oneInfoArray[2]);
            map.put("age", oneInfoArray[3]);
            map.put("sex", oneInfoArray[4]);
            map.put("birth", oneInfoArray[5]);
            map.put("pslid", oneInfoArray[6]);
            map.put("mano", oneInfoArray[7]);
            map.put("mccrse", oneInfoArray[8]);
            map.put("mccrsename", oneInfoArray[9]);
            list.add(map);
        }

        return list;
    }

    private List<Map<String, Object>> getKindFileNameList() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        // 暂时改造去掉读文件功能
        // String info = readFileSdcard(this.getString(R.string.path_questions));
        // String[] infoArray=info.split(this.getString(R.string.str_split));
        // 暂时改造不从cvs取直接从string.xml取
        String[] infoArray = {getString(R.string.str_questions1),
        						getString(R.string.str_questions2)};

        for (int i = 0; i < infoArray.length; i++) {
            map = new HashMap<String, Object>();
            String oneInfo = infoArray[i];
            String[] oneInfoArray=oneInfo.split(",");
            map.put("filenameE", oneInfoArray[0]);
            map.put("filenameJ", oneInfoArray[1]);
            list.add(map);
        }
        return list;
    }
    private List<Map<String, Object>> getKindData(Map<String, Object> person, List<Map<String, Object>> kindname) {

        List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapKindName;
        Map<String, Object> map;

        String resultPath = this.getString(R.string.path_result);
        resultPath += person.get("pslid").toString().trim();
        resultPath += "_";

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.getDefault());
        String nowData = format.format(Calendar.getInstance().getTime());
        
        for(int i=0; i < kindname.size(); i++){
        	String path = resultPath;
        	mapKindName = kindname.get(i);
        	path += mapKindName.get("filenameE").toString().trim();
        	path += "_";
        	path += nowData;
        	path += ".csv";
        	File file = new File(path);
        	map = new HashMap<String, Object>();
        	if(file.exists()){
        		map.put("filename", mapKindName.get("filenameE").toString().trim());
        		map.put("kind", mapKindName.get("filenameJ").toString().trim());
        		map.put("img", R.drawable.book);
                map.put("status", " 記入済み");
        	}else{
        		map.put("filename", mapKindName.get("filenameE").toString().trim());
        		map.put("kind", mapKindName.get("filenameJ").toString().trim());
        		map.put("img", R.drawable.write);
                map.put("status", "未記入");
        	}
        	rlist.add(map);
        }

        return rlist;
    }
    private List<Map<String, Object>> searchData(List<Map<String, Object>> list,String name) {
    	List<Map<String, Object>> ltSearch = new ArrayList<Map<String, Object>>();
    	Map<String, Object> map = new HashMap<String, Object>();
    	String temp = "受診者がいません";
    	String tempName;
    	int noPerson = 0;
    	int i;
    	for(i=0;i<list.size();i++){
    		map = list.get(i);
    		tempName = map.get("kajiname").toString().trim();
    		if(tempName.indexOf(name) != -1){
    			ltSearch.add(map);
    			noPerson++;
    		}
    	}
    	if( noPerson == 0 ){
    		ltSearch = null;
    		showToast(temp);
    	}

    	return ltSearch;
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
        				SearchActivity.this.finish();
	                	getApplicationContext().sendBroadcast(new Intent("finish"));	
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
/*
	private void initKindFileName() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File path = Environment.getExternalStorageDirectory();
			File file = new File(path.getPath() + "/questions/");
			File[] files = file.listFiles();
			getFileName(files);
		}
	}

	private void getFileName(File[] files) {
		for (File currentFile : files) {
			if (currentFile.isDirectory()) {
				getFileName(currentFile.listFiles());
			}else{
				String fileName = currentFile.getName();
				if (fileName.endsWith(".csv"))
					ltKindFileName.add(fileName.substring(0, fileName.lastIndexOf(".")));
				//ltKindFileName.add(fileName);
			}
		}
	}
*/

	public final class personViewHolder{
        public TextView date;
        public TextView name;
        public TextView age;
        public TextView sex;
        public TextView emptyColumn;
        public LinearLayout personLayout;
	}

    public class PersonAdapter extends BaseAdapter{
    	
        LayoutInflater mInflater;
        List<Map<String, Object>> ltData;
        int selectItem = -1;
        personViewHolder holder;
        View view;

        public PersonAdapter(List<Map<String, Object>> list){
        	ltData = list;
        }
        @Override
        public int getCount() {
            return ltData.size();
        }
        
        @Override
        public Object getItem(int position) {
            return ltData.get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        public  void setSelectItem(int selectItem) {   
            this.selectItem = selectItem;   
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	view = mInflater.inflate(R.layout.personlist, null, false);
            holder = (personViewHolder)view.getTag();
            if( holder == null ){    
                holder=new personViewHolder();
                holder.date = (TextView)view.findViewById(R.id.date);
                holder.name = (TextView)view.findViewById(R.id.name);
                holder.age = (TextView)view.findViewById(R.id.age);
                holder.sex = (TextView)view.findViewById(R.id.sex);
                holder.emptyColumn = (TextView)view.findViewById(R.id.emptyColumn);
                holder.personLayout = (LinearLayout)view.findViewById(R.id.PersonListLayout);
                view.setTag(holder);
            }

            if (position == selectItem) {
                holder.date.setSelected(true);
                holder.name.setSelected(true);
                holder.age.setSelected(true);
                holder.sex.setSelected(true);
                holder.emptyColumn.setSelected(true);
                holder.date.setPressed(true);
                holder.name.setPressed(true);
                holder.age.setPressed(true);
                holder.sex.setPressed(true);
                holder.emptyColumn.setPressed(true);
                holder.personLayout.setBackgroundColor(Color.rgb(202, 225, 255));
            }
            else {
                holder.date.setSelected(false);
                holder.name.setSelected(false);
                holder.age.setSelected(false);
                holder.sex.setSelected(false);
                holder.emptyColumn.setSelected(false);
                holder.date.setPressed(false);
                holder.name.setPressed(false);
                holder.age.setPressed(false);
                holder.sex.setPressed(false);
                holder.emptyColumn.setPressed(false);
                holder.personLayout.setBackgroundColor(Color.TRANSPARENT);
            }
            holder.date.setText((String)ltData.get(position).get("date"));
            holder.name.setText((String)ltData.get(position).get("kajiname")); 
            holder.age.setText((String)ltData.get(position).get("age"));
            holder.sex.setText((String)ltData.get(position).get("sex"));

            return view;
        }
    }
}