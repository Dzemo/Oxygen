package com.deep_blue.oxygen.synchronisation.json;

import java.util.List;

import com.deep_blue.oxygen.model.Utilisateur;

public class JsonResponseContainer {

	private List<Utilisateur> utilisateurs;
	

	public JsonResponseContainer(){
		this.utilisateurs = null;
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
}
