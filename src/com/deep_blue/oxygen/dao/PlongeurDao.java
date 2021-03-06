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
	public static final String DESACTIVE = "desactive";
	
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
		    VERSION + " INTEGER DEFAULT 0," +
		    DESACTIVE + " INTEGER DEFAULT 0" +
	    ");";
	
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public PlongeurDao(Context pContext){
		super(pContext);
		this.pContext = pContext;
	}
	
	/**
	 * Renvoi le timestamp de la derni�re modification ou 0 si aucune modification
	 * @return
	 */
	public Long getMaxVersion(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME + " WHERE " + DESACTIVE+ " = 0",null);
		mDb.close();
		
		if(cursor.getCount() == 1){
			return cursor.getLong(0);
		}
		else{
			return Long.valueOf(0);
		}
	}
	
	/**
	 * Retourne les nombres dernier plongeur, utilis� pour les suggestion
	 * 
	 * @param nombre Nombre de plongeur retourn�
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
				//On ne prend pas les plongeurs dont le nom/prenom/date de naissance ne sont pas renseign�
				cle = plongeur.getNom()+"-"+plongeur.getPrenom()+"-"+plongeur.getDateNaissance();
				if(!plongeursMap.containsKey(cle)){
					plongeursMap.put(cle, plongeur);
				}
			}
		}

		return new ArrayList<Plongeur>(plongeursMap.values());
	}
	
	/**
	 * Return le plongeur d'id sp�cifi�
	 * @param idPlongeur
	 * @return
	 */
	public Plongeur getById(int idPlongeur){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ID+" = ?", new String[]{String.valueOf(idPlongeur)});
		
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
	 * Return tout les plongeur appartenant � la palanqu�e d�sign�e
	 * @param idPalanquee
	 * @param desactive Inclue ou pas les plongeur d�sactiv�s (pour la synchronisation)
	 * @return
	 */
	public ListePlongeurs getByIdPalanquee(int idPalanquee, boolean desactive){
		SQLiteDatabase mDb = open();
		
		Cursor cursor;
		if(desactive)
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_PALANQUEE+" = ?", new String[]{String.valueOf(idPalanquee)});
		else
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ID_PALANQUEE+" = ?", new String[]{String.valueOf(idPalanquee)});
		
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
		
		//Mise � jours de la version
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
		value.put(TELEPHONE_URGENCE, plongeur.getTelephoneUrgence());
		value.put(DATE_NAISSANCE, plongeur.getDateNaissance());
		value.put(PROFONDEUR_REALISEE, plongeur.getProfondeurRealisee());
		value.put(DUREE_REALISEE, plongeur.getDureeRealisee());
		value.put(DESACTIVE, plongeur.isDesactive() ? 1 : 0);
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

		//Mise � jours de la version
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
		value.put(TELEPHONE_URGENCE, plongeur.getTelephoneUrgence());
		value.put(DATE_NAISSANCE, plongeur.getDateNaissance());
		value.put(PROFONDEUR_REALISEE, plongeur.getProfondeurRealisee());
		value.put(DUREE_REALISEE, plongeur.getDureeRealisee());
		value.put(DESACTIVE, plongeur.isDesactive() ? 1 : 0);
		value.put(VERSION, plongeur.getVersion());
		
		mDb.update(TABLE_NAME, value, ID  + " = ?", new String[] {String.valueOf(plongeur.getId())});
		mDb.close();

		plongeur.setModifie(false);
		return plongeur;
	}
	
	/**
	 * Met � jours les plongeurs de la palanqu� pass� en parametre :
	 * Supprime les plongeurs qui appartenait a la palanqu� mais qui ne sont plus dans le tableau de plongeur de la palanqu�s et met � jours ou insert les autres
	 * Pour la mise � jours des plongeurs, priviligier FicheSecuriteDao::update()
	 * @param palanquee
	 * @return
	 */
	public Palanquee updatePlongeursFromPalanquee(Palanquee palanquee){
		if(palanquee == null){
			return null;
		}
		
		//Suppression logique des plongeurs des palanquees qui ont �t� supprim�es de la fiche
		String whereClause = ID_PALANQUEE+" = ?";
		String[] whereArgsTmp = new String[palanquee.getPlongeurs().size()+1];
		whereArgsTmp[0] = palanquee.getId().toString();
		int i = 1;
		for(Plongeur plongeur : palanquee.getPlongeurs()){
			if(plongeur.getId() != null){
				whereClause += " AND "+ID+" != ?";
				whereArgsTmp[i] = plongeur.getId().toString();
				
				i++;
			}
		}	
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		SQLiteDatabase mDb = open();
		String[] whereArgs = new String[i];
		System.arraycopy(whereArgsTmp, 0, whereArgs, 0, i);
		mDb.update(TABLE_NAME, value, whereClause, whereArgs);
		mDb.close();		
		
		//Mise � jours des palanqu�es
		for(int j = 0; j < palanquee.getPlongeurs().size(); j++){
			Plongeur plongeur = palanquee.getPlongeurs().get(j);
			
			plongeur.setIdFicheSecurite(palanquee.getIdFicheSecurite());
			plongeur.setIdPalanquee(palanquee.getId());
			
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
	 * Suppression physique d'un plongeur
	 * @param plongeurId
	 */
	public void deletePhysique(Long plongeurId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID + " = ?", new String[] {String.valueOf(plongeurId)});
		
		mDb.close();
	}
	
	/**
	 * Suppression logique d'un plongeur
	 * @param plongeurId
	 */
	public void deleteLogique(Long plongeurId){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		mDb.update(TABLE_NAME, value, ID + " = ?", new String[] {String.valueOf(plongeurId)});
		
		mDb.close();
	}
	
	/**
	 * Suppression physique des plongeur appartenant � une palanquee
	 * @param palanqueeId
	 */
	public void deletePhysiqueParPalanqueeId(Long palanqueeId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID_PALANQUEE + " = ?", new String[] {String.valueOf(palanqueeId)});
		
		mDb.close();
	}
	
	/**
	 * Suppression logique des plongeur appartenant � une palanquee
	 * @param palanqueeId
	 */
	public void deleteLogiqueParPalanqueeId(Long palanqueeId){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		mDb.update(TABLE_NAME, value, ID_PALANQUEE + " = ?", new String[] {String.valueOf(palanqueeId)});
		
		mDb.close();
	}
	
	/**
	 * Suppression physique des plongeur appartenant � une palanquee de la fiche sp�cifi�
	 * @param ficheId
	 */
	public void deletePhysiqueParFicheId(Long ficheId){
		SQLiteDatabase mDb = open();
	
		mDb.delete(TABLE_NAME, ID_FICHE_SECURITE + " = ?", new String[] {String.valueOf(ficheId)});
		
		mDb.close();
	}
	
	/**
	 * Suppression logique des plongeur appartenant � une palanquee de la fiche sp�cifi�
	 * @param ficheId
	 */
	public void deleteLogiqueParFicheId(Long ficheId){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		mDb.update(TABLE_NAME, value, ID_FICHE_SECURITE + " = ?", new String[] {String.valueOf(ficheId)});
		
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
					cursor.getInt(cursor.getColumnIndex(DESACTIVE)) == 1,
					cursor.getLong(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(plongeur);			
		}
		cursor.close();
		
		return resultList;
	}
}
