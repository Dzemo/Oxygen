package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Moniteur implements Parcelable{

	public static final Parcelable.Creator<Moniteur> CREATOR = new Parcelable.Creator<Moniteur>() {
		@Override
		public Moniteur createFromParcel(Parcel source) {
			return new Moniteur(source);
		}

		@Override
		public Moniteur[] newArray(int size) {
			return new Moniteur[size];
		}
	};
	
	private Integer id;
	private String nom;
	private String prenom;
	private ListeAptitudes aptitudes;
	private boolean actif;
	private boolean directeurPlongee;
	private String email;
	private String telephone;
	private Integer version;
	
	/**
	 * 
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param aptitudes
	 * @param actif
	 * @param directeurPlongee
	 * @param email
	 * @param telephone
	 * @param version
	 */
	public Moniteur(Integer id, String nom, String prenom,
			ListeAptitudes aptitudes, boolean actif, boolean directeurPlongee,
			String email, String telephone, Integer version) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.aptitudes = aptitudes;
		this.actif = actif;
		this.directeurPlongee = directeurPlongee;
		this.email = email;
		this.telephone = telephone;
		this.version = version;
	}
	
	public Moniteur(Parcel source){
		id = source.readInt();
		nom = source.readString();
		prenom = source.readString();
		aptitudes = source.readParcelable(ListeAptitudes.class.getClassLoader());
		actif = source.readInt() > 0;
		directeurPlongee = source.readInt() > 0;
		telephone = source.readString();
		email = source.readString();
		version = source.readInt();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public ListeAptitudes getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(ListeAptitudes aptitudes) {
		this.aptitudes = aptitudes;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public boolean isDirecteurPlongee() {
		return directeurPlongee;
	}

	public void setDirecteurPlongee(boolean directeurPlongee) {
		this.directeurPlongee = directeurPlongee;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public static Parcelable.Creator<Moniteur> getCreator() {
		return CREATOR;
	}

	@Override
	public String toString() {
		return "Moniteur [id=" + id + ", nom=" + nom + ", prenom=" + prenom
				+ ", aptitudes=" + aptitudes + ", actif=" + actif
				+ ", directeurPlongee=" + directeurPlongee + ", email=" + email
				+ ", telephone=" + telephone + ", version=" + version + "]";
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(nom);
		dest.writeString(prenom);
		dest.writeParcelable(aptitudes, flags);
		dest.writeInt(actif ? 1 : 0);
		dest.writeInt(directeurPlongee ? 1 : 0);
		dest.writeString(email);
		dest.writeString(telephone);
		dest.writeInt(version);		
	}
	
	
}
