package com.deep_blue.oxygen.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;

public class AptitudeDao extends BaseDao {
	
	public static final String TABLE_NAME = "db_aptitude";
	
	public static final String ID_WEB = "id_web";
	public static final String LIBELLE_COURT = "libelle_court";
	public static final String LIBELLE_LONG = "libelle_long";
	
	public static final String TECHNIQUE_MAX = "technique_max";
	public static final String ENCADREE_MAX = "encadree_max";
	public static final String AUTONOME_MAX = "autonome_max";

	public static final String NITROX_MAX = "nitrox_max";
	public static final String AJOUT_MAX = "ajout_max";

	public static final String ENSEIGNEMENT_AIR_MAX = "enseignement_air_max";
	public static final String ENSEIGNEMENT_NITROX_MAX = "enseignement_nitrox_max";
	public static final String ENCADREMENT_MAX = "encadrement_max";

	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
																		    ID_WEB +" INTEGER PRIMARY KEY," +
																		    LIBELLE_COURT + " TEXT," +
																		    LIBELLE_LONG + " TEXT, " +
																		    TECHNIQUE_MAX + " INTEGER, " +
																		    ENCADREE_MAX + " INTEGER, " +
																		    AUTONOME_MAX + " INTEGER, " +
																		    NITROX_MAX + " INTEGER, " +
																		    AJOUT_MAX + " INTEGER, " +
																		    ENSEIGNEMENT_AIR_MAX + " INTEGER, " +
																		    ENSEIGNEMENT_NITROX_MAX + " INTEGER, " +
																		    ENCADREMENT_MAX + " INTEGER, " +
																		    VERSION + " INTEGER DEFAULT 0 " +
																	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public AptitudeDao(Context pContext){
		super(pContext);
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
	 * Return an Aptitude or null
	 * @param aptitudeId
	 * @return
	 */
	public Aptitude getById(Integer aptitudeId){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_WEB+" = ?", new String[]{String.valueOf(aptitudeId)});
		
		SparseArray<Aptitude> resultList  = cursorToAptitudeList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(aptitudeId);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return all aptitude indexed by their ids
	 * @return
	 */
	public SparseArray<Aptitude> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		SparseArray<Aptitude> resultList  = cursorToAptitudeList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Aptitude indexed by their ids
	 * @param ids
	 * @return
	 */
	public SparseArray<Aptitude> getByIds(List<Integer> ids){
		String queryWhereClause = "";
		String[] param = new String[ids.size()];
		
		int i = 0;
		for(Integer id : ids){
			if(queryWhereClause.isEmpty()){
				queryWhereClause += " WHERE "+ID_WEB+" = ?";
			}
			else{
				queryWhereClause += " OR "+ID_WEB+" = ?";
			}
			param[i] = String.valueOf(id);
		}
		
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME+ queryWhereClause, param);
		
		SparseArray<Aptitude> resultList  = cursorToAptitudeList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * 
	 * @param aptitude
	 * @return
	 */
	public Aptitude insert(Aptitude aptitude){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, aptitude.getIdWeb());
		value.put(LIBELLE_COURT, aptitude.getLibelleCourt());
		value.put(LIBELLE_LONG, aptitude.getLibelleLong());
		
		value.put(TECHNIQUE_MAX, aptitude.getTechniqueMax());
		value.put(ENCADREE_MAX, aptitude.getEncadreeMax());
		value.put(AUTONOME_MAX, aptitude.getAutonomeMax());
		
		value.put(NITROX_MAX, aptitude.getNitroxMax());
		value.put(AJOUT_MAX, aptitude.getAjoutMax());
		
		value.put(ENSEIGNEMENT_AIR_MAX, aptitude.getEnseignementAirMax());
		value.put(ENSEIGNEMENT_NITROX_MAX, aptitude.getEnseignementNitroxMax());
		value.put(ENCADREMENT_MAX, aptitude.getEncadrementMax());
		
		value.put(VERSION, aptitude.getVersion());
		
		mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		return aptitude;
	}
	
	/**
	 * 
	 * @param aptitude
	 * @return
	 */
	public Aptitude update(Aptitude aptitude){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(LIBELLE_COURT, aptitude.getLibelleCourt());
		value.put(LIBELLE_LONG, aptitude.getLibelleLong());
		
		value.put(TECHNIQUE_MAX, aptitude.getTechniqueMax());
		value.put(ENCADREE_MAX, aptitude.getEncadreeMax());
		value.put(AUTONOME_MAX, aptitude.getAutonomeMax());
		
		value.put(NITROX_MAX, aptitude.getNitroxMax());
		value.put(AJOUT_MAX, aptitude.getAjoutMax());
		
		value.put(ENSEIGNEMENT_AIR_MAX, aptitude.getEnseignementAirMax());
		value.put(ENSEIGNEMENT_NITROX_MAX, aptitude.getEnseignementNitroxMax());
		value.put(ENCADREMENT_MAX, aptitude.getEncadrementMax());
		
		value.put(VERSION, aptitude.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_WEB  + " = ?", new String[] {String.valueOf(aptitude.getIdWeb())});
		mDb.close();
		
		return aptitude;
	}
	
	/**
	 * Delete an aptitude by his Id
	 * @param aptitudeId
	 */
	public void delete(Integer aptitudeId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID_WEB + " = ?", new String[] {String.valueOf(aptitudeId)});
		
		mDb.close();
	}

	/**
	 * Transforme a cursor result of a query on the aptitude table into an array of Aptitude
	 * @param cursor
	 * @return
	 */
	private SparseArray<Aptitude> cursorToAptitudeList(Cursor cursor){
		SparseArray<Aptitude> resultList = new SparseArray<Aptitude>();
		
		while(cursor.moveToNext()){
			Aptitude aptitude = new Aptitude(
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getString(cursor.getColumnIndex(LIBELLE_COURT)),
					cursor.getString(cursor.getColumnIndex(LIBELLE_LONG)),
					
					cursor.getInt(cursor.getColumnIndex(TECHNIQUE_MAX)),
					cursor.getInt(cursor.getColumnIndex(ENCADREE_MAX)),
					cursor.getInt(cursor.getColumnIndex(AUTONOME_MAX)),
					
					cursor.getInt(cursor.getColumnIndex(NITROX_MAX)),
					cursor.getInt(cursor.getColumnIndex(AJOUT_MAX)),
					
					cursor.getInt(cursor.getColumnIndex(ENSEIGNEMENT_AIR_MAX)),
					cursor.getInt(cursor.getColumnIndex(ENSEIGNEMENT_NITROX_MAX)),
					cursor.getInt(cursor.getColumnIndex(ENCADREMENT_MAX)),
					
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.put(aptitude.getIdWeb(), aptitude);			
		}
		cursor.close();
		
		return resultList;
	}
}
