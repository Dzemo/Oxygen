package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.deep_blue.oxygen.util.DateStringUtils;

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


	private Long id;
	private Integer idWeb;
	private Long idFicheSecurite;
	private Moniteur moniteur;
	private Integer numero;
	private EnumTypePlonge typePlonge;
	private EnumTypeGaz typeGaz;
	private Float profondeurPrevue;
	private Float profondeurRealiseeMoniteur;
	private Integer dureePrevue;
	private Integer dureeRealiseeMoniteur;
	private String heure;
	private Long version;
	private ListePlongeurs plongeurs;
	private boolean modifie;
	private boolean desactive;
	
	public Palanquee() {
		super();
		this.id = -1L;
		this.idWeb = -1;
		this.idFicheSecurite = -1L;
		this.moniteur = null;
		this.numero = 0;
		this.typePlonge = EnumTypePlonge.NULL;
		this.typeGaz = EnumTypeGaz.NULL;
		this.profondeurPrevue = 0F;
		this.profondeurRealiseeMoniteur = 0F;
		this.dureePrevue = 0;
		this.dureeRealiseeMoniteur = 0;
		this.heure = "";
		this.version = 0L;
		this.plongeurs = new ListePlongeurs();
		this.modifie = false;
		this.desactive = false;
	}
	
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
	 * @param desactive
	 * @param version
	 * @param plongeurs
	 */
	public Palanquee(Long id, Integer idWeb, Long idFicheSecurite, Moniteur moniteur,
			Integer numero, EnumTypePlonge typePlonge, EnumTypeGaz typeGaz,
			Float profondeurPrevue, Float profondeurRealisee,
			Integer dureePrevue, Integer dureeRealisee, String heure, boolean desactive, Long version,
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
		this.heure = heure;
		this.version = version;
		this.plongeurs = plongeurs;
		this.modifie = false;
		this.desactive = desactive;
	}
	
	public Palanquee(Parcel source){
		id = source.readLong();
		idWeb = source.readInt();
		idFicheSecurite = source.readLong();
		moniteur = source.readParcelable(Moniteur.class.getClassLoader());
		numero = source.readInt();
		typePlonge = EnumTypePlonge.valueOf(source.readString());
		typeGaz = EnumTypeGaz.valueOf(source.readString());
		profondeurPrevue = source.readFloat();
		profondeurRealiseeMoniteur = source.readFloat();
		dureePrevue = source.readInt();
		dureeRealiseeMoniteur = source.readInt();
		heure = source.readString();
		version = source.readLong();
		plongeurs = source.readParcelable(ListePlongeurs.class.getClassLoader());
		modifie = source.readByte() == 1;
		desactive = source.readByte() == 1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.modifie = true;
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
		this.modifie = true;
	}

	public Long getIdFicheSecurite() {
		return idFicheSecurite;
	}

	public void setIdFicheSecurite(Long idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
		this.modifie = true;
	}

	public Moniteur getMoniteur() {
		return moniteur;
	}

	public void setMoniteur(Moniteur moniteur) {
		this.moniteur = moniteur;
		this.modifie = true;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
		this.modifie = true;
	}

	public EnumTypePlonge getTypePlonge() {
		return typePlonge;
	}

	public void setTypePlonge(EnumTypePlonge typePlonge) {
		this.typePlonge = typePlonge != null ? typePlonge : EnumTypePlonge.NULL;
		this.modifie = true;
	}

	public EnumTypeGaz getTypeGaz() {
		return typeGaz;
	}

	public void setTypeGaz(EnumTypeGaz typeGaz) {
		this.typeGaz = typeGaz != null ? typeGaz : EnumTypeGaz.NULL;
		this.modifie = true;
	}

	public Float getProfondeurPrevue() {
		return profondeurPrevue;
	}

	public void setProfondeurPrevue(Float profondeurPrevue) {
		this.profondeurPrevue = profondeurPrevue;
		this.modifie = true;
	}

	public Float getProfondeurRealiseeMoniteur() {
		return profondeurRealiseeMoniteur;
	}

	public void setProfondeurRealiseeMoniteur(Float profondeurRealisee) {
		this.profondeurRealiseeMoniteur = profondeurRealisee;
		this.modifie = true;
	}

	public Integer getDureePrevue() {
		return dureePrevue;
	}

	public void setDureePrevue(Integer dureePrevue) {
		this.dureePrevue = dureePrevue;
		this.modifie = true;
	}

	public Integer getDureeRealiseeMoniteur() {
		return dureeRealiseeMoniteur;
	}

	public void setDureeRealiseeMoniteur(Integer dureeRealisee) {
		this.dureeRealiseeMoniteur = dureeRealisee;
		this.modifie = true;
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
		this.modifie = true;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
		this.modifie = true;
	}

	/**
	 * Met � jours la version
	 */
	public void updateVersion(){
		this.version =  DateStringUtils.getCurrentTimestamps();
	}
	
	public ListePlongeurs getPlongeurs() {
		return plongeurs;
	}

	public void setPlongeurs(ListePlongeurs plongeurs) {
		this.plongeurs = plongeurs;
		this.modifie = true;
	}

	public boolean isModifie() {
		return modifie;
	}

	public void setModifie(boolean modifie) {
		this.modifie = modifie;
	}
	
	public boolean isDesactive() {
		return desactive;
	}

	public void setDesactive(boolean desactive) {
		this.desactive = desactive;
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
				+ "\n\tprofondeurRealiseeMoniteur: "
				+ profondeurRealiseeMoniteur + "\n\tdureePrevue: "
				+ dureePrevue + "\n\tdureeRealiseeMoniteur: "
				+ dureeRealiseeMoniteur + "\n\theure: " + heure
				+ "\n\tversion: " + version + "\n\tplongeurs: " + plongeurs
				+ "\n\tmodifie: " + modifie + "\n\tdesactive: " + desactive
				+ "\n}";
	}
	

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeInt(idWeb);
		dest.writeLong(idFicheSecurite);
		dest.writeParcelable(moniteur, flags);
		dest.writeInt(numero);
		dest.writeString(typePlonge.toString());
		dest.writeString(typeGaz.toString());
		dest.writeFloat(profondeurPrevue);
		dest.writeFloat(profondeurRealiseeMoniteur);
		dest.writeInt(dureePrevue);
		dest.writeInt(dureeRealiseeMoniteur);
		dest.writeString(heure);
		dest.writeLong(version);
		dest.writeParcelable(plongeurs, flags);
		dest.writeByte((byte)(modifie ? 1 : 0));
		dest.writeByte((byte)(desactive ? 1 : 0));
	}
}
