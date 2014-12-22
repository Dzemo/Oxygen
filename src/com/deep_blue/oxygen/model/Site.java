package com.deep_blue.oxygen.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Site implements Parcelable {

	public static final Parcelable.Creator<Site> CREATOR = new Parcelable.Creator<Site>() {
		@Override
		public Site createFromParcel(Parcel source) {
			return new Site(source);
		}

		@Override
		public Site[] newArray(int size) {
			return new Site[size];
		}
	};

	private Long id;
	private Integer idWeb;
	private String nom;
	private String commentaire;
	private Boolean desactive;
	private Long version;

	public Site() {
		super();
		this.id = null;
		this.idWeb = null;
		this.nom = null;
		this.commentaire = null;
		this.desactive = null;
		this.version = null;
	}
	
	/**
	 * Créer un nouveau site avec tout ses attributs, issue de la base de données ou de la synchronisation par exemple
	 * @param id
	 * @param idWeb
	 * @param nom
	 * @param commentaire
	 * @param desactive
	 * @param version
	 */
	public Site(Long id, Integer idWeb, String nom, String commentaire, Boolean desactive, Long version) {
		super();
		this.id = id;
		this.idWeb = idWeb;
		this.nom = nom;
		this.commentaire = commentaire;
		this.desactive = desactive;
		this.version = version;
	}

	/**
	 * Créer un nouveau site en initialisation la version au timestamps courant, lors de la création par l'utilisateur par exemple
	 * @param id
	 * @param idWeb
	 * @param nom
	 * @param commentaire
	 * @param desactive
	 */
	public Site(Long id, Integer idWeb, String nom, String commentaire, Boolean desactive) {
		super();
		this.id = id;
		this.idWeb = idWeb;
		this.nom = nom;
		this.commentaire = commentaire;
		this.desactive = desactive;
		Date now = new Date();
		this.version = now.getTime() / 1000;
	}
	
	public Site(Parcel source){
		super();
		this.id = source.readLong();
		this.idWeb = source.readInt();
		this.nom = source.readString();
		this.commentaire = source.readString();
		this.desactive = source.readInt() == 1;
		this.version = source.readLong();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the idWeb
	 */
	public Integer getIdWeb() {
		return idWeb;
	}

	/**
	 * @param idWeb the idWeb to set
	 */
	public void setIdWeb(Integer idWeb) {
		this.idWeb = idWeb;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}
	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @return the desactive
	 */
	public Boolean getDesactive() {
		return desactive;
	}

	/**
	 * @param desactive the desactive to set
	 */
	public void setDesactive(Boolean desactive) {
		this.desactive = desactive;
	}
	
	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tidWeb: " + idWeb + "\n\tnom: " + nom
				+ "\n\tcommentaire: " + commentaire + "\n\tdesactive: "
				+ desactive + "\n\tversion: " + version + "\n}";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeInt(idWeb);
		dest.writeString(nom);
		dest.writeString(commentaire);		
		dest.writeInt(desactive ? 1 : 0);
		dest.writeLong(version);
	}
	
	
}
