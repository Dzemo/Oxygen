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
	public static final String DESACTIVE = "desactive";
	
	public static final String TABLE_CREATE = "CREATE TABLE "+TABLE_NAME+" ( "+
			ID_LOCAL + " INTEGER PRIMARY KEY, " +
			ID_WEB +" INTEGER, "+
			ID_EMBARCATION_WEB + " INTEGER, " +
			ID_DIRECTEUR_PLONGE_WEB + " INTEGER, " +
			TIMESTAMP + " INTEGER, " +
			ID_SITE + " TEXT, " +
			ETAT + " TEXT, " +
		    VERSION + " INTEGER DEFAULT 0," +
			DESACTIVE + " INTEGER DEFAULT 0" +
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
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ETAT+" != '" +EnumEtat.VALIDE.toString()+"'",null);

		Long maxVersion = Long.valueOf(0);
		if(cursor.getCount() == 1){
			cursor.moveToNext();
			maxVersion = cursor.getLong(0);
		}
		
		mDb.close();
		
		return maxVersion;	
	}
	
	/**
	 * Return la fiche de sécurite d'id spécifié
	 * @param idFicheSecurite
	 * @return
	 */
//	public FicheSecurite getById(int idFicheSecurite){
//		SQLiteDatabase mDb = open();
//		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ID_LOCAL+" = ?", new String[]{String.valueOf(idFicheSecurite)});
//		
//		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
//		
//		mDb.close();
//		
//		if(resultList.size() == 1){
//			return resultList.get(0);
//		}
//		else{
//			return null;
//		}
//	}
	
	/**
	 * Return la fiche de sécurite d'id web spécifié
	 * @param idWebFicheSecurite
	 * @return
	 */
//	public FicheSecurite getByIdWeb(int idWebFicheSecurite){
//		SQLiteDatabase mDb = open();
//		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ID_WEB+" = ?", new String[]{String.valueOf(idWebFicheSecurite)});
//		
//		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor);
//		
//		mDb.close();
//		
//		if(resultList.size() == 1){
//			return resultList.get(0);
//		}
//		else{
//			return null;
//		}
//	}
	
	/**
	 * Return tout les fiches de sécurité appartenant à l'état spécifié
	 * @param avecDesactive Inclue ou pas les fiches, palanquées et plongeurs desactivé
	 * @param etat
	 * @return
	 */
	public ListeFichesSecurite getByEtat(EnumEtat etat, boolean avecDesactive){
		SQLiteDatabase mDb = open();
		
		Cursor cursor;
		if(avecDesactive)
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ETAT+" = '"+String.valueOf(etat)+"'", null);
		else
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ETAT+" = '"+String.valueOf(etat)+"'", null);
			
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor, avecDesactive);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Return toutes les fiches de sécurité
	 * @param avecDesactive Inclue ou pas les fiches, palanquées et plongeurs desactivé
	 * @return
	 */
	public ListeFichesSecurite getAll(boolean avecDesactive){
		SQLiteDatabase mDb = open();
		
		Cursor cursor;
		if(avecDesactive)
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		else
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0", null);
			
		ListeFichesSecurite resultList  = cursorToFichesecuriteList(cursor, avecDesactive);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Met à jour une fiche desécurité dans la base de donnée et met à jours également toute les palanquée/plongeurs
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite update(FicheSecurite ficheSecurite){
		if(ficheSecurite == null)
			return null;
		
		//Enregistrement du site si il n'est pas déjà enregistré
		if(ficheSecurite.getSite() != null && (ficheSecurite.getSite().getId() == null || ficheSecurite.getSite().getId() < 0)){
			SiteDao siteDao = new SiteDao(pContext);
			siteDao.insert(ficheSecurite.getSite());
		}
		
		//Mise à jours de la version
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
		value.put(DESACTIVE, ficheSecurite.isDesactive() ? 1 : 0);
		
		mDb.update(TABLE_NAME, value, ID_LOCAL+" = ?", new String[]{ficheSecurite.getId().toString()});
		mDb.close();
		
		//Maj de toute les palanquées
		PalanqueeDao palanqueeDao = new PalanqueeDao(pContext);
		ficheSecurite = palanqueeDao.updatePalanqueeFromFiche(ficheSecurite);
		ficheSecurite.setModifie(false);
		
		return ficheSecurite;
	}
	
	/**
	 * Met à jour l'état d'une fiche de sécurité
	 * @param palanquee
	 * @return
	 */
	public FicheSecurite updateEtat(FicheSecurite ficheSecurite, EnumEtat etat){
		
		//Modification de l'état
		ficheSecurite.setEtat(etat);
		
		//Mise à jours de la version
		ficheSecurite.updateVersion();

		//Maj de la fiche
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(ETAT, ficheSecurite.getEtat() != null ? ficheSecurite.getEtat().toString() : null);
		value.put(VERSION, ficheSecurite.getVersion());
		
		mDb.update(TABLE_NAME, value, ID_LOCAL+" = ?", new String[]{ficheSecurite.getId().toString()});
		mDb.close();
		ficheSecurite.setModifie(false);

		return ficheSecurite;
	}
	
	/**
	 * Ajoute une nouvelle fiche de sécurité dans la base de donnée et ajoute également toute les palanquée/plongeurs
	 * @param ficheSecurite
	 * @return
	 */
	public FicheSecurite insert(FicheSecurite ficheSecurite){
		if(ficheSecurite == null){
			return null;
		}
		
		//Enregistrement du site si il n'est pas déjà enregistré
		if(ficheSecurite.getSite() != null && (ficheSecurite.getSite().getId() == null || ficheSecurite.getSite().getId() < 0)){
			SiteDao siteDao = new SiteDao(pContext);
			siteDao.insert(ficheSecurite.getSite());
		}
		
		//Mise à jours de la version
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
		value.put(DESACTIVE, ficheSecurite.isDesactive() ? 1 : 0);
		
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

		ficheSecurite.setModifie(false);
		return ficheSecurite;
	}
	
	/**
	 * Suppression logique toute les fiches archivées dont l'id est dans la liste fourni
	 * @param idsFiche
	 */
	public void deleteLogiqueByIds(List<Long> idsFiche){
		if(idsFiche.size() > 0){
			String whereClause = "";
			String[] whereArgs =  new String[idsFiche.size()];

			PalanqueeDao palanqueeDao = new PalanqueeDao(pContext);
			int i = 0;
			for(Long idFiche : idsFiche){
				whereArgs[i] = idFiche.toString();
				if(!whereClause.isEmpty()) whereClause += " OR ";
				whereClause += ID_LOCAL + " = ?";
				i++;
				
				//Suppression des palanquées
				palanqueeDao.deleteLogiqueByFicheSecuriteId(idFiche);
			}		
			whereClause = ETAT + " = '" + EnumEtat.VALIDE.toString()+"' AND ( " + whereClause + " )";

			ContentValues value = new ContentValues();
			value.put(DESACTIVE, 1);
			SQLiteDatabase mDb = open();
			mDb.update(TABLE_NAME, value, whereClause, whereArgs);
			mDb.close();
			
		}
	}

	/**
	 * Supprime physique la fiche passé en paramêtre et ses éventuelles historiques
	 * @param id
	 */
	public void deletePhysique(Long id) {
		if(id != null){
			HistoriqueDao historiqueDao = new HistoriqueDao(pContext);
			historiqueDao.deleteByIdFiche(id);
			
			SQLiteDatabase mDb = open();
			mDb.delete(TABLE_NAME, ID_LOCAL +" = ?", new String[]{id.toString()});
			mDb.close();
		}
	}
	
	/**
	 * Supprime logique la fiche en base de données
	 * @param palanquee
	 * @return
	 */
	public void deleteLogique(Long id){
		//Maj de la fiche
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		
		mDb.update(TABLE_NAME, value, ID_LOCAL+" = ?", new String[]{id.toString()});
		mDb.close();
	}
	
	/**
	 * Transforme un curseur issue d'une requete sur la table des fiches de sécurité en une ListeFichesSecurite
	 * @param cursor
	 * @return
	 */
	private ListeFichesSecurite cursorToFichesecuriteList(Cursor cursor, boolean avecDesative){
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
					palanqueeDao.getByIdFicheSecurite(cursor.getInt(cursor.getColumnIndex(ID_LOCAL)), avecDesative),
					cursor.getInt(cursor.getColumnIndex(DESACTIVE)) == 1
					);
			
			resultList.add(ficheSecurite);
			
		}
		cursor.close();
		
		return resultList;
	}
}
