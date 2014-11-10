package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant une embarcation, utiliser pour faire des sortis de plongé
 * @author Raphael *
 */
public class Embarcation implements Parcelable {
	
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
	 * Id de l'embarcation.
	 */
	private Integer id;
	
	/**
	 * Libelle court de l'embarcation=
	 */
	private String libelle;
	
	/**
	 * Commentaire sur l'embarcation, peut être plus long
	 */
	private String comentaire;
	
	/**
	 * Indique si l'embarcation est disponible ou si elle est supprimé/desactivé/vendu/perdu/cassé/en reparation
	 */
	private boolean disponible;
	
	/**
	 * Version de cette embarcation, utilisé pour la synchronisation entre l'interface web
	 * et l'application de plongé.
	 */
	private Integer version;

	/**
	 * 
	 * @param id
	 * @param libelle
	 * @param comentaire
	 * @param disponible
	 * @param version
	 */
	public Embarcation(int id, String libelle, String comentaire,
			boolean disponible, Integer version) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.comentaire = comentaire;
		this.disponible = disponible;
		this.version = version;
	}
	
	/**
	 * 
	 * @param source
	 */
	public Embarcation(Parcel source){
		id = source.readInt();
		libelle = source.readString();
		comentaire = source.readString();
		disponible = source.readInt() > 0;
		version = source.readInt();
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getComentaire() {
		return comentaire;
	}

	public void setComentaire(String comentaire) {
		this.comentaire = comentaire;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Embarcation [id=" + id + ", libelle=" + libelle
				+ ", comentaire=" + comentaire + ", disponible=" + disponible
				+ ", version=" + version + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(libelle);
		dest.writeString(comentaire);
		dest.writeInt(disponible ? 1 : 0);
		dest.writeInt(version);
	}

}
