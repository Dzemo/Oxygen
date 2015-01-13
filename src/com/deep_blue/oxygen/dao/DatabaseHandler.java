package com.deep_blue.oxygen.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(AptitudeDao.TABLE_CREATE);
		db.execSQL(EmbarcationDao.TABLE_CREATE);
		db.execSQL(FicheSecuriteDao.TABLE_CREATE);
		db.execSQL(HistoriqueDao.TABLE_CREATE);
		db.execSQL(MoniteurDao.TABLE_CREATE);
		db.execSQL(PalanqueeDao.TABLE_CREATE);
		db.execSQL(PlongeurDao.TABLE_CREATE);
		db.execSQL(UtilisateurDao.TABLE_CREATE);
		db.execSQL(SiteDao.TABLE_CREATE);
		
		//Ajout des "+PlongeurDao.APTITUDES+"
		//Debutant
		/*db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (1, 'Débutant', 'Débutant', 6, 0, 0, 0, 0, 0, 0, 0);");
		
		//PA
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (2, 'PA-12', 'Plongé autonome 12m', 12, 20, 12, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (3, 'PA-20', 'Plongé autonome 20m', 20, 40, 20, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (4, 'PA-40', 'Plongé autonome 40m', 40, 60, 40, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (5, 'PA-60', 'Plongé autonome 60m', 60, 60, 60, 0, 0, 0, 0, 0);");
		//PE
        db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (6, 'PE-12', 'Plongé encadré', 12, 22, 0, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (7, 'PE-20', 'Plongé encadré', 20, 40, 0, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (8, 'PE-40', 'Plongé encadré', 40, 60, 0, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (9, 'PE-60', 'Plongé encadré', 60, 60, 0, 0, 0, 0, 0, 0);");
		
        //Nitrox
        db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (10, 'PN-20', 'Plongé au nitrox 20m', 0, 0, 0, 20, 0, 0, 0, 0);   ");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (11, 'PN-C', 'Plongé au nitrox confirmée', 0, 0, 0, 60, 0, 0, 0, 0);");
		
		//Enseignement
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (12, 'E-1', 'Enseignement niveau 1, BPJEPS plongée, Stagiaire BPJEPS plongée', 0, 0, 0, 0, 0, 6, 0, 6);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (13, 'E-2', 'Enseignement niveau 2, Stagiaire BEES 1 plongée', 0, 0, 0, 0, 0, 20, 20, 20);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (14, 'E-3', 'Enseignement niveau 3, BEES 1 plongée Stagiaire, DEJEPS plongée Stagiaire, DESJEPS plongée', 0, 0, 0, 0, 0, 40, 40, 40);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (15, 'E-4', 'Enseignement niveau 4, BEES 2 plongée, DEJEPS plongée, DESJEPS plongée', 0, 0, 0, 0, 0, 60, 60, 60);");
		
		//GP
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (16, 'GP', 'Guide de palanqué, BPJEPS plongée, Stagiaire BPJEPS plongée', 0, 0, 0, 0, 40, 0, 0, 40);");
		
		///P
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (17, 'P-1', 'Plongé niveau 1', 20, 20, 0, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (18, 'P-1 a', 'Plongé niveau 1 autonome', 20, 20, 12, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (19, 'P-2', 'Plongé niveau 2', 40, 40, 20, 0, 0, 0, 0, 0);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (20, 'P-3', 'Plongé niveau 3', 60, 60, 60, 60, 60, 60, 60, 60);");
		db.execSQL("INSERT INTO "+AptitudeDao.TABLE_NAME+" ("+AptitudeDao.ID_WEB+", "+AptitudeDao.LIBELLE_COURT+", "+AptitudeDao.LIBELLE_LONG+", "+AptitudeDao.TECHNIQUE_MAX+", "+AptitudeDao.ENCADREE_MAX+", "+AptitudeDao.AUTONOME_MAX+", "+AptitudeDao.NITROX_MAX+", "+AptitudeDao.AJOUT_MAX+", "+AptitudeDao.ENSEIGNEMENT_AIR_MAX+", "+AptitudeDao.ENSEIGNEMENT_NITROX_MAX+", "+AptitudeDao.ENCADREMENT_MAX+") VALUES (21, 'P-4', 'Plongé niveau 4', 60, 60, 60, 0, 40, 0, 0, 40);");
	
		//Ajout des embarcations de tests
		db.execSQL("INSERT INTO "+EmbarcationDao.TABLE_NAME +" ("+EmbarcationDao.ID_WEB +", "+EmbarcationDao.LIBELLE +", "+EmbarcationDao.COMMENTAIRE +", "+EmbarcationDao.DISPONIBLE +", "+EmbarcationDao.CONTENANCE +") VALUES (1, 'EMB-1', 'Embarcation-1, disponible', 1, 10);");
		db.execSQL("INSERT INTO "+EmbarcationDao.TABLE_NAME +" ("+EmbarcationDao.ID_WEB +", "+EmbarcationDao.LIBELLE +", "+EmbarcationDao.COMMENTAIRE +", "+EmbarcationDao.DISPONIBLE +", "+EmbarcationDao.CONTENANCE +") VALUES (2, 'EMB-2', 'Embarcation-2, indisponible', 0, 10);");
		
		//Ajout des sites
		db.execSQL("INSERT INTO "+SiteDao.TABLE_NAME +" ("+SiteDao.ID +", "+SiteDao.ID_WEB +", "+SiteDao.NOM +", "+SiteDao.COMMENTAIRE +", "+SiteDao.DESACTIVE +", "+SiteDao.VERSION+") VALUES (1, 1, 'La plage sur mer', 'Pour débutant', 0, 0);");
		db.execSQL("INSERT INTO "+SiteDao.TABLE_NAME +" ("+SiteDao.ID +", "+SiteDao.ID_WEB +", "+SiteDao.NOM +", "+SiteDao.COMMENTAIRE +", "+SiteDao.DESACTIVE +", "+SiteDao.VERSION+") VALUES (2, 2, 'Le grand bleu', 'Idéal 20m', 0, 0);");
		
		
		//Ajout des fiches de sécurités
		db.execSQL("INSERT INTO "+FicheSecuriteDao.TABLE_NAME+" ("+FicheSecuriteDao.ID_LOCAL+", "+FicheSecuriteDao.ID_EMBARCATION_WEB+", "+FicheSecuriteDao.ID_DIRECTEUR_PLONGE_WEB+", "+FicheSecuriteDao.TIMESTAMP+", "+FicheSecuriteDao.ID_SITE+", "+FicheSecuriteDao.ETAT+") VALUES (1, 1, 3, 1418228748, 1, 'SYNCHRONISE');");
		db.execSQL("INSERT INTO "+FicheSecuriteDao.TABLE_NAME+" ("+FicheSecuriteDao.ID_LOCAL+", "+FicheSecuriteDao.ID_EMBARCATION_WEB+", "+FicheSecuriteDao.ID_DIRECTEUR_PLONGE_WEB+", "+FicheSecuriteDao.TIMESTAMP+", "+FicheSecuriteDao.ID_SITE+", "+FicheSecuriteDao.ETAT+") VALUES (2, 1, 4, 1394209548, 2, 'SYNCHRONISE');");
		//db.execSQL("INSERT INTO "+FicheSecuriteDao.TABLE_NAME+" ("+FicheSecuriteDao.ID_LOCAL+", "+FicheSecuriteDao.ID_EMBARCATION_WEB+", "+FicheSecuriteDao.ID_DIRECTEUR_PLONGE_WEB+", "+FicheSecuriteDao.TIMESTAMP+", "+FicheSecuriteDao.ID_SITE+", "+FicheSecuriteDao.ETAT+") VALUES (3, 1, 4, 1314249548, 2, 'VALIDE');");
		
		//Ajout des historiques
		db.execSQL("INSERT INTO "+HistoriqueDao.TABLE_NAME+" ("+HistoriqueDao.ID_HISTORIQUE+", "+HistoriqueDao.LOGIN_UTILISATEUR+", "+HistoriqueDao.TIMESTAMP+", "+HistoriqueDao.COMMENTAIRE+", "+HistoriqueDao.ID_LOCAL_FICHE_SECURITE+") VALUES (1, 'admin', 1314249548, 'Récupération de la fiche', 3);");
		db.execSQL("INSERT INTO "+HistoriqueDao.TABLE_NAME+" ("+HistoriqueDao.ID_HISTORIQUE+", "+HistoriqueDao.LOGIN_UTILISATEUR+", "+HistoriqueDao.TIMESTAMP+", "+HistoriqueDao.COMMENTAIRE+", "+HistoriqueDao.ID_LOCAL_FICHE_SECURITE+") VALUES (2, 'admin', 1314249248, 'Validation de la fiche', 3);");
		
		//Ajout des palanques
		db.execSQL("INSERT INTO "+PalanqueeDao.TABLE_NAME+" ("+PalanqueeDao.ID+", "+PalanqueeDao.ID_FICHE_SECURITE+", "+PalanqueeDao.ID_MONITEUR_WEB+", "+PalanqueeDao.NUMERO+", "+PalanqueeDao.TYPE_PLONGE+", "+PalanqueeDao.TYPE_GAZ+", "+PalanqueeDao.PROFONDEUR_PREVUE+", "+PalanqueeDao.PROFONDEUR_REALISE_MONITEUR+", "+PalanqueeDao.DUREE_PREVUE+", "+PalanqueeDao.DUREE_REALISE_MONITEUR+", "+PalanqueeDao.HEURE+") VALUES (1, 1, 1, 1, 'TECHNIQUE', 'AIR', 12, NULL, 900, NULL, '10:00:00');");
		db.execSQL("INSERT INTO "+PalanqueeDao.TABLE_NAME+" ("+PalanqueeDao.ID+", "+PalanqueeDao.ID_FICHE_SECURITE+", "+PalanqueeDao.ID_MONITEUR_WEB+", "+PalanqueeDao.NUMERO+", "+PalanqueeDao.TYPE_PLONGE+", "+PalanqueeDao.TYPE_GAZ+", "+PalanqueeDao.PROFONDEUR_PREVUE+", "+PalanqueeDao.PROFONDEUR_REALISE_MONITEUR+", "+PalanqueeDao.DUREE_PREVUE+", "+PalanqueeDao.DUREE_REALISE_MONITEUR+", "+PalanqueeDao.HEURE+") VALUES (2, 1, 2, 2, 'ENCADRE', 'NITROX', 60, NULL, 2700, NULL, '11:00:00');");
		db.execSQL("INSERT INTO "+PalanqueeDao.TABLE_NAME+" ("+PalanqueeDao.ID+", "+PalanqueeDao.ID_FICHE_SECURITE+", "+PalanqueeDao.ID_MONITEUR_WEB+", "+PalanqueeDao.NUMERO+", "+PalanqueeDao.TYPE_PLONGE+", "+PalanqueeDao.TYPE_GAZ+", "+PalanqueeDao.PROFONDEUR_PREVUE+", "+PalanqueeDao.PROFONDEUR_REALISE_MONITEUR+", "+PalanqueeDao.DUREE_PREVUE+", "+PalanqueeDao.DUREE_REALISE_MONITEUR+", "+PalanqueeDao.HEURE+") VALUES (3, 1, NULL, 3, 'AUTONOME', 'AIR', 25, NULL, 1800, NULL, '16:00:00');");
		db.execSQL("INSERT INTO "+PalanqueeDao.TABLE_NAME+" ("+PalanqueeDao.ID+", "+PalanqueeDao.ID_FICHE_SECURITE+", "+PalanqueeDao.ID_MONITEUR_WEB+", "+PalanqueeDao.NUMERO+", "+PalanqueeDao.TYPE_PLONGE+", "+PalanqueeDao.TYPE_GAZ+", "+PalanqueeDao.PROFONDEUR_PREVUE+", "+PalanqueeDao.PROFONDEUR_REALISE_MONITEUR+", "+PalanqueeDao.DUREE_PREVUE+", "+PalanqueeDao.DUREE_REALISE_MONITEUR+", "+PalanqueeDao.HEURE+") VALUES (4, 2, NULL, 1, 'AUTONOME', 'AIR', 25, NULL, 1800, NULL, '16:00:00');");
		db.execSQL("INSERT INTO "+PalanqueeDao.TABLE_NAME+" ("+PalanqueeDao.ID+", "+PalanqueeDao.ID_FICHE_SECURITE+", "+PalanqueeDao.ID_MONITEUR_WEB+", "+PalanqueeDao.NUMERO+", "+PalanqueeDao.TYPE_PLONGE+", "+PalanqueeDao.TYPE_GAZ+", "+PalanqueeDao.PROFONDEUR_PREVUE+", "+PalanqueeDao.PROFONDEUR_REALISE_MONITEUR+", "+PalanqueeDao.DUREE_PREVUE+", "+PalanqueeDao.DUREE_REALISE_MONITEUR+", "+PalanqueeDao.HEURE+") VALUES (5, 3, NULL, 1, 'AUTONOME', 'AIR', 25, NULL, 1800, NULL, '16:00:00');");
		
		//Ajout des plongeurs (groupé par palanquée)
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (1, 1, 1, 'Bessiere', 'Cyril', '6', '01 23 45 67 89', '01 98 76 54 32', '01/12/1984');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (2, 1, 1, 'Guillon', 'Amelie', '6', '01 23 45 67 89', '01 98 76 54 32', '31/01/1987');");
		
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (3, 2, 1, 'Verhaeghe', 'Marie', '9;11', '01 23 45 67 89', '01 98 76 54 32', '13/05/1984');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (4, 2, 1, 'Verhaeghe', 'Hervé', '9;11', '01 23 45 67 89', '01 98 76 54 32', '02/06/1974');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (5, 2, 1, 'Gimenez', 'Sara', '9;11', '01 23 45 67 89', '01 98 76 54 32', '08/04/1991');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (6, 2, 1, 'Saunier', 'Jean-Luc', '9;11', '01 23 45 67 89', '01 98 76 54 32', '11/10/1985');");
		
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (7, 3, 1, 'Lacour', 'Myriam', '4;10', '01 23 45 67 89', '01 98 76 54 32', '30/07/1974');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (8 ,3, 1, 'Girault', 'Stéphane', '4;10', '01 23 45 67 89', '01 98 76 54 32', '20/11/1967');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (9, 3, 1, 'Leriche', 'Marc', '4;10', '01 23 45 67 89', '01 98 76 54 32', '15/06/1985');");
		
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (10, 4, 2, 'Lacour', 'Myriam', '4;10', '01 23 45 67 89', '01 98 76 54 32', '30/07/1974');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (11, 4, 2, 'Girault', 'Stéphane', '4;10', '01 23 45 67 89', '01 98 76 54 32', '20/11/1967');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (12, 4, 2, 'Leriche', 'Marc', '4;10', '01 23 45 67 89', '01 98 76 54 32', '15/06/1985');");

		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (13, 5, 3, 'Lacour', 'Myriam', '4;10', '01 23 45 67 89', '01 98 76 54 32', '30/07/1974');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (14, 5, 3, 'Girault', 'Stéphane', '4;10', '01 23 45 67 89', '01 98 76 54 32', '20/11/1967');");
		db.execSQL("INSERT INTO "+PlongeurDao.TABLE_NAME+" ("+PlongeurDao.ID+", "+PlongeurDao.ID_PALANQUEE+", "+PlongeurDao.ID_FICHE_SECURITE+", "+PlongeurDao.NOM+", "+PlongeurDao.PRENOM+", "+PlongeurDao.APTITUDES+", "+PlongeurDao.TELEPHONE+", "+PlongeurDao.TELEPHONE_URGENCE+", "+PlongeurDao.DATE_NAISSANCE+") VALUES (15, 5, 3, 'Leriche', 'Marc', '4;10', '01 23 45 67 89', '01 98 76 54 32', '15/06/1985');");
		
		//Ajout des moniteurs de tests
		db.execSQL("INSERT INTO "+MoniteurDao.TABLE_NAME+" ("+MoniteurDao.ID_WEB+", "+MoniteurDao.NOM+", "+MoniteurDao.PRENOM+", "+MoniteurDao.APTITUDES+", "+MoniteurDao.ACTIF+", "+MoniteurDao.DIRECTEUR_PLONGE+", "+MoniteurDao.EMAIL+", "+MoniteurDao.TELEPHONE+") VALUES (1, 'Tomas', 'Bessiere', '5;15', 1, 0, 'tomas.bessiere@email.com', '01 23 45 67 89');");
		db.execSQL("INSERT INTO "+MoniteurDao.TABLE_NAME+" ("+MoniteurDao.ID_WEB+", "+MoniteurDao.NOM+", "+MoniteurDao.PRENOM+", "+MoniteurDao.APTITUDES+", "+MoniteurDao.ACTIF+", "+MoniteurDao.DIRECTEUR_PLONGE+", "+MoniteurDao.EMAIL+", "+MoniteurDao.TELEPHONE+") VALUES (2, 'François ', 'Simonet', '5;15;11', 1, 0, 'francois.simonet@email.com', '01 23 45 67 89');");
		db.execSQL("INSERT INTO "+MoniteurDao.TABLE_NAME+" ("+MoniteurDao.ID_WEB+", "+MoniteurDao.NOM+", "+MoniteurDao.PRENOM+", "+MoniteurDao.APTITUDES+", "+MoniteurDao.ACTIF+", "+MoniteurDao.DIRECTEUR_PLONGE+", "+MoniteurDao.EMAIL+", "+MoniteurDao.TELEPHONE+") VALUES (3, 'Pierre', 'Saunier', '16;5', 1, 1, 'pierre.saunier@email.com', '01 23 45 67 89');");
		db.execSQL("INSERT INTO "+MoniteurDao.TABLE_NAME+" ("+MoniteurDao.ID_WEB+", "+MoniteurDao.NOM+", "+MoniteurDao.PRENOM+", "+MoniteurDao.APTITUDES+", "+MoniteurDao.ACTIF+", "+MoniteurDao.DIRECTEUR_PLONGE+", "+MoniteurDao.EMAIL+", "+MoniteurDao.TELEPHONE+") VALUES (4, 'Eric', 'Delaunay', '21;11', 1, 1, 'eric.delaunay@email.com', '01 23 45 67 89');");

		//Ajout d'un utilisateur admin de test
		db.execSQL("INSERT INTO "+UtilisateurDao.TABLE_NAME+" ("+UtilisateurDao.LOGIN+","+UtilisateurDao.NOM+", "+UtilisateurDao.PRENOM+", "+UtilisateurDao.MOTDEPASSE+", "+UtilisateurDao.ADMINISTRATEUR+", "+UtilisateurDao.EMAIL+", "+UtilisateurDao.ACTIF+", "+UtilisateurDao.ID_WEB_MONITEUR_ASSOCIE+") VALUES ('admin','admin', 'admin', 'admin', 1, 'raphael.bideau@gmail.com', 1, 3);");
		db.execSQL("INSERT INTO "+UtilisateurDao.TABLE_NAME+" ("+UtilisateurDao.LOGIN+","+UtilisateurDao.NOM+", "+UtilisateurDao.PRENOM+", "+UtilisateurDao.MOTDEPASSE+", "+UtilisateurDao.ADMINISTRATEUR+", "+UtilisateurDao.EMAIL+", "+UtilisateurDao.ACTIF+", "+UtilisateurDao.ID_WEB_MONITEUR_ASSOCIE+") VALUES ('test','test', 'test', 'test', 1, 'raphael.bideau@gmail.com', 0, NULL);");
		
		/** FIN AJOUT DONNES TEST */
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion < 23){
			db.execSQL(AptitudeDao.TABLE_DROP);
			db.execSQL(EmbarcationDao.TABLE_DROP);
			db.execSQL(FicheSecuriteDao.TABLE_DROP);
			db.execSQL(HistoriqueDao.TABLE_DROP);
			db.execSQL(MoniteurDao.TABLE_DROP);
			db.execSQL(PlongeurDao.TABLE_DROP);
			db.execSQL(PalanqueeDao.TABLE_DROP);
			db.execSQL(UtilisateurDao.TABLE_DROP);
			db.execSQL(SiteDao.TABLE_DROP);
	
			onCreate(db);
		} else if (newVersion == 23){
			db.execSQL("ALTER TABLE "+FicheSecuriteDao.TABLE_NAME+" ADD COLUMN "+FicheSecuriteDao.DESACTIVE+ " INTEGER DEFAULT 0");
			db.execSQL("ALTER TABLE "+PalanqueeDao.TABLE_NAME+" ADD COLUMN "+PalanqueeDao.DESACTIVE+ " INTEGER DEFAULT 0");
			db.execSQL("ALTER TABLE "+PlongeurDao.TABLE_NAME+" ADD COLUMN "+PlongeurDao.DESACTIVE+ " INTEGER DEFAULT 0");
		}
	}
}