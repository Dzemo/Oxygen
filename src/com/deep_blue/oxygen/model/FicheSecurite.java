package com.deep_blue.oxygen.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class FicheSecurite  implements Parcelable {

	public static final Parcelable.Creator<FicheSecurite> CREATOR = new Parcelable.Creator<FicheSecurite>() {
		@Override
		public FicheSecurite createFromParcel(Parcel source) {
			return new FicheSecurite(source);
		}

		@Override
		public FicheSecurite[] newArray(int size) {
			return new FicheSecurite[size];
		}
	};

	
	private Integer id;
	private Integer idWeb;
	private Embarcation embarcation;
	private Moniteur directeurPlonge;
	private Long timestamp;
	private Site site;
	private EnumEtat etat;
	private Long version;
	private ListePalanquees palanquees;
	
	public FicheSecurite() {
		super();
		this.id = null;
		this.idWeb = null;
		this.embarcation = null;
		this.directeurPlonge = null;
		this.timestamp = null;
		this.site = null;
		this.etat = null;
		this.version = null;
		this.palanquees = null;
	}
	
	/**
	 * @param id
	 * @param idWeb
	 * @param embarcation
	 * @param directeurPlonge
	 * @param timestamp
	 * @param site
	 * @param etat
	 * @param version
	 * @param palanquees
	 */
	public FicheSecurite(Integer id, Integer idWeb, Embarcation embarcation,
			Moniteur directeurPlonge, Long timestamp, Site site, EnumEtat etat,
			Long version, ListePalanquees palanquees) {
		super();
		this.id = id;
		this.idWeb = idWeb;
		this.embarcation = embarcation;
		this.directeurPlonge = directeurPlonge;
		this.timestamp = timestamp;
		this.site = site;
		this.etat = etat;
		this.version = version;
		this.palanquees = palanquees;
	}
	
	/**
	 * 
	 * @param source
	 */
	public FicheSecurite(Parcel source){
		boolean isPresent;
		
		id = source.readInt();
		idWeb = source.readInt();
		
		isPresent = source.readByte() == 1;
		if(isPresent)
			embarcation = source.readParcelable(Embarcation.class.getClassLoader());
		else
			embarcation = null;
		
		timestamp = source.readLong();
		isPresent = source.readByte() == 1;
		if(isPresent)
			site = source.readParcelable(Site.class.getClassLoader());
		else
			site = null;
		isPresent = source.readByte() == 1;
		if(isPresent)
			directeurPlonge = source.readParcelable(Moniteur.class.getClassLoader());
		else
			directeurPlonge = null;
		etat = EnumEtat.valueOf(source.readString());
		version = source.readLong();		
		isPresent = source.readByte() == 1;
		if(isPresent)
			palanquees = source.readParcelable(ListePalanquees.class.getClassLoader());
		else
			palanquees = new ListePalanquees();
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
	 * @return the embarcation
	 */
	public Embarcation getEmbarcation() {
		return embarcation;
	}

	/**
	 * @param embarcation the embarcation to set
	 */
	public void setEmbarcation(Embarcation embarcation) {
		this.embarcation = embarcation;
	}

	/**
	 * @return the directeurPlonge
	 */
	public Moniteur getDirecteurPlonge() {
		return directeurPlonge;
	}

	/**
	 * @param directeurPlonge the directeurPlonge to set
	 */
	public void setDirecteurPlonge(Moniteur directeurPlonge) {
		this.directeurPlonge = directeurPlonge;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setDate(Date date){
		timestamp = date.getTime() / 1000;
	}
	
	public Date getDate(){
		return new Date(timestamp*1000);
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}

	/**
	 * @param site the site to set
	 */
	public void setSite(Site site) {
		this.site = site;
	}

	/**
	 * @return the etat
	 */
	public EnumEtat getEtat() {
		return etat;
	}

	/**
	 * @param etat the etat to set
	 */
	public void setEtat(EnumEtat etat) {
		this.etat = etat;
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
	
	/**
	 * Met à jours la version
	 */
	public void updateVersion(){
		this.version = (new Date()).getTime() / 1000;
	}

	/**
	 * @return the palanquees
	 */
	public ListePalanquees getPalanquees() {
		return palanquees;
	}

	/**
	 * @param palanquees the palanquees to set
	 */
	public void setPalanquees(ListePalanquees palanquees) {
		this.palanquees = palanquees;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tidWeb: " + idWeb
				+ "\n\tembarcation: " + embarcation + "\n\tdirecteurPlonge: "
				+ directeurPlonge + "\n\ttimestamp: " + timestamp
				+ "\n\tsite: " + site + "\n\tetat: " + etat + "\n\tversion: "
				+ version + "\n\tpalanquees: " + palanquees + "\n}";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(idWeb);
		dest.writeByte((byte)(embarcation != null ? 1 : 0));
		if(embarcation != null)
			dest.writeParcelable(embarcation, flags);
		dest.writeLong(timestamp);
		dest.writeByte((byte)(site != null ? 1 : 0));
		if(site != null)
		dest.writeParcelable(site,flags);
		dest.writeByte((byte)(directeurPlonge != null ? 1 : 0));
		if(directeurPlonge != null)
			dest.writeParcelable(directeurPlonge, flags);
		dest.writeString(etat != null ? etat.toString() : null);
		dest.writeLong(version);
		dest.writeByte((byte)(palanquees != null ? 1 : 0));
		dest.writeParcelable(palanquees, flags);
	}
}
