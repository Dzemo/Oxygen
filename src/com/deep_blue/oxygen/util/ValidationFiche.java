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
 * Classe offrant des m�thodes pour v�rifier les r�gles de gestion des fiches de s�curit�
 * @author Flavio
 *
 */
public class ValidationFiche {
	
	
	/**
	 * Permet de v�rifier s'il y a des erreurs vis � vis de la validation des fiches
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationClotureFiche(FicheSecurite fichesecurite){
		// D�claration des variables
		List<String> erreurs = new ArrayList<String>();
		ArrayList<Palanquee> palanqueesFiche = fichesecurite.getPalanquees();
		
		/**
		 * V�rification des saisies
		 */
		
		// V�rif saisie de la date
		if(fichesecurite.getTimestamp() == null || fichesecurite.getTimestamp() <= 0){
			erreurs.add("Pas de date saisie (format JJ/MM/AAAA attendu)");
		}
	
		// Verif saisie Directeur de plong�e
		if(fichesecurite.getDirecteurPlonge() == null || fichesecurite.getDirecteurPlonge().getIdWeb() <= 0){
			erreurs.add("Pas de directeur de plong�e saisie");
		}
	
		// Verif palanqu�es
		for(Palanquee palanquee  : palanqueesFiche){
			erreurs.addAll(validationPalanquee(palanquee,true));
		}
		
		
		
		return erreurs;
	}
	
	/**
	 * Permet de v�rifier s'il y a des erreurs vis � vis de la validation des fiches
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationEnregistrementFiche(FicheSecurite fichesecurite){
		
		// D�claration des variables
		List<String> erreurs = new ArrayList<String>();
		ArrayList<Palanquee> palanqueesFiche = fichesecurite.getPalanquees();
		
		/**
		 * V�rification des saisies
		 */
		
		// V�rif saisie de la date
		if(fichesecurite.getTimestamp() == null || fichesecurite.getTimestamp() <= 0){
			erreurs.add("Pas de date saisie (format JJ/MM/AAAA attendu)");
		}
	
		// Verif saisie Directeur de plong�e
		if(fichesecurite.getDirecteurPlonge() == null || fichesecurite.getDirecteurPlonge().getIdWeb() <= 0){
			erreurs.add("Pas de directeur de plong�e saisie");
		}
	
		// Verif palanqu�es
		for(Palanquee palanquee  : palanqueesFiche){
			erreurs.addAll(validationPalanquee(palanquee,false));
		}
		
		
		
		return erreurs;
	}
	
	/**
	 * Permet de v�rifier s'il y a des erreurs vis � vis de la validation des palanquee
	 * @param palanquee
	 * @return List<String> d'erreurs
	 */
	public static List<String> validationPalanquee(Palanquee palanquee, boolean cloture){
		
		// D�claration des variables
		List<String> erreurs = new ArrayList<String>();
		
		/**
		 * V�rification des saisies
		 */
		
		// Verif saisie Type Gaz
		if(palanquee.getTypeGaz() == null || palanquee.getTypeGaz() == EnumTypeGaz.NULL){
			erreurs.add("Pas de gaz saisi sur la palanqu�e "+palanquee.getNumero());
		}
		
		// Verif saisie Type de plong�e
		if(palanquee.getTypePlonge() == null || palanquee.getTypePlonge() == EnumTypePlonge.NULL){
			erreurs.add("Pas de type de plong�e saisie sur la palanqu�e "+palanquee.getNumero());
		}
		
		// Verif saisie Profondeur
		if(palanquee.getProfondeurPrevue() == null || palanquee.getProfondeurPrevue() <= 0){
			erreurs.add("Erreur de profondeur (null ou n�gative) saisie sur la palanqu�e "+palanquee.getNumero());
		}
		
		if(cloture){
			// V�rif dur�e r�alis�e
			for(Plongeur plongeurs : palanquee.getPlongeurs()){
				if(plongeurs.getDureeRealisee() == null || plongeurs.getDureeRealisee() <= 0){
					erreurs.add("Erreur de dur�e r�alis�e (null ou n�gative) saisie pour le plongeur "+plongeurs.getNom()+" "+plongeurs.getPrenom()+" ");
				}
				if(plongeurs.getProfondeurRealisee() == null || plongeurs.getProfondeurRealisee() <= 0){
					erreurs.add("Erreur de profondeur r�alis�e (null ou n�gative) saisie pour le plongeur "+plongeurs.getNom()+" "+plongeurs.getPrenom()+" ");
				}
			}
			// V�rif profondeur r�alis�e
			
		}
		
		// Verif saisie Heure de plong�e
		/*if(palanquee.getHeure() == null || palanquee.getHeure().isEmpty()){
			erreurs.add("Pas d'heure de plong�e saisie sur la palanqu�e "+palanquee.getNumero());
		}*/
		
		/**
		 * V�rification des r�gles de gestion
		 */
		
		// Type de plong�
		if(palanquee.getProfondeurPrevue() <= 6){
			if(palanquee.getTypePlonge() == EnumTypePlonge.AUTONOME){
				erreurs.add("Impossible de plonger en autonomie entre 0 et 6 m�tres de profondeur pour la palanqu�e "+palanquee.getNumero());
			}
			else if(palanquee.getTypeGaz() == EnumTypeGaz.NITROX && palanquee.getTypePlonge() == EnumTypePlonge.ENCADRE){
				erreurs.add("Impossible de plonger en exploration encadr� au nitrox entre 0 et 6 m�tres de profondeur");
			}
		}
		else if(palanquee.getTypePlonge() == EnumTypePlonge.BAPTEME){
			erreurs.add("Impossible de faire un bapt�me au dela de 6 m�tres");
		}	
		
		// Pr�sence du moniteur
		if(palanquee.getTypePlonge() != EnumTypePlonge.AUTONOME){
			if(palanquee.getMoniteur() == null){
				erreurs.add("Il est n�c�ssaire d'avoir un moniteur pour ce type de plong�e");
			}
		}
	
		// Nombre de plongeur
		// Plong�e BAPTEME [1 plongeur MAX - 1 Bonus]
		if(palanquee.getTypePlonge() == EnumTypePlonge.BAPTEME){
			int maxPlongeurs = 1;
			int plongeurBonusPossible = 1;
			int plongeurBonus = 0;
			if(palanquee.getPlongeurs().size() > maxPlongeurs){
				// Si il y a plus de 1 plongeur, il faut v�rifier qu'il n'y en a qu'un seul autre
				if(palanquee.getPlongeurs().size() <= (maxPlongeurs + plongeurBonusPossible)){
					// Nous savons qu'il y a donc deux plongeurs, si un des deux plongeurs peut �tre plongeur bonus, alors c'est bon
					for(Plongeur plongeur : palanquee.getPlongeurs()){
						if(peutEtrePlongeurBonus(plongeur,palanquee)){
							plongeurBonus++;
						}
					}
					if(plongeurBonus == 0){
						erreurs.add("Le plongeur suppl�mentaire de cette palanqu�e n'a pas les bonnes aptitudes pour cette plong�e");
					}
				}
				else {
					erreurs.add("Il y a trop de plongeurs dans cette palanqu�e pour cette plong�e");
				}
			}
			
		}
		// Plong�e TECHNIQUE & ENCADRE [4 plongeurs MAX - 1 Bonus]
		else if(palanquee.getTypePlonge() == EnumTypePlonge.TECHNIQUE || palanquee.getTypePlonge() == EnumTypePlonge.ENCADRE){
			int maxPlongeurs = 4;
			int plongeurBonusPossible = 1;
			int plongeurBonus = 0;
			if(palanquee.getPlongeurs().size() > maxPlongeurs){
				// Si il y a plus de 4 plongeur, il faut v�rifier qu'il n'y en a qu'un seul autre
				if(palanquee.getPlongeurs().size() <= (maxPlongeurs + plongeurBonusPossible)){
					// Nous savons qu'il y a donc 5 plongeurs, si un des 5 plongeurs peut �tre bonus, alors c'est bon
					for(Plongeur plongeur : palanquee.getPlongeurs()){
						if(peutEtrePlongeurBonus(plongeur,palanquee)){
							plongeurBonus++;
						}
					}
					if(plongeurBonus == 0){
						erreurs.add("Le plongeur suppl�mentaire de cette palanqu�e n'a pas les bonnes aptitudes pour cette plong�e");
					}
				}
				else {
					erreurs.add("Il y a trop de plongeurs dans cette palanqu�e pour cette plong�e");
				}
			}
		}
		else{
			// Plong�e autonome [3 plongeurs minimum]
			int minPlongeurs = 3;
			if(palanquee.getPlongeurs().size() < minPlongeurs){
				erreurs.add("Il n'y a pas assez de plongeurs dans cette palanqu�e pour une plong�e autonome");
			}
			
		}
		// V�rification de la profondeur
		for(Plongeur plongeur : palanquee.getPlongeurs()){
			if(!validationProfondeur(plongeur,palanquee)){
				erreurs.add("Un plongeur ne peut pas plonger � cette profondeur");
			}
		}
		
	
		// V�rification aptitudes moniteur
		if(palanquee.getMoniteur() != null){
			if(palanquee.getMoniteur().getAptitudes() != null){
				// Plong�e au Nitrox : Besoin de v�rifier les aptitudes d'enseignement au Nitrox
				boolean checkEnseignementNitrox = palanquee.getTypeGaz() != EnumTypeGaz.NITROX || palanquee.getTypePlonge() != EnumTypePlonge.TECHNIQUE;
				// Plong�e au Air : Besoin de v�rifier les aptitudes d'enseignement au Air 
				boolean checkEnseignementAir = palanquee.getTypeGaz() != EnumTypeGaz.AIR || palanquee.getTypePlonge() != EnumTypePlonge.TECHNIQUE;
				// Si la plong�e est encadr�, alors il faut la v�rifier ses aptitudes d'encadrement
				boolean checkEncadrement = palanquee.getTypePlonge() != EnumTypePlonge.ENCADRE;
				// Plong�e � l'air : Pas besoin de v�rifier le type de gaz Nitrox
				boolean checkGaz = palanquee.getTypeGaz() != EnumTypeGaz.NITROX;
				// Profondeur d'enseigmenent = profondeur de plong�e
				double profondeurMoniteur = Math.ceil(palanquee.getProfondeurPrevue());
				
				for(Aptitude aptitude : palanquee.getMoniteur().getAptitudes()){
					// Si plong�e au Nitrox, on v�rifie qu'il a la bonne aptitude
					if(!checkEnseignementNitrox && aptitude.getEnseignementNitroxMax() >= profondeurMoniteur){
						checkEnseignementNitrox = true;
					}
					// Si plong�e � l'air, on v�rifie qu'il a la bonne aptitude
					if(!checkEnseignementAir && aptitude.getEnseignementAirMax() >= profondeurMoniteur){
						checkEnseignementAir = true;
					}
					// Si plong�e encadr�, on v�rifie qu'il peut encadrer � cette profondeur
					if(!checkEncadrement && aptitude.getEncadrementMax() >= profondeurMoniteur){
						checkEncadrement = true;
					}
					// Si plong�e au Nitrox, on v�rifie qu'il peut plongeur au nitrox � cette profondeur
					if(!checkGaz && aptitude.getNitroxMax() >= profondeurMoniteur){
						checkGaz = true;
					}
				}
				
				if(!checkEncadrement || !checkEnseignementAir || !checkEnseignementNitrox || !checkGaz){
					
					
					if(!checkEnseignementNitrox)
						erreurs.add("Le moniteur ne peut pas enseigner � une palanqu�e utilisant du nitrox � cette profondeur");
					if(!checkEnseignementAir)
						erreurs.add("Le moniteur ne peut pas enseigner � une palanqu�e � cette profondeur");
					if(!checkEncadrement){
						if(!checkGaz)
							erreurs.add("Le moniteur ne peut pas encadrer une palanqu�e utilisant du nitrox � cette profondeur");
						else
							erreurs.add("Le moniteur ne peut pas encadrer une palanqu�e � cette profondeur");
					}
					else {
						erreurs.add("Le moniteur ne peut pas encadrer une palanqu�e utilisant du nitrox");
					}
				}
			}
			else {
				erreurs.add("Le moniteur n'a pas les aptitudes n�c�ssaires pour cette palanqu�e");
			}
		}
	
		// V�rification Nitrox plongeurs
		if(palanquee.getTypeGaz() == EnumTypeGaz.NITROX){
			for(Plongeur plongeur : palanquee.getPlongeurs()){
				if(!validationNitrox(plongeur,palanquee)){
					erreurs.add("Le plongeur "+plongeur.getNom()+" "+plongeur.getPrenom()+" n'a pas les aptitudes nitrox n�c�ssaires pour cette plong�e");
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
						// On consid�re qu'il s'agit d'un PN-C
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
		// Profondeur de plong�e
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
		
		// V�rifie que la plong�e est bien � moins de 40m
		if(palanquee.getProfondeurPrevue() > 40){
			return false;
		}
		else {
			// V�rifier qu'il est GP ou P-4
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
