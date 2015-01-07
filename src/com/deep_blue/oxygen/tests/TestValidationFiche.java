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
	
	private Context pContext;
	private PlongeurFactory plongeurFactory;
	private MoniteurFactory moniteurFactory;
	
	public TestValidationFiche(Context pContext){
		this.pContext = pContext;
		
		AptitudeDao aptitudeDao = new AptitudeDao(pContext);
		
		
		SparseArray<Aptitude> aptitudes = aptitudeDao.getAll();
		
		this.plongeurFactory = new PlongeurFactory(aptitudes);
		this.moniteurFactory = new MoniteurFactory(aptitudes);
	}
	
	public void testValidationEnregistrementFiche() {
		
		// Création de la fiche de test
		
		FicheSecurite fichesecuritetest = new FicheSecurite();
			fichesecuritetest.setId(1L);
			fichesecuritetest.setIdWeb(1);
			fichesecuritetest.setDate(new Date(2015,01,07));
			
			// Ajout du directeur de plongée
			Moniteur directeurplonge = moniteurFactory.generateDirecteurPlongee(1);
			fichesecuritetest.setDirecteurPlonge(directeurplonge);
			
			// Création d'une palanquée de test
			Palanquee palanquee1 = new Palanquee();
			fichesecuritetest.getPalanquees().add(palanquee1);
			
			// Type de plongée + Type de gaz + Profondeur prévue + Durée Prévue
			palanquee1.setTypePlonge(EnumTypePlonge.TECHNIQUE);
			palanquee1.setTypeGaz(EnumTypeGaz.AIR);
			palanquee1.setProfondeurPrevue(20F);
			palanquee1.setDureePrevue(20);
			
			// Ajout d'un moniteur
			palanquee1.setMoniteur(directeurplonge);
			
		
			// Ajout des plongeurs à la palanquée de test
			
			
				// Création des plongeurs pour le test
				Plongeur random1 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random1 = plongeurFactory.addAptitudeForPlongeur(random1, new String[]{"PE-12"});
				
				Plongeur random2 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random1 = plongeurFactory.addAptitudeForPlongeur(random2, new String[]{"PE-20"});
				
				Plongeur random3 = plongeurFactory.generateRandomPlongeur(1L, 1L);
				random1 = plongeurFactory.addAptitudeForPlongeur(random3, new String[]{"PE-20","PA-12"});
				
				// Ajout à la palanquée
				palanquee1.getPlongeurs().add(random1);
				palanquee1.getPlongeurs().add(random2);
				palanquee1.getPlongeurs().add(random3);
				
		
		// Test que ça marche
		List<String> erreurs = ValidationFiche.validationEnregistrementFiche(fichesecuritetest);
		for(String erreur : erreurs){
			Log.e("Erreur : %s", erreur);
		}
	}

}
