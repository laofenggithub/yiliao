package yiliao.demo.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

import yiliao.demo.R;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * 功能：读取文件，写入文件
 * 
 * */
public class MyToolKit {
	// 字符编码类型
	private final static String CODING_TYPE = "Shift-JIS";
	// 读取文件
    public static String readFileSdcard(String fileName){
		String res=""; 
        try{ 
        	FileInputStream fin = new FileInputStream(fileName); 
        	int length = fin.available();
        	byte [] buffer = new byte[length];
        	fin.read(buffer);
        	res = EncodingUtils.getString(buffer, CODING_TYPE); 
        	fin.close();     
        } 
        catch(Exception e){ 
        	e.printStackTrace(); 
        } 
        return res; 
	}
    // 写入文件
    public static void writeFileSdcard(String fileName,String message){ 
    	System.out.println("文件@"+"fileName"+"@"+"！");
    	File file = new File(fileName);
    	if (file.exists()) {
			System.out.println("文件@"+"fileName"+"@"+"已存在！");
			return;
		}
    	try{
	        FileOutputStream fout = new FileOutputStream(fileName);
	        byte [] bytes = message.getBytes(CODING_TYPE); 
		    fout.write(bytes); 
	        fout.close();
        }catch(Exception e){ 
			e.printStackTrace(); 
		}
    }

//    // 隐藏状态栏
//    private void HideStatusBar(){
//    	requestWindowFeature(Window.FEATURE_NO_TITLE);
//    	int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//    	Window myWindow = this.getWindow();
//    	myWindow.setFlags(flag, flag);
//    }
}
