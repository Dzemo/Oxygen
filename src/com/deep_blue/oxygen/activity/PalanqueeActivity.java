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
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureePrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeDureeRealiseeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeHeureDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurPrevueDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeProfondeurRealiseeMoniteurDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeTypeGazDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PalanqueeTypePlongeDialogFragment;
import com.deep_blue.oxygen.activity.fragment.dialog.PlongeurPickerDialogFragment;
import com.deep_blue.oxygen.dao.PlongeurDao;
import com.deep_blue.oxygen.listener.PlongeurOnClickListener;
import com.deep_blue.oxygen.model.EnumEtat;
import com.deep_blue.oxygen.model.EnumTypeGaz;
import com.deep_blue.oxygen.model.EnumTypePlonge;
import com.deep_blue.oxygen.model.FicheSecurite;
import com.deep_blue.oxygen.model.Moniteur;
import com.deep_blue.oxygen.model.Palanquee;
import com.deep_blue.oxygen.model.Plongeur;
import com.deep_blue.oxygen.util.DateStringUtils;
import com.deep_blue.oxygen.util.IntentKey;

@SuppressLint("InflateParams") 
public class PalanqueeActivity extends FragmentActivity implements ConfirmDialogFragment.ConfirmDialogListener, PlongeurPickerDialogFragment.PlongeurPickerDialogListener {

	
	private FicheSecurite ficheSecurite;
	private Palanquee palanquee;
	private View rootView;
	private List<Plongeur> listePlongeursSuggeres = null;
	
	public PalanqueeActivity(){
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Affichage du bouton de retour à coté du logo
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		//Récupération de la palanquee et de l'utilisateur
		Intent intent = getIntent();
		palanquee = intent.getParcelableExtra(IntentKey.PALANQUEE_COURANTE.toString());
		ficheSecurite = intent.getParcelableExtra(IntentKey.FICHE_SECURITE_COURANTE.toString());
		
		//Inlfation du layout
		rootView = getLayoutInflater().inflate(R.layout.activity_palanquee_layout, null);
		setContentView(rootView);
		
		//Numéro de la palanquée dans le titre
		setTitle(getTitle()+" "+palanquee.getNumero());

		// Initialisation de la vue avec la palanquée séléctionné
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_gaz_value))
				.setText(palanquee.getTypeGaz() != EnumTypeGaz.NULL ? palanquee.getTypeGaz().toString() : "");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_plongee_value))
				.setText(palanquee.getTypePlonge() != EnumTypePlonge.NULL ? palanquee.getTypePlonge().toString() : "");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_profondeur_prevue_value))
				.setText(palanquee.getProfondeurPrevue() != 0F ? palanquee.getProfondeurPrevue().toString()
						+ " mètres" : "");
		if (palanquee.getProfondeurRealiseeMoniteur() != null  && palanquee.getProfondeurRealiseeMoniteur() > 0)
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_profondeur_realisee_moniteur_value))
					.setText(palanquee.getProfondeurRealiseeMoniteur()
							.toString() + " mètres");
		((TextView) rootView
				.findViewById(R.id.textView_palanquee_info_duree_prevue_value))
				.setText(DateStringUtils.minutesToNiceString(palanquee
						.getDureePrevue()));
		if (palanquee.getDureeRealiseeMoniteur() > 0)
			((TextView) rootView
					.findViewById(R.id.textView_palanquee_info_duree_realisee_moniteur_value))
					.setText(DateStringUtils.minutesToNiceString(palanquee
							.getDureeRealiseeMoniteur()));
		((TextView) rootView.findViewById(R.id.textView_palanquee_heure))
				.setText(palanquee.getHeure());
		//Moniteur si présent
		Moniteur moniteur = palanquee.getMoniteur();
		if (moniteur != null) {
			((TextView) rootView.findViewById(R.id.textView_palanquee_moniteur)).setText(moniteur != null ? moniteur.getPrenom() + " " + moniteur.getNom() : "");
		} 
		updateMoniteurVisibilite();
		
		
		if(ficheSecurite.getEtat() == EnumEtat.VALIDE){
			cacherBoutonModification();			
			
			//Alignement pour le titre
			TextView tvTitrePalanquee = ((TextView)findViewById(R.id.textView_palanquee_plongeur_title));
			TableRow.LayoutParams params = (LayoutParams) tvTitrePalanquee.getLayoutParams();
			params.span = 2;
			tvTitrePalanquee.setLayoutParams(params); // causes layout update
		} else{
			// onClick listener sur les images
			ajouterClickListener();
		}		

		//Affichage des plongeurs
		afficherPlongeurs();
		
		rootView.requestLayout();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		palanquee = (Palanquee) data.getExtras().get(IntentKey.RESULT_PALANQUEE.toString());
		
		String text = (String) data.getExtras().get(IntentKey.RESULT_TEXT.toString());
		if(text != null){
			Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
			toast.show();
		}
		
		afficherPlongeurs();
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Affichage du menu uniquement sur les fiches de sécurité non validé
		if(ficheSecurite.getEtat() != EnumEtat.VALIDE)
			getMenuInflater().inflate(R.menu.palanquee, menu);
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
					String.format(getResources().getString(R.string.palanquee_dialog_suppression), palanquee.getNumero().toString()), ConfirmDialogFragment.SUPPRESSION_PALANQUEE);
			confirmDialogFragment.show(getSupportFragmentManager(), "ConfirmDialogFragment");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onBackPressed(){
		Intent result = new Intent();
		if(palanquee != null && palanquee.isModifie()){
			ficheSecurite.getPalanquees().ajouterOuMajPalanquee(palanquee);
			ficheSecurite.setModifie(true);
		}
		result.putExtra(IntentKey.RESULT_FICHE_SECURITE.toString(), ficheSecurite);
		setResult(RESULT_OK, result);
		finish();
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, int confirmType) {
		if(confirmType == ConfirmDialogFragment.SUPPRESSION_PALANQUEE){
			Intent result = new Intent();
			ficheSecurite.getPalanquees().remove(palanquee);
			ficheSecurite.setModifie(true);
			result.putExtra(IntentKey.RESULT_TEXT.toString(), String.format(getResources().getString(R.string.palanquee_dialog_suppression_confirm), palanquee.getNumero()));
			result.putExtra(IntentKey.RESULT_FICHE_SECURITE.toString(), ficheSecurite);
			setResult(RESULT_OK, result);
			finish();
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog, int confirmType) {
		//L'utilisateur à choisi de ne pas supprimer cette palanquee, on ne fait rien alors
	}
	
	/**
	 * Cache les boutons permettend de modifier la palanquée
	 */
	private void cacherBoutonModification(){
		//Profondeur réalisée
		rootView.findViewById(R.id.iB_palanquee_profondeur_realisee_moniteur).setVisibility(View.GONE);
		//Profondeur prévue
		rootView.findViewById(R.id.iB_palanquee_profondeur_prevue).setVisibility(View.GONE);
		//Duree prévue
		rootView.findViewById(R.id.iB_palanquee_duree_prevue).setVisibility(View.GONE);
		//Duree réalisé
		rootView.findViewById(R.id.iB_palanquee_duree_realisee_moniteur).setVisibility(View.GONE);
		//Heure
		rootView.findViewById(R.id.iB_palanquee_heure).setVisibility(View.GONE);
		//Type gaz
		rootView.findViewById(R.id.iB_palanquee_gaz).setVisibility(View.GONE);
		//Type plonge
		rootView.findViewById(R.id.iB_palanquee_plonge).setVisibility(View.GONE);
		//Moniteur
		rootView.findViewById(R.id.iB_palanquee_moniteur).setVisibility(View.GONE);
		
		// Ajout du clique d'ajout de plongeur
		rootView.findViewById(R.id.iB_palanquee_ajout_plongeur).setVisibility(View.GONE);
	}
	
	/**
	 * Ajoute les onClickListener qui fait apparaitre le dialog fragment correspondant pour les informations de la palanquée
	 */
	private void ajouterClickListener(){
		//Profondeur réalisée
		rootView.findViewById(R.id.iB_palanquee_profondeur_realisee_moniteur)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						PalanqueeProfondeurRealiseeMoniteurDialogFragment ndf = new PalanqueeProfondeurRealiseeMoniteurDialogFragment(
								rootView, palanquee);
						ndf.show(getSupportFragmentManager(), "TAG");
					}
				});
		//Profondeur prévue
		rootView.findViewById(R.id.iB_palanquee_profondeur_prevue)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeProfondeurPrevueDialogFragment ndf = new PalanqueeProfondeurPrevueDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Duree prévue
		rootView.findViewById(R.id.iB_palanquee_duree_prevue)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeDureePrevueDialogFragment ndf = new PalanqueeDureePrevueDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Duree réalisé
		rootView.findViewById(R.id.iB_palanquee_duree_realisee_moniteur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeDureeRealiseeMoniteurDialogFragment ndf = new PalanqueeDureeRealiseeMoniteurDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Heure
		rootView.findViewById(R.id.iB_palanquee_heure)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeHeureDialogFragment ndf = new PalanqueeHeureDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Type gaz
		rootView.findViewById(R.id.iB_palanquee_gaz)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeTypeGazDialogFragment ndf = new PalanqueeTypeGazDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Type plonge
		rootView.findViewById(R.id.iB_palanquee_plonge)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeTypePlongeDialogFragment ndf = new PalanqueeTypePlongeDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		//Moniteur
		rootView.findViewById(R.id.iB_palanquee_moniteur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PalanqueeMoniteurDialogFragment ndf = new PalanqueeMoniteurDialogFragment(
						rootView, palanquee);
				ndf.show(getSupportFragmentManager(), "TAG");
			}
		});
		
		// Ajout du clique d'ajout de plongeur
		rootView.findViewById(R.id.iB_palanquee_ajout_plongeur)
		.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(listePlongeursSuggeres == null){
					int nombre = 15;
					try{
						nombre = Integer.valueOf(R.string.nombre_plongeur_suggerer);
					} catch(NumberFormatException e){}
					
					PlongeurDao plongeurDao = new PlongeurDao(getApplicationContext());
					listePlongeursSuggeres = plongeurDao.getLastX(nombre);
				}
				
				
				PlongeurPickerDialogFragment plongeurPickerDialogFragment = new PlongeurPickerDialogFragment(listePlongeursSuggeres);
				plongeurPickerDialogFragment.show(getSupportFragmentManager(), "TAG");
			}
		});
	}
	
	@Override
	public void onPlongeurSelection(Plongeur plongeurSelectionne) {
		if(plongeurSelectionne == null){
			//Ajout d'un nouveau plongeur
			Plongeur plongeur = new Plongeur();
			plongeur.setId(palanquee.getPlongeurs().getNextNegativeId());
			plongeur.setIdPalanquee(palanquee.getId());
			plongeur.setIdFicheSecurite(palanquee.getIdFicheSecurite());
			palanquee.getPlongeurs().add(plongeur);
		} else{
			//Initialisation du nouveau plongeur avec les infos du plongeur selectionner
			Plongeur plongeur = new Plongeur();
			plongeur.setId(palanquee.getPlongeurs().getNextNegativeId());
			plongeur.setIdPalanquee(palanquee.getId());
			plongeur.setIdFicheSecurite(palanquee.getIdFicheSecurite());
			plongeur.setNom(plongeurSelectionne.getNom());
			plongeur.setPrenom(plongeurSelectionne.getPrenom());
			plongeur.setDateNaissance(plongeurSelectionne.getDateNaissance());
			plongeur.setTelephone(plongeurSelectionne.getTelephone());
			plongeur.setTelephoneUrgence(plongeurSelectionne.getTelephoneUrgence());
			plongeur.setAptitudes(plongeurSelectionne.getAptitudes());
			palanquee.getPlongeurs().add(plongeur);
		}
		
		afficherPlongeurs();
	}
	
	/**
	 * Met à jours la visibilité du moniteur dans la palanquée en fonction du type de plongé
	 */
	public void updateMoniteurVisibilite(){
		if(palanquee.getTypePlonge() == EnumTypePlonge.AUTONOME){
			findViewById(R.id.palanquee_moniteur).setVisibility(View.GONE);
		} else {
			findViewById(R.id.palanquee_moniteur).setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Affiche les plongeurs de la palanquée dans la vue
	 */
	private void afficherPlongeurs(){
		// Ajout des plongeurs
		TableLayout tableLayout = (TableLayout) rootView
				.findViewById(R.id.activity_fiche_securite_fragment_palanquee_list_plongeurs);
		
		tableLayout.removeAllViews();
		
		//Affichage du titre en fonction du nombre de plongeurs
		((TextView) findViewById(R.id.textView_palanquee_plongeur_title)).setText(getResources().getQuantityText(R.plurals.palanquee_plongeur_title, palanquee.getPlongeurs().size()));
		
		int index = 0;
		int parite_background = palanquee.getMoniteur() != null ? 1 : 0;

		for (Plongeur plongeur : palanquee.getPlongeurs()) {

			TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.activity_palanquee_plongeur_layout, tableLayout, false);
			
			// Ajout du plongeur
			((TextView) row.findViewById(R.id.textView_palanquee_plongeur_label))
			.setText("Plongeur: " + plongeur.getPrenom() + " "
					+ plongeur.getNom());

			// Coloration selon la parité de la row
			if (parite_background % 2 == 0)
				row.setBackgroundResource(R.drawable.list_item_background_plongeur_1);
			else
				row.setBackgroundResource(R.drawable.list_item_background_plongeur_2);
			parite_background++;

			if (ficheSecurite.getEtat() == EnumEtat.VALIDE) {
				// Pour les fiches validée, on change l'icone des plongeurs
				((ImageButton)row.findViewById(R.id.iB_palanquee_plongeur)).setImageResource(android.R.drawable.ic_menu_info_details);
			}
			
			// Clik listener sur la row
			row.findViewById(R.id.iB_palanquee_plongeur).setOnClickListener(new PlongeurOnClickListener(
					plongeur, null, palanquee, ficheSecurite.getEtat() != EnumEtat.VALIDE, this));
			
			// Ajout de la row dans la table
			tableLayout.addView(row, index, row.getLayoutParams());
			index++;
		}
	}
}