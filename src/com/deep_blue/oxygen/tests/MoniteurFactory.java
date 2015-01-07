package com.deep_blue.oxygen.tests;

import java.util.HashMap;
import java.util.Map;

import android.util.SparseArray;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.ListeAptitudes;
import com.deep_blue.oxygen.model.Moniteur;

public class MoniteurFactory {

	private Map<String, Aptitude> aptitudesMap;
	
	public MoniteurFactory(SparseArray<Aptitude> aptitudes){

		aptitudesMap = new HashMap<String, Aptitude>();
		
		for(int i = 0; i < aptitudes.size(); i++ ){
			int key = aptitudes.keyAt(i);
			Aptitude aptitude = aptitudes.get(key);
			aptitudesMap.put(aptitude.getLibelleCourt(), aptitude);
		}
	}
	
	public Moniteur generateDirecteurPlongee(Integer idWeb){
		Moniteur moniteur = new Moniteur();
		
		moniteur.setIdWeb(idWeb);
		moniteur.setDirecteurPlongee(true);
		
		return moniteur;
	}
	
	public Moniteur addAptitudeForMoniteur(Moniteur moniteur, String[] aptitudesString){
		moniteur.setAptitudes(new ListeAptitudes());
		
		for(String aptitudeLibelle : aptitudesString){
			moniteur.getAptitudes().add(aptitudesMap.get(aptitudeLibelle));
		}
		
		return moniteur;
	}
}
