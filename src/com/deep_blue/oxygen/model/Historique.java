package com.deep_blue.oxygen.model;

public class Historique {
	
	
	private String loginUtilisateur;
	private Long timestamp;
	private Integer idFicheSecurite;
	private String commentaire;
	
	public Historique(String loginUtilisateur, Long timestamp,
			Integer idFicheSecurite, String commentaire) {
		super();
		this.loginUtilisateur = loginUtilisateur;
		this.timestamp = timestamp;
		this.idFicheSecurite = idFicheSecurite;
		this.commentaire = commentaire;
	}
	
	public String getLoginUtilisateur() {
		return loginUtilisateur;
	}
	public void setLoginUtilisateur(String loginUtilisateur) {
		this.loginUtilisateur = loginUtilisateur;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getIdFicheSecurite() {
		return idFicheSecurite;
	}
	public void setIdFicheSecurite(Integer idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public String toString() {
		return "Historique [loginUtilisateur=" + loginUtilisateur
				+ ", timestamp=" + timestamp + ", idFicheSecurite="
				+ idFicheSecurite + ", commentaire=" + commentaire + "]";
	}	
}
