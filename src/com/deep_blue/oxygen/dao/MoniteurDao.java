package com.deep_blue.oxygen.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.Moniteur;

public class MoniteurDao extends BaseDao {

public static final String TABLE_NAME = "db_moniteur";
	
	public static final String ID_WEB = "id_web";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String APTITUDES = "aptitudes";
	public static final String ACTIF = "actif";
	public static final String DIRECTEUR_PLONGE = "directeur_plonge";
	public static final String EMAIL = "email";
	public static final String TELEPHONE = "telephone";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
		    ID_WEB +" INTEGER PRIMARY KEY," +
		    NOM + " TEXT, " +
		    PRENOM + " TEXT, " +
		    APTITUDES + " TEXT, " +
		    ACTIF + " INTEGER, " +
		    DIRECTEUR_PLONGE + " INTEGER, " +
		    EMAIL + " TEXT, " +
		    TELEPHONE + " TEXT, " +
		    VERSION + " INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public MoniteurDao(Context pContext){
		super(pContext);
		this.pContext = pContext;
	}
	
	/**
	 * Renvoi le timestamp de la dernière modification ou 0 si aucune modification
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
	 * Return le moniteur d'id spécifié
	 * @param idMoniteur
	 * @return
	 */
	public Moniteur getByIdWeb(int idMoniteur){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_WEB+" = ?", new String[]{String.valueOf(idMoniteur)});
		
		List<Moniteur> resultList  = cursorToMoniteurList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return all Moniteur
	 * @return
	 */
	public List<Moniteur> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		List<Moniteur> resultList  = cursorToMoniteurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Retourne tout les moniteurs actifs
	 * @return
	 */
	public List<Moniteur> getAllActif(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ACTIF+" = 1", null);
		
		List<Moniteur> resultList  = cursorToMoniteurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Retourne tout les moniteurs actifs qui peuvent être aussi directeur de plongé
	 * @return
	 */
	public List<Moniteur> getAllActifDirecteurPlonge(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ACTIF+" = 1 AND "+DIRECTEUR_PLONGE+" = 1", null);
		
		List<Moniteur> resultList  = cursorToMoniteurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * 
	 * @param Moniteur
	 * @return
	 */
	public Moniteur insert(Moniteur moniteur){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, moniteur.getIdWeb());;
		value.put(NOM, moniteur.getNom());
		value.put(PRENOM, moniteur.getPrenom());		
		value.put(APTITUDES, ((ListeAptitudes)moniteur.getAptitudes()).toIdsList());
		value.put(ACTIF, moniteur.isActif() ? 1 : 0);
		value.put(DIRECTEUR_PLONGE, moniteur.isDirecteurPlongee() ? 1 : 0);
		value.put(EMAIL, moniteur.getEmail());
		value.put(TELEPHONE, moniteur.getTelephone());
		value.put(VERSION, moniteur.getVersion());
		
		mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		return moniteur;
	}
	
	/**
	 * 
	 * @param Moniteur
	 * @return
	 */
	public Moniteur update(Moniteur moniteur){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(NOM, moniteur.getNom());
		value.put(PRENOM, moniteur.getPrenom());		
		value.put(APTITUDES,((ListeAptitudes)moniteur.getAptitudes()).toIdsList());
		value.put(ACTIF, moniteur.isActif() ? 1 : 0);
		value.put(DIRECTEUR_PLONGE, moniteur.isDirecteurPlongee() ? 1 : 0);
		value.put(EMAIL, moniteur.getEmail());
		value.put(TELEPHONE, moniteur.getTelephone());
		value.put(VERSION, moniteur.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_WEB  + " = ?", new String[] {String.valueOf(moniteur.getIdWeb())});
		mDb.close();
		
		return moniteur;
	}
	
	/**
	 * Delete an Moniteur by his Id
	 * @param MoniteurId
	 */
	public void delete(Integer MoniteurId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID_WEB + " = ?", new String[] {String.valueOf(MoniteurId)});
		
		mDb.close();
	}
	
	/**
	 * Transforme a cursor result of a query on the Moniteur table into an array of Moniteur
	 * @param cursor
	 * @return
	 */
	private List<Moniteur> cursorToMoniteurList(Cursor cursor){
		List<Moniteur> resultList = new ArrayList<Moniteur>();
		
		AptitudeDao aptitudeDao = new AptitudeDao(pContext);
		SparseArray<Aptitude> allAptitudes = aptitudeDao.getAll();
		
		while(cursor.moveToNext()){
			Moniteur moniteur = new Moniteur(
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getString(cursor.getColumnIndex(NOM)),
					cursor.getString(cursor.getColumnIndex(PRENOM)),
					new ListeAptitudes(cursor.getString(cursor.getColumnIndex(APTITUDES)), allAptitudes),
					cursor.getInt(cursor.getColumnIndex(ACTIF)) > 0,
					cursor.getInt(cursor.getColumnIndex(DIRECTEUR_PLONGE)) > 0,
					cursor.getString(cursor.getColumnIndex(EMAIL)),					
					cursor.getString(cursor.getColumnIndex(TELEPHONE)),
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(moniteur);			
		}
		cursor.close();
		
		return resultList;
	}
}
