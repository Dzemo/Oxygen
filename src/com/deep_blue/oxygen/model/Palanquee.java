package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Palanquee d'une fiche de securite
 * 
 * @author Raphael *
 */
public class Palanquee implements Parcelable {

	public static final Parcelable.Creator<Palanquee> CREATOR = new Parcelable.Creator<Palanquee>() {
		@Override
		public Palanquee createFromParcel(Parcel source) {
			return new Palanquee(source);
		}

		@Override
		public Palanquee[] newArray(int size) {
			return new Palanquee[size];
		}
	};


	private Integer id;
	private Integer idFicheSecurite;
	private Moniteur moniteur;
	private Integer numero;
	private EnumTypePlonge typePlonge;
	private EnumTypeGaz typeGaz;
	private Float profondeurPrevue;
	private Float profondeurRealisee;
	private Integer dureePrevue;
	private Integer dureeRealisee;
	private Integer version;
	private ListePlongeurs plongeurs;
	
	/**
	 * @param id
	 * @param idFicheSecurite
	 * @param moniteur
	 * @param numero
	 * @param typePlonge
	 * @param typeGaz
	 * @param profondeurPrevue
	 * @param profondeurRealisee
	 * @param dureePrevue
	 * @param dureeRealisee
	 * @param version
	 * @param plongeurs
	 */
	public Palanquee(Integer id, Integer idFicheSecurite, Moniteur moniteur,
			Integer numero, EnumTypePlonge typePlonge, EnumTypeGaz typeGaz,
			Float profondeurPrevue, Float profondeurRealisee,
			Integer dureePrevue, Integer dureeRealisee, Integer version,
			ListePlongeurs plongeurs) {
		super();
		this.id = id;
		this.idFicheSecurite = idFicheSecurite;
		this.moniteur = moniteur;
		this.numero = numero;
		this.typePlonge = typePlonge;
		this.typeGaz = typeGaz;
		this.profondeurPrevue = profondeurPrevue;
		this.profondeurRealisee = profondeurRealisee;
		this.dureePrevue = dureePrevue;
		this.dureeRealisee = dureeRealisee;
		this.version = version;
		this.plongeurs = plongeurs;
	}
	
	public Palanquee(Parcel source){
		id = source.readInt();
		idFicheSecurite = source.readInt();
		moniteur = source.readParcelable(Moniteur.class.getClassLoader());
		numero = source.readInt();
		typePlonge = EnumTypePlonge.valueOf(source.readString());
		typeGaz = EnumTypeGaz.valueOf(source.readString());
		profondeurPrevue = source.readFloat();
		profondeurRealisee = source.readFloat();
		dureePrevue = source.readInt();
		dureeRealisee = source.readInt();
		version = source.readInt();
		plongeurs = source.readParcelable(ListePlongeurs.class.getClassLoader());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdFicheSecurite() {
		return idFicheSecurite;
	}

	public void setIdFicheSecurite(Integer idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
	}

	public Moniteur getMoniteur() {
		return moniteur;
	}

	public void setMoniteur(Moniteur moniteur) {
		this.moniteur = moniteur;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public EnumTypePlonge getTypePlonge() {
		return typePlonge;
	}

	public void setTypePlonge(EnumTypePlonge typePlonge) {
		this.typePlonge = typePlonge;
	}

	public EnumTypeGaz getTypeGaz() {
		return typeGaz;
	}

	public void setTypeGaz(EnumTypeGaz typeGaz) {
		this.typeGaz = typeGaz;
	}

	public Float getProfondeurPrevue() {
		return profondeurPrevue;
	}

	public void setProfondeurPrevue(Float profondeurPrevue) {
		this.profondeurPrevue = profondeurPrevue;
	}

	public Float getProfondeurRealisee() {
		return profondeurRealisee;
	}

	public void setProfondeurRealisee(Float profondeurRealisee) {
		this.profondeurRealisee = profondeurRealisee;
	}

	public Integer getDureePrevue() {
		return dureePrevue;
	}

	public void setDureePrevue(Integer dureePrevue) {
		this.dureePrevue = dureePrevue;
	}

	public Integer getDureeRealisee() {
		return dureeRealisee;
	}

	public void setDureeRealisee(Integer dureeRealisee) {
		this.dureeRealisee = dureeRealisee;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public ListePlongeurs getPlongeurs() {
		return plongeurs;
	}

	public void setPlongeurs(ListePlongeurs plongeurs) {
		this.plongeurs = plongeurs;
	}
	
	@Override
	public String toString() {
		return "Palanquee [id=" + id + ", idFicheSecurite=" + idFicheSecurite
				+ ", moniteur=" + moniteur + ", numero=" + numero
				+ ", typePlonge=" + typePlonge + ", typeGaz=" + typeGaz
				+ ", profondeurPrevue=" + profondeurPrevue
				+ ", profondeurRealisee=" + profondeurRealisee
				+ ", dureePrevue=" + dureePrevue + ", dureeRealisee="
				+ dureeRealisee + ", version=" + version + ", plongeurs="
				+ plongeurs + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(idFicheSecurite);
		dest.writeParcelable(moniteur, flags);
		dest.writeInt(numero);
		dest.writeString(typePlonge.toString());
		dest.writeString(typeGaz.toString());
		dest.writeFloat(profondeurPrevue);
		dest.writeFloat(profondeurRealisee);
		dest.writeInt(dureePrevue);
		dest.writeInt(dureeRealisee);
		dest.writeInt(version);
		dest.writeParcelable(plongeurs, flags);
	}
}
