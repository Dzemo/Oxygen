package com.deep_blue.oxygen.model;

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
	private int version;

	/**
	 * 
	 * @param login
	 * @param nom
	 * @param prenom
	 * @param motDePasse
	 * @param administrateur
	 * @param email
	 * @param actif
	 * @param version
	 */
	public Utilisateur(String login, String nom, String prenom,
			String motDePasse, Boolean administrateur, String email,
			Boolean actif, int version) {
		super();
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motDePasse;
		this.administrateur = administrateur;
		this.email = email;
		this.actif = actif;
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
		version = in.readInt();
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
	 * 
	 * @return
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Utilisateur [login=" + login + ", nom=" + nom + ", prenom="
				+ prenom + ", motDePasse=" + motDePasse + ", administrateur="
				+ administrateur + ", email=" + email + ", actif=" + actif
				+ ", version=" + version + "]";
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
		dest.writeInt(version);
	}
}
