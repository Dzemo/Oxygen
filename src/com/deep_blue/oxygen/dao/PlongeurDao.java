package com.deep_blue.oxygen.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.ListePlongeurs;
import com.deep_blue.oxygen.model.Palanquee;
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
	public static final String PROFONDEUR_REALISEE = "profondeur_realisee";
	public static final String DUREE_REALISEE = "duree_realisee";
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
		    PROFONDEUR_REALISEE + " REAL, " +
		    DUREE_REALISEE + " INTEGER, " +
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
	 * Retourne les nombres dernier plongeur, utilisé pour les suggestion
	 * 
	 * @param nombre Nombre de plongeur retourné
	 * @return
	 */
	public List<Plongeur> getLastX(int nombre){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME+" ORDER BY "+ VERSION+" DESC LIMIT ?", new String[]{String.valueOf(nombre*4)});
		
		List<Plongeur> resultList  = cursorToPlongeurList(cursor);
		
		mDb.close();
		
		//Suppression des doublons et des plongeurs non set
		Map<String, Plongeur> plongeursMap = new HashMap<String, Plongeur>();
		String cle;
		for(Plongeur plongeur : resultList){
			if(plongeur.getNom() != null && !plongeur.getNom().isEmpty() &&
					plongeur.getPrenom() != null && !plongeur.getPrenom().isEmpty() &&
					plongeur.getDateNaissance() != null && !plongeur.getDateNaissance().isEmpty()){
				//On ne prend pas les plongeurs dont le nom/prenom/date de naissance ne sont pas renseigné
				cle = plongeur.getNom()+"-"+plongeur.getPrenom()+"-"+plongeur.getDateNaissance();
				if(!plongeursMap.containsKey(cle)){
					plongeursMap.put(cle, plongeur);
				}
			}
		}

		return new ArrayList<Plongeur>(plongeursMap.values());
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
		
		//Mise à jours de la version
		plongeur.updateVersion();
		
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
		value.put(PROFONDEUR_REALISEE, plongeur.getProfondeurRealisee());
		value.put(DUREE_REALISEE, plongeur.getDureeRealisee());
		value.put(VERSION, plongeur.getVersion());
		
		Long insertedId = mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		plongeur.setId(insertedId);
		plongeur.setModifie(false);
		return plongeur;
	}
	
	/**
	 * 
	 * @param Plongeur
	 * @return
	 */
	public Plongeur update(Plongeur plongeur){

		//Mise à jours de la version
		plongeur.updateVersion();
		
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
		value.put(PROFONDEUR_REALISEE, plongeur.getProfondeurRealisee());
		value.put(DUREE_REALISEE, plongeur.getDureeRealisee());
		value.put(VERSION, plongeur.getVersion());
		
		mDb.update(TABLE_NAME, value, ID  + " = ?", new String[] {String.valueOf(plongeur.getId())});
		mDb.close();

		plongeur.setModifie(false);
		return plongeur;
	}
	
	/**
	 * Met à jours les plongeurs de la palanqué passé en parametre :
	 * Supprime les plongeurs qui appartenait a la palanqué mais qui ne sont plus dans le tableau de plongeur de la palanqués et met à jours ou insert les autres
	 * Pour la mise à jours des plongeurs, priviligier FicheSecuriteDao::update()
	 * @param palanquee
	 * @return
	 */
	public Palanquee updatePlongeursFromPalanquee(Palanquee palanquee){
		if(palanquee == null){
			return null;
		}
		
		//Suppression des palanquées qui ont été supprimées de la fiche
		String whereClause = ID_PALANQUEE+" = ?";
		String[] whereArgs = new String[palanquee.getPlongeurs().size()+1];
		whereArgs[0] = palanquee.getId().toString();
		int i = 1;
		for(Plongeur plongeur : palanquee.getPlongeurs()){
			whereClause += " AND "+ID+" != ?";
			whereArgs[i] = plongeur.getId().toString();
		}	
		SQLiteDatabase mDb = open();
		mDb.delete(TABLE_NAME, whereClause, whereArgs);
		mDb.close();		
		
		//Mise à jours des palanquées
		for(int j = 0; j < palanquee.getPlongeurs().size(); j++){
			Plongeur plongeur = palanquee.getPlongeurs().get(j);
						
			if(plongeur.getId() == null || plongeur.getId() < 0)
				plongeur = insert(plongeur);
			else
				plongeur = update(plongeur);
			
			palanquee.getPlongeurs().remove(j);
			palanquee.getPlongeurs().add(j, plongeur);
		}
		
		return palanquee;
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
					cursor.getLong(cursor.getColumnIndex(ID)),
					cursor.getInt(cursor.getColumnIndex(ID_WEB)),
					cursor.getLong(cursor.getColumnIndex(ID_PALANQUEE)),
					cursor.getLong(cursor.getColumnIndex(ID_FICHE_SECURITE)),
					cursor.getString(cursor.getColumnIndex(NOM)),
					cursor.getString(cursor.getColumnIndex(PRENOM)),
					new ListeAptitudes(cursor.getString(cursor.getColumnIndex(APTITUDES)), allAptitudes),
					cursor.getString(cursor.getColumnIndex(TELEPHONE)),
					cursor.getString(cursor.getColumnIndex(TELEPHONE_URGENCE)),
					cursor.getString(cursor.getColumnIndex(DATE_NAISSANCE)),
					cursor.getFloat(cursor.getColumnIndex(PROFONDEUR_REALISEE)),
					cursor.getInt(cursor.getColumnIndex(DUREE_REALISEE)),
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(plongeur);			
		}
		cursor.close();
		
		return resultList;
	}
}
