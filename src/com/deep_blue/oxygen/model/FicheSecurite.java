package com.deep_blue.oxygen.model;

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
	private Embarcation embarcation;
	private Moniteur directeurPlonge;
	private Long timestamp;
	private Site site;
	private EnumEtat etat;
	private Integer version;
	private ListePalanquees palanquees;
	
	/**
	 * @param id
	 * @param embarcation
	 * @param directeurPlonge
	 * @param timestamp
	 * @param site
	 * @param etat
	 * @param version
	 * @param palanquees
	 */
	public FicheSecurite(Integer id, Embarcation embarcation,
			Moniteur directeurPlonge, Long timestamp, Site site, EnumEtat etat,
			Integer version, ListePalanquees palanquees) {
		super();
		this.id = id;
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
		version = source.readInt();		
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
	public Integer getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
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
	
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tembarcation: "
				+ embarcation + "\n\tdirecteurPlonge: " + directeurPlonge
				+ "\n\ttimestamp: " + timestamp + "\n\tsite: " + site
				+ "\n\tetat: " + etat + "\n\tversion: " + version
				+ "\n\tpalanquees: " + palanquees + "\n}";
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
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
		dest.writeInt(version);
		dest.writeByte((byte)(palanquees != null ? 1 : 0));
		dest.writeParcelable(palanquees, flags);
	}
}
