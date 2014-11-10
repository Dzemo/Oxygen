package com.deep_blue.oxygen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListeFichesSecurite;

public class FicheSecuriteDao extends BaseDao {

	public static final String TABLE_NAME = "db_fiche_securite";
	
	public static final String ID = "id";
	public static final String ID_EMBARCATION = "id_embarcation";
	public static final String ID_DIRECTEUR_PLONGE = "id_directeur_plonge";
	public static final String TIMESTAMP = "timestamp";
	public static final String SITE = "site";
	public static final String ETAT = "etat";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID + " INTEGER PRIMARY KEY, " +
			ID_EMBARCATION + " INTEGER, " +
			ID_DIRECTEUR_PLONGE + " INTEGER, " +
			TIMESTAMP + " INTEGER, " +
			SITE + " TEXT, " +
			ETAT + " TEXT, " +
		    VERSION + " INTEGER INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public FicheSecuriteDao(Context pContext){
		super(pContext);
		
		this.pContext = pContext;
	}
	
	/**
	 * Return la fiche de sécurite d'id spécifié
	 * @param idFicheSecurite
	 * @return
	 */
	public FicheSecurite getById(int idFicheSecurite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return tout les fiches de sécurité appartenant à l'état spécifié
	 * @param etat
	 * @return
	 */
	public ListeFichesSecurite getByIdFicheSecurite(EnumEtat etat){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ETAT+" = ?", new String[]{String.valueOf(etat)});
		
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return toutes les fiches de sécurité
	 * @return
	 */
	public ListeFichesSecurite getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Met à jour une fiche desécurité dans la base de donnée
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite update(FicheSecurite ficheSecurite){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_EMBARCATION, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getId() : null);
		value.put(ID_DIRECTEUR_PLONGE, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getId() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(SITE, ficheSecurite.getSite());
		value.put(ETAT, ficheSecurite.getEtat().toString());
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.update(TABLE_NAME, value, "WHERE "+ID+" = ?", new String[]{ficheSecurite.getId().toString()});
		mDb.close();
		
		return ficheSecurite;
	}
	
	/**
	 * Ajoute une nouvelle fiche de sécurité dans la base de donnée
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite insert(FicheSecurite ficheSecurite){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID, ficheSecurite.getId());
		value.put(ID_EMBARCATION, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getId() : null);
		value.put(ID_DIRECTEUR_PLONGE, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getId() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(SITE, ficheSecurite.getSite());
		value.put(ETAT, ficheSecurite.getEtat().toString());
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		return ficheSecurite;
	}
	
	/**
	 * Transforme un curseur issue d'une requete sur la table des fiches de sécurité en une ListeFichesSecurite
	 * @param cursor
	 * @return
	 */
	private ListeFichesSecurite cursorToFichesecuriteList(Cursor cursor){
		ListeFichesSecurite resultList = new ListeFichesSecurite();
		
		MoniteurDao moniteurDao = new MoniteurDao(pContext);
		PalanqueeDao palanqueeDao = new PalanqueeDao(pContext);
		EmbarcationDao embarcationDao = new EmbarcationDao(pContext);
		
		while(cursor.moveToNext()){
			FicheSecurite ficheSecurite = new FicheSecurite(
					cursor.getInt(cursor.getColumnIndex(ID)),
					embarcationDao.getById(cursor.getInt(cursor.getColumnIndex(ID_EMBARCATION))),
					moniteurDao.getById(cursor.getInt(cursor.getColumnIndex(ID_DIRECTEUR_PLONGE))),
					cursor.getLong(cursor.getColumnIndex(TIMESTAMP)),
					cursor.getString(cursor.getColumnIndex(SITE)),
					EnumEtat.valueOf(cursor.getString(cursor.getColumnIndex(ETAT))),
					cursor.getInt(cursor.getColumnIndex(VERSION)),
					palanqueeDao.getByIdFicheSecurite(cursor.getInt(cursor.getColumnIndex(ID)))
					);
			
			resultList.add(ficheSecurite);
			
		}
		cursor.close();
		
		return resultList;
	}
}
