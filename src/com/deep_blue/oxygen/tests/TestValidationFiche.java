package com.deep_blue.oxygen.tests;

import java.sql.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.deep_blue.oxygen.dao.AptitudeDao;
import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.ValidationFiche;


public class TestValidationFiche {
	
	private PlongeurFactory plongeurFactory;
	private MoniteurFactory moniteurFactory;
	
	public TestValidationFiche(Context pContext){
		
		AptitudeDao aptitudeDao = new AptitudeDao(pContext);
		
		
		SparseArray<Aptitude> aptitudes = aptitudeDao.getAll();
		
		this.plongeurFactory = new PlongeurFactory(aptitudes);
		this.moniteurFactory = new MoniteurFactory(aptitudes);
	}
	
	public void testValidationEnregistrementFiche() {
		
		// Cr�ation de la fiche de test
		
		FicheSecurite fichesecuritetest = new FicheSecurite();
			fichesecuritetest.setId(1L);
			fichesecuritetest.setIdWeb(1);
			fichesecuritetest.setDate(new Date(2015,01,07));
			
			// Ajout du directeur de plong�e
			Moniteur directeurplonge = moniteurFactory.generateDirecteurPlongee(1);
			fichesecuritetest.setDirecteurPlonge(directeurplonge);
			
			// Cr�ation d'une palanqu�e de test
			Palanquee palanquee1 = new Palanquee();
			palanquee1.setId(1L);
			palanquee1.setIdFicheSecurite(1L);
			palanquee1.setNumero(1);
			fichesecuritetest.getPalanquees().add(palanquee1);
			
			// Type de plong�e + Type de gaz + Profondeur pr�vue + Dur�e Pr�vue
			palanquee1.setTypePlonge(EnumTypePlonge.TECHNIQUE);
			palanquee1.setTypeGaz(EnumTypeGaz.AIR);
			palanquee1.setProfondeurPrevue(20F);
			palanquee1.setDureePrevue(20);
			
			// Ajout d'un moniteur
			Moniteur moniteur1 = moniteurFactory.generateDirecteurPlongee(1);
			moniteur1 = moniteurFactory.addAptitudeForMoniteur(moniteur1, new String[]{"E-4"});
			palanquee1.setMoniteur(moniteur1);
			
		
			// Ajout des plongeurs � la palanqu�e de test
			
			
				// Cr�ation des plongeurs pour le test
				Plongeur random1 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random1 = plongeurFactory.addAptitudeForPlongeur(random1, new String[]{"PE-20"});
				
				Plongeur random2 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random2 = plongeurFactory.addAptitudeForPlongeur(random2, new String[]{"PE-20"});
				
				Plongeur random3 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random3 = plongeurFactory.addAptitudeForPlongeur(random3, new String[]{"PE-20","PA-12"});
				
				// Ajout � la palanqu�e
				palanquee1.getPlongeurs().add(random1);
				palanquee1.getPlongeurs().add(random2);
				palanquee1.getPlongeurs().add(random3); 
				
		
		// Test que �a marche
		List<String> erreurs = ValidationFiche.validationClotureFiche(fichesecuritetest);
		Log.e("Nombre Erreurs","Nombre d'erreurs : "+erreurs.size());
		int i = 0;
		for(String erreur : erreurs){
			Log.e("Erreur","Erreur num�ro :"+i+" msg : "+erreur);
			i++;
		}
	}

}
