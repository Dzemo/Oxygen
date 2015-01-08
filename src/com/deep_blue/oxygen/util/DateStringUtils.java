package com.deep_blue.oxygen.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe offrant des méthodes static pour transformer des dates ou timestamp en String et vice versa
 * @author Raphael
 *
 */
public class DateStringUtils {
	
	/**
	 * Transforme un timestamp en date au format MM/dd/yyyy
	 * Renvoi une chaine vide si timestamp est null
	 * @param timestamp
	 * @return
	 */
	public static String timestampsToDate(Long timestamp){
		if(timestamp != null)
			return new SimpleDateFormat("dd MMM yyyy", new Locale("fr_FR")).format(new Date(timestamp*1000));
		else
			return "";
	}
	
	/**
	 * Récupère l'heure d'un timestamp au format hh:mm
	 * @param timestamp
	 * @return
	 */
	public static String timestampsToHeure(Long timestamp){
        return new SimpleDateFormat("hh:mm", new Locale("fr_FR")).format(new Date(timestamp*1000));
	}
	
	/**
	 * Retoure la date au format MM/dd/yyyy hh:mm
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date){
		return new SimpleDateFormat("dd MMM yyyy à HH:mm", new Locale("fr_FR")).format(date);
	}
	
	/**
	 * Transforme des seconds en hh'mm''ss Renvoi une chaine vide pour 0
	 * secondes
	 * 
	 * @param seconds
	 * @return
	 */
	public static String secondsToNiceString(Integer seconds) {
		String result = "";

		if (seconds > 0) {

			if (seconds > 3600) {
				result += (seconds / 3600) + "h";
				seconds /= 3600;
			}
			if (seconds > 60)
				result += (seconds / 60) + "m";

			result += (seconds % 60) + "s";
		}
		return result;
	}

	/**
	 * Renvoi le timestamps courant avec une précision à la seconde, comme en PHP
	 * @return
	 */
	public static Long getCurrentTimestamps(){
		return (new Date()).getTime() / 1000;
	}
}
