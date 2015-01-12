package com.deep_blue.oxygen.util;

import java.util.ArrayList;
import java.util.List;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;

/**
 * Classe offrant des méthodes pour vérifier les règles de gestion des fiches de sécurité
 * @author Flavio
 *
 */
public class ValidationFiche {
	
	
	/**
	 * Permet de vérifier s'il y a des erreurs vis à vis de la validation des fiches
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationClotureFiche(FicheSecurite fichesecurite){
		// Déclaration des variables
		List<String> erreurs = new ArrayList<String>();
		ArrayList<Palanquee> palanqueesFiche = fichesecurite.getPalanquees();
		
		/**
		 * Vérification des saisies
		 */
		
		// Vérif saisie de la date
		if(fichesecurite.getTimestamp() == null || fichesecurite.getTimestamp() <= 0){
			erreurs.add("Pas de date saisie (format JJ/MM/AAAA attendu)");
		}
	
		// Verif saisie Directeur de plongée
		if(fichesecurite.getDirecteurPlonge() == null || fichesecurite.getDirecteurPlonge().getIdWeb() <= 0){
			erreurs.add("Pas de directeur de plongée saisie");
		}
	
		// Verif palanquées
		for(Palanquee palanquee  : palanqueesFiche){
			erreurs.addAll(validationPalanquee(palanquee,true));
		}
		
		
		
		return erreurs;
	}
	
	/**
	 * Permet de vérifier s'il y a des erreurs vis à vis de la validation des fiches
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationEnregistrementFiche(FicheSecurite fichesecurite){
		
		// Déclaration des variables
		List<String> erreurs = new ArrayList<String>();
		ArrayList<Palanquee> palanqueesFiche = fichesecurite.getPalanquees();
		
		/**
		 * Vérification des saisies
		 */
		
		// Vérif saisie de la date
		if(fichesecurite.getTimestamp() == null || fichesecurite.getTimestamp() <= 0){
			erreurs.add("Pas de date saisie (format JJ/MM/AAAA attendu)");
		}
	
		// Verif saisie Directeur de plongée
		if(fichesecurite.getDirecteurPlonge() == null || fichesecurite.getDirecteurPlonge().getIdWeb() <= 0){
			erreurs.add("Pas de directeur de plongée saisie");
		}
	
		// Verif palanquées
		for(Palanquee palanquee  : palanqueesFiche){
			erreurs.addAll(validationPalanquee(palanquee,false));
		}
		
		
		
		return erreurs;
	}
	
	/**
	 * Permet de vérifier s'il y a des erreurs vis à vis de la validation des palanquee
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationPalanquee(Palanquee palanquee, boolean cloture){
		
		// Déclaration des variables
		List<String> erreurs = new ArrayList<String>();
		
		/**
		 * Vérification des saisies
		 */
		
		// Verif saisie Type Gaz
		if(palanquee.getTypeGaz() == null || palanquee.getTypeGaz() == EnumTypeGaz.NULL){
			erreurs.add("Pas de gaz saisi sur la palanquée "+palanquee.getNumero());
		}
		
		// Verif saisie Type de plongée
		if(palanquee.getTypePlonge() == null || palanquee.getTypePlonge() == EnumTypePlonge.NULL){
			erreurs.add("Pas de type de plongée saisie sur la palanquée "+palanquee.getNumero());
		}
		
		// Verif saisie Profondeur
		if(palanquee.getProfondeurPrevue() == null || palanquee.getProfondeurPrevue() <= 0){
			erreurs.add("Erreur de profondeur (null ou négative) saisie sur la palanquée "+palanquee.getNumero());
		}
		
		if(cloture){
			// Vérif durée réalisée
			for(Plongeur plongeurs : palanquee.getPlongeurs()){
				if(plongeurs.getDureeRealisee() == null || plongeurs.getDureeRealisee() <= 0){
					erreurs.add("Erreur de durée réalisée (null ou négative) saisie pour le plongeur "+plongeurs.getNom()+" "+plongeurs.getPrenom()+" ");
				}
				if(plongeurs.getProfondeurRealisee() == null || plongeurs.getProfondeurRealisee() <= 0){
					erreurs.add("Erreur de profondeur réalisée (null ou négative) saisie pour le plongeur "+plongeurs.getNom()+" "+plongeurs.getPrenom()+" ");
				}
			}
			// Vérif profondeur réalisée
			
		}
		
		// Verif saisie Heure de plongée
		/*if(palanquee.getHeure() == null || palanquee.getHeure().isEmpty()){
			erreurs.add("Pas d'heure de plongée saisie sur la palanquée "+palanquee.getNumero());
		}*/
		
		/**
		 * Vérification des règles de gestion
		 */
		
		// Type de plongé
		if(palanquee.getProfondeurPrevue() <= 6){
			if(palanquee.getTypePlonge() == EnumTypePlonge.AUTONOME){
				erreurs.add("Impossible de plonger en autonomie entre 0 et 6 mètres de profondeur pour la palanquée "+palanquee.getNumero());
			}
			else if(palanquee.getTypeGaz() == EnumTypeGaz.NITROX && palanquee.getTypePlonge() == EnumTypePlonge.ENCADRE){
				erreurs.add("Impossible de plonger en exploration encadré au nitrox entre 0 et 6 mètres de profondeur");
			}
		}
		else if(palanquee.getTypePlonge() == EnumTypePlonge.BAPTEME){
			erreurs.add("Impossible de faire un baptême au dela de 6 mètres");
		}	
		
		// Présence du moniteur
		if(palanquee.getTypePlonge() != EnumTypePlonge.AUTONOME){
			if(palanquee.getMoniteur() == null){
				erreurs.add("Il est nécéssaire d'avoir un moniteur pour ce type de plongée");
			}
		}
	
		// Nombre de plongeur
		// Plongée BAPTEME [1 plongeur MAX - 1 Bonus]
		if(palanquee.getTypePlonge() == EnumTypePlonge.BAPTEME){
			int maxPlongeurs = 1;
			int plongeurBonusPossible = 1;
			int plongeurBonus = 0;
			if(palanquee.getPlongeurs().size() > maxPlongeurs){
				// Si il y a plus de 1 plongeur, il faut vérifier qu'il n'y en a qu'un seul autre
				if(palanquee.getPlongeurs().size() <= (maxPlongeurs + plongeurBonusPossible)){
					// Nous savons qu'il y a donc deux plongeurs, si un des deux plongeurs peut être plongeur bonus, alors c'est bon
					for(Plongeur plongeur : palanquee.getPlongeurs()){
						if(peutEtrePlongeurBonus(plongeur,palanquee)){
							plongeurBonus++;
						}
					}
					if(plongeurBonus == 0){
						erreurs.add("Le plongeur supplémentaire de cette palanquée n'a pas les bonnes aptitudes pour cette plongée");
					}
				}
				else {
					erreurs.add("Il y a trop de plongeurs dans cette palanquée pour cette plongée");
				}
			}
			
		}
		// Plongée TECHNIQUE & ENCADRE [4 plongeurs MAX - 1 Bonus]
		else if(palanquee.getTypePlonge() == EnumTypePlonge.TECHNIQUE || palanquee.getTypePlonge() == EnumTypePlonge.ENCADRE){
			int maxPlongeurs = 4;
			int plongeurBonusPossible = 1;
			int plongeurBonus = 0;
			if(palanquee.getPlongeurs().size() > maxPlongeurs){
				// Si il y a plus de 4 plongeur, il faut vérifier qu'il n'y en a qu'un seul autre
				if(palanquee.getPlongeurs().size() <= (maxPlongeurs + plongeurBonusPossible)){
					// Nous savons qu'il y a donc 5 plongeurs, si un des 5 plongeurs peut être bonus, alors c'est bon
					for(Plongeur plongeur : palanquee.getPlongeurs()){
						if(peutEtrePlongeurBonus(plongeur,palanquee)){
							plongeurBonus++;
						}
					}
					if(plongeurBonus == 0){
						erreurs.add("Le plongeur supplémentaire de cette palanquée n'a pas les bonnes aptitudes pour cette plongée");
					}
				}
				else {
					erreurs.add("Il y a trop de plongeurs dans cette palanquée pour cette plongée");
				}
			}
		}
		else{
			// Plongée autonome [3 plongeurs minimum]
			int minPlongeurs = 3;
			if(palanquee.getPlongeurs().size() < minPlongeurs){
				erreurs.add("Il n'y a pas assez de plongeurs dans cette palanquée pour une plongée autonome");
			}
			
		}
		// Vérification de la profondeur
		for(Plongeur plongeur : palanquee.getPlongeurs()){
			if(!validationProfondeur(plongeur,palanquee)){
				erreurs.add("Un plongeur ne peut pas plonger à cette profondeur");
			}
		}
		
	
		// Vérification aptitudes moniteur
		if(palanquee.getMoniteur() != null){
			if(palanquee.getMoniteur().getAptitudes() != null){
				// Plongée au Nitrox : Besoin de vérifier les aptitudes d'enseignement au Nitrox
				boolean checkEnseignementNitrox = palanquee.getTypeGaz() != EnumTypeGaz.NITROX || palanquee.getTypePlonge() != EnumTypePlonge.TECHNIQUE;
				// Plongée au Air : Besoin de vérifier les aptitudes d'enseignement au Air 
				boolean checkEnseignementAir = palanquee.getTypeGaz() != EnumTypeGaz.AIR || palanquee.getTypePlonge() != EnumTypePlonge.TECHNIQUE;
				// Si la plongée est encadré, alors il faut la vérifier ses aptitudes d'encadrement
				boolean checkEncadrement = palanquee.getTypePlonge() != EnumTypePlonge.ENCADRE;
				// Plongée à l'air : Pas besoin de vérifier le type de gaz Nitrox
				boolean checkGaz = palanquee.getTypeGaz() != EnumTypeGaz.NITROX;
				// Profondeur d'enseigmenent = profondeur de plongée
				double profondeurMoniteur = Math.ceil(palanquee.getProfondeurPrevue());
				
				for(Aptitude aptitude : palanquee.getMoniteur().getAptitudes()){
					// Si plongée au Nitrox, on vérifie qu'il a la bonne aptitude
					if(!checkEnseignementNitrox && aptitude.getEnseignementNitroxMax() >= profondeurMoniteur){
						checkEnseignementNitrox = true;
					}
					// Si plongée à l'air, on vérifie qu'il a la bonne aptitude
					if(!checkEnseignementAir && aptitude.getEnseignementAirMax() >= profondeurMoniteur){
						checkEnseignementAir = true;
					}
					// Si plongée encadré, on vérifie qu'il peut encadrer à cette profondeur
					if(!checkEncadrement && aptitude.getEncadrementMax() >= profondeurMoniteur){
						checkEncadrement = true;
					}
					// Si plongée au Nitrox, on vérifie qu'il peut plongeur au nitrox à cette profondeur
					if(!checkGaz && aptitude.getNitroxMax() >= profondeurMoniteur){
						checkGaz = true;
					}
				}
				
				if(!checkEncadrement || !checkEnseignementAir || !checkEnseignementNitrox || !checkGaz){
					
					
					if(!checkEnseignementNitrox)
						erreurs.add("Le moniteur ne peut pas enseigner à une palanquée utilisant du nitrox à cette profondeur");
					if(!checkEnseignementAir)
						erreurs.add("Le moniteur ne peut pas enseigner à une palanquée à cette profondeur");
					if(!checkEncadrement){
						if(!checkGaz)
							erreurs.add("Le moniteur ne peut pas encadrer une palanquée utilisant du nitrox à cette profondeur");
						else
							erreurs.add("Le moniteur ne peut pas encadrer une palanquée à cette profondeur");
					}
					else {
						erreurs.add("Le moniteur ne peut pas encadrer une palanquée utilisant du nitrox");
					}
				}
			}
			else {
				erreurs.add("Le moniteur n'a pas les aptitudes nécéssaires pour cette palanquée");
			}
		}
	
		// Vérification Nitrox plongeurs
		if(palanquee.getTypeGaz() == EnumTypeGaz.NITROX){
			for(Plongeur plongeur : palanquee.getPlongeurs()){
				if(!validationNitrox(plongeur,palanquee)){
					erreurs.add("Le plongeur "+plongeur.getNom()+" "+plongeur.getPrenom()+" n'a pas les aptitudes nitrox nécéssaires pour cette plongée");
				}
			}
		}
		return erreurs;
	}
	
	
	private static boolean validationNitrox(Plongeur plongeur, Palanquee palanquee){

		if(plongeur.getAptitudes() != null){
			for(Aptitude aptitude : plongeur.getAptitudes()){
				if(aptitude != null){
					if(aptitude.getNitroxMax() >= 20){
						// On considère qu'il s'agit d'un PN-C
						return true;
					}
				}
			}
			return false;
		}
		else {
			return false;
		}
	}
	
	private static boolean validationProfondeur(Plongeur plongeur, Palanquee palanquee){
		// Profondeur de plongée
		double profondeurPrevue = Math.ceil(palanquee.getProfondeurPrevue());
		
		if(profondeurPrevue <= 6)
			return true;
		if(plongeur.getAptitudes() == null && profondeurPrevue > 6)
			return false;
		else{
			for(Aptitude aptitude : plongeur.getAptitudes()){
				if(aptitude != null){
					if(palanquee.getTypePlonge() == EnumTypePlonge.AUTONOME){
						if(aptitude.getAutonomeMax() >= profondeurPrevue)
							return true;
					}
					else if(palanquee.getTypePlonge() == EnumTypePlonge.ENCADRE){
						if(aptitude.getEncadreeMax() >= profondeurPrevue)
							return true;
					}
					else if(palanquee.getTypePlonge() == EnumTypePlonge.TECHNIQUE){
						if(aptitude.getTechniqueMax() >= profondeurPrevue)
							return true;
					}
					else if(palanquee.getTypePlonge() == EnumTypePlonge.BAPTEME){
						return true;
					}
				}
			}
			return false;
		}
	}
	
		
	private static boolean peutEtrePlongeurBonus(Plongeur plongeur, Palanquee palanquee){
		
		// Vérifie que la plongée est bien à moins de 40m
		if(palanquee.getProfondeurPrevue() > 40){
			return false;
		}
		else {
			// Vérifier qu'il est GP ou P-4
			if(plongeur.getAptitudes() == null){
				return false;
			}
			else {
				for(Aptitude aptitude : plongeur.getAptitudes()){
					if(aptitude.getLibelleCourt() == "GP" || aptitude.getLibelleCourt() == "P-4")
						return true;

				}
				return false;
			}
		}
	}
}
