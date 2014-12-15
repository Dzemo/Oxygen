package com.deep_blue.oxygen.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant un utilisateur de l'application
 * 
 * @author Raphael 
 */
public class Utilisateur implements Parcelable {

	public static final Parcelable.Creator<Utilisateur> CREATOR = new Parcelable.Creator<Utilisateur>() {
		@Override
		public Utilisateur createFromParcel(Parcel source) {
			return new Utilisateur(source);
		}

		@Override
		public Utilisateur[] newArray(int size) {
			return new Utilisateur[size];
		}
	};

	private String login;
	private String nom;
	private String prenom;
	private String motDePasse;
	private Boolean administrateur;
	private String email;
	private Boolean actif;
	private Moniteur moniteurAssocie;
	private Long version;

	/**
	 * @param login
	 * @param nom
	 * @param prenom
	 * @param motDePasse
	 * @param administrateur
	 * @param email
	 * @param actif
	 * @param moniteurAssocie
	 * @param version
	 */
	public Utilisateur(String login, String nom, String prenom,
			String motDePasse, Boolean administrateur, String email,
			Boolean actif, Moniteur moniteurAssocie, Long version) {
		super();
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
		this.email = email;
		this.actif = actif;
		this.moniteurAssocie = moniteurAssocie;
		this.version = version;
	}
	
	public Utilisateur(Parcel in) {
		login = in.readString();
		nom = in.readString();
		prenom = in.readString();
		motDePasse = in.readString();
		administrateur = in.readInt() > 0;
		email = in.readString();
		actif = in.readInt() > 0;
		boolean isPresent = in.readByte() == 1;
		if(isPresent)
			moniteurAssocie = in.readParcelable(Moniteur.class.getClassLoader());
		else
			moniteurAssocie = null;
		version = in.readLong();
	}

	// GETTER and SETTER

	/**
	 * 
	 * @return
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * 
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * 
	 * @return
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * 
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * 
	 * @return
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * 
	 * @param motDePasse
	 */
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getAdministrateur() {
		return administrateur;
	}

	/**
	 * 
	 * @param administrateur
	 */
	public void setAdministrateur(Boolean administrateur) {
		this.administrateur = administrateur;
	}

	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getActif() {
		return actif;
	}

	/**
	 * 
	 * @param actif
	 */
	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	/**
	 * @return the moniteurAssocie
	 */
	public Moniteur getMoniteurAssocie() {
		return moniteurAssocie;
	}

	/**
	 * @param moniteurAssocie the moniteurAssocie to set
	 */
	public void setMoniteurAssocie(Moniteur moniteurAssocie) {
		this.moniteurAssocie = moniteurAssocie;
	}

	/**
	 * 
	 * @return
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * 
	 * @param version
	 */
	public void setVersion(Long version) {
		this.version = version;
	}	
	
	/**
	 * Met à jours la version
	 */
	public void updateVersion(){
		this.version = (new Date()).getTime() / 1000;
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tlogin: " + login + "\n\tnom: "
				+ nom + "\n\tprenom: " + prenom + "\n\tmotDePasse: "
				+ motDePasse + "\n\tadministrateur: " + administrateur
				+ "\n\temail: " + email + "\n\tactif: " + actif
				+ "\n\tmoniteurAssocie: " + moniteurAssocie + "\n\tversion: "
				+ version + "\n}";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(login != null ? login : "");
		dest.writeString(nom != null ? nom : "");
		dest.writeString(prenom != null ? prenom : "");
		dest.writeString(motDePasse != null ? motDePasse : "");
		dest.writeInt(administrateur ? 1 : 0);
		dest.writeString(email != null ? email : "");
		dest.writeInt(actif ? 1 : 0);
		dest.writeByte((byte)(moniteurAssocie != null ? 1 : 0));
		if(moniteurAssocie != null)
			dest.writeParcelable(moniteurAssocie, flags);
		dest.writeLong(version);
	}
}
