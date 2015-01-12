package com.deep_blue.oxygen.listener;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.deep_blue.oxygen.activity.PlongeurActivity;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.IntentKey;

public class PlongeurOnClickListener implements OnClickListener{

	private Plongeur clickedPlongeur;
	private Utilisateur currentUtilisateur;
	private Palanquee currentPalanquee;
	private boolean modifiable;
	
	private FragmentActivity palanqueeActivity;
	
	public PlongeurOnClickListener(Plongeur clickedPlongeur, Utilisateur utilisateur, Palanquee palanquee, boolean modifiable, FragmentActivity palanqueeActivity){
		this.clickedPlongeur = clickedPlongeur;
		this.palanqueeActivity = palanqueeActivity;
		this.currentUtilisateur = utilisateur;
		this.currentPalanquee = palanquee;
		this.modifiable = modifiable;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(palanqueeActivity, PlongeurActivity.class);		
		intent.putExtra(IntentKey.PLONGEUR_COURANT.toString(), clickedPlongeur);
		intent.putExtra(IntentKey.UTILISATEUR_COURANT.toString(), currentUtilisateur);
		intent.putExtra(IntentKey.PALANQUEE_COURANTE.toString(), currentPalanquee);
		intent.putExtra(IntentKey.MODIFIABLE.toString(), modifiable);
		
		palanqueeActivity.startActivityForResult(intent, 0);
	}

}