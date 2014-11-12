package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe représentant un plongeur d'une palanquée
 * @author Raphael
 *
 */
public class Plongeur implements Parcelable {

	public static final Parcelable.Creator<Plongeur> CREATOR = new Parcelable.Creator<Plongeur>() {
		@Override
		public Plongeur createFromParcel(Parcel source) {
			return new Plongeur(source);
		}

		@Override
		public Plongeur[] newArray(int size) {
			return new Plongeur[size];
		}
	};

	/**
	 * Id du plongeur
	 */
	private Integer id;

	/**
	 * Id de la palanquée à laquel appartient ce plongeur
	 */
	private Integer idPalanquee;
	
	/**
	 * Id de la fiche de sécurité à laquel appartient ce plongeur=
	 */
	private Integer idFicheSecurite;
	
	/**
	 * Nom du plongeur
	 */
	private String nom;
	
	/**
	 * Prénom du plongeur
	 */
	private String prenom;
	
	/**
	 * Liste des aptitudes de ce plongeur
	 */
	private ListeAptitudes aptitudes;
	
	/**
	 * Numéro de téléphone du plongeur
	 */
	private String telephone;
	
	/**
	 * Numéro de téléphone à contacter d'urgence du plongeur
	 */
	private String telephoneUrgence;
	
	/**
	 * Date de naissance du plongeur, sous forme de chaine de caractères
	 */
	private String dateNaissance;
	
	/**
	 * Version du plongeur, pour la synchronisation
	 */
	private Integer version;

	/**
	 * 
	 * @param id
	 * @param idPalanquee
	 * @param idFicheSecurite
	 * @param nom
	 * @param prenom
	 * @param aptitudes
	 * @param telephone
	 * @param telephoneUrgence
	 * @param dateNaissance
	 * @param version
	 */
	public Plongeur(Integer id, Integer idPalanquee, Integer idFicheSecurite,
			String nom, String prenom, ListeAptitudes aptitudes,
			String telephone, String telephoneUrgence, String dateNaissance,
			Integer version) {
		super();
		this.id = id;
		this.idPalanquee = idPalanquee;
		this.idFicheSecurite = idFicheSecurite;
		this.nom = nom;
		this.prenom = prenom;
		this.aptitudes = aptitudes;
		this.telephone = telephone;
		this.telephoneUrgence = telephoneUrgence;
		this.dateNaissance = dateNaissance;
		this.version = version;
	}
	
	/**
	 * 
	 * @param source
	 */
	public Plongeur(Parcel source){
		id = source.readInt();
		idPalanquee = source.readInt();
		idFicheSecurite = source.readInt();
		nom = source.readString();
		prenom = source.readString();
		//aptitudes = source.readParcelable(ListeAptitudes.class.getClassLoader());
		telephone = source.readString();
		telephoneUrgence = source.readString();
		dateNaissance = source.readString();
		version = source.readInt();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPalanquee() {
		return idPalanquee;
	}

	public void setIdPalanquee(Integer idPalanquee) {
		this.idPalanquee = idPalanquee;
	}

	public Integer getIdFicheSecurite() {
		return idFicheSecurite;
	}

	public void setIdFicheSecurite(Integer idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
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

	public ListeAptitudes getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(ListeAptitudes aptitudes) {
		this.aptitudes = aptitudes;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getTelephoneUrgence() {
		return telephoneUrgence;
	}

	public void setTelephoneUrgence(String telephoneUrgence) {
		this.telephoneUrgence = telephoneUrgence;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Plongeur [id=" + id + ", idPalanquee=" + idPalanquee
				+ ", idFicheSecurite=" + idFicheSecurite + ", nom=" + nom
				+ ", prenom=" + prenom + ", aptitudes=" + aptitudes
				+ ", telephone=" + telephone + ", telephoneUrgence="
				+ telephoneUrgence + ", dateNaissance=" + dateNaissance
				+ ", version=" + version + "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(idPalanquee);
		dest.writeInt(idFicheSecurite);
		dest.writeString(nom);
		dest.writeString(prenom);
		//dest.writeParcelable(aptitudes, flags);
		dest.writeString(telephone);
		dest.writeString(telephoneUrgence);
		dest.writeString(dateNaissance);
		dest.writeInt(version);		
	}
}
