package com.deep_blue.oxygen.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao {

	protected final static int BASE_VERSION = 4;

	protected final static String NOM = "database.db";

	protected SQLiteDatabase mDb = null;
	protected DatabaseHandler mHandler = null;

	public BaseDao(Context pContext) {
		this.mHandler = new DatabaseHandler(pContext, NOM, null, BASE_VERSION);
	}

	public SQLiteDatabase open() {
		mDb = mHandler.getWritableDatabase();
		return mDb;
	}

	public void close() {
		mDb.close();
	}

	public SQLiteDatabase getDb() {
		return mDb;
	}
}
