package com.deep_blue.oxygen.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.ListePalanquees;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

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
	public static final String DESACTIVE = "desactive";
	
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
		    VERSION + " INTEGER  DEFAULT 0," +
		    DESACTIVE + " INTEGER DEFAULT 0" +
	    ");";
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	private Context pContext;
	
	public PalanqueeDao(Context pContext){
		super(pContext);
		
		this.pContext = pContext;
	}
	
	/**
	 * Renvoi le timestamp de la derni�re modification ou 0 si aucune modification
	 * @return
	 */
	public Long getMaxVersion(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT max("+VERSION+") FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0",null);
		mDb.close();
		
		if(cursor.getCount() == 1){
			return cursor.getLong(0);
		}
		else{
			return Long.valueOf(0);
		}
	}
	
	/**
	 * Return tout les palanqu�es appartenant � la fiche de s�curit� d�sign�e
	 * @param idFicheSecurite
	 * @param avecDesactive Inclue ou pas les palanqu�es et plongeurs desactiv� (pour la synchronisation)
	 * @return
	 */
	public ListePalanquees getByIdFicheSecurite(int idFicheSecurite, boolean avecDesactive){
		SQLiteDatabase mDb = open();
		
		Cursor cursor;
		if(avecDesactive)
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ID_FICHE_SECURITE+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		else
			cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+DESACTIVE+" = 0 AND "+ID_FICHE_SECURITE+" = ?", new String[]{String.valueOf(idFicheSecurite)});
		
		ListePalanquees resultList  = cursorToPalanqueeList(cursor, avecDesactive);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Met � jour une palanque dans la base de donn�e
	 * @param palanquee
	 * @return
	 */
	public Palanquee update(Palanquee palanquee){

		//Mise � jours de la version
		palanquee.updateVersion();
		
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, palanquee.getIdWeb());
		value.put(ID_FICHE_SECURITE, palanquee.getIdFicheSecurite());
		value.put(ID_MONITEUR_WEB, palanquee.getMoniteur() != null ? palanquee.getMoniteur().getIdWeb() : null);
		value.put(NUMERO, palanquee.getNumero());
		value.put(TYPE_PLONGE, palanquee.getTypePlonge() != null ? palanquee.getTypePlonge().toString() : null);
		value.put(TYPE_GAZ, palanquee.getTypeGaz() != null ?  palanquee.getTypeGaz().toString() : null);
		value.put(PROFONDEUR_PREVUE, palanquee.getProfondeurPrevue());
		value.put(PROFONDEUR_REALISE_MONITEUR, palanquee.getProfondeurRealiseeMoniteur());
		value.put(DUREE_PREVUE, palanquee.getDureePrevue());
		value.put(DUREE_REALISE_MONITEUR, palanquee.getDureeRealiseeMoniteur());
		value.put(DESACTIVE, palanquee.isDesactive() ? 1 : 0);
		value.put(HEURE, palanquee.getHeure());
		value.put(VERSION, palanquee.getVersion());
		
		mDb.update(TABLE_NAME, value, ID+" = ?", new String[]{palanquee.getId().toString()});
		mDb.close();
		
		//Maj de toute les plongeurs
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		palanquee = plongeurDao.updatePlongeursFromPalanquee(palanquee);
		palanquee.setModifie(false);
		return palanquee;
	}
	
	/**
	 * Ajoute une nouvelle palanque dans la base de donn�e
	 * @param palanquee
	 * @return
	 */
	public Palanquee insert(Palanquee palanquee){

		//Mise � jours de la version
		palanquee.updateVersion();
		
		//Ajout de la palanqu�e
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(ID_WEB, palanquee.getIdWeb());
		value.put(ID_FICHE_SECURITE, palanquee.getIdFicheSecurite());
		value.put(ID_MONITEUR_WEB, palanquee.getMoniteur() != null ? palanquee.getMoniteur().getIdWeb() : null);
		value.put(NUMERO, palanquee.getNumero());
		value.put(TYPE_PLONGE, palanquee.getTypePlonge() != null ? palanquee.getTypePlonge().toString() : null);
		value.put(TYPE_GAZ, palanquee.getTypeGaz() != null ?  palanquee.getTypeGaz().toString() : null);
		value.put(PROFONDEUR_PREVUE, palanquee.getProfondeurPrevue());
		value.put(PROFONDEUR_REALISE_MONITEUR, palanquee.getProfondeurRealiseeMoniteur());
		value.put(DUREE_PREVUE, palanquee.getDureePrevue());
		value.put(DUREE_REALISE_MONITEUR, palanquee.getDureeRealiseeMoniteur());
		value.put(DESACTIVE, palanquee.isDesactive() ? 1 : 0);
		value.put(HEURE, palanquee.getHeure());
		value.put(VERSION, palanquee.getVersion());
		
		Long insertedId = mDb.insert(TABLE_NAME, null, value);
		mDb.close();
		
		palanquee.setId(insertedId);
		
		//Ajout des plongeurs
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		for(int i = 0; i < palanquee.getPlongeurs().size(); i++){
			Plongeur plongeur = palanquee.getPlongeurs().get(i);
			
			plongeur.setIdPalanquee(palanquee.getId());
			plongeur.setIdFicheSecurite(palanquee.getIdFicheSecurite());
			
			plongeur = plongeurDao.insert(plongeur);
			
			palanquee.getPlongeurs().remove(i);
			palanquee.getPlongeurs().add(i, plongeur);
		}

		palanquee.setModifie(false);
		return palanquee;
	}
	
	public FicheSecurite updatePalanqueeFromFiche(FicheSecurite ficheSecurite){
		if(ficheSecurite == null){
			return null;
		}
		
		//Suppression logique des palanqu�es qui ont �t� supprim�es de la fiche
		String whereClause = ID_FICHE_SECURITE+" = ?";
		String[] whereArgsTmp = new String[ficheSecurite.getPalanquees().size()+1];
		whereArgsTmp[0] = ficheSecurite.getId().toString();
		int i = 1;
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		for(Palanquee palanquee : ficheSecurite.getPalanquees()){
			if(palanquee.getId() != null){
				whereClause += " AND "+ID+" != ?";
				whereArgsTmp[i] = palanquee.getId().toString();
			
				//Suppression des plongeurs
				plongeurDao.deleteLogiqueParPalanqueeId(palanquee.getId());
				
				i++;
			}
		}	
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		String[] whereArgs = new String[i];
		System.arraycopy(whereArgsTmp, 0, whereArgs, 0, i);
		mDb.update(TABLE_NAME, value, whereClause, whereArgs);
		mDb.close();	
		
		//Mise � jours des palanqu�es
		for(int j = 0; j < ficheSecurite.getPalanquees().size(); j++){
			Palanquee palanquee = ficheSecurite.getPalanquees().get(j);
			
			palanquee.setIdFicheSecurite(ficheSecurite.getId());
			
			if(palanquee.getId() == null || palanquee.getId() < 0)
				palanquee = insert(palanquee);
			else
				palanquee = update(palanquee);
			
			ficheSecurite.getPalanquees().remove(j);
			ficheSecurite.getPalanquees().add(j, palanquee);
		}
		
		return ficheSecurite;
	}
	
	/**
	 * Effectue une suppression logique des palanqu�es appartenant � la fiche sp�cifi�
	 * @param idFicheSecurite
	 */
	public void deleteLogiqueByFicheSecuriteId(Long idFicheSecurite){
		
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		plongeurDao.deleteLogiqueParFicheId(idFicheSecurite);
		
		SQLiteDatabase mDb = open();
		ContentValues value = new ContentValues();
		value.put(DESACTIVE, 1);
		mDb.update(TABLE_NAME, value, ID_FICHE_SECURITE +" = ?", new String[]{idFicheSecurite.toString()});
		mDb.close();	
	}
	
	/**
	 * Effectue une suppression physique des palanqu�es appartenant � la fiche sp�cifi�
	 * @param idFicheSecurite
	 */
	public void deletePhysiqueByFicheSecuriteId(Long idFicheSecurite){
		
		PlongeurDao plongeurDao = new PlongeurDao(pContext);
		plongeurDao.deletePhysiqueParFicheId(idFicheSecurite);
		
		SQLiteDatabase mDb = open();
		mDb.delete(TABLE_NAME, ID_FICHE_SECURITE +" = ?", new String[]{idFicheSecurite.toString()});
		mDb.close();	
	}
	
	/**
	 * Transforme a cursor result of a query on the palanquee table into an array of Palanquee
	 * @param cursor
	 * @param avecDesactive
	 * @return
	 */
	private ListePalanquees cursorToPalanqueeList(Cursor cursor, boolean avecDesactive){
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
					cursor.getInt(cursor.getColumnIndex(DESACTIVE)) == 1,
					cursor.getLong(cursor.getColumnIndex(VERSION)),
					plongeurDao.getByIdPalanquee(cursor.getInt(cursor.getColumnIndex(ID)), avecDesactive)
					);
			
			resultList.add(palanquee);			
		}
		cursor.close();
		
		return resultList;
	}
}
