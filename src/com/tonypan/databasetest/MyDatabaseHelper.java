package com.tonypan.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{

	public static final String CREATE_BOOK = "create table Book ("
										  + "id integer primary key autoincrement, "
										  + "author text, "
										  + "price real, "
										  + "pages integer, "
										  + "name text,"
										  + "category_id integer)";
	
	public static final String CREATE_CATEGORY = "create table Category("
											   + "id integer primary key autoincrement, "
											   + "category_name text, "
											   + "category_code integer )";
	
	private Context mContext;
	
	public MyDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BOOK);
		db.execSQL(CREATE_CATEGORY);
		//跨程序访问的时候不能使用Toast弹出消息？？？？？？？？？？？
		//Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//判断版本进行相应的升级操作
		switch (oldVersion) {
		case 1:
			db.execSQL(CREATE_CATEGORY);
		case 2:
			db.execSQL("alter table Book add column category_id integer");
		//每个case都没有加break，这样保证跨版本升级的时候每个case都能得到执行
		default:
		}
	}

}
