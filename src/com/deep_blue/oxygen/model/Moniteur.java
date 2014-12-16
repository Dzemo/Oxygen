package com.deep_blue.oxygen.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonAnySetter;

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
	
	private Integer idWeb;
	private String nom;
	private String prenom;
	private ListeAptitudes aptitudes;
	private Boolean actif;
	private Boolean directeurPlongee;
	private String email;
	private String telephone;
	private Long version;
	
	public Moniteur() {
		super();
		this.idWeb = null;
		this.nom = null;
		this.prenom = null;
		this.aptitudes = null;
		this.actif = null;
		this.directeurPlongee = null;
		this.email = null;
		this.telephone = null;
		this.version = null;
	}
	
	/**
	 * 
	 * @param idWeb
	 * @param nom
	 * @param prenom
	 * @param aptitudes
	 * @param actif
	 * @param directeurPlongee
	 * @param email
	 * @param telephone
	 * @param version
	 */
	public Moniteur(Integer idWeb, String nom, String prenom,
			ListeAptitudes aptitudes, boolean actif, boolean directeurPlongee,
			String email, String telephone, Long version) {
		super();
		this.idWeb = idWeb;
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
		idWeb = source.readInt();
		nom = source.readString();
		prenom = source.readString();
		aptitudes = source.readParcelable(ListeAptitudes.class.getClassLoader());
		actif = source.readInt() > 0;
		directeurPlongee = source.readInt() > 0;
		telephone = source.readString();
		email = source.readString();
		version = source.readLong();
	}

	public Integer getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(Integer id) {
		this.idWeb = id;
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

	public List<Aptitude> getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(ListeAptitudes aptitudes) {
		this.aptitudes = aptitudes;
	}

	public Boolean isActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Boolean isDirecteurPlongee() {
		return directeurPlongee;
	}

	public void setDirecteurPlongee(Boolean directeurPlongee) {
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public static Parcelable.Creator<Moniteur> getCreator() {
		return CREATOR;
	}

	@Override
	public String toString() {
		return "Moniteur [idWeb=" + idWeb + ", nom=" + nom + ", prenom=" + prenom
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
		dest.writeInt(idWeb);
		dest.writeString(nom != null ? nom : "");
		dest.writeString(prenom != null ? prenom : "");
		dest.writeParcelable(aptitudes != null ? aptitudes : new ListeAptitudes(), flags);
		dest.writeInt(actif ? 1 : 0);
		dest.writeInt(directeurPlongee ? 1 : 0);
		dest.writeString(email != null ? email : "");
		dest.writeString(telephone != null ? telephone : "");
		dest.writeLong(version);		
	}

    @JsonAnySetter
    public void set(String name, Object value) {
        aptitudes.add((Aptitude)value);
    }
}
