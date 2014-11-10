package com.deep_blue.oxygen.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.deep_blue.oxygen.model.Utilisateur;

/**
 * 
 * @author Raphael
 *
 */
public class UtilisateurDao extends BaseDao{

	public static final String TABLE_NAME = "db_utilisateur";
	
	public static final String LOGIN = "login";
	public static final String NOM = "nom";
	public static final String PRENOM = "prenom";
	public static final String MOTDEPASSE = "mot_de_passe";
	public static final String ADMINISTRATEUR = "administrateur";
	public static final String EMAIL = "email";
	public static final String ACTIF = "actif";
	public static final String VERSION = "version";
	
	public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" + 
					LOGIN + " TEXT PRIMARY KEY, " +
					NOM + " TEXT, " + 
					PRENOM + " TEXT, " + 
					MOTDEPASSE + " TEXT, " + 
					ADMINISTRATEUR + " INTEGER, " + 
					EMAIL + " TEXT, " + 
					ACTIF + " INTEGER, " + 
					VERSION + " INTEGER INTEGER DEFAULT 0"+
					");";
	
	public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	
	public UtilisateurDao(Context pContext){
		super(pContext);
	}
	
	/**
	 * Retourne tout les utilisateur
	 * @return
	 */
	public List<Utilisateur> getAll(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		List<Utilisateur> resultList  = cursorToUtilisateurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Retourne tout les utilisateur actifs
	 * @return
	 */
	public List<Utilisateur> getAllActif(){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ACTIF+" = 1", null);
		
		List<Utilisateur> resultList  = cursorToUtilisateurList(cursor);
		
		mDb.close();
		
		return resultList;
	}
	
	/**
	 * Retourne l'utilisateur de login spécifié ou null si il n'existe pas
	 * @param login
	 * @return
	 */
	public Utilisateur getByLogin(String login){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+LOGIN+" = ?", new String[]{login});
		
		List<Utilisateur> resultList  = cursorToUtilisateurList(cursor);
		
		mDb.close();
		
		if(resultList.size() == 1)
			return resultList.get(0);
		else
			return null;
	}
	
	/**
	 * 
	 * @param utilisateur
	 * @return
	 */
	public Utilisateur insert(Utilisateur utilisateur){
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(UtilisateurDao.LOGIN, utilisateur.getLogin());
		value.put(UtilisateurDao.NOM, utilisateur.getNom());
		value.put(UtilisateurDao.PRENOM, utilisateur.getPrenom());
		value.put(UtilisateurDao.MOTDEPASSE, utilisateur.getMotDePasse());
		value.put(UtilisateurDao.ADMINISTRATEUR, utilisateur.getAdministrateur() ? 1 : 0);
		value.put(UtilisateurDao.EMAIL, utilisateur.getEmail());
		value.put(UtilisateurDao.ACTIF, utilisateur.getActif() ? 1 : 0);
		value.put(UtilisateurDao.VERSION, utilisateur.getVersion());
		
		mDb.insert(UtilisateurDao.TABLE_NAME, null, value);
		
		mDb.close();
		return utilisateur;
	}
	
	/**
	 * 
	 * @param utilisateur
	 * @return
	 */
	public Utilisateur update(Utilisateur utilisateur){
		
		SQLiteDatabase mDb = open();
		
		ContentValues value = new ContentValues();
		value.put(UtilisateurDao.NOM, utilisateur.getNom());
		value.put(UtilisateurDao.PRENOM, utilisateur.getPrenom());
		value.put(UtilisateurDao.MOTDEPASSE, utilisateur.getMotDePasse());
		value.put(UtilisateurDao.ADMINISTRATEUR, utilisateur.getAdministrateur() ? 1 : 0);
		value.put(UtilisateurDao.EMAIL, utilisateur.getEmail());
		value.put(UtilisateurDao.ACTIF, utilisateur.getActif() ? 1 : 0);
		value.put(UtilisateurDao.VERSION, utilisateur.getVersion());
		
		mDb.update(UtilisateurDao.TABLE_NAME, null, "WHERE "+LOGIN+" = ?", new String[]{utilisateur.getLogin()});
		
		mDb.close();
		return utilisateur;
	}
	
	public Utilisateur authentifier(String login, String password){
		SQLiteDatabase mDb = open();
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+LOGIN+" = ? AND "+MOTDEPASSE+" = ?", new String[]{login, password});
		
		List<Utilisateur> resultList = cursorToUtilisateurList(cursor);
		mDb.close();
		
		if(resultList.size() == 1)
			return resultList.get(0);
		else
			return null;
	}
	
	/**
	 * Transforme a cursor result of a query on the Utilisateur table into an array of Utilisateur
	 * @param cursor
	 * @return
	 */
	private List<Utilisateur> cursorToUtilisateurList(Cursor cursor){
		List<Utilisateur> resultList = new ArrayList<Utilisateur>();
		
		while(cursor.moveToNext()){
			Utilisateur utilisateur = new Utilisateur(
					cursor.getString(cursor.getColumnIndex(LOGIN)),
					cursor.getString(cursor.getColumnIndex(NOM)),
					cursor.getString(cursor.getColumnIndex(PRENOM)),
					cursor.getString(cursor.getColumnIndex(MOTDEPASSE)),
					cursor.getInt(cursor.getColumnIndex(ADMINISTRATEUR)) > 0,
					cursor.getString(cursor.getColumnIndex(EMAIL)),
					cursor.getInt(cursor.getColumnIndex(ACTIF)) > 0,
					cursor.getInt(cursor.getColumnIndex(VERSION))
					);
			
			resultList.add(utilisateur);			
		}
		cursor.close();
		
		return resultList;
	}
}