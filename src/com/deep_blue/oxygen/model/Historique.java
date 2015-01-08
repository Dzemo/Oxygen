package com.deep_blue.oxygen.model;

public class Historique {
	
	private Integer idHistorique;
	private String loginUtilisateur;
	private Long timestamp;
	private Long idFicheSecurite;
	private String commentaire;
	
	/**
	 * Pour la récupération d'un historique en base
	 * @param idHistorique
	 * @param loginUtilisateur
	 * @param timestamp
	 * @param idFicheSecurite
	 * @param commentaire
	 */
	public Historique(Integer idHistorique, String loginUtilisateur, Long timestamp,
			Long idFicheSecurite, String commentaire) {
		super();
		this.idHistorique = idHistorique;
		this.loginUtilisateur = loginUtilisateur;
		this.timestamp = timestamp;
		this.idFicheSecurite = idFicheSecurite;
		this.commentaire = commentaire;
	}
	
	/**
	 * Pour la création d'un historique
	 * @param loginUtilisateur
	 * @param timestamp
	 * @param idFicheSecurite
	 * @param commentaire
	 */
	public Historique(String loginUtilisateur, Long timestamp,
			Long idFicheSecurite, String commentaire) {
		super();
		this.idHistorique = -1;
		this.loginUtilisateur = loginUtilisateur;
		this.timestamp = timestamp;
		this.idFicheSecurite = idFicheSecurite;
		this.commentaire = commentaire;
	}

	public Integer getIdHistorique() {
		return idHistorique;
	}
	public void setIdHistorique(Integer idHistorique) {
		this.idHistorique = idHistorique;
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
	public Long getIdFicheSecurite() {
		return idFicheSecurite;
	}
	public void setIdFicheSecurite(Long idFicheSecurite) {
		this.idFicheSecurite = idFicheSecurite;
	}
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " {\n\tidHistorique: " + idHistorique
				+ "\n\tloginUtilisateur: " + loginUtilisateur
				+ "\n\ttimestamp: " + timestamp + "\n\tidFicheSecurite: "
				+ idFicheSecurite + "\n\tcommentaire: " + commentaire + "\n}";
	}
}
