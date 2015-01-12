package com.deep_blue.oxygen.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.deep_blue.oxygen.R;
import com.deep_blue.oxygen.activity.fragment.dialog.ConfirmDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurAptitudeDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurDateNaissanceDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurDureeRealiseeDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurProfondeurRealiseeDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurTextDialogFragment;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.EnumPlongeurText;
import com.deep_blue.oxygen.util.IntentKey;

public class PlongeurActivity extends FragmentActivity implements ConfirmDialogFragment.ConfirmDialogListener{

	private Plongeur plongeur;
	private View rootView;
	private Palanquee palanquee;
	private boolean modifiable;
	
	@SuppressLint("InflateParams") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		rootView = getLayoutInflater().inflate(R.layout.activity_plongeur, null);
		setContentView(rootView);

		// Specify that tabs should be displayed in the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		plongeur = (Plongeur) intent
				.getParcelableExtra(IntentKey.PLONGEUR_COURANT.toString());
		palanquee = (Palanquee) intent
				.getParcelableExtra(IntentKey.PALANQUEE_COURANTE.toString());
		modifiable = (boolean) intent.getBooleanExtra(IntentKey.MODIFIABLE.toString(), true);
		
		// Initialisaton de la vue avec le plongeur
		((TextView) findViewById(R.id.textView_plongeur_nom_value))
				.setText(plongeur.getNom());
		
		((TextView) findViewById(R.id.textView_plongeur_prenom_value))
				.setText(plongeur.getPrenom());
		
		((TextView) findViewById(R.id.textView_plongeur_profondeur_realisee_value))
				.setText(plongeur.getProfondeurRealisee() != null && plongeur.getProfondeurRealisee() > 0.0 ? plongeur
						.getProfondeurRealisee() + " mètres" : "");
		
		if (plongeur.getDureeRealisee() > 0)
			((TextView) findViewById(R.id.textView_plongeur_duree_realisee_value))
					.setText(DateStringUtils.secondsToNiceString(plongeur
							.getDureeRealisee()));
		((TextView) findViewById(R.id.textView_plongeur_aptitudes_value))
				.setText(plongeur.getAptitudes().toString());
		((TextView) findViewById(R.id.textView_plongeur_date_naissance_value))
				.setText(plongeur.getDateNaissance());
		((TextView) findViewById(R.id.textView_plongeur_telephone_value))
				.setText(plongeur.getTelephone());
		((TextView) findViewById(R.id.textView_plongeur_telephone_urgence_value))
				.setText(plongeur.getTelephoneUrgence());
		
		//Gestion de l'affichage des icones de modification
		if(modifiable){
			// Ajout des onClick
			ajouterOnClickListener();
		} else {
			cacherOnClickBouton();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Affichage du menu uniquement sur les fiches de sécurité non validé
		if(modifiable)
			getMenuInflater().inflate(R.menu.plongeur, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.itemDelete:
			ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment(
					String.format(getResources().getString(R.string.plongeur_dialog_suppression), plongeur.getPrenom()+" "+plongeur.getNom()),
					ConfirmDialogFragment.SUPPRESSION_PLONGEUR);
			confirmDialogFragment.show(getSupportFragmentManager(), "ConfirmDialogFragment");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed(){
		Intent result = new Intent();
		palanquee.getPlongeurs().ajouterOuMajPlongeur(plongeur);
		result.putExtra(IntentKey.RESULT_PALANQUEE.toString(), palanquee);
		setResult(RESULT_OK, result);
		finish();
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, int confirmType) {
		if(confirmType == ConfirmDialogFragment.SUPPRESSION_PLONGEUR){
			Intent result = new Intent();
			palanquee.getPlongeurs().remove(plongeur);
			result.putExtra(IntentKey.RESULT_TEXT.toString(), String.format(getResources().getString(R.string.plongeur_dialog_suppression_confirm), plongeur.getPrenom()+" "+plongeur.getNom()));
			result.putExtra(IntentKey.RESULT_PALANQUEE.toString(), palanquee);
			setResult(RESULT_OK, result);
			finish();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog, int confirmType) {
		//L'utilisateur à annulé la suppresion, on ne fait rien
	}
	
	/**
	 * Cache les cliques listener lorsqu'on visualise un plongeur non modifiable (d'une fiche de sécurité validé)
	 */
	private void cacherOnClickBouton(){
		// Click pour changer le nom du plongeur
		findViewById(R.id.iB_plongeur_nom).setVisibility(View.GONE);
		// Click pour changer le prénom du plongeur
		findViewById(R.id.iB_plongeur_prenom).setVisibility(View.GONE);
		// Click pour changer le num de telephone du plongeur
		findViewById(R.id.iB_plongeur_telephone).setVisibility(View.GONE);
		// Click pour changer le numéro de téléphone d'urgence du plongeur
		findViewById(R.id.iB_plongeur_telephone_urgence).setVisibility(View.GONE);
		// Click pour changer la durée réalisée du plongeur
		findViewById(R.id.iB_plongeur_duree_realisee).setVisibility(View.GONE);
		// Click pour changer la profondeur réalisée du plongeur
		findViewById(R.id.iB_plongeur_profondeur_realisee).setVisibility(View.GONE);
		// Click pour changer la date de naissance du plongeur
		findViewById(R.id.iB_plongeur_date_naissance).setVisibility(View.GONE);
		// Click pour changer les aptitudes du plongeur
		findViewById(R.id.iB_plongeur_aptitudes).setVisibility(View.GONE);
	}
	
	/**
	 * Ajoute les cliques listener pour la modificaton du plongeur
	 */
	private void ajouterOnClickListener(){
		// Click pour changer le nom du plongeur
		findViewById(R.id.iB_plongeur_nom)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurTextDialogFragment ndf = new PlongeurTextDialogFragment(
						rootView, plongeur, EnumPlongeurText.NOM);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer le prénom du plongeur
		findViewById(R.id.iB_plongeur_prenom)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurTextDialogFragment ndf = new PlongeurTextDialogFragment(
						rootView, plongeur, EnumPlongeurText.PRENOM);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer le num de telephone du plongeur
		findViewById(R.id.iB_plongeur_telephone)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurTextDialogFragment ndf = new PlongeurTextDialogFragment(
						rootView, plongeur, EnumPlongeurText.TELEPHONE);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer le numéro de téléphone d'urgence du plongeur
		findViewById(R.id.iB_plongeur_telephone_urgence)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurTextDialogFragment ndf = new PlongeurTextDialogFragment(
						rootView, plongeur, EnumPlongeurText.TELEPHONE_URGENCE);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer la durée réalisée du plongeur
		findViewById(R.id.iB_plongeur_duree_realisee)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurDureeRealiseeDialogFragment ndf = new PlongeurDureeRealiseeDialogFragment(
						rootView, plongeur, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer la profondeur réalisée du plongeur
		findViewById(R.id.iB_plongeur_profondeur_realisee)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurProfondeurRealiseeDialogFragment ndf = new PlongeurProfondeurRealiseeDialogFragment(
						rootView, plongeur, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer la date de naissance du plongeur
		findViewById(R.id.iB_plongeur_date_naissance)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PlongeurDateNaissanceDialogFragment ndf = new PlongeurDateNaissanceDialogFragment(
						rootView, plongeur);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		// Click pour changer les aptitudes du plongeur
				findViewById(R.id.iB_plongeur_aptitudes)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PlongeurAptitudeDialogFragment ndf = new PlongeurAptitudeDialogFragment(
								rootView, plongeur, palanquee);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
	}
}