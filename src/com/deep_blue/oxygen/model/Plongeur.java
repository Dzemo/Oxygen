package com.deep_blue.oxygen.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.deep_blue.oxygen.util.DateStringUtils;

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
	private Long id;
	
	/**
	 * Id du plongeur sur le serveur distant
	 */
	private Integer idWeb;

	/**
	 * Id de la palanquée à laquel appartient ce plongeur
	 */
	private Long idPalanquee;
	
	/**
	 * Id de la fiche de sécurité à laquel appartient ce plongeur=
	 */
	private Long idFicheSecurite;
	
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
	private Long version;
	
	/**
	 * Profondeur qu'a réalisé le plongeur (en mètre)
	 */
	private Float profondeurRealisee;
	
	/**
	 * Durée de plongé réalisé par le plongeur (en seconde)
	 */
	private Integer dureeRealisee;
	
	/**
	 * Indique si le plongeur a été modifier lors de l'utilisation de l'application actuelle, pour proposer d'enregistrer la fiche de sécurité correspondente en quittant
	 */
	private boolean modifie;
	
	/**
	 * Indique si le plongeur a été supprimer
	 */
	private boolean desactive;
	
	public Plongeur() {
		super();
		this.id = -1L;
		this.idWeb = -1;
		this.idPalanquee = -1L;
		this.idFicheSecurite = -1L;
		this.nom = "";
		this.prenom = "";
		this.aptitudes = new ListeAptitudes();
		this.telephone = "";
		this.telephoneUrgence = "";
		this.dateNaissance = "";
		this.profondeurRealisee = 0F;
		this.dureeRealisee = 0;
		this.version = -1L;
		this.modifie = false;
		this.desactive = false;
	}
	
	/**
	 * 
	 * @param id
	 * @param idWeb
	 * @param idPalanquee
	 * @param idFicheSecurite
	 * @param nom
	 * @param prenom
	 * @param aptitudes
	 * @param telephone
	 * @param telephoneUrgence
	 * @param dateNaissance
	 * @param profondeurRealisee
	 * @param dureeRealisee
	 * @param desactive
	 * @param version
	 */
	public Plongeur(Long id, Integer idWeb, Long idPalanquee, Long idFicheSecurite,
			String nom, String prenom, ListeAptitudes aptitudes,
			String telephone, String telephoneUrgence, String dateNaissance, Float profondeurRealisee,
			Integer dureeRealisee, Boolean desactive,
			Long version) {
		super();
		this.id = id;
		this.idWeb = idWeb;
		this.idPalanquee = idPalanquee;
		this.idFicheSecurite = idFicheSecurite;
		this.nom = nom;
		this.prenom = prenom;
		this.aptitudes = aptitudes;
		this.telephone = telephone;
		this.telephoneUrgence = telephoneUrgence;
		this.dateNaissance = dateNaissance;
		this.profondeurRealisee = profondeurRealisee;
		this.dureeRealisee = dureeRealisee;
		this.version = version;
		this.modifie = false;
		this.desactive = desactive;
	}
	
	/**
	 * 
	 * @param source
	 */
	public Plongeur(Parcel source){
		id = source.readLong();
		idWeb = source.readInt();
		idPalanquee = source.readLong();
		idFicheSecurite = source.readLong();
		nom = source.readString();
		prenom = source.readString();
		aptitudes = source.readParcelable(ListeAptitudes.class.getClassLoader());
		telephone = source.readString();
		telephoneUrgence = source.readString();
		dateNaissance = source.readString();
		profondeurRealisee = source.readFloat();
		dureeRealisee = source.readInt();
		version = source.readLong();
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

	public Long getIdPalanquee() {
		return idPalanquee;
	}

	public void setIdPalanquee(Long idPalanquee) {
		this.idPalanquee = idPalanquee;
		this.modifie = true;
	}

	public Long getIdFicheSecurite() {
		return idFicheSecurite;
	}

	public void setIdFicheSecurite(Long idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
		this.modifie = true;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
		this.modifie = true;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
		this.modifie = true;
	}

	public ListeAptitudes getAptitudes() {
		return aptitudes;
	}

	public void setAptitudes(ListeAptitudes aptitudes) {
		this.aptitudes = aptitudes;
		this.modifie = true;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
		this.modifie = true;
	}

	public String getTelephoneUrgence() {
		return telephoneUrgence;
	}

	public void setTelephoneUrgence(String telephoneUrgence) {
		this.telephoneUrgence = telephoneUrgence;
		this.modifie = true;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
		this.modifie = true;
	}
	
	public Float getProfondeurRealisee() {
		return profondeurRealisee;
	}

	public void setProfondeurRealisee(Float profondeurRealisee) {
		this.profondeurRealisee = profondeurRealisee;
		this.modifie = true;
	}
	
	public Integer getDureeRealisee() {
		return dureeRealisee;
	}

	public void setDureeRealisee(Integer dureeRealisee) {
		this.dureeRealisee = dureeRealisee;
		this.modifie = true;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
		this.modifie = true;
	}

	public boolean isDesactive() {
		return desactive;
	}

	public void setDesactive(boolean desactive) {
		this.desactive = desactive;
	}	
	
	/**
	 * Met à jours la version
	 */
	public void updateVersion(){
		this.version =  DateStringUtils.getCurrentTimestamps();
		this.modifie = true;
	}
	
	public boolean isModifie() {
		return modifie;
	}
	public void setModifie(boolean modifie) {
		this.modifie = modifie;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tid: " + id + "\n\tidWeb: " + idWeb
				+ "\n\tidPalanquee: " + idPalanquee + "\n\tidFicheSecurite: "
				+ idFicheSecurite + "\n\tnom: " + nom + "\n\tprenom: " + prenom
				+ "\n\taptitudes: " + aptitudes + "\n\ttelephone: " + telephone
				+ "\n\ttelephoneUrgence: " + telephoneUrgence
				+ "\n\tdateNaissance: " + dateNaissance + "\n\tversion: "
				+ version + "\n\tprofondeurRealisee: " + profondeurRealisee
				+ "\n\tdureeRealisee: " + dureeRealisee + "\n\tmodifie: "
				+ modifie + "\n\tdesactive: " + desactive + "\n}";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(id);
		dest.writeInt(idWeb);
		dest.writeLong(idPalanquee);
		dest.writeLong(idFicheSecurite);
		dest.writeString(nom != null ? nom : "");
		dest.writeString(prenom != null ? prenom : "");
		dest.writeParcelable(aptitudes, flags);
		dest.writeString(telephone != null ? telephone : "");
		dest.writeString(telephoneUrgence != null ? telephoneUrgence : "");
		dest.writeString(dateNaissance != null ? dateNaissance : "");
		dest.writeFloat(profondeurRealisee);
		dest.writeInt(dureeRealisee);
		dest.writeLong(version);	
		dest.writeByte((byte) (modifie ? 1 : 0));
		dest.writeByte((byte)(desactive ? 1 : 0));
	}
}
