package com.deep_blue.oxygen.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.Embarcation;

public class EmbarcationDao extends BaseDao {

	public static final String TABLE_NAME = "db_embarcation";
	
	public static final String ID_WEB = "id_web";
	public static final String LIBELLE = "libelle";
	public static final String COMMENTAIRE = "commentaire";
	public static final String DISPONIBLE = "disponible";
	public static final String CONTENANCE = "contenance";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID_WEB +" INTEGER PRIMARY KEY," +
		    LIBELLE + " TEXT," +
		    COMMENTAIRE + " TEXT, " +
		    DISPONIBLE + " INTEGER, " +
		    CONTENANCE + " INTEGER, " +
		    VERSION + " INTEGER INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public EmbarcationDao(Context pContext){
		super(pContext);
	}
	
	/**
	 * Renvoi le timestamp de la dernière modification ou 0 si aucune modification
	 * @return
	 */
	public Long getMaxVersion(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME,null);
		mDb.close();
		
		if(cursor.getCount() == 1){
			return cursor.getLong(0);
		}
		else{
			return Long.valueOf(0);
		}
	}
	
	/**
	 * Return an Embarcation or null
	 * @param embarcationIdWeb
	 * @return
	 */
	public Embarcation getById(int embarcationIdWeb){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_WEB+" = ?", new String[]{String.valueOf(embarcationIdWeb)});
		
		List<Embarcation> resultList  = cursorToEmbarcationList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return all Embarcation
	 * @return
	 */
	public List<Embarcation> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		List<Embarcation> resultList  = cursorToEmbarcationList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return all disponible Embarcation
	 * @return
	 */
	public List<Embarcation> getAllDisponible(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE "+DISPONIBLE+" = 1", null);
		
		List<Embarcation> resultList  = cursorToEmbarcationList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * 
	 * @param embarcation
	 * @return
	 */
	public Embarcation insert(Embarcation embarcation){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, embarcation.getIdWeb());
		value.put(LIBELLE, embarcation.getLibelle());
		value.put(COMMENTAIRE, embarcation.getComentaire());		
		value.put(DISPONIBLE, embarcation.isDisponible() ? 1 : 0);	
		value.put(CONTENANCE, embarcation.getContenance());		
		value.put(VERSION, embarcation.getVersion());
		
		mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		return embarcation;
	}
	
	/**
	 * 
	 * @param Embarcation
	 * @return
	 */
	public Embarcation update(Embarcation embarcation){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(LIBELLE, embarcation.getLibelle());
		value.put(COMMENTAIRE, embarcation.getComentaire());		
		value.put(DISPONIBLE, embarcation.isDisponible() ? 1 : 0);	
		value.put(CONTENANCE, embarcation.getContenance());		
		value.put(VERSION, embarcation.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_WEB  + " = ?", new String[] {String.valueOf(embarcation.getIdWeb())});
		mDb.close();
		
		return embarcation;
	}
	
	/**
	 * Delete an embarcation by his Id
	 * @param embarcationIdWeb
	 */
	public void delete(Integer embarcationIdWeb){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID_WEB + " = ?", new String[] {String.valueOf(embarcationIdWeb)});
		
		mDb.close();
	}
	
	/**
	 * Transforme a cursor result of a query on the embarcation table into an array of Embarcation
	 * @param cursor
	 * @return
	 */
	private List<Embarcation> cursorToEmbarcationList(Cursor cursor){
		List<Embarcation> resultList = new ArrayList<Embarcation>();
		
		while(cursor.moveToNext()){
			Embarcation embarcation = new Embarcation(
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getString(cursor.getColumnIndex(LIBELLE)),
					cursor.getString(cursor.getColumnIndex(COMMENTAIRE)),					
					cursor.getInt(cursor.getColumnIndex(DISPONIBLE)) > 0,	
					cursor.getInt(cursor.getColumnIndex(CONTENANCE)),			
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(embarcation);			
		}
		cursor.close();
		
		return resultList;
	}
}
