package com.deep_blue.oxygen.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe offrant des m�thodes static pour transformer des dates ou timestamp en String et vice versa
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
		return new SimpleDateFormat("MM/dd/yyyy", new Locale("fr_FR")).format(new Date(timestamp*1000));
	}
	
	/**
	 * R�cup�re l'heure d'un timestamp au format hh:mm
	 * @param timestamp
	 * @return
	 */
	public static String timestampsToHeure(Long timestamp){
        return new SimpleDateFormat("hh:mm", new Locale("fr_FR")).format(new Date(timestamp*1000));
	}

}
