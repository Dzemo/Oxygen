package com.deep_blue.oxygen.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.deep_blue.oxygen.activity.FicheSecuriteInfoActivity;
import com.deep_blue.oxygen.activity.PalanqueeActivity;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.IntentKey;

public class PalanqueeOnClickListener implements OnClickListener{

	private Palanquee clickedPalanquee;
	private Utilisateur currentUtilisateur;
	private FicheSecurite currentFiche;
	
	private FicheSecuriteInfoActivity ficheSecuriteInfoActivity;
	
	public PalanqueeOnClickListener(Palanquee clickedPalanquee, Utilisateur utilisateur, FicheSecurite currentFiche, FicheSecuriteInfoActivity ficheSecuriteInfoActivity){
		this.clickedPalanquee = clickedPalanquee;
		this.ficheSecuriteInfoActivity = ficheSecuriteInfoActivity;
		this.currentFiche = currentFiche;
		this.currentUtilisateur = utilisateur;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(ficheSecuriteInfoActivity, PalanqueeActivity.class);		
		intent.putExtra(IntentKey.PALANQUEE_COURANTE.toString(), clickedPalanquee);	
		intent.putExtra(IntentKey.FICHE_SECURITE_COURANTE.toString(), currentFiche);
		intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(), currentUtilisateur);
		
		ficheSecuriteInfoActivity.startActivityForResult(intent, 0);
	}

}