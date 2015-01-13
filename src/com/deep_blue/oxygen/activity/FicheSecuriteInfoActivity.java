package com.deep_blue.oxygen.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.ConfirmDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheDateDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheDirecteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheEmbarcationDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.FicheSiteDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.ListeErreursDialogFragment;
import com.deep_blue.oxygen.dao.FicheSecuriteDao;
import com.deep_blue.oxygen.dao.HistoriqueDao;
import com.deep_blue.oxygen.listener.PalanqueeOnClickListener;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Historique;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Utilisateur;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;
import com.deep_blue.oxygen.util.ValidationFiche;

public class FicheSecuriteInfoActivity extends FragmentActivity implements ConfirmDialogFragment.ConfirmDialogListener, ListeErreursDialogFragment.ListeErreursDialogListener{

	private FicheSecurite ficheSecurite = null;
	private View rootView;
	private Utilisateur utilisateur = null;
	private FicheSecuriteDao ficheSecuriteDao;
	
	@SuppressLint("InflateParams") @Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		this.ficheSecuriteDao = new FicheSecuriteDao(this);
		
		//Affichage du bouton de retour à coté du logo
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//Récupération de la fiche de sécurité et de l'utilisateur
		Intent intent = getIntent();
		ficheSecurite = intent.getParcelableExtra(IntentKey.FICHE_SECURITE_COURANTE.toString());
		utilisateur = intent.getParcelableExtra(IntentKey.UTILISATEUR_COURANT.toString());
		
		//Inlfation du layout
		rootView = getLayoutInflater().inflate(R.layout.activity_fiche_securite_info, null);
		setContentView(rootView);
		
		if (ficheSecurite != null) {
			// Initialisation de la vue avec la fiche de sécurité séléctionné

			// Info général de la fiche
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_date_value))
					.setText(DateStringUtils.timestampsToDate(ficheSecurite
							.getTimestamp()));
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_site_value))
					.setText(ficheSecurite.getSite() != null ? ficheSecurite
							.getSite().getNom() : "");
			String directeurPlongeValue = "";
			if (ficheSecurite.getDirecteurPlonge() != null) {
				directeurPlongeValue = ficheSecurite.getDirecteurPlonge()
						.getPrenom()
						+ " "
						+ ficheSecurite.getDirecteurPlonge().getNom();
			}
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_directeur_plonge_value))
					.setText(directeurPlongeValue);
			((TextView) rootView
					.findViewById(R.id.textView_fiche_infos_embarcation_value))
					.setText(ficheSecurite.getEmbarcation() != null ? ficheSecurite
							.getEmbarcation().getLibelle() : "");
		}

		afficherPalanquees();
		
		if(ficheSecurite.getEtat() == EnumEtat.VALIDE){			
			cacherBoutonModification();			
			
			//Alignement pour le titre
			TextView tvTitrePalanquee = ((TextView)findViewById(R.id.textView_palanquee_plongeur_title));
			TableRow.LayoutParams params = (LayoutParams) tvTitrePalanquee.getLayoutParams();
			params.span = 2;
			tvTitrePalanquee.setLayoutParams(params); // causes layout update
		} else {
			ajouterClickListener();	
		}
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ficheSecurite = (FicheSecurite) data.getExtras().get(IntentKey.RESULT_FICHE_SECURITE.toString());
		
		String text = (String) data.getExtras().get(IntentKey.RESULT_TEXT.toString());
		if(text != null){
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			toast.show();
		}
			
		afficherPalanquees();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Affichage du menu uniquement sur les fiches de sécurité non validé
		if(ficheSecurite.getEtat() != EnumEtat.VALIDE)
			getMenuInflater().inflate(R.menu.fiche_securite_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.itemValide:
			ConfirmDialogFragment confirmDialogFragmentValide = new ConfirmDialogFragment(getResources().getString(R.string.fiche_info_dialog_cloture), ConfirmDialogFragment.CLOTURE_FICHE_SECURITE);
			confirmDialogFragmentValide.show(getSupportFragmentManager(), "ConfirmDialogFragment");
			return true;
		case R.id.itemSave:
			if(sauvegarderFiche(true)){
				//Affichage d'un message à l'utilisateur pour confirmer l'enregistrement
				Toast toastSave = Toast.makeText(this, getResources().getString(R.string.fiche_securite_enregistrement_ok), Toast.LENGTH_SHORT);
				toastSave.show();
			}
			return true;
		case R.id.itemDelete:
			ConfirmDialogFragment confirmDialogFragmentDelete = new ConfirmDialogFragment(getResources().getString(R.string.fiche_info_dialog_suppression), ConfirmDialogFragment.SUPPRESSION_FICHE_SECURITE);
			confirmDialogFragmentDelete.show(getSupportFragmentManager(), "ConfirmDialogFragment");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed(){
		if(ficheSecurite.isModifie()){
			//Si des modification non enregistré ont été apporté à la fiche, on propose d'abord de les enregistrer
			ConfirmDialogFragment confirmDialogFragmentQuitterSansEnregistrer = new ConfirmDialogFragment(getResources().getString(R.string.fiche_info_dialog_quitter_sauvegrder), ConfirmDialogFragment.QUITTER_SANS_SAUVEGARDER);
			confirmDialogFragmentQuitterSansEnregistrer.show(getSupportFragmentManager(), "ConfirmDialogFragment");
		}else{
			setResult(RESULT_OK, new Intent());
			finish();
		}
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, int confirmType) {
		if(confirmType == ConfirmDialogFragment.SUPPRESSION_FICHE_SECURITE){
			//Suppression de la fiche de sécurité si elle était enregistrée
			if(ficheSecurite.getId() != null || ficheSecurite.getId() < 0){
				ficheSecuriteDao.deleteLogique(ficheSecurite.getId());
			}
			Intent result = new Intent();
			result.putExtra(IntentKey.RESULT_TEXT.toString(), getResources().getString(R.string.fiche_info_suppression_confirm));
			setResult(RESULT_OK, result);
			finish();
		} 
		else if(confirmType == ConfirmDialogFragment.CLOTURE_FICHE_SECURITE){
			cloturerFiche(true);
		}
		else if(confirmType == ConfirmDialogFragment.QUITTER_SANS_SAUVEGARDER){
			//L'utilisateur souhaite quitter et enregister la fiche
			if(sauvegarderFiche(true)){
				//Renvoi de l'utilisateur à l'activité parente (liste des fiches)
				Intent result = new Intent();
				result.putExtra(IntentKey.RESULT_TEXT.toString(), getResources().getString(R.string.fiche_securite_enregistrement_ok));
				setResult(RESULT_OK, result);
				finish();
			}
		}
		else if(confirmType == ListeErreursDialogFragment.LISTE_ERREURS_ENREGISTREMENT){
			//On enregistre sans tenir compte des erreurs
			if(sauvegarderFiche(false)){
				//Affichage d'un message à l'utilisateur pour confirmer l'enregistrement
				Toast toastSave = Toast.makeText(this, getResources().getString(R.string.fiche_securite_enregistrement_ok), Toast.LENGTH_SHORT);
				toastSave.show();
			}
		}
		else if(confirmType == ListeErreursDialogFragment.LISTE_ERREURS_CLOTURE){
			//force la cloture de la fiche sans tenir compte des erreurs
			cloturerFiche(false);
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog, int confirmType) {
		if(confirmType == ConfirmDialogFragment.QUITTER_SANS_SAUVEGARDER){
			//L'utilisateur n'a pas souhaité enregistrer les modification, on quitte donc
			setResult(RESULT_OK, new Intent());
			finish();
		}
	}
	
	/**
	 * Tente de cloturer la fiche.
	 * Si aucune erreur de gestion ne sont présente, cloture la fiche (dao) et termine cette activité pour retourner a la liste des fiche
	 * Si des erreurs sont présente, affiche un modal avec la liste de ces erreurs
	 * 
	 * @param verifierRegleGestion Determine si les règles de gestion doivent être vérifier ou pas
	 */
	private void cloturerFiche(boolean verifierRegleGestion){
		
		if(verifierRegleGestion){
			//vérification de la fiche
			List<String> erreurs = ValidationFiche.validationClotureFiche(ficheSecurite);
			
			if(erreurs.size() > 0){
				//Si des erreurs sont présente, on affiche un dialog d'alert
				ListeErreursDialogFragment listeErreursDialogFragment = new ListeErreursDialogFragment(erreurs, ListeErreursDialogFragment.LISTE_ERREURS_CLOTURE);
				listeErreursDialogFragment.show(getSupportFragmentManager(), "ListeErreursDialogFragment");
				return ;
			}
		}
		
		//Appel dao pour cloturer la fiche
		if(ficheSecurite.getId() == null || ficheSecurite.getId() < 0){
			ficheSecurite = ficheSecuriteDao.insert(ficheSecurite);
			ficheSecurite = ficheSecuriteDao.updateEtat(ficheSecurite, EnumEtat.VALIDE);
		}
		else{
			ficheSecurite = ficheSecuriteDao.updateEtat(ficheSecurite, EnumEtat.VALIDE);
		}
		
		if(ficheSecurite == null){
			//Affichage d'un message à l'utilisateur lors d'une erreur lors de la cloture car la fiche retourner est null
			Toast toastSave = Toast.makeText(this, getResources().getString(R.string.fiche_info_cloture_problem), Toast.LENGTH_SHORT);
			toastSave.show();
		}
		else{		

			//Enregistrement d'un historique
			Historique historique = new Historique(utilisateur.getLogin(),  DateStringUtils.getCurrentTimestamps(), ficheSecurite.getId(), "Cloture de la fiche de sécurité");
			HistoriqueDao historiqueDao = new HistoriqueDao(this);
			historiqueDao.insert(historique);
			
			//Renvoi de l'utilisateur à l'activité parente (liste des fiches)
			Intent result = new Intent();
			result.putExtra(IntentKey.RESULT_TEXT.toString(), getResources().getString(R.string.fiche_info_cloture_confirm));
			setResult(RESULT_OK, result);
			finish();
		}
	}

	/**
	 * Tenre de sauvegarder la fiche.
	 * Si aucune erreur de gestion ne sont présente, enregistre la fiche (dao) et retourne true
	 * Si des erreurs sont présente, affiche un modal avec la liste de ces erreurs et renvoi false
	 * 
	 * @param verifierRegleGestion Determine si les règles de gestion doivent être vérifier ou pas
	 * @return true si la fiche a été enregistre, false sinon
	 */
	private boolean sauvegarderFiche(boolean verifierRegleGestion){
		
		if(verifierRegleGestion){
			//vérification de la fiche
			List<String> erreurs = ValidationFiche.validationEnregistrementFiche(ficheSecurite);
			
			if(erreurs.size() > 0){
				//Si des erreurs sont présente, on affiche un dialog d'alert
				ListeErreursDialogFragment listeErreursDialogFragment = new ListeErreursDialogFragment(erreurs, ListeErreursDialogFragment.LISTE_ERREURS_ENREGISTREMENT);
				listeErreursDialogFragment.show(getSupportFragmentManager(), "ListeErreursDialogFragment");
				return false;
			}
		}
		
		//Enregistrement de la fiche
		String commentaire;
		if(ficheSecurite.getId() == null || ficheSecurite.getId() < 0){
			ficheSecurite = ficheSecuriteDao.insert(ficheSecurite);
			commentaire = "Création de la fiche";
		}
		else{
			commentaire = "Mise à jours de la fiche";
			ficheSecurite = ficheSecuriteDao.update(ficheSecurite);
		}
		
		if(ficheSecurite == null){
			//Affichage d'un message à l'utilisateur lors d'une erreur lors de l'enregistrement car la fiche retourner est null
			Toast toastSave = Toast.makeText(this, getResources().getString(R.string.fiche_securite_enregistrement_probleme), Toast.LENGTH_SHORT);
			toastSave.show();
			
			return false;
		}
		else{
			//Enregistrement d'un historique
			Historique historique = new Historique(utilisateur.getLogin(),  DateStringUtils.getCurrentTimestamps(), ficheSecurite.getId(), commentaire);
			HistoriqueDao historiqueDao = new HistoriqueDao(this);
			historiqueDao.insert(historique);
			
			return true;
		}
	}
	
	/**
	 * Cache les bouton de modifications des informations de la palanque 
	 */
	private void cacherBoutonModification(){
		// Ne pas afficher le bouton de modification de la date
		rootView.findViewById(R.id.iB_infos_date).setVisibility(View.GONE);
		
		// Ne pas afficher le bouton de modification du directeur de plonge
		rootView.findViewById(R.id.iB_infos_directeur_plonge).setVisibility(View.GONE);
		
		// Ne pas afficher le bouton de modification de l'embarcation
		rootView.findViewById(R.id.iB_infos_embarcation).setVisibility(View.GONE);
		
		// Ne pas afficher le bouton de modification du site
		rootView.findViewById(R.id.iB_infos_site).setVisibility(View.GONE);
		
		// Ne pas afficher le bouton d'ajout de palanquee
		rootView.findViewById(R.id.iB_fiche_ajout_palanquee).setVisibility(View.GONE);
	}
	
	/**
	 * Ajoute les cliques listeners sur les boutons d'édition
	 */
	private void ajouterClickListener(){
		// Ajout du clique de modification de la date
		rootView.findViewById(R.id.iB_infos_date).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheDateDialogFragment ndf = new FicheDateDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification du directeur de plonge
		rootView.findViewById(R.id.iB_infos_directeur_plonge)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheDirecteurDialogFragment ndf = new FicheDirecteurDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification de l'embarcation
		rootView.findViewById(R.id.iB_infos_embarcation)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						FicheEmbarcationDialogFragment ndf = new FicheEmbarcationDialogFragment(
								rootView, ficheSecurite);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		// Ajout du clique de modification du site
		rootView.findViewById(R.id.iB_infos_site)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FicheSiteDialogFragment ndf = new FicheSiteDialogFragment(
						rootView, ficheSecurite);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		
		// Ajout du clique d'ajout de palanquee
		rootView.findViewById(R.id.iB_fiche_ajout_palanquee)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Palanquee palanquee = new Palanquee();
				palanquee.setId(ficheSecurite.getPalanquees().getNextNegativeId());
				palanquee.setNumero(ficheSecurite.getPalanquees().getNextNumero());
				try{
					palanquee.setDureePrevue(Integer.valueOf(getResources().getString(R.string.plonger_duree_default)));
				} catch(NumberFormatException e){}
				try{
					palanquee.setProfondeurPrevue(Float.valueOf(getResources().getString(R.string.plonger_profondeur_default)));
				} catch(NumberFormatException e){}
				ficheSecurite.getPalanquees().add(palanquee);
				afficherPalanquees();
			}
		});
	}
	
	/**
	 * Affiche la liste des palanquées
	 */
	private void afficherPalanquees(){
		TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.activity_fiche_securite_info_palanquees);
		
		//On efface les anciennes palanquée
		tableLayout.removeAllViews();

		//Affichage des palanquée
		int index = 0;
		for (Palanquee palanquee : ficheSecurite.getPalanquees()) {
			
			TableRow row = (TableRow) getLayoutInflater().inflate(
					R.layout.activity_fiche_securite_row_palanquee_layout, tableLayout, false);

			TextView tvDescription = (TextView) row
					.findViewById(R.id.textView_palanquee_label);
			tvDescription.setText("Palanquee "+palanquee.getNumero()+
					" ("+(palanquee.getTypePlonge() != EnumTypePlonge.NULL ? palanquee.getTypePlonge().toString() : "")+
					" - "+(palanquee.getProfondeurPrevue() != 0F ? palanquee.getProfondeurPrevue()+"m" : "")+")");

			// Coloration selon la parité de la row
			if (index % 2 == 0)
				row.setBackgroundResource(R.drawable.list_item_background_2);
			else
				row.setBackgroundResource(R.drawable.list_item_background_1);

			if (ficheSecurite.getEtat() == EnumEtat.VALIDE) {
				// Pour les fiches validée, on change l'icone des palanquée
				((ImageButton)row.findViewById(R.id.iB_palanquee)).setImageResource(android.R.drawable.ic_menu_info_details);
			}
			
			// Clik listener sur l'image de la palanquee
			row.findViewById(R.id.iB_palanquee).setOnClickListener(
					new PalanqueeOnClickListener(palanquee, utilisateur,
							ficheSecurite, this));
			
			// Ajout de la row dans la table
			tableLayout.addView(row, index);
			
			index++;
		}
	}
}
