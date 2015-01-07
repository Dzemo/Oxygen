package com.deep_blue.oxygen.tests;

import java.util.HashMap;
import java.util.Map;

import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.Plongeur;

public class PlongeurFactory {

	private Map<String, Aptitude> aptitudesMap;
	
	public PlongeurFactory(SparseArray<Aptitude> aptitudes){

		aptitudesMap = new HashMap<String, Aptitude>();
		
		for(int i = 0; i < aptitudes.size(); i++ ){
			int key = aptitudes.keyAt(i);
			Aptitude aptitude = aptitudes.get(key);
			aptitudesMap.put(aptitude.getLibelleCourt(), aptitude);
		}
	}
	
	public Plongeur generateRandomPlongeur(Long idFiche, Long idPalanquee){
		Plongeur plongeur = new Plongeur();
		
		plongeur.setId(-1L);
		plongeur.setIdFicheSecurite(idFiche);
		plongeur.setIdPalanquee(idPalanquee);
		plongeur.setIdWeb(0);
		
		return plongeur;
	}
	
	public Plongeur addAptitudeForPlongeur(Plongeur plongeur, String[] aptitudesString){
		plongeur.setAptitudes(new ListeAptitudes());
		
		for(String aptitudeLibelle : aptitudesString){
			plongeur.getAptitudes().add(aptitudesMap.get(aptitudeLibelle));
		}
		
		return plongeur;
	}
}
