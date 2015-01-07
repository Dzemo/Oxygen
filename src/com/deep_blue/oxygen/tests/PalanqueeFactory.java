package com.deep_blue.oxygen.tests;

import java.util.List;

import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

public class PalanqueeFactory {
	
	public Palanquee createPalanquee(Moniteur moniteur, List<Plongeur> plongeurs){
		
		Palanquee palanquee = new Palanquee();
		palanquee.setMoniteur(moniteur);
	
		
		
		
		return palanquee;
	}
}
