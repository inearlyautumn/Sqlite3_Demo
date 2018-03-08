package com.sqlite3_demo.util;

import android.util.Log;

/**
 * Log 日志的工具类
 * @author lijun
 *
 */
public class LogUtil {
	public static boolean is_debug=true;//默认是调试模式
	public static void d(String tag,String msg){
		if(is_debug){
			Log.d(tag, msg);
		}
	}
	public static void i(String tag,String msg){
		if(is_debug){
			Log.i(tag, msg);
		}
	}
	public static void w(String tag,String msg){
		if(is_debug){
			Log.w(tag, msg);
		}
	}

}
