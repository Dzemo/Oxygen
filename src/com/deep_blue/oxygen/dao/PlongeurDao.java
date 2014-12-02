package com.deep_blue.oxygen.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.ListePlongeurs;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurDao extends BaseDao {
	
	public static final String TABLE_NAME = "db_plongeur";
	
	public static final String ID = "id";
	public static final String ID_WEB = "id_web";
	public static final String ID_PALANQUEE = "id_palanquee";
	public static final String ID_FICHE_SECURITE = "id_fiche_securite";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String APTITUDES = "aptitudes";
	public static final String TELEPHONE = "telephone";
	public static final String TELEPHONE_URGENCE = "telephone_urgence";
	public static final String DATE_NAISSANCE = "date_naissance";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
		    ID +" INTEGER PRIMARY KEY," +
		    ID_WEB + " INTEGER," +
		    ID_PALANQUEE + " INTEGER," +
		    ID_FICHE_SECURITE + " INTEGER," +
		    NOM + " TEXT, " +
		    PRENOM + " TEXT, " +
		    APTITUDES + " TEXT, " +
		    TELEPHONE + " TEXT, " +
		    TELEPHONE_URGENCE + " TEXT, " +
		    DATE_NAISSANCE + " TEXT, " +
		    VERSION + " INTEGER INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public PlongeurDao(Context pContext){
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
	 * Return le plongeur d'id spécifié
	 * @param idPlongeur
	 * @return
	 */
	public Plongeur getById(int idPlongeur){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID+" = ?", new String[]{String.valueOf(idPlongeur)});
		
		List<Plongeur> resultList  = cursorToPlongeurList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return tout les plongeur appartenant à la palanquée désignée
	 * @param idPalanquee
	 * @return
	 */
	public ListePlongeurs getByIdPalanquee(int idPalanquee){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_PALANQUEE+" = ?", new String[]{String.valueOf(idPalanquee)});
		
		ListePlongeurs resultList  = cursorToPlongeurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return tout les plongeur appartenant à la fiche de sécurité désignée
	 * @param idFicheSecurite
	 * @return
	 */
	public ListePlongeurs getByIdFicheSecurite(int idFicheSecurite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_FICHE_SECURITE+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		
		ListePlongeurs resultList  = cursorToPlongeurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return all Plongeur
	 * @return
	 */
	public ListePlongeurs getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		ListePlongeurs resultList  = cursorToPlongeurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * 
	 * @param Plongeur
	 * @return
	 */
	public Plongeur insert(Plongeur plongeur){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID, plongeur.getId());
		value.put(ID_WEB, plongeur.getIdWeb());
		value.put(ID_PALANQUEE, plongeur.getIdPalanquee());
		value.put(ID_FICHE_SECURITE, plongeur.getIdFicheSecurite());
		value.put(NOM, plongeur.getNom());
		value.put(PRENOM, plongeur.getPrenom());		
		value.put(APTITUDES, plongeur.getAptitudes().toIdsList());
		value.put(TELEPHONE, plongeur.getTelephone());
		value.put(TELEPHONE_URGENCE, plongeur.getPrenom());
		value.put(DATE_NAISSANCE, plongeur.getDateNaissance());
		value.put(VERSION, plongeur.getVersion());
		
		mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		return plongeur;
	}
	
	/**
	 * 
	 * @param Plongeur
	 * @return
	 */
	public Plongeur update(Plongeur plongeur){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, plongeur.getIdWeb());
		value.put(ID_PALANQUEE, plongeur.getIdPalanquee());
		value.put(ID_FICHE_SECURITE, plongeur.getIdFicheSecurite());
		value.put(NOM, plongeur.getNom());
		value.put(PRENOM, plongeur.getPrenom());		
		value.put(APTITUDES, plongeur.getAptitudes().toIdsList());
		value.put(TELEPHONE, plongeur.getTelephone());
		value.put(TELEPHONE_URGENCE, plongeur.getPrenom());
		value.put(DATE_NAISSANCE, plongeur.getDateNaissance());
		value.put(VERSION, plongeur.getVersion());
		
		mDb.update(TABLE_NAME, value, ID  + " = ?", new String[] {String.valueOf(plongeur.getId())});
		mDb.close();
		
		return plongeur;
	}
	
	/**
	 * Delete an Plongeur by his Id
	 * @param PlongeurId
	 */
	public void delete(Integer PlongeurId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID + " = ?", new String[] {String.valueOf(PlongeurId)});
		
		mDb.close();
	}
	
	/**
	 * Transforme a cursor result of a query on the Plongeur table into an array of Plongeur
	 * @param cursor
	 * @return
	 */
	private ListePlongeurs cursorToPlongeurList(Cursor cursor){
		ListePlongeurs resultList = new ListePlongeurs();
		
		AptitudeDao aptitudeDao = new AptitudeDao(pContext);
		SparseArray<Aptitude> allAptitudes = aptitudeDao.getAll();
		
		while(cursor.moveToNext()){
			Plongeur plongeur = new Plongeur(
					cursor.getInt(cursor.getColumnIndex(ID)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getInt(cursor.getColumnIndex(ID_PALANQUEE)),
					cursor.getInt(cursor.getColumnIndex(ID_FICHE_SECURITE)),
					cursor.getString(cursor.getColumnIndex(NOM)),
					cursor.getString(cursor.getColumnIndex(PRENOM)),
					new ListeAptitudes(cursor.getString(cursor.getColumnIndex(APTITUDES)), allAptitudes),
					cursor.getString(cursor.getColumnIndex(TELEPHONE)),
					cursor.getString(cursor.getColumnIndex(TELEPHONE_URGENCE)),
					cursor.getString(cursor.getColumnIndex(DATE_NAISSANCE)),
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(plongeur);			
		}
		cursor.close();
		
		return resultList;
	}
}
