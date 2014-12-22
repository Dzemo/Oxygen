package com.deep_blue.oxygen.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Historique;

public class HistoriqueDao extends BaseDao {

	public static final String TABLE_NAME = "db_historique";
	
	public static final String ID_HISTORIQUE = "id_historique";
	public static final String LOGIN_UTILISATEUR = "login_utilisateur";
	public static final String TIMESTAMP = "timestamp";
	public static final String ID_FICHE_SECURITE = "id_fiche_securite";
	public static final String COMMENTAIRE = "commentaire";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID_HISTORIQUE + " INTEGER PRIMARY KEY, "+
		    LOGIN_UTILISATEUR + " TEXT," +
		    TIMESTAMP + " INTEGER, " +
		    COMMENTAIRE + " TEXT, " +
		    ID_FICHE_SECURITE + " INTEGER " +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public HistoriqueDao(Context pContext){
		super(pContext);
	}
	
	/**
	 * Renvoi tout les historique enregister dans la base
	 * @return
	 */
	public List<Historique> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		List<Historique> resultList = cursorToHistoriqueList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	public List<Historique> getForListFiche(List<FicheSecurite> listeFiches){
		if(listeFiches.size() == 0){
			return new ArrayList<Historique>();
		}
		
		String queryParametre = "";
		for(FicheSecurite fiche : listeFiches){
			if(!queryParametre.isEmpty()) queryParametre += " AND";
			queryParametre += " " + ID_FICHE_SECURITE + " = " + fiche.getId();
		}
		
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE"+queryParametre, null);
		
		List<Historique> resultList = cursorToHistoriqueList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Insert un historique dans la base
	 * @param historique
	 * @return
	 */
	public Historique insert(Historique historique){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(HistoriqueDao.ID_HISTORIQUE, historique.getIdHistorique());
		value.put(HistoriqueDao.LOGIN_UTILISATEUR, historique.getLoginUtilisateur());
		value.put(HistoriqueDao.TIMESTAMP, historique.getTimestamp());
		value.put(HistoriqueDao.ID_FICHE_SECURITE, historique.getIdFicheSecurite());
		value.put(HistoriqueDao.COMMENTAIRE, historique.getCommentaire());
		
		mDb.insert(UtilisateurDao.TABLE_NAME, null, value);
		
		mDb.close();
		return historique;
	}
	
	/**
	 * Supprime toute les historiques dont l'id est dans la liste fourni
	 * @param idsHistoriques
	 */
	public void deleteByIds(List<Integer> idsHistoriques){
		if(idsHistoriques.size() > 0){
			String whereClause = "";
			String[] whereArgs =  new String[idsHistoriques.size()];
			
			int i = 0;
			for(Integer idHistorique : idsHistoriques){
				whereArgs[i] = idHistorique.toString();
				if(!whereClause.isEmpty()) whereClause += " OR ";
				whereClause += ID_HISTORIQUE + " = ?";
				i++;
			}		
			
			SQLiteDatabase mDb = open();
			mDb.delete(TABLE_NAME, whereClause, whereArgs);
			mDb.close();
		}
	}
	
	/**
	 * Transforme a cursor result of a query on the Historique table into an array of Historique
	 * @param cursor
	 * @return
	 */
	private List<Historique> cursorToHistoriqueList(Cursor cursor){
		List<Historique> resultList = new ArrayList<Historique>();
		
		while(cursor.moveToNext()){
			Historique historique = new Historique(
					cursor.getInt(cursor.getColumnIndex(ID_HISTORIQUE)),
					cursor.getString(cursor.getColumnIndex(LOGIN_UTILISATEUR)),
					cursor.getLong(cursor.getColumnIndex(TIMESTAMP)),
					cursor.getInt(cursor.getColumnIndex(ID_FICHE_SECURITE)),
					cursor.getString(cursor.getColumnIndex(COMMENTAIRE))
					);
			
			resultList.add(historique);			
		}
		cursor.close();
		
		return resultList;
	}
}
