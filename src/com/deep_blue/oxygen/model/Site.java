package com.deep_blue.oxygen.model;

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

	private Integer id;
	private String nom;
	private String commentaire;
	private Boolean desactive;

	/**
	 * @param id
	 * @param nom
	 * @param commentaire
	 * @param desactive
	 */
	public Site(Integer id, String nom, String commentaire, Boolean desactive) {
		super();
		this.id = id;
		this.nom = nom;
		this.commentaire = commentaire;
		this.desactive = desactive;
	}

	
	public Site(Parcel source){
		super();
		this.id = source.readInt();
		this.nom = source.readString();
		this.commentaire = source.readString();
		this.desactive = source.readInt() == 1;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tnom: " + nom
				+ "\n\tcommentaire: " + commentaire + "\n\tdesactive: "
				+ desactive + "\n}";
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(nom);
		dest.writeString(commentaire);		
		dest.writeInt(desactive ? 1 : 0);
	}
	
	
}
