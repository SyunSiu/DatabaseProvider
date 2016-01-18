package com.tonypan.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener{

	private MyDatabaseHelper dbHelper;
	private Button createDB, addData, updateData, deleteData, queryData, replaceData;
	//测试slkjdslk
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
		
		createDB = (Button) findViewById(R.id.create_database);
		addData = (Button) findViewById(R.id.add_btn);
		updateData = (Button) findViewById(R.id.update_btn);
		deleteData = (Button) findViewById(R.id.delete_btn);
		queryData = (Button) findViewById(R.id.query_btn);
		replaceData = (Button) findViewById(R.id.replace_btn);
		
		createDB.setOnClickListener(this);
		addData.setOnClickListener(this);
		updateData.setOnClickListener(this);
		deleteData.setOnClickListener(this);
		queryData.setOnClickListener(this);
		replaceData.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.create_database:
			dbHelper.getWritableDatabase();
			break;
		case R.id.add_btn:
			SQLiteDatabase database = dbHelper.getWritableDatabase();
			ContentValues contentValues = new ContentValues();
			//开始组装要添加的数据
			contentValues.put("name", "达芬奇密码");
			contentValues.put("author", "Dan Brown");
			contentValues.put("pages", 454);
			contentValues.put("price", 16.94);
			//添加第一条数据
			database.insert("Book", null, contentValues);
			contentValues.clear();//清空contentValues中的数据， 方便下次添加
			//组装第二个数据
			contentValues.put("author", "Dan Brown");
			contentValues.put("name", "The Lost Symbol");
			contentValues.put("pages", 510);
			contentValues.put("price", 19.95);	
			database.insert("Book", null, contentValues);
			contentValues.clear();
			break;
		case R.id.update_btn:
			SQLiteDatabase database2 = dbHelper.getWritableDatabase();
			ContentValues contentValues2 = new ContentValues();
			contentValues2.put("price", 10.02);
			database2.update("Book", contentValues2, "name = ? ", new String[]{"达芬奇密码"});
			break;
		case R.id.delete_btn:
			SQLiteDatabase database3 =dbHelper.getWritableDatabase();
			database3.delete("Book", "pages > ? ", new String[]{"500"});
			
			break;
		case R.id.query_btn:
			SQLiteDatabase database4 = dbHelper.getWritableDatabase();
			//查询所有数据
			Cursor cursor = database4.query("Book", null, null, null, null, null, null);
			if (cursor.moveToFirst()) {
				do {
					String name = cursor.getString(cursor.getColumnIndex("name"));
					String author = cursor.getString(cursor.getColumnIndex("author"));
					Double price = cursor.getDouble(cursor.getColumnIndex("price"));
					int pages = cursor.getInt(cursor.getColumnIndex("pages"));
					Log.v("MainActivity", "书名是：" + name);
					Log.v("MainActivity", "作者是：" + author);
					Log.v("MainActivity", "页数是：" + pages);
					Log.v("MainActivity", "价格是：" + price);
				} while (cursor.moveToNext());
			}
			cursor.close();
			break;
		case R.id.replace_btn:
			SQLiteDatabase database5 = dbHelper.getWritableDatabase();
			database5.beginTransaction();
			try {
				database5.delete("Book", null, null);
				ContentValues contentValues3 = new ContentValues();
				contentValues3.put("name", "Game of Thrones");
				contentValues3.put("price", 23.23);
				contentValues3.put("pages", 999);
				contentValues3.put("author", "George Martin");
				database5.insert("Book", null, contentValues3);
				database5.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				database5.endTransaction();
			}
			break;
		default:
			break;
		}
	}

}
