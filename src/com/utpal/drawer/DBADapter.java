
package com.utpal.drawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBADapter
{	
	private static final String TAG = "DBAdapter";
	
	public static final String KEY_ROWID = "_id";
	public static final int COL_ROWID = 0;
	
	public static final String KEY_NAME = "name";
	public static final String KEY_STUDENTNUM = "studentnum";
	
	public static final int COL_NAME = 1;
	public static final int COL_STUDENTNUM = 2;
	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STUDENTNUM};
	
	
	public static final String DATABASE_NAME = "utpal";
	public static final String DATABASE_TABLE = "mainTable1";
	
	public static final int DATABASE_VERSION = 2;	
	
	private static final String DATABASE_CREATE_SQL = 
			"create table " + DATABASE_TABLE 
			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_NAME + " text not null, "
			+ KEY_STUDENTNUM + " string not null"
			+ ");";
	
	
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	
	public DBADapter(Context ctx)
	{
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	


	public DBADapter open()
	{
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	
	public void close()
	{
		myDBHelper.close();
	}
	
	public long insertRow(String name,String studentnum) 
	{	
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_STUDENTNUM,studentnum);
		
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	
	public boolean deleteRow(long rowId)
	{
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}
	
	public void deleteAll()
	{
		Cursor c = getAllRows();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst())
		{
			do {
				deleteRow(c.getLong((int) rowId));				
			   } while (c.moveToNext());
		}
		c.close();
	}
	
	
	public Cursor getAllRows()
	{
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
							where, null, null, null, null, null);
		if (c != null)
		{
			c.moveToFirst();
		}
		return c;
	}

	
	public Cursor getRow(long rowId)
	{
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, 
						where, null, null, null, null, null);
		if (c != null)
		{
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean updateRow(long rowId, String name,String studentnum) 
	{
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_NAME, name);
		newValues.put(KEY_STUDENTNUM,studentnum);
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) 
		{
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			onCreate(_db);
		}
	}
}
