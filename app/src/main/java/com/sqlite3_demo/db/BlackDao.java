package com.sqlite3_demo.db;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sqlite3_demo.entity.BlackInfo;

/**
 * 黑名单数据库的工具类
 */
public class BlackDao {
    private BlackDBHelper mHelper;//数据库的辅助类，通过它可以获取SqliteDatabase 类

    public BlackDao(Context context) {
        mHelper = new BlackDBHelper(context);
    }

    // 添加黑名单
    public boolean add(String number, int type) {
        //db：最好不要全局，对象会一直存在
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackDB.BlackList.COLUMN_NUMBER, number);
        values.put(BlackDB.BlackList.COLUMN_TYPE, type);
        // insert：插入的行号
        long insert = db.insert(BlackDB.BlackList.TABLE_NAME, null, values);
        db.close();
        return insert != -1;
    }

    // 删除操作 ：
    public boolean delete(String number) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String whereClause = BlackDB.BlackList.COLUMN_NUMBER + "=?";
        String[] whereArgs = {number};
        /*
        * whereClause：条件
        * (另一种写法db.delete(BlackDB.BlackList.TABLE_NAME, BlackDB.BlackList.COLUMN_NUMBER + "="+number, null))
        * whereArgs：条件参数
        * delete：删除影响的行数
        * */
        int delete = db.delete(BlackDB.BlackList.TABLE_NAME, whereClause, whereArgs);
        db.close();
        return delete == 1;

    }

    //更新操作,更新某个电话的拦截类型
    public boolean update(String number, int type) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BlackDB.BlackList.COLUMN_TYPE, type);

        //update：影响的行，这里不管type是否一样都更新了一行
        int update = db.update(BlackDB.BlackList.TABLE_NAME, values, BlackDB.BlackList.COLUMN_NUMBER + "=" + number, null);
        db.close();
        return update == 1;
    }

    //查询 某个电话的拦截类型
    public int getType(String number) {
        int type = -1;
        //getReadableDatabase()加了个锁，只读
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //第二参数：要查询的字段，如果为Null则查询全部
        //第三参数：条件
        Cursor c = db.query(BlackDB.BlackList.TABLE_NAME, new String[]{BlackDB.BlackList.COLUMN_TYPE},
                BlackDB.BlackList.COLUMN_NUMBER + "=?", new String[]{number}, null, null, null);
        if (c != null) {
            if (c.moveToNext()) {
                type = c.getInt(c.getColumnIndex(BlackDB.BlackList.COLUMN_TYPE));
                c.close();
            }
        }
        db.close();
        return type;
    }

    // 查询所有黑名单列表
    public List<BlackInfo> getBlackList() {
        List<BlackInfo> data = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c = db.query(BlackDB.BlackList.TABLE_NAME, new String[]{BlackDB.BlackList.COLUMN_NUMBER, BlackDB.BlackList.COLUMN_TYPE},
                null, null, null, null, null);
        if (c != null) {
//			if(c.getCount()>0){
//				data=new ArrayList<BlackInfo>();
//			}
            data = new ArrayList<BlackInfo>();
            while (c.moveToNext()) {
                BlackInfo blackInfo = new BlackInfo();
                String number = c.getString(0);
                int type = c.getInt(1);
                blackInfo.number = number;
                blackInfo.type = type;
                data.add(blackInfo);
            }

        }
        c.close();
        db.close();

        return data;

    }

    //查询所有黑名单列表，返回的是游标
    public Cursor getBlackListByCursor() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        return db.query(BlackDB.BlackList.TABLE_NAME, new String[]{BlackDB.BlackList.COLUMN_NUMBER, BlackDB.BlackList.COLUMN_TYPE},
                null, null, null, null, null);

        //返回游标，不能关闭数据库
//		db.close();

    }

    /**
     * 分批加载数据
     *
     * @param limit       ： 限制， 每次加载数据的最大值
     * @param startOffset ：偏移量
     * @return
     */
    public List<BlackInfo> getPartBlackList(int limit, int startOffset) {
        List<BlackInfo> data = null;
        SQLiteDatabase db = mHelper.getReadableDatabase();
//		Cursor c = db.query(BlackDB.BlackList.TABLE_NAME, new String[]{BlackDB.BlackList.COLUMN_NUMBER,BlackDB.BlackList.COLUMN_TYPE}, 
//				null, null, null, null, null);

        Cursor c = db.rawQuery("select number,type from t_black limit " + limit + " offset " + startOffset, null);
//		Cursor c1 = db.query(BlackDB.BlackList.TABLE_NAME, new String[]{BlackDB.BlackList.COLUMN_NUMBER,BlackDB.BlackList.COLUMN_TYPE}, 
//				null, null, null, null, startOffset+","+limit);

        if (c != null) {
//			if(c.getCount()>0){
//				data=new ArrayList<BlackInfo>();
//			}
            data = new ArrayList<BlackInfo>();
            while (c.moveToNext()) {
                BlackInfo blackInfo = new BlackInfo();
                String number = c.getString(0);
                int type = c.getInt(1);
                blackInfo.number = number;
                blackInfo.type = type;
                data.add(blackInfo);
            }

        }
        c.close();
        db.close();

        return data;
    }

}
