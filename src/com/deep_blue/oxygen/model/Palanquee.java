package com.deep_blue.oxygen.model;

import java.util.Date;

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
	private Integer idWeb;
	private Integer idFicheSecurite;
	private Moniteur moniteur;
	private Integer numero;
	private EnumTypePlonge typePlonge;
	private EnumTypeGaz typeGaz;
	private Float profondeurPrevue;
	private Float profondeurRealiseeMoniteur;
	private Integer dureePrevue;
	private Integer dureeRealiseeMoniteur;
	private Long version;
	private ListePlongeurs plongeurs;
	
	/**
	 * @param id
	 * @param idWeb
	 * @param idFicheSecurite
	 * @param moniteur
	 * @param numero
	 * @param typePlonge
	 * @param typeGaz
	 * @param profondeurPrevue
	 * @param profondeurRealiseeMoniteur
	 * @param dureePrevue
	 * @param dureeRealiseeMoniteur
	 * @param version
	 * @param plongeurs
	 */
	public Palanquee(Integer id, Integer idWeb, Integer idFicheSecurite, Moniteur moniteur,
			Integer numero, EnumTypePlonge typePlonge, EnumTypeGaz typeGaz,
			Float profondeurPrevue, Float profondeurRealisee,
			Integer dureePrevue, Integer dureeRealisee, Long version,
			ListePlongeurs plongeurs) {
		super();
		this.id = id;
		this.idWeb = idWeb;
		this.idFicheSecurite = idFicheSecurite;
		this.moniteur = moniteur;
		this.numero = numero;
		this.typePlonge = typePlonge;
		this.typeGaz = typeGaz;
		this.profondeurPrevue = profondeurPrevue;
		this.profondeurRealiseeMoniteur = profondeurRealisee;
		this.dureePrevue = dureePrevue;
		this.dureeRealiseeMoniteur = dureeRealisee;
		this.version = version;
		this.plongeurs = plongeurs;
	}
	
	public Palanquee(Parcel source){
		id = source.readInt();
		idWeb = source.readInt();
		idFicheSecurite = source.readInt();
		moniteur = source.readParcelable(Moniteur.class.getClassLoader());
		numero = source.readInt();
		typePlonge = EnumTypePlonge.valueOf(source.readString());
		typeGaz = EnumTypeGaz.valueOf(source.readString());
		profondeurPrevue = source.readFloat();
		profondeurRealiseeMoniteur = source.readFloat();
		dureePrevue = source.readInt();
		dureeRealiseeMoniteur = source.readInt();
		version = source.readLong();
		plongeurs = source.readParcelable(ListePlongeurs.class.getClassLoader());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Float getProfondeurRealiseeMoniteur() {
		return profondeurRealiseeMoniteur;
	}

	public void setProfondeurRealiseeMoniteur(Float profondeurRealisee) {
		this.profondeurRealiseeMoniteur = profondeurRealisee;
	}

	public Integer getDureePrevue() {
		return dureePrevue;
	}

	public void setDureePrevue(Integer dureePrevue) {
		this.dureePrevue = dureePrevue;
	}

	public Integer getDureeRealiseeMoniteur() {
		return dureeRealiseeMoniteur;
	}

	public void setDureeRealiseeMoniteur(Integer dureeRealisee) {
		this.dureeRealiseeMoniteur = dureeRealisee;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Met � jours la version
	 */
	public void updateVersion(){
		this.version = (new Date()).getTime() / 1000;
	}
	
	public ListePlongeurs getPlongeurs() {
		return plongeurs;
	}

	public void setPlongeurs(ListePlongeurs plongeurs) {
		this.plongeurs = plongeurs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tidWeb: " + idWeb
				+ "\n\tidFicheSecurite: " + idFicheSecurite + "\n\tmoniteur: "
				+ moniteur + "\n\tnumero: " + numero + "\n\ttypePlonge: "
				+ typePlonge + "\n\ttypeGaz: " + typeGaz
				+ "\n\tprofondeurPrevue: " + profondeurPrevue
				+ "\n\tprofondeurRealiseeMoniteur: " + profondeurRealiseeMoniteur
				+ "\n\tdureePrevue: " + dureePrevue + "\n\tdureeRealiseeMoniteur: "
				+ dureeRealiseeMoniteur + "\n\tversion: " + version + "\n\tplongeurs: "
				+ plongeurs + "\n}";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(idWeb);
		dest.writeInt(idFicheSecurite);
		dest.writeParcelable(moniteur, flags);
		dest.writeInt(numero);
		dest.writeString(typePlonge.toString());
		dest.writeString(typeGaz.toString());
		dest.writeFloat(profondeurPrevue);
		dest.writeFloat(profondeurRealiseeMoniteur);
		dest.writeInt(dureePrevue);
		dest.writeInt(dureeRealiseeMoniteur);
		dest.writeLong(version);
		dest.writeParcelable(plongeurs, flags);
	}
}
