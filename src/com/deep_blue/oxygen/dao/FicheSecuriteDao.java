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
	
	public static final String ID_LOCAL = "id_local";
	public static final String ID_WEB = "id_web";
	public static final String ID_EMBARCATION_WEB = "id_embarcation_web";
	public static final String ID_DIRECTEUR_PLONGE_WEB = "id_directeur_plonge_web";
	public static final String TIMESTAMP = "timestamp";
	public static final String ID_SITE = "id_site";
	public static final String ETAT = "etat";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID_LOCAL + " INTEGER PRIMARY KEY, " +
			ID_WEB +" INTEGER, "+
			ID_EMBARCATION_WEB + " INTEGER, " +
			ID_DIRECTEUR_PLONGE_WEB + " INTEGER, " +
			TIMESTAMP + " INTEGER, " +
			ID_SITE + " TEXT, " +
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
	 * Return la fiche de sécurite d'id spécifié
	 * @param idFicheSecurite
	 * @return
	 */
	public FicheSecurite getById(int idFicheSecurite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_LOCAL+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		
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
		value.put(ID_WEB, ficheSecurite.getIdWeb());
		value.put(ID_EMBARCATION_WEB, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getIdWeb() : null);
		value.put(ID_DIRECTEUR_PLONGE_WEB, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getIdWeb() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(ID_SITE, ficheSecurite.getSite() != null ? ficheSecurite.getSite().getId() : null);
		value.put(ETAT, ficheSecurite.getEtat().toString());
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.update(TABLE_NAME, value, "WHERE "+ID_LOCAL+" = ?", new String[]{ficheSecurite.getId().toString()});
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
		value.put(ID_LOCAL, ficheSecurite.getId());
		value.put(ID_WEB, ficheSecurite.getIdWeb());
		value.put(ID_EMBARCATION_WEB, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getIdWeb() : null);
		value.put(ID_DIRECTEUR_PLONGE_WEB, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getIdWeb() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(ID_SITE, ficheSecurite.getSite() != null ? ficheSecurite.getSite().getId() : null);
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
		SiteDao siteDao = new SiteDao(pContext);
		
		while(cursor.moveToNext()){
			FicheSecurite ficheSecurite = new FicheSecurite(
					cursor.getInt(cursor.getColumnIndex(ID_LOCAL)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					embarcationDao.getById(cursor.getInt(cursor.getColumnIndex(ID_EMBARCATION_WEB))),
					moniteurDao.getById(cursor.getInt(cursor.getColumnIndex(ID_DIRECTEUR_PLONGE_WEB))),
					cursor.getLong(cursor.getColumnIndex(TIMESTAMP)),
					siteDao.getById((cursor.getInt(cursor.getColumnIndex(ID_SITE)))),
					EnumEtat.valueOf(cursor.getString(cursor.getColumnIndex(ETAT))),
					cursor.getLong(cursor.getColumnIndex(VERSION)),
					palanqueeDao.getByIdFicheSecurite(cursor.getInt(cursor.getColumnIndex(ID_LOCAL)))
					);
			
			resultList.add(ficheSecurite);
			
		}
		cursor.close();
		
		return resultList;
	}
}
