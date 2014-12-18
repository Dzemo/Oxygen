package com.deep_blue.oxygen.synchronisation.json;

import java.util.List;

import com.deep_blue.oxygen.model.Aptitude;
import com.deep_blue.oxygen.model.Embarcation;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Site;
import com.deep_blue.oxygen.model.Utilisateur;

public class JsonResponseContainer {

	private List<Utilisateur> utilisateurs;
	private List<FicheSecurite> fichesSecurite;
	private List<Aptitude> aptitudes;
	private List<Embarcation> embarcations;
	private List<Site> sites;
	private List<Moniteur> moniteurs;
	private Boolean fichesOk;
	private Boolean historiquesOk;
	
	public JsonResponseContainer() {
		super();
		this.utilisateurs = null;
		this.fichesSecurite = null;
		this.aptitudes = null;
		this.embarcations = null;
		this.sites = null;
		this.moniteurs = null;
		this.fichesOk = false;
		this.historiquesOk = false;
	}

	/**
	 * @return the utilisateurs
	 */
	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	/**
	 * @param utilisateurs the utilisateurs to set
	 */
	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	/**
	 * @return the fichesSecurite
	 */
	public List<FicheSecurite> getFichesSecurite() {
		return fichesSecurite;
	}

	/**
	 * @param fichesSecurite the fichesSecurite to set
	 */
	public void setFichesSecurite(List<FicheSecurite> fichesSecurite) {
		this.fichesSecurite = fichesSecurite;
	}

	/**
	 * @return the aptitudes
	 */
	public List<Aptitude> getAptitudes() {
		return aptitudes;
	}

	/**
	 * @param aptitudes the aptitudes to set
	 */
	public void setAptitudes(List<Aptitude> aptitudes) {
		this.aptitudes = aptitudes;
	}

	/**
	 * @return the embarcations
	 */
	public List<Embarcation> getEmbarcations() {
		return embarcations;
	}

	/**
	 * @param embarcations the embarcations to set
	 */
	public void setEmbarcations(List<Embarcation> embarcations) {
		this.embarcations = embarcations;
	}

	/**
	 * @return the sites
	 */
	public List<Site> getSites() {
		return sites;
	}

	/**
	 * @param sites the sites to set
	 */
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	/**
	 * @return the moniteurs
	 */
	public List<Moniteur> getMoniteurs() {
		return moniteurs;
	}

	/**
	 * @param moniteurs the moniteurs to set
	 */
	public void setMoniteurs(List<Moniteur> moniteurs) {
		this.moniteurs = moniteurs;
	}

	/**
	 * @return the fichesOk
	 */
	public Boolean getFichesOk() {
		return fichesOk;
	}

	/**
	 * @param fichesOk the fichesOk to set
	 */
	public void setFichesOk(Boolean fichesOk) {
		this.fichesOk = fichesOk;
	}

	/**
	 * @return the historiquesOk
	 */
	public Boolean getHistoriquesOk() {
		return historiquesOk;
	}

	/**
	 * @param historiquesOk the historiquesOk to set
	 */
	public void setHistoriquesOk(Boolean historiquesOk) {
		this.historiquesOk = historiquesOk;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tutilisateurs: " + utilisateurs
				+ "\n\tfichesSecurite: " + fichesSecurite + "\n\taptitudes: "
				+ aptitudes + "\n\tembarcations: " + embarcations
				+ "\n\tsites: " + sites + "\n\tmoniteurs: " + moniteurs
				+ "\n\tfichesOk: " + fichesOk + "\n\thistoriquesOk: "
				+ historiquesOk + "\n}";
	}
	
}
