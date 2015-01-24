package com.deep_blue.oxygen.synchronisation.json;

import java.util.List;

import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Historique;

public class JsonRequestContainer {

	private Long utilisateurMaxVersion;
	private String utilisateurLogin;
	private Integer synchRetrieveLength;
	private Boolean synchRetrieveTypeAll;
	private Long aptitudeMaxVersion;
	private Long embarcationMaxVersion;
	private Long ficheSecuriteMaxVersion;
	private Long siteMaxVersion;
	private Long moniteurMaxVersion;
	private List<FicheSecurite> fichesSecuriteValidees;
	private List<FicheSecurite> fichesSecuriteEnCours;
	private List<Historique> historiques;

	public JsonRequestContainer() {
		super();
		this.utilisateurMaxVersion = null;
		this.utilisateurLogin = null;
		this.synchRetrieveLength = null;
		this.synchRetrieveTypeAll = null;
		this.aptitudeMaxVersion = null;
		this.embarcationMaxVersion = null;
		this.ficheSecuriteMaxVersion = null;
		this.siteMaxVersion = null;
		this.moniteurMaxVersion = null;
		this.fichesSecuriteValidees = null;
		this.fichesSecuriteEnCours = null;
		this.historiques = null;
	}

	/**
	 * @param utilisateurMaxVersion
	 * @param utilisateurLogin
	 * @param synchRetrieveLength
	 * @param synchRetrieveTypeAll
	 * @param aptitudeMaxVersion
	 * @param embarcationMaxVersion
	 * @param ficheSecuriteMaxVersion
	 * @param siteMaxVersion
	 * @param moniteurMaxVersion
	 * @param fichesSecuriteValidees
	 * @param historiques
	 */
	public JsonRequestContainer(Long utilisateurMaxVersion,
			String utilisateurLogin, Integer synchRetrieveLength,
			Boolean synchRetrieveTypeAll, Long aptitudeMaxVersion,
			Long embarcationMaxVersion, Long ficheSecuriteMaxVersion,
			Long siteMaxVersion, Long moniteurMaxVersion,
			List<FicheSecurite> fichesSecuriteValidees,
			List<FicheSecurite> fichesSecuriteEnCours,
			List<Historique> historiques) {
		super();
		this.utilisateurMaxVersion = utilisateurMaxVersion;
		this.utilisateurLogin = utilisateurLogin;
		this.synchRetrieveLength = synchRetrieveLength;
		this.synchRetrieveTypeAll = synchRetrieveTypeAll;
		this.aptitudeMaxVersion = aptitudeMaxVersion;
		this.embarcationMaxVersion = embarcationMaxVersion;
		this.ficheSecuriteMaxVersion = ficheSecuriteMaxVersion;
		this.siteMaxVersion = siteMaxVersion;
		this.moniteurMaxVersion = moniteurMaxVersion;
		this.fichesSecuriteValidees = fichesSecuriteValidees;
		this.fichesSecuriteEnCours = fichesSecuriteEnCours;
		this.historiques = historiques;
	}

	/**
	 * @return the utilisateurMaxVersion
	 */
	public Long getUtilisateurMaxVersion() {
		return utilisateurMaxVersion;
	}

	/**
	 * @param utilisateurMaxVersion
	 *            the utilisateurMaxVersion to set
	 */
	public void setUtilisateurMaxVersion(Long utilisateurMaxVersion) {
		this.utilisateurMaxVersion = utilisateurMaxVersion;
	}

	/**
	 * @return the utilisateurLogin
	 */
	public String getUtilisateurLogin() {
		return utilisateurLogin;
	}

	/**
	 * @param utilisateurLogin
	 *            the utilisateurLogin to set
	 */
	public void setUtilisateurLogin(String utilisateurLogin) {
		this.utilisateurLogin = utilisateurLogin;
	}

	/**
	 * @return the synchRetrieveLength
	 */
	public Integer getSynchRetrieveLength() {
		return synchRetrieveLength;
	}

	/**
	 * @param synchRetrieveLength
	 *            the synchRetrieveLength to set
	 */
	public void setSynchRetrieveLength(Integer synchRetrieveLength) {
		this.synchRetrieveLength = synchRetrieveLength;
	}

	/**
	 * @return the synchRetrieveTypeAll
	 */
	public Boolean getSynchRetrieveTypeAll() {
		return synchRetrieveTypeAll;
	}

	/**
	 * @param synchRetrieveTypeAll
	 *            the synchRetrieveTypeAll to set
	 */
	public void setSynchRetrieveTypeAll(Boolean synchRetrieveTypeAll) {
		this.synchRetrieveTypeAll = synchRetrieveTypeAll;
	}

	/**
	 * @return the aptitudeMaxVersion
	 */
	public Long getAptitudeMaxVersion() {
		return aptitudeMaxVersion;
	}

	/**
	 * @param aptitudeMaxVersion
	 *            the aptitudeMaxVersion to set
	 */
	public void setAptitudeMaxVersion(Long aptitudeMaxVersion) {
		this.aptitudeMaxVersion = aptitudeMaxVersion;
	}

	/**
	 * @return the embarcationMaxVersion
	 */
	public Long getEmbarcationMaxVersion() {
		return embarcationMaxVersion;
	}

	/**
	 * @param embarcationMaxVersion
	 *            the embarcationMaxVersion to set
	 */
	public void setEmbarcationMaxVersion(Long embarcationMaxVersion) {
		this.embarcationMaxVersion = embarcationMaxVersion;
	}

	/**
	 * @return the ficheSecuriteMaxVersion
	 */
	public Long getFicheSecuriteMaxVersion() {
		return ficheSecuriteMaxVersion;
	}

	/**
	 * @param ficheSecuriteMaxVersion
	 *            the ficheSecuriteMaxVersion to set
	 */
	public void setFicheSecuriteMaxVersion(Long ficheSecuriteMaxVersion) {
		this.ficheSecuriteMaxVersion = ficheSecuriteMaxVersion;
	}

	/**
	 * @return the siteMaxVersion
	 */
	public Long getSiteMaxVersion() {
		return siteMaxVersion;
	}

	/**
	 * @param siteMaxVersion
	 *            the siteMaxVersion to set
	 */
	public void setSiteMaxVersion(Long siteMaxVersion) {
		this.siteMaxVersion = siteMaxVersion;
	}

	/**
	 * @return the moniteurMaxVersion
	 */
	public Long getMoniteurMaxVersion() {
		return moniteurMaxVersion;
	}

	/**
	 * @param moniteurMaxVersion
	 *            the moniteurMaxVersion to set
	 */
	public void setMoniteurMaxVersion(Long moniteurMaxVersion) {
		this.moniteurMaxVersion = moniteurMaxVersion;
	}

	/**
	 * @return the fichesSecuriteValidees
	 */
	public List<FicheSecurite> getFichesSecuriteValidees() {
		return fichesSecuriteValidees;
	}

	/**
	 * @param fichesSecuriteValidees
	 *            the fichesSecuriteValidees to set
	 */
	public void setFichesSecuriteValidees(
			List<FicheSecurite> fichesSecuriteValidees) {
		this.fichesSecuriteValidees = fichesSecuriteValidees;
	}

	/**
	 * @return the fichesSecuriteEnCours
	 */
	public List<FicheSecurite> getFichesSecuriteEnCours() {
		return fichesSecuriteEnCours;
	}

	/**
	 * @param fichesSecuriteEnCours
	 *            the fichesSecuriteEnCours to set
	 */
	public void setFichesSecuriteEnCours(
			List<FicheSecurite> fichesSecuriteEnCours) {
		this.fichesSecuriteEnCours = fichesSecuriteEnCours;
	}

	/**
	 * @return the historiques
	 */
	public List<Historique> getHistoriques() {
		return historiques;
	}

	/**
	 * @param historiques
	 *            the historiques to set
	 */
	public void setHistoriques(List<Historique> historiques) {
		this.historiques = historiques;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tutilisateurMaxVersion: "
				+ utilisateurMaxVersion + "\n\tutilisateurLogin: "
				+ utilisateurLogin + "\n\tsynchRetrieveLength: "
				+ synchRetrieveLength + "\n\tsynchRetrieveTypeAll: "
				+ synchRetrieveTypeAll + "\n\taptitudeMaxVersion: "
				+ aptitudeMaxVersion + "\n\tembarcationMaxVersion: "
				+ embarcationMaxVersion + "\n\tficheSecuriteMaxVersion: "
				+ ficheSecuriteMaxVersion + "\n\tsiteMaxVersion: "
				+ siteMaxVersion + "\n\tmoniteurMaxVersion: "
				+ moniteurMaxVersion + "\n\tfichesSecuriteValidees: "
				+ fichesSecuriteValidees + "\n\tfichesSecuriteEnCours: "
				+ fichesSecuriteEnCours + "\n\thistoriques: " + historiques
				+ "\n}";
	}
}
