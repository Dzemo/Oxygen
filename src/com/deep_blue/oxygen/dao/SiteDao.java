package com.deep_blue.oxygen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.deep_blue.oxygen.model.Site;

public class SiteDao extends BaseDao {

	public static final String TABLE_NAME = "db_site";
	
	public static final String ID = "id";
	public static final String ID_WEB = "id_web";
	public static final String NOM = "nom";
	public static final String COMMENTAIRE = "commentaire";
	public static final String DESACTIVE = "desactive";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID + " INTEGER PRIMARY KEY, " +
			ID_WEB + " INTEGER, " +
			NOM + " TEXT, " +
			COMMENTAIRE + " TEXT, " +
			DESACTIVE + " INTEGER DEFAULT 0," +
			VERSION +" INTEGER" +			
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	@SuppressWarnings("unused")
	private Context pContext;
	
	public SiteDao(Context pContext){
		super(pContext);
		
		this.pContext = pContext;
	}
	
	/**
	 * Renvoi le timestamp de la derni�re modification ou 0 si aucune modification
	 * @return
	 */
	public Long getMaxVersion(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME,null);

		Long maxVersion = Long.valueOf(0);
		if(cursor.getCount() == 1){
			cursor.moveToNext();
			maxVersion = cursor.getLong(0);
		}
		
		mDb.close();
		
		return maxVersion;	
	}
	
	/**
	 * Return le site d'id sp�cifi�
	 * @param idSite
	 * @return
	 */
	public Site getById(int idSite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID+" = ?", new String[]{String.valueOf(idSite)});
		
		SparseArray<Site> resultList  = cursorToSiteList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(idSite);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return le site d'id web sp�cifi�
	 * @param idWebSite
	 * @return
	 */
	public Site getByIdWeb(int idWebSite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_WEB+" = ?", new String[]{String.valueOf(idWebSite)});
		
		SparseArray<Site> resultList  = cursorToSiteList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(idWebSite);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return toutes le site activ� index� par leurs ids
	 * @return
	 */
	public SparseArray<Site> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		SparseArray<Site> resultList  = cursorToSiteList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Met � jour un site dans la base de donn�e
	 * @param palanquee
	 * @return
	 */
	public Site update(Site site){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, site.getIdWeb());
		value.put(NOM, site.getNom());
		value.put(COMMENTAIRE, site.getCommentaire());
		value.put(DESACTIVE, site.getDesactive() ? 1 : 0);
		value.put(VERSION, site.getVersion());
		
		mDb.update(TABLE_NAME, value, ID+" = ?", new String[]{site.getId().toString()});
		mDb.close();
		
		return site;
	}
	
	/**
	 * Ajoute un nouveau site dans la base de donn�e
	 * @param palanquee
	 * @return
	 */
	public Site insert(Site site){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, site.getIdWeb());
		value.put(NOM, site.getNom());
		value.put(COMMENTAIRE, site.getCommentaire());
		value.put(DESACTIVE, site.getDesactive() ? 1 : 0);
		value.put(VERSION, site.getVersion());
		
		Long insertedId = mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		site.setId(insertedId);
		return site;
	}
	
	/**
	 * Retourne une liste des sites index� par leurs ids
	 * @param cursor
	 * @return
	 */
	private SparseArray<Site> cursorToSiteList(Cursor cursor){
		SparseArray<Site> resultList = new SparseArray<Site>();
		
		while(cursor.moveToNext()){
			Site site = new Site(
					cursor.getLong(cursor.getColumnIndex(ID)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getString(cursor.getColumnIndex(NOM)),
					cursor.getString(cursor.getColumnIndex(COMMENTAIRE)),
					cursor.getInt(cursor.getColumnIndex(DESACTIVE)) == 1,
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.put(site.getId().intValue(), site);			
		}
		cursor.close();
		
		return resultList;
	}
}
