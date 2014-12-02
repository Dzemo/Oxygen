package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant une embarcation, utiliser pour faire des sortis de plongé
 * @author Raphael *
 */
public class Embarcation implements Parcelable {
	
	public static final Parcelable.Creator<Embarcation> CREATOR = new Parcelable.Creator<Embarcation>() {
		@Override
		public Embarcation createFromParcel(Parcel source) {
			return new Embarcation(source);
		}

		@Override
		public Embarcation[] newArray(int size) {
			return new Embarcation[size];
		}
	};
	
	/**
	 * Id de l'embarcation sur le serveur web distant
	 */
	private Integer idWeb;
	
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
	 * et l'application de plongé. Timestamp de dernière modification
	 */
	private Long version;

	/**
	 * 
	 * @param idWeb
	 * @param libelle
	 * @param comentaire
	 * @param disponible
	 * @param version
	 */
	public Embarcation(int idWeb, String libelle, String comentaire,
			boolean disponible, Long version) {
		super();
		this.idWeb = idWeb;
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
		idWeb = source.readInt();
		libelle = source.readString();
		comentaire = source.readString();
		disponible = source.readInt() > 0;
		version = source.readLong();
	}public Integer getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(Integer idWeb) {
		this.idWeb = idWeb;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Embarcation [idWeb=" + idWeb + ", libelle=" + libelle
				+ ", comentaire=" + comentaire + ", disponible=" + disponible
				+ ", version=" + version + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(idWeb);
		dest.writeString(libelle != null ? libelle : "");
		dest.writeString(comentaire != null ? comentaire : "");
		dest.writeInt(disponible ? 1 : 0);
		dest.writeLong(version);
	}

}
