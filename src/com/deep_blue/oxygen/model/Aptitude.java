package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe Aptitude représentent les aptitudes que peut avoir un directeur de plongé, un moniteur
 * ou un plongeur 
 * 
 * @author Raphael
 */
public class Aptitude implements Parcelable {
	
	public static final String SEPARATOR = ";";
	
	public static final Parcelable.Creator<Aptitude> CREATOR = new Parcelable.Creator<Aptitude>() {
		@Override
		public Aptitude createFromParcel(Parcel source) {
			return new Aptitude(source);
		}

		@Override
		public Aptitude[] newArray(int size) {
			return new Aptitude[size];
		}
	};
	
	/**
	 * Id de l'aptitude local et idWeb web, vu qu'on ne créer pas d'aptitude ici
	 */
	private int idWeb;
	
	/**
	 * Libelle raccourci de l'aptitude
	 * Exemple: PA-12
	 */
	private String libelleCourt;
	

	/**
	 * Libelle complet de l'aptitudes
	 * Exemple: Plongé autonome 12m
	 */
	private String libelleLong;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur/moniteur ayant cette aptitude peut plongé 
	 * dans le cadre d'une plongé technique (sous réserve qu'il dispose aussi des aptitudes
	 * supplémentaire éventuellement nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int techniqueMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur/moniteur ayant cette aptitude peut plongé 
	 * dans le cadre d'une plongé encadrée (sous réserve qu'il dispose aussi des aptitudes
	 * supplémentaire éventuellement nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int encardeeMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur/moniteur ayant cette aptitude peut plongé 
	 * dans le cadre d'une plongé autonome (sous réserve qu'il dispose aussi des aptitudes
	 * supplémentaire éventuellement nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int autonomeMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur/moniteur ayant cette aptitude peut plongé 
	 * dans le cadre d'une plongé au nitrox
	 */
	private int nitroxMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur ayant cette aptitude peut s'ajouter à une
	 * palanqué au dessus de la limite de plongeur (sous réserve qu'il dispose aussi des aptitudes
	 * supplémentaire éventuellement nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int ajoutMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur ayant cette aptitude peut enseigner
	 * à une palanqué utilisant de l'air
	 */
	private int enseignementAirMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur ayant cette aptitude peut enseigner 
	 * à une palanqué utilisant du nitrox (sous réserve qu'il dispose aussi des aptitudes
	 * supplémentaire éventuellement nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int enseignementNitroxMax;
	
	/**
	 * Profondeur en metre maximale à laquel le plongeur ayant cette aptitude peut encarder
	 * une palanqué (sous réserve qu'il dispose aussi des aptitudes supplémentaire éventuellement 
	 * nécessaire dans le cadre du plongé au nitrox par exemple)
	 */
	private int encadrementMax;
	
	/**
	 * Version de l'aptitude, utilisé pour la synchronisation. Timestamp de derniere modification
	 */
	private Long version;	
	
	public Aptitude() {
		super();
		this.idWeb = 0;
		this.libelleCourt = null;
		this.libelleLong = null;
		this.techniqueMax = 0;
		this.encardeeMax = 0;
		this.autonomeMax = 0;
		this.nitroxMax = 0;
		this.ajoutMax = 0;
		this.enseignementAirMax = 0;
		this.enseignementNitroxMax = 0;
		this.encadrementMax = 0;
		this.version = null;
	}
	
	/**
	 * 
	 * @param idWeb
	 * @param libelleCourt
	 * @param libelleLong
	 * @param techniqueMax
	 * @param encardeeMax
	 * @param autonomeMax
	 * @param nitroxMax
	 * @param ajoutMax
	 * @param enseignementAirMax
	 * @param enseignementNitroxMax
	 * @param encadrementMax
	 * @param version
	 */
	public Aptitude(int idWeb, String libelleCourt, String libelleLong,
			int techniqueMax, int encardeeMax, int autonomeMax, int nitroxMax,
			int ajoutMax, int enseignementAirMax, int enseignementNitroxMax,
			int encadrementMax, Long version) {
		super();
		this.idWeb = idWeb;
		this.libelleCourt = libelleCourt;
		this.libelleLong = libelleLong;
		this.techniqueMax = techniqueMax;
		this.encardeeMax = encardeeMax;
		this.autonomeMax = autonomeMax;
		this.nitroxMax = nitroxMax;
		this.ajoutMax = ajoutMax;
		this.enseignementAirMax = enseignementAirMax;
		this.enseignementNitroxMax = enseignementNitroxMax;
		this.encadrementMax = encadrementMax;
		this.version = version;
	}
	
	
	public Aptitude(Parcel source) {
		idWeb = source.readInt();
		libelleCourt = source.readString();
		libelleLong = source.readString();
		
		techniqueMax = source.readInt();
		encardeeMax = source.readInt();
		autonomeMax = source.readInt();
		
		nitroxMax = source.readInt();
		ajoutMax = source.readInt();
		
		enseignementAirMax = source.readInt();
		enseignementNitroxMax = source.readInt();
		encadrementMax = source.readInt();
		
		version  = source.readLong();
	}


	public int getIdWeb() {
		return idWeb;
	}
	public void setIdWeb(int id) {
		this.idWeb = id;
	}
	public String getLibelleCourt() {
		return libelleCourt;
	}
	public void setLibelleCourt(String libelleCourt) {
		this.libelleCourt = libelleCourt;
	}
	public String getLibelleLong() {
		return libelleLong;
	}
	public void setLibelleLong(String libelleLong) {
		this.libelleLong = libelleLong;
	}
	public int getTechniqueMax() {
		return techniqueMax;
	}
	public void setTechniqueMax(int techniqueMax) {
		this.techniqueMax = techniqueMax;
	}
	public int getEncardeeMax() {
		return encardeeMax;
	}
	public void setEncardeeMax(int encardeeMax) {
		this.encardeeMax = encardeeMax;
	}
	public int getAutonomeMax() {
		return autonomeMax;
	}
	public void setAutonomeMax(int autonomeMax) {
		this.autonomeMax = autonomeMax;
	}
	public int getNitroxMax() {
		return nitroxMax;
	}
	public void setNitroxMax(int nitroxMax) {
		this.nitroxMax = nitroxMax;
	}
	public int getAjoutMax() {
		return ajoutMax;
	}
	public void setAjoutMax(int ajoutMax) {
		this.ajoutMax = ajoutMax;
	}
	public int getEnseignementAirMax() {
		return enseignementAirMax;
	}
	public void setEnseignementAirMax(int enseignementAirMax) {
		this.enseignementAirMax = enseignementAirMax;
	}
	public int getEnseignementNitroxMax() {
		return enseignementNitroxMax;
	}
	public void setEnseignementNitroxMax(int enseignementNitroxMax) {
		this.enseignementNitroxMax = enseignementNitroxMax;
	}
	public int getEncadrementMax() {
		return encadrementMax;
	}
	public void setEncadrementMax(int encadrementMax) {
		this.encadrementMax = encadrementMax;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Aptitude [idWeb=" + idWeb + ", libelleCourt=" + libelleCourt
				+ ", libelleLong=" + libelleLong + ", techniqueMax="
				+ techniqueMax + ", encardeeMax=" + encardeeMax
				+ ", autonomeMax=" + autonomeMax + ", nitroxMax=" + nitroxMax
				+ ", ajoutMax=" + ajoutMax + ", enseignementAirMax="
				+ enseignementAirMax + ", enseignementNitroxMax="
				+ enseignementNitroxMax + ", encadrementMax=" + encadrementMax
				+ ", version=" + version + "]";
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idWeb);
		dest.writeString(libelleCourt != null ? libelleCourt : "");
		dest.writeString(libelleLong != null ? libelleLong : "");
		
		dest.writeInt(techniqueMax);
		dest.writeInt(encardeeMax);
		dest.writeInt(autonomeMax);
		
		dest.writeInt(nitroxMax);
		dest.writeInt(ajoutMax);
		
		dest.writeInt(enseignementAirMax);
		dest.writeInt(enseignementNitroxMax);
		dest.writeInt(encadrementMax);
		
		dest.writeLong(version);
	}
	
	
}
