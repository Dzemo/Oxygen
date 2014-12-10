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
	 * @param timestamp
	 * @return
	 */
	public static String timestampsToDate(Long timestamp){
		return new SimpleDateFormat("dd MMM yyyy", new Locale("fr_FR")).format(new Date(timestamp*1000));
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
		return new SimpleDateFormat("dd MMM yyyy hh:mm", new Locale("fr_FR")).format(date);
	}
	
	/**
	 * Transforme des seconds en hh'mm''ss
	 * @param seconds
	 * @return
	 */
	public static String secondsToNiceString(Integer seconds){
		String result = "";
		
		if(seconds > 3600){
			result += (seconds / 3600) + "h";
			seconds /= 3600;
		}
		if(seconds > 60)
			result += (seconds / 60) + "m";
		
		result += (seconds % 60) + "s";
		
		return result;
	}

}
