package com.deep_blue.oxygen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.ListePalanquees;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.EnumTypePlonge;

public class PalanqueeDao extends BaseDao {
	
	public static final String TABLE_NAME = "db_palanquee";
	
	public static final String ID = "id_palanque";
	public static final String ID_WEB = "id_palanque_web";
	public static final String ID_FICHE_SECURITE = "id_fiche_securite";
	public static final String ID_MONITEUR_WEB = "id_moniteur_web";
	public static final String NUMERO = "numero";
	public static final String TYPE_PLONGE = "type_plonge";
	public static final String TYPE_GAZ = "type_gaz";
	public static final String PROFONDEUR_PREVUE = "profondeur_prevue";
	public static final String PROFONDEUR_REALISE_MONITEUR = "profondeur_realise_moniteur";
	public static final String DUREE_PREVUE = "duree_prevue";
	public static final String HEURE = "heure";
	public static final String DUREE_REALISE_MONITEUR = "duree_realise_moniteur";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID + " INTEGER PRIMARY KEY, " +
			ID_WEB + " INTEGER, " +
		    ID_FICHE_SECURITE + " INTEGER, " +
		    ID_MONITEUR_WEB + " INTEGER, " +
		    NUMERO + " INTEGER, " +
		    TYPE_PLONGE + " TEXT, " +
		    TYPE_GAZ + " TEXT, " +
		    PROFONDEUR_PREVUE +" REAL, " +
		    PROFONDEUR_REALISE_MONITEUR + " REAL, " +
		    DUREE_PREVUE + " INTEGER, " +
		    HEURE + " TEXT," +
		    DUREE_REALISE_MONITEUR + " INTEGER, " +
		    VERSION + " INTEGER INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public PalanqueeDao(Context pContext){
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
	 * Return la palanquee d'id spécifié
	 * @param idPlalanquee
	 * @return
	 */
	public Palanquee getById(int idPlalanquee){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID+" = ?", new String[]{String.valueOf(idPlalanquee)});
		
		ListePalanquees resultList  = cursorToPalanqueeList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1){
			return resultList.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Return tout les palanquées appartenant à la fiche de sécurité désignée
	 * @param idFicheSecurite
	 * @return
	 */
	public ListePalanquees getByIdFicheSecurite(int idFicheSecurite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_FICHE_SECURITE+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		
		ListePalanquees resultList  = cursorToPalanqueeList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return toutes les palanquees
	 * @return
	 */
	public ListePalanquees getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		ListePalanquees resultList  = cursorToPalanqueeList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Met à jour une palanque dans la base de donnée
	 * @param palanquee
	 * @return
	 */
	public Palanquee update(Palanquee palanquee){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, palanquee.getIdWeb());
		value.put(ID_FICHE_SECURITE, palanquee.getIdFicheSecurite());
		value.put(ID_MONITEUR_WEB, palanquee.getMoniteur() != null ? palanquee.getMoniteur().getIdWeb() : null);
		value.put(NUMERO, palanquee.getNumero());
		value.put(TYPE_PLONGE, palanquee.getTypePlonge().toString());
		value.put(TYPE_GAZ, palanquee.getTypeGaz().toString());
		value.put(PROFONDEUR_PREVUE, palanquee.getProfondeurPrevue());
		value.put(PROFONDEUR_REALISE_MONITEUR, palanquee.getProfondeurRealiseeMoniteur());
		value.put(DUREE_PREVUE, palanquee.getDureePrevue());
		value.put(DUREE_REALISE_MONITEUR, palanquee.getDureeRealiseeMoniteur());
		value.put(HEURE, palanquee.getHeure());
		value.put(VERSION, palanquee.getVersion());
		
		mDb.update(TABLE_NAME, value, ID+" = ?", new String[]{palanquee.getId().toString()});
		mDb.close();
		
		return palanquee;
	}
	
	/**
	 * Ajoute une nouvelle palanque dans la base de donnée
	 * @param palanquee
	 * @return
	 */
	public Palanquee insert(Palanquee palanquee){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID, palanquee.getId());
		value.put(ID_WEB, palanquee.getIdWeb());
		value.put(ID_FICHE_SECURITE, palanquee.getIdFicheSecurite());
		value.put(ID_MONITEUR_WEB, palanquee.getMoniteur() != null ? palanquee.getMoniteur().getIdWeb() : null);
		value.put(NUMERO, palanquee.getNumero());
		value.put(TYPE_PLONGE, palanquee.getTypePlonge().toString());
		value.put(TYPE_GAZ, palanquee.getTypeGaz().toString());
		value.put(PROFONDEUR_PREVUE, palanquee.getProfondeurPrevue());
		value.put(PROFONDEUR_REALISE_MONITEUR, palanquee.getProfondeurRealiseeMoniteur());
		value.put(DUREE_PREVUE, palanquee.getDureePrevue());
		value.put(DUREE_REALISE_MONITEUR, palanquee.getDureeRealiseeMoniteur());
		value.put(HEURE, palanquee.getHeure());
		value.put(VERSION, palanquee.getVersion());
		
		Long insertedId = mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		palanquee.setId(insertedId);
		return palanquee;
	}
	
	/**
	 * Transforme a cursor result of a query on the palanquee table into an array of Palanquee
	 * @param cursor
	 * @return
	 */
	private ListePalanquees cursorToPalanqueeList(Cursor cursor){
		ListePalanquees resultList = new ListePalanquees();
		
		MoniteurDao moniteurDao = new MoniteurDao(pContext);
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		
		while(cursor.moveToNext()){
			Palanquee palanquee = new Palanquee(
					cursor.getLong(cursor.getColumnIndex(ID)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getLong(cursor.getColumnIndex(ID_FICHE_SECURITE)),
					moniteurDao.getByIdWeb(cursor.getInt(cursor.getColumnIndex(ID_MONITEUR_WEB))),
					cursor.getInt(cursor.getColumnIndex(NUMERO)),
					EnumTypePlonge.valueOf(cursor.getString(cursor.getColumnIndex(TYPE_PLONGE))),
					EnumTypeGaz.valueOf(cursor.getString(cursor.getColumnIndex(TYPE_GAZ))),
					cursor.getFloat(cursor.getColumnIndex(PROFONDEUR_PREVUE)),
					cursor.getFloat(cursor.getColumnIndex(PROFONDEUR_REALISE_MONITEUR)),
					cursor.getInt(cursor.getColumnIndex(DUREE_PREVUE)),
					cursor.getInt(cursor.getColumnIndex(DUREE_REALISE_MONITEUR)),
					cursor.getString(cursor.getColumnIndex(HEURE)),
					cursor.getLong(cursor.getColumnIndex(VERSION)),
					plongeurDao.getByIdPalanquee(cursor.getInt(cursor.getColumnIndex(ID)))
					);
			
			resultList.add(palanquee);			
		}
		cursor.close();
		
		return resultList;
	}
}
