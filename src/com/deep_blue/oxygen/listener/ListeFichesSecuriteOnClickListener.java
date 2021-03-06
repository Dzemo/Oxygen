package com.deep_blue.oxygen.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.deep_blue.oxygen.activity.FicheSecuriteInfoActivity;
import com.deep_blue.oxygen.activity.ListeFichesSecuriteActivity;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.IntentKey;

public class ListeFichesSecuriteOnClickListener implements OnClickListener{

	private FicheSecurite clickedFiche;
	private Utilisateur currentUtilisateur;
	
	private ListeFichesSecuriteActivity listeFicheSecuriteActivity;
	
	public ListeFichesSecuriteOnClickListener(FicheSecurite clickedFiche, Utilisateur utilisateur, ListeFichesSecuriteActivity ficheSecuriteActivity){
		this.clickedFiche = clickedFiche;
		this.listeFicheSecuriteActivity = ficheSecuriteActivity;
		this.currentUtilisateur = utilisateur;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(listeFicheSecuriteActivity, FicheSecuriteInfoActivity.class);		
		intent.putExtra(IntentKey.FICHE_SECURITE_COURANTE.toString(), clickedFiche);
		intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(), currentUtilisateur);
		
		listeFicheSecuriteActivity.startActivityForResult(intent, 0);
	}
}