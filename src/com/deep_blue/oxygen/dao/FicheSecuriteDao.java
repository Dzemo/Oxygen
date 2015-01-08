package com.deep_blue.oxygen.dao;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListeFichesSecurite;
import com.deep_blue.oxygen.model.Palanquee;

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
	 * Renvoi le timestamp de la derni�re modification ou 0 si aucune modification
	 * @return
	 */
	public Long getMaxVersion(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME + " WHERE "+ETAT+" != '" +EnumEtat.VALIDE.toString()+"'",null);

		Long maxVersion = Long.valueOf(0);
		if(cursor.getCount() == 1){
			cursor.moveToNext();
			maxVersion = cursor.getLong(0);
		}
		
		mDb.close();
		
		return maxVersion;	
	}
	
	/**
	 * Return la fiche de s�curite d'id sp�cifi�
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
	 * Return la fiche de s�curite d'id web sp�cifi�
	 * @param idWebFicheSecurite
	 * @return
	 */
	public FicheSecurite getByIdWeb(int idWebFicheSecurite){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_WEB+" = ?", new String[]{String.valueOf(idWebFicheSecurite)});
		
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
	 * Return tout les fiches de s�curit� appartenant � l'�tat sp�cifi�
	 * @param etat
	 * @return
	 */
	public ListeFichesSecurite getByEtat(EnumEtat etat){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ETAT+" = '"+String.valueOf(etat)+"'", null);
		
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return toutes les fiches de s�curit�
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
	 * Met � jour une fiche des�curit� dans la base de donn�e et met � jours �galement toute les palanqu�e/plongeurs
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite update(FicheSecurite ficheSecurite){
		/*if(ficheSecurite == null)
			return null;*/
		
		//Enregistrement du site si il n'est pas d�j� enregistr�
		if(ficheSecurite.getSite() != null && (ficheSecurite.getSite().getId() == null || ficheSecurite.getSite().getId() < 0)){
			SiteDao siteDao = new SiteDao(pContext);
			siteDao.insert(ficheSecurite.getSite());
		}
		
		//Mise � jours de la version
		ficheSecurite.updateVersion();

		//Maj de la fiche
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(ID_WEB, ficheSecurite.getIdWeb());
		value.put(ID_EMBARCATION_WEB, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getIdWeb() : null);
		value.put(ID_DIRECTEUR_PLONGE_WEB, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getIdWeb() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(ID_SITE, ficheSecurite.getSite() != null ? ficheSecurite.getSite().getId() : null);
		value.put(ETAT, ficheSecurite.getEtat() != null ? ficheSecurite.getEtat().toString() : null);
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_LOCAL+" = ?", new String[]{ficheSecurite.getId().toString()});
		mDb.close();
		
		//Maj de toute les palanqu�es
		PalanqueeDao palanqueeDao = new PalanqueeDao(pContext);
		return palanqueeDao.updatePalanqueeFromFiche(ficheSecurite);
	}
	
	/**
	 * Met � jour l'�tat d'une fiche de s�curit�
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite updateEtat(FicheSecurite ficheSecurite, EnumEtat etat){
		
		//Modification de l'�tat
		ficheSecurite.setEtat(etat);
		
		//Mise � jours de la version
		ficheSecurite.updateVersion();

		//Maj de la fiche
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(ETAT, ficheSecurite.getEtat() != null ? ficheSecurite.getEtat().toString() : null);
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_LOCAL+" = ?", new String[]{ficheSecurite.getId().toString()});
		mDb.close();

		return ficheSecurite;
	}
	
	/**
	 * Ajoute une nouvelle fiche de s�curit� dans la base de donn�e et ajoute �galement toute les palanqu�e/plongeurs
	 * @param ficheSecurite
	 * @return
	 */
	public FicheSecurite insert(FicheSecurite ficheSecurite){
		if(ficheSecurite == null){
			return null;
		}
		
		//Enregistrement du site si il n'est pas d�j� enregistr�
		if(ficheSecurite.getSite() != null && (ficheSecurite.getSite().getId() == null || ficheSecurite.getSite().getId() < 0)){
			SiteDao siteDao = new SiteDao(pContext);
			siteDao.insert(ficheSecurite.getSite());
		}
		
		//Mise � jours de la version
		ficheSecurite.updateVersion();

		//Ajout de la fiche en base
		SQLiteDatabase mDb = open();		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, ficheSecurite.getIdWeb());
		value.put(ID_EMBARCATION_WEB, ficheSecurite.getEmbarcation() != null ? ficheSecurite.getEmbarcation().getIdWeb() : null);
		value.put(ID_DIRECTEUR_PLONGE_WEB, ficheSecurite.getDirecteurPlonge() != null ? ficheSecurite.getDirecteurPlonge().getIdWeb() : null);
		value.put(TIMESTAMP, ficheSecurite.getTimestamp());
		value.put(ID_SITE, ficheSecurite.getSite() != null ? ficheSecurite.getSite().getId() : null);
		value.put(ETAT, ficheSecurite.getEtat() != null ? ficheSecurite.getEtat().toString() : null);
		value.put(VERSION, ficheSecurite.getVersion());
		
		long insertedId = mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		//Maj de l'id de la fiche
		ficheSecurite.setId(insertedId);
		
		//Ajout des palanques
		PalanqueeDao palanqueeDao = new PalanqueeDao(pContext);
		for(int i = 0; i < ficheSecurite.getPalanquees().size(); i++){
			Palanquee palanquee = ficheSecurite.getPalanquees().get(i);
			
			palanquee.setIdFicheSecurite(ficheSecurite.getId());
			
			palanqueeDao.insert(palanquee);
			
			ficheSecurite.getPalanquees().remove(i);
			ficheSecurite.getPalanquees().add(i, palanquee);
		}
		
		return ficheSecurite;
	}
	
	/**
	 * Supprime toute les fiches archiv�es dont l'id est dans la liste fourni
	 * @param idsFiche
	 */
	public void deleteByIds(List<Integer> idsFiche){
		if(idsFiche.size() > 0){
			String whereClause = "";
			String[] whereArgs =  new String[idsFiche.size()];
			
			int i = 0;
			for(Integer idFiche : idsFiche){
				whereArgs[i] = idFiche.toString();
				if(!whereClause.isEmpty()) whereClause += " OR ";
				whereClause += ID_LOCAL + " = ?";
				i++;
			}		
			whereClause = ETAT + " = '" + EnumEtat.VALIDE.toString()+"' AND ( " + whereClause + " )";
			
			SQLiteDatabase mDb = open();
			mDb.delete(TABLE_NAME, whereClause, whereArgs);
			mDb.close();
		}
	}

	/**
	 * Supprime la fiche pass� en param�tre et ses �ventuelles historiques
	 * @param id
	 */
	public void delete(Long id) {
		if(id != null){
			HistoriqueDao historiqueDao = new HistoriqueDao(pContext);
			historiqueDao.deleteByIdFiche(id);
			
			SQLiteDatabase mDb = open();
			mDb.delete(TABLE_NAME, ID_LOCAL +" = ?", new String[]{id.toString()});
			mDb.close();
		}
	}
	
	/**
	 * Transforme un curseur issue d'une requete sur la table des fiches de s�curit� en une ListeFichesSecurite
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
					cursor.getLong(cursor.getColumnIndex(ID_LOCAL)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					embarcationDao.getByIdWeb(cursor.getInt(cursor.getColumnIndex(ID_EMBARCATION_WEB))),
					moniteurDao.getByIdWeb(cursor.getInt(cursor.getColumnIndex(ID_DIRECTEUR_PLONGE_WEB))),
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
